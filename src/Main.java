import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
}
    private Main()
    {
        //SETTING UP MAIN MENU SCREEN
        JFrame mainWindow = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(screenSize.width/2, screenSize.height/2);
        mainWindow.setUndecorated(true);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);

        //ADDING BACKGROUND
        JLabel mainPanel = new JLabel(Tools.resizeImage("resources/Game Graphics/MenuBackground.jpg", screenSize.width/2, screenSize.height/2));
        mainWindow.add(mainPanel);

        //SETTING UP LAYOUT'S DETAILS
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        //ADDING MUSIC AND SOUNDS PLAYER
        Tools tools = new Tools();
        tools.setClip("resources/Music/MAIN_THEME.wav");
        tools.getCurrentClip().setMicrosecondPosition(0);
        tools.getCurrentClip().start();
        Tools soundEffects = new Tools();
        soundEffects.setClip("resources/Music/SoundEffects/BUTTON_PRESSED.wav");

        //ADDING ELEMENTS TO MAIN MENU

            //SETTING MAIN MENU COMPONENTS' SIZE
            Dimension scaledElementDimension = new Dimension(screenSize.width/7, screenSize.height/18);

            //LOGO LABEL
            JLabel logoLabel = new JLabel();
            logoLabel.setIcon(Tools.resizeImage("resources/Game Graphics/GameLogo.png", screenSize.width/7, screenSize.height/15));
            mainPanel.add(logoLabel, gridBagConstraints);

            //NEW GAME BUTTON
            JButton newGameButton = new JButton("NEW GAME");
            newGameButton.setPreferredSize(scaledElementDimension);
            newGameButton.setContentAreaFilled(false);
            mainPanel.add(newGameButton, gridBagConstraints);
            newGameButton.addActionListener(e -> {
                if (!GameboardFrame.isGameActive)
                {
                    soundEffects.getCurrentClip().setMicrosecondPosition(0);
                    soundEffects.getCurrentClip().start();
                    BoardSizeFrame boardSizeFrame = new BoardSizeFrame(tools.getCurrentClip());
                    boardSizeFrame.setSize(screenSize.width/5, screenSize.height/6);
                    boardSizeFrame.setLocationRelativeTo(null);
                }
            });

            //HIGH SCORES BUTTON
            JButton highScoresButton = new JButton("HIGH SCORES");
            highScoresButton.setPreferredSize(scaledElementDimension);
            highScoresButton.setContentAreaFilled(false);
            mainPanel.add(highScoresButton, gridBagConstraints);
            highScoresButton.addActionListener(e -> {
                soundEffects.getCurrentClip().setMicrosecondPosition(0);
                soundEffects.getCurrentClip().start();

                if (!GameboardFrame.isGameActive)
                {
                    HighscoresList highscoresList = new HighscoresList();
                    highscoresList.setSize(screenSize.width/4, screenSize.height/4);
                    highscoresList.setVisible(true);
                    highscoresList.setLocationRelativeTo(null);
                }
            });

            //EXIT BUTTON
            JButton exitButton = new JButton("EXIT");
            exitButton.setPreferredSize(scaledElementDimension);
            exitButton.setContentAreaFilled(false);
            mainPanel.add(exitButton, gridBagConstraints);
            exitButton.addActionListener(e -> {
                soundEffects.getCurrentClip().setMicrosecondPosition(0);
                soundEffects.getCurrentClip().start();
                System.exit(0);
            });
    }
}
