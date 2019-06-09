import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class AddToHighScoresFrame extends JFrame {

    AddToHighScoresFrame (int size, String timer, int time)
    {

        JTextField nameField = new JTextField("INSERT YOUR NAME HERE");
        JLabel runLabel = new JLabel();
        this.setLayout(new FlowLayout());

        JButton submitButton = new JButton("ADD TO HIGH SCORES");
        submitButton.setContentAreaFilled(false);
        submitButton.addActionListener(e -> {
                if (!nameField.getText().equals(""))
                {
                    ArrayList<HighScore> deserializedHighscores = null;
                    if (new File ("resources/highscores.bin").length() != 0) {
                        deserializedHighscores = deserialize("resources/highscores.bin");
                    }
                    else
                        deserializedHighscores = new ArrayList<HighScore>();

                    deserializedHighscores.add(new HighScore(nameField.getText(), size, time));
                    Collections.sort(deserializedHighscores);
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("resources/highscores.bin")))
                    {
                        try {
                            objectOutputStream.writeObject(deserializedHighscores);
                            objectOutputStream.flush();
                            objectOutputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    dispose();
                }
        });

        this.setTitle("Congratulations!");

        runLabel.setText("Grid Size: " + size + "x" + size + ", Time: " + timer);

        this.setUndecorated(true);
        this.setVisible(true);

        this.add(nameField);
        this.add(runLabel);
        this.add(submitButton);
    }

    static ArrayList <HighScore> deserialize (String path)
    {
        ArrayList<HighScore> deserializedHighscores = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
            try {
                deserializedHighscores = (ArrayList<HighScore>) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deserializedHighscores;
    }
}
