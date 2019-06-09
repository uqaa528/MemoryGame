import java.util.ArrayList;

public class HighScore implements java.io.Serializable, Comparable <HighScore>
{


    private static final long serialVersionUID = -3484553307280061515L;
    private String name;
    private int gridSize;
    private int time;

    static ArrayList<HighScore> highScoreArrayList = new ArrayList<>();


    HighScore(String name, int gridSize, int time)
    {
        this.name = name;
        this.gridSize = gridSize;
        this.time = time;
    }


    public String getName() {
        return name;
    }
    private int getGridSize() {
        return gridSize;
    }

    private int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Grid: " + gridSize + " x " + gridSize + ", Time: "+ (((time / 600) % 60 < 10) ? "0":"") + (time / 600) % 60 + ":" +
                (((time / 10) % 60 < 10) ? "0":"") + (time / 10) % 60 + ":" + time % 10 + "0";
    }

    @Override
    public int compareTo(HighScore o) {
        return (this.getTime()/this.getGridSize()) - (o.getTime()/o.getGridSize());
    }
}
