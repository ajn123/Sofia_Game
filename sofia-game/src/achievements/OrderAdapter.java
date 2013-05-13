package achievements;

import sofia.game.R;
import android.content.res.Resources;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * The Order Adapter orders the list view according to the order of the
 * achievement board.
 *
 * @author Aj Norton
 * @version Mar 8, 2013
 */
public class OrderAdapter
    extends ArrayAdapter<Achievement_Item>
{

    /**
     * The inflater that takes over the screen to show the leaderboard.
     */
    LayoutInflater inflater;
    /**
     * holds the view for the LeaderBoard Screen.
     */
    ViewHolder     viewHolder;
    /**
     * context of the application.
     */
    Context        globalContext;
    private String PACKAGE_NAME;


    // ----------------------------------------------------------
    /**
     * Create a new OrderAdapter object.
     *
     * @param context
     *            you need
     * @param textViewResourceId
     *            the ID for the text view
     * @param item
     *            array list to populate the view.
     * @param PackageName
     */
    public OrderAdapter(
        Context context,
        int textViewResourceId,
        ArrayList<Achievement_Item> item,
        String PackageName)
    {

        // let android do the initializing :)
        super(context, textViewResourceId, item);
        this.PACKAGE_NAME = PackageName;
        this.globalContext = context;

    }


    // class for caching the views in a row
    private class ViewHolder
    {
        ImageView photo;
        CheckBox  cb;
        TextView  name, team;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            // inflate the custom layout
            inflater =
                (LayoutInflater)getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();

            // cache the views
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.icon);
            viewHolder.name = (TextView)convertView.findViewById(R.id.toptext);
            viewHolder.team =
                (TextView)convertView.findViewById(R.id.bottomtext);
            viewHolder.cb =
                (CheckBox)convertView.findViewById(R.id.CheckBoxRow);

            // link the views to the convertview
            convertView.setTag(viewHolder);

        }

        if (convertView != null)
        {
            viewHolder = (ViewHolder)convertView.getTag();

            int drawableId = 0;

            try
            {

                Resources r = globalContext.getResources();

                r.getIdentifier(
                    "achievement_"
                        + Achievement.getInstance().AchievementBoard()
                            .get(position).getGoal().replace(' ', '_'),
                    "drawable",
                    PACKAGE_NAME);

                Class res = R.drawable.class;
                Field field =
                    res.getField("achievement_"
                        + Achievement.getInstance().AchievementBoard()
                            .get(position).getGoal().replace(' ', '_'));
                drawableId = field.getInt(null);
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

            // set the data to be displayed
            viewHolder.photo.setBackgroundResource(drawableId);
            viewHolder.name.setText(Achievement.getInstance()
                .AchievementBoard().get(position).getGoal());
            viewHolder.team.setText(Achievement.getInstance()
                .AchievementBoard().get(position).getExplanation());
            viewHolder.cb.setClickable(false);
            viewHolder.cb.setChecked(Achievement.getInstance()
                .AchievementBoard().get(position).isComplete());

        }
        return convertView;
    }

}
