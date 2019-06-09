import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TimerTask;


class GameboardFrame extends JFrame {
        private JButton selection;
        private boolean isActive;
        static boolean isGameActive;
        private JLabel messagePanel;
        private GameTimerLabel mainGameTimerLabel;

        GameboardFrame(int BOARD_HEIGHT, int BOARD_WIDTH)
        {
            //SOUNDS AND GAME MUSIC
            Tools battleMusic = new Tools();
            Tools mainMusic = new Tools();
            Tools soundEffects = new Tools();
            Tools matched = new Tools();
            Tools gameWon = new Tools();
            soundEffects.setClip("resources/Music/SoundEffects/BUTTON_PRESSED.wav");

                //LISTING AUDIO FILES; NEEDED FOR RANDOM SELECTION
                File newGameSounds = new File("resources/Music/SoundEffects/NewGame");
                File[] newGameSoundsArray = newGameSounds.listFiles();
                File battleMusicSounds = new File("resources/Music/GameMusic");
                File[] battleMusicArray = battleMusicSounds.listFiles();
                File revealSounds = new File("resources/Music/SoundEffects/Reveal");
                File[] revealSoundsArray = revealSounds.listFiles();

                Random randomTrack = new Random();

                //PLAYING INTRO SOUND AT THE GAME'S BEGINNING
            if (newGameSoundsArray != null) {
                battleMusic.playSound(newGameSoundsArray[randomTrack.nextInt(newGameSoundsArray.length)].getPath());
            }

            //ADDING LISTENER THAT PLAYS MUSIC AFTER INTRO SOUND FINISHES PLAYING
                battleMusic.getCurrentClip().addLineListener(event -> {
                    if (!isGameActive)
                        return;
                    if (battleMusicArray != null) {
                        mainMusic.playSound(battleMusicArray[randomTrack.nextInt(battleMusicArray.length)].getPath());
                    }
                    mainMusic.getCurrentClip().loop(Clip.LOOP_CONTINUOUSLY);
                });

            //CREATING MEMORY CARDS

                //LOADING FACE-DOWN PICTURES
                ArrayList<JButton> buttonList = new ArrayList<>();
                File folder = new File("resources/Memory Pictures");
                File[] listOfFiles = folder.listFiles();
                ArrayList<String> picturesPathlist = new ArrayList<>();
                if (listOfFiles != null) {
                    for (File listOfFile : listOfFiles) {
                        if (listOfFile.isFile())
                            picturesPathlist.add(listOfFile.getAbsolutePath());
                    }
                }
                else
                {
                    System.out.println("Cannot load 'Memory Pictures'!");
                    System.exit(-1);
                }

                //RANDOMIZING PICTURES
                Collections.shuffle(picturesPathlist);

                for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {

                //SETTING CARD'S ATTRIBUTES

                //IMAGES
                CardButton jButton = new CardButton(Tools.resizeImage(picturesPathlist.get(i/2+1), BoardSizeFrame.scaledSize, BoardSizeFrame.scaledSize));
                jButton.setCardback((Tools.resizeImage("resources/Game Graphics/Cardback.png",BoardSizeFrame.scaledSize, BoardSizeFrame.scaledSize)));
                jButton.setIcon(jButton.getCardback());

                //SIZE
                jButton.setPreferredSize(new Dimension (BoardSizeFrame.scaledSize, BoardSizeFrame.scaledSize));
                jButton.setBorder(BorderFactory.createEmptyBorder());

                //OTHER ATTRIBUTES
                jButton.setContentAreaFilled(false);
                jButton.setFocusable(false);
                jButton.putClientProperty("IDENTIFIER", (i/2+1));
                jButton.putClientProperty("UNIQUE_ID", i);

                //ADDING ACTION LISTENER
                jButton.addActionListener(cardListener -> {
                    if (!isActive)
                        return;

                    //REVEALING THE CARD

                    soundEffects.getCurrentClip().setMicrosecondPosition(0);
                    soundEffects.getCurrentClip().start();
                    jButton.setIcon(jButton.getCardImage());

                    if (selection == null)
                    {
                        selection = jButton;
                    }
                    else if (selection.getClientProperty("UNIQUE_ID") != jButton.getClientProperty("UNIQUE_ID"))
                    {
                        //CARDS MATCHING
                        if (selection.getClientProperty("IDENTIFIER") == jButton.getClientProperty("IDENTIFIER"))
                        {
                            if (revealSoundsArray != null) {
                                matched.playSound(revealSoundsArray[randomTrack.nextInt(revealSoundsArray.length)].getPath());
                            }
                            messagePanel.setText("Correct!");
                            selection.setEnabled(false);
                            jButton.setEnabled(false);
                            selection = null;
                            int winCounter = 0;
                            for (JButton button : buttonList) {
                                if (!button.isEnabled())
                                    winCounter++;
                            }
                            if (winCounter == buttonList.size()) {
                                try {
                                    mainMusic.getCurrentClip().stop();
                                }
                                catch (NullPointerException ex)
                                {
                                    System.out.println("Cannot find audio file.");
                                }
                                AddToHighScoresFrame addToHighScoresFrame = new AddToHighScoresFrame(BOARD_HEIGHT, mainGameTimerLabel.getTimerDisplay(), mainGameTimerLabel.getTenth());
                                addToHighScoresFrame.setSize(BoardSizeFrame.scaledSize * 3, BoardSizeFrame.scaledSize);
                                addToHighScoresFrame.setLocationRelativeTo(null);
                                mainGameTimerLabel.stopTimer();
                                gameWon.playSound("resources/Music/SoundEffects/WIN_BATTLE.wav");
                                dispose();
                            }
                        }
                        //CARDS NOT MATCHING
                        else
                        {
                            java.util.Timer timer = new java.util.Timer(true);
                            isActive = false;
                            messagePanel.setText("Incorrect!");
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                    isActive = true;
                                    selection.setIcon(jButton.getCardback());
                                    jButton.setIcon(jButton.getCardback());
                                    messagePanel.setText("");
                                    selection = null;
                                }
                            };
                            timer.schedule(timerTask, 500);
                        }
                    }
                });
                buttonList.add(jButton);
                }

                //RANDOMIZING BUTTONS
                Collections.shuffle(buttonList);

            //GAMEBOARD'S DETAILS
            this.isActive = true;
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setUndecorated(true);
            this.setVisible(true);
            this.setResizable(false);
            this.setFocusable(true);
            GridLayout gridLayout = new GridLayout(BOARD_HEIGHT + 1, BOARD_WIDTH);
            this.setLayout(gridLayout);

            //ADDING SHORTCUT FOR CLOSING THE WINDOW
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_Q && e.isControlDown() && e.isShiftDown()) {
                        dispose();
                    }
                }
            });

            //SWITCHING 'isGameActive' TO FALSE AND STOPPING MUSIC AFTER WINDOW BEING CLOSED
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    battleMusic.getCurrentClip().stop();
                    try
                    {
                        mainMusic.getCurrentClip().stop();
                    }
                    catch (NullPointerException ex)
                    {
                        System.out.println("Clip already stopped!");
                    }
                    super.windowClosing(e);
                    isGameActive = false;
                }
            });

            //ADDING MESSAGE PANEL
            messagePanel = new JLabel("<html><div style='text-align: center;'>Heroes III<br/>Memory Game</div></html>", SwingConstants.CENTER);
            messagePanel.setFont(new Font ("Times New Roman", Font.BOLD, 28 - (2 * BOARD_HEIGHT)));
            getContentPane().add(messagePanel);
            for (int i = 0; i < BOARD_WIDTH - 2; i++) {
                this.add(new JPanel());
            }

            //ADDING TIMER
            mainGameTimerLabel = new GameTimerLabel();
            mainGameTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainGameTimerLabel.setFont(new Font ("Times New Roman", Font.BOLD, 28 - (2 * BOARD_HEIGHT)));
            getContentPane().add(mainGameTimerLabel);

            //ADDING BUTTONS TO GAMEBOARD
            for (JButton jButton : buttonList) {
                this.add(jButton);
            }
        }
}

