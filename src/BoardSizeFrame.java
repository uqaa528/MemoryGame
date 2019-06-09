import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

class BoardSizeFrame extends JFrame {

    static int scaledSize;

    BoardSizeFrame(Clip mainMenuSound)
    {
        //GETTING SCREEN SIZE
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Tools soundEffects = new Tools();
        soundEffects.setClip("resources/Music/SoundEffects/BUTTON_PRESSED.wav");

        //CREATING SLIDER
        JSlider boardSizeSlider = new JSlider(JSlider.HORIZONTAL,4, 10, 4);
        boardSizeSlider.setMajorTickSpacing(2);
        boardSizeSlider.setSnapToTicks(true);
        boardSizeSlider.setPaintTicks(true);

        //LABEL WITH CURRENT SLIDER VALUE
        JLabel currentValueLabel = new JLabel(boardSizeSlider.getValue() + "",SwingConstants.CENTER);
        this.setTitle("SET BOARD SIZE");
        this.setVisible(true);
        this.setLayout(new FlowLayout());

        //ADDING SLIDER'S LISTENER
        boardSizeSlider.addChangeListener(e -> currentValueLabel.setText("" + boardSizeSlider.getValue()));

        //START GAME BUTTON
        JButton startGameButton = new JButton("START GAME");
        startGameButton.setContentAreaFilled(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //ADDING 'START GAME' BUTTON'S LISTENER
        startGameButton.addActionListener(e -> {

            //PLAYING 'ButtonPressed' SOUND
            soundEffects.getCurrentClip().setMicrosecondPosition(0);
            soundEffects.getCurrentClip().start();

            //SETTING UP VARIABLE USED FOR SCALING ITEM'S
            scaledSize = (int) ((screenSize.height / (boardSizeSlider.getValue() + 1)) / 1.5);

            //CREATING GAMEBOARD
            GameboardFrame gameboardFrame = new GameboardFrame(boardSizeSlider.getValue(), boardSizeSlider.getValue());
            gameboardFrame.setVisible(true);
            gameboardFrame.setSize((scaledSize * boardSizeSlider.getValue()), (scaledSize * (boardSizeSlider.getValue() + 1)));
            gameboardFrame.setLocationRelativeTo(null);
            GameboardFrame.isGameActive = true;
            mainMenuSound.stop();
            mainMenuSound.close();
            dispose();

        });

        //ADDING COMPONENTS
        this.add(boardSizeSlider);
        this.add(currentValueLabel);
        this.add(startGameButton);
    }
}
