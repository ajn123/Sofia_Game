package leaderboard;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * The Leader Board writes to the external memory of the SD card. The user must
 * have the "WRITE_EXTERNAL_STORAGE" privilege to be able to save and load
 * scores. The BoardItem objects toString method is added to a file while is
 * parsed through later to load the high scores.
 *
 * @author Aj Norton
 * @version Feb 5, 2013
 */
public class LeaderBoard
    extends Screen
{
    private ArrayList<String>    scoreBoard;
    private static LeaderBoard   board;
    private Context              context;
    public String                path;
    private File                 file;
    private final String         FINALPATH = "/leaderboard.txt";
    private ArrayList<BoardItem> ObjectItems;
    int                          size;


    // ----------------------------------------------------------
    /**
     * Create a new LeaderBoard object.
     */
    private LeaderBoard(Context context)
    {
        // the default size is set to 20.
        this.size = 20;

        Environment.getExternalStorageState();
        this.path = Environment.getExternalStorageDirectory().getAbsolutePath();

        this.file = new File(this.path + FINALPATH);

        this.scoreBoard = new ArrayList<String>();
        this.ObjectItems = new ArrayList<BoardItem>();

        this.context = context.getApplicationContext();

    }


    /**
     * Used Singleton constructor to allow to the user to only initialize one
     * LeaderBoard object.
     *
     * @param context
     *            that you need.
     * @return the only available leader board object.
     */
    public static LeaderBoard getLeaderBoard(Context context)//
    {
        if (board == null)
        {
            board = new LeaderBoard(context);//
        }

        return board;
    }


    // ----------------------------------------------------------
    /**
     * Loads the scores in the leaderboard text file.
     */
    public void loadScores()
    {

        try
        {
            Scanner s =
                new Scanner(new BufferedReader(new FileReader(this.path
                    + FINALPATH)));
            s = s.useDelimiter(":");
            Scanner StringScanner;
            while (s.hasNext())
            {

                StringScanner = new Scanner(s.next());
                StringScanner = StringScanner.useDelimiter(",");
                int score = Integer.parseInt(StringScanner.next());
                String title = StringScanner.next();
                LeaderBoard.getLeaderBoard(this).newScore(score, title);

                StringScanner.close();

            }
            s.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("loading score is not working");
        }

        Iterator<BoardItem> iterator = this.ObjectItems.iterator();

        int numberOfScores = 0;
        while (iterator.hasNext() && numberOfScores < this.size)
        {

            LeaderBoard.getLeaderBoard(this).leaderBoard()
                .add(iterator.next().toString());
            numberOfScores++;

        }

    }


    /**
     * Saves the score of the player by creating a new Board Item object and
     * writing its toString to a file.\
     *
     * @param score
     *            of the player
     * @param name
     *            of the player.
     */
    public void saveScores(int score, String name)
    {

        BoardItem a = new BoardItem(score, name);

        Writer writer = null;

        try
        {
            writer =
                new BufferedWriter(new FileWriter(this.path + FINALPATH, true));
            writer.write(a.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("not working on save");
        }
        finally
        {
            if (writer != null)
                try
                {
                    writer.close();
                }
                catch (IOException ignore)
                {
                    System.out.println("file can not be found.");

                }
        }

    }


    // ----------------------------------------------------------
    /**
     * Adds a new score to the array list in the right position.
     *
     * @param score
     *            score of the user
     * @param userName
     *            of the player.
     */
    public void newScore(int score, String userName)
    {

        BoardItem highScore = new BoardItem(score, userName);
        if (this.ObjectItems.size() == 0)
        {
            this.ObjectItems.add(highScore);
        }
        else if (this.ObjectItems.get(this.ObjectItems.size() - 1)
            .getPosition() < highScore.getPosition())
        {
            this.sortScore(highScore);
        }
        else
        {
            this.ObjectItems.add(highScore);
        }

    }


    // ----------------------------------------------------------
    /**
     * Adds the newest item to the array list so it is in order.
     *
     * @param score
     *            the player achieved.
     */
    public void sortScore(BoardItem score)
    {
        int i = 0;
        int sizeOfItems = this.ObjectItems.size();
        while (i < sizeOfItems)
        {
            if (score.getPosition() > this.ObjectItems.get(i).getPosition())
            {
                this.ObjectItems.add(i, score);
                break;
            }
            i++;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the scoreboard.
     *
     * @return the array list containing the players info.
     */
    public ArrayList<String> leaderBoard()
    {
        return this.scoreBoard;
    }


    /**
     * Deletes the file and allows the user to start a new list of high scores.
     */
    public void delete()
    {
        this.file.delete();
    }


    // ----------------------------------------------------------
    /**
     * Set the size of the leaderboard.
     *
     * @param size
     */
    public void setSize(int size)
    {
        this.size = size;
    }

}
