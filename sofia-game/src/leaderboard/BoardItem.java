package leaderboard;

// -------------------------------------------------------------------------
/**
 * The board Item object holds the title (name) and position (score of each user
 * that wants to store his/her name on the high score board.
 *
 * @author Aj Norton
 * @version Feb 5, 2013
 */
public class BoardItem

{
    private String title;
    private int    score;


    // ----------------------------------------------------------
    /**
     * Create a new BoardItem object.
     *
     * @param score
     * @param title
     */
    public BoardItem(int score, String title)
    {
        this.title = title;
        this.score = score;
    }


    // ----------------------------------------------------------
    /**
     * @return the tile
     */
    public String getTitle()
    {
        return this.title;
    }


    // ----------------------------------------------------------
    /**
     * @return the position.
     */
    public int getPosition()
    {
        return this.score;
    }


    // ----------------------------------------------------------
    /**
     * @param title
     *            you want your high score to be under.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }


    // ----------------------------------------------------------
    /**
     * @param Position
     *            you want your score to be.
     */
    public void setPosition(int position)
    {
        this.score = position;
    }


    @Override
    public String toString()
    {
        return this.score + ",       " + this.title + ":";
    }


}
