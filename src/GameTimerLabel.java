import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

class GameTimerLabel extends JLabel {
    private int tenth = 0;
    private Timer gameTimer;
    private String timerDisplay;

    GameTimerLabel()
    {
        this.gameTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                tenth++;
                setTimerDisplay((((tenth / 600) % 60 < 10) ? "0":"") + (tenth / 600) % 60 + ":" +
                        (((tenth / 10) % 60 < 10) ? "0":"") + (tenth / 10) % 60 + ":" + tenth % 10 + "0");
                setText(getTimerDisplay());
            }
        };
        gameTimer.scheduleAtFixedRate(timerTask, 0,100);
    }

    void stopTimer()
    {
        gameTimer.cancel();
        gameTimer.purge();
    }

    private void setTimerDisplay(String timerDisplay) {
        this.timerDisplay = timerDisplay;
    }

    String getTimerDisplay() {
        return timerDisplay;
    }

    int getTenth() {
        return tenth;
    }
}
