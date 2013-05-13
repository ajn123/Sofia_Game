package achievements;

// -------------------------------------------------------------------------
/**
 * This class contains the variables for what a achievement can hold. An
 * Achievement can hold a goal and explanation for the achievement toast. Also,
 * a caption,decription, and link can be inputted in the text file for a
 * facebook status update.
 *
 * @author Aj Norton
 * @version Feb 27, 2013
 */
public class Achievement_Item
{
    private String  goal;
    private String  explanation;
    private String  postName;
    private String  caption;
    private String  description;
    private String  link;
    private boolean complete;
    private boolean FaceBookUpdate;


    // ----------------------------------------------------------
    /**
     * Create a new Achievement_Item object.
     *
     * @param AchievementGoal
     *            the achievement to be accomplished.
     * @param explanation
     *            the explanation of the achievement.
     */
    public Achievement_Item(String AchievementGoal, String explanation)
    {
        this.goal = AchievementGoal;
        this.explanation = explanation;
        this.complete = false;

    }


    // ----------------------------------------------------------
    /**
     * Create a new Achievement_Item object.
     *
     * @param AchievementGoal
     * @param explanation
     * @param postName
     * @param caption
     * @param description
     * @param link
     * @param faceBookUpdate
     */
    public Achievement_Item(
        String AchievementGoal,
        String explanation,
        String postName,
        String caption,
        String description,
        String link,
        boolean faceBookUpdate)
    {
        this.goal = AchievementGoal;
        this.explanation = explanation;
        this.postName = postName;
        this.caption = caption;
        this.description = description;
        this.link = link;
        this.complete = false;
        this.FaceBookUpdate = faceBookUpdate;
    }


    public String toString()
    {
        return this.goal + ": " + this.explanation;
    }


    // ----------------------------------------------------------
    /**
     * Returns the goal.
     *
     * @return string of the goal.
     */
    public String getGoal()
    {
        return this.goal;
    }


    // ----------------------------------------------------------
    /**
     * Returns the explanation of the .
     *
     * @return the explanation.
     */
    public String getExplanation()
    {
        return this.explanation;
    }


    // ----------------------------------------------------------
    /**
     * Checks if the achievement is complete or not.
     *
     * @return true if teh achievement is complete, false if not.
     */
    public boolean isComplete()
    {
        return this.complete;
    }


    // ----------------------------------------------------------
    /**
     * Sets the Achievement to the indicated value.
     *
     * @param value
     *            of your achievement.
     */
    public void setComplete(boolean value)
    {
        this.complete = value;
    }


    // ----------------------------------------------------------
    /**
     * Return the post.
     *
     * @return the post name.
     */
    public String getPostName()
    {
        return this.postName;
    }


    // ----------------------------------------------------------
    /**
     * Returns the caption.
     *
     * @return the caption.
     */
    public String getCaption()
    {
        return this.caption;
    }


    // ----------------------------------------------------------
    /**
     * Returns the description of the link.
     *
     * @return the description of the link.
     */
    public String getDescription()
    {
        return this.description;
    }


    // ----------------------------------------------------------
    /**
     * Link of your status if desired.
     *
     * @return the link the user wants to take you too.
     */
    public String getLink()
    {
        return this.link;
    }


    // ----------------------------------------------------------
    /**
     * Returns true if the user wants to update their facebook,false otherwise.
     *
     * @return if the user wants to update their facebook status.
     */
    public boolean getFacebookOK()
    {
        return this.FaceBookUpdate;
    }

}
