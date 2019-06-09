import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoresList extends JFrame {

    HighscoresList ()
    {
        //ADDING SCROLL AND JLIST
        JScrollPane jScrollPane = new JScrollPane();
        DefaultListModel<HighScore> model = new DefaultListModel<>();
        JList <HighScore> highScoreJList = new JList<>(model);
        jScrollPane.setViewportView(highScoreJList);

        highScoreJList.setLayoutOrientation(JList.VERTICAL);

        //ADDING ELEMENTS TO HIGHSCORES LIST
        ArrayList <HighScore> highScores = AddToHighScoresFrame.deserialize("resources/highscores.bin");
        Collections.sort(highScores);
        for (int i = 0; i < highScores.size(); i++) {
            model.addElement(highScores.get(i));
        }
        add(jScrollPane);
    }
}
