package achievements;

import sofia.game.R;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

// -------------------------------------------------------------------------
/**
 * Screen that acts as a list activity to populate a custom view defined in
 * rows.xml.
 *
 * @author Aj Norton
 * @version Mar 8, 2013
 */
public class AchievementScreen
    extends ListActivity
{
    /**
     * Allows for a custom view to be inflated on the screen.
     */
    private LayoutInflater inflater;
    private String         PACKAGE_NAME;


    /**
     * Creates the rows that will serve as a way to store and show the
     * achievements.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        inflater =
            (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        OrderAdapter adapter =
            new OrderAdapter(getApplicationContext(), R.layout.row, Achievement
                .getInstance().AchievementBoard(), PACKAGE_NAME);

        setListAdapter(adapter);
    }

// ----------------------------------------------------------
}
