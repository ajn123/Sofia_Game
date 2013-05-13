package leaderboard;

import android.view.View;
import android.widget.ArrayAdapter;
import sofia.app.Screen;
import sofia.widget.ListView;

// -------------------------------------------------------------------------
/**
 * The leader board class is a class the user can extend to easily implement a
 * list view that acts as a high score leader board. If the user creates their
 * own list view, its I.D. must be named "scoreBoard" or else the list view will
 * take up the whole screen.
 *
 * @author Aj Norton
 * @version Sep 18, 2012
 */
public abstract class LeaderBoardScreen
    extends Screen
{
    private ListView             view;
    private ArrayAdapter<String> arrayAdapter;
    private boolean              hasListView;


    /**
     * Checks to see if the xml file has a listView named "scoreBoard" if it
     * does, the array adapter connects with it. If it is not it creates a
     * listview that tanks up an otherwise blank screen.
     */
    protected void afterLayoutInflated(boolean inflated)
    {

        if (inflated)
        {
            hasListView = false;

            int listViewId =
                getResources().getIdentifier(
                    "scoreBoard",
                    "id",
                    getPackageName());

            if (listViewId != 0)
            {
                View viewing = findViewById(listViewId);

                if (viewing instanceof ListView)
                {

                    this.view = (ListView)findViewById(listViewId);
                    hasListView = true;

                }
            }
            if (!hasListView)
            {
                throw new IllegalStateException("A LeaderBoard that uses a "
                    + "custom layout must contain an "
                    + "android.graphics.ShapeView with the ID "
                    + "\"scoreBoard\".");
            }

        }
        else
        {
            this.view = new ListView(this);
            setContentView(this.view);
            this.view.requestFocus();

        }


        this.arrayAdapter =
            new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                LeaderBoard.getLeaderBoard(this).leaderBoard());

        view.setAdapter(arrayAdapter);

        /**
         * Loads the scores saved on the text file so that the user only has to
         * save them.
         */
        LeaderBoard.getLeaderBoard(this).loadScores();

    }


    @Override
    public boolean doInitializeAfterLayout()
    {
        return true;
    }

    // ----------------------------------------------------------
}
