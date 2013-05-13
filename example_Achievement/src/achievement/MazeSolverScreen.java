package achievement;

import achievements.Achievement;
import achievements.AchievementScreen;
import leaderboard.LeaderBoard;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 *  Example application shows leaderboard and achievements.
 *
 *  @author Aj Norton
 *  @version May 13, 2013
 */
public class MazeSolverScreen
    extends Screen
{

    public void initialize()
    {

        /**
         * Loads the achievement file you have inputted, could be named
         * anything.
         */
        Achievement.getInstance().loadAchievements("achievement", this);

        /**
         * Loads a score to the leaderboard.
         */
        LeaderBoard.getLeaderBoard(this).saveScores(77, "AJ Norton");

    }


    public void achievementToastClicked()
    {

        /*
         * Demonstrate achievement unlocked
         */
        Achievement.getInstance().AchievementUnlocked("pig king", this);
    }


    public void screenClicked()
    {
        /**
         * Demonstrated leaderboard screen.
         */
        presentScreen(GameScreen.class);
    }

    // ----------------------------------------------------------
    /**
     * Displays the achievement Screen display.
     */
    public void achscreenClicked()
    {
        presentScreen(AchievementExample.class);
    }
}
