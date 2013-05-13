package achievements;

import sofia.game.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * Achievement class that allows for capabilities of posting to facebook as well
 * as a enabling a special toast when an achievement is unlocked.
 *
 * @author Aj Norton
 * @version Feb 27, 2013
 */
public class Achievement
    extends Screen
    implements ListenerShareFacebook
{
    private static Achievement          achievement;
    private ArrayList<Achievement_Item> achievementBoard;
    private Context                     context;
    private static Activity             mActivity;


    // ----------------------------------------------------------
    /**
     * Create a new Achievement object.
     */
    private Achievement()
    {
        this.achievementBoard = new ArrayList<Achievement_Item>();

    }


    // ----------------------------------------------------------
    /**
     * Gets the achievement of the in the array list based on the name.
     *
     * @param name
     *            of the achievement.
     * @return the achievement item passed in.
     */
    public Achievement_Item getAchievement(String name)
    {
        Iterator<Achievement_Item> iterator = this.achievementBoard.iterator();
        while (iterator.hasNext())
        {
            Achievement_Item item = iterator.next();

            if (item.getGoal().equals(name))
            {
                return item;// Stops before duplicate achievement can be added.
            }
        }

        return null;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @return Achievement Board Array List.
     */
    public ArrayList<Achievement_Item> AchievementBoard()
    {
        return this.achievementBoard;
    }


    // ----------------------------------------------------------
    /**
     * Returns the size of the array list.
     *
     * @return the length of array list.
     */
    public int getAchievementBoardLength()
    {
        return this.achievementBoard.size();
    }


    // ----------------------------------------------------------
    /**
     * Adds Achievement to array list..
     *
     * @param goal
     *            name of achievement
     * @param explanation
     *            how to get achievement.
     * @param postName
     * @param caption
     * @param description
     * @param link
     * @param postOK
     */
    public void addAchievement(
        String goal,
        String explanation,
        String postName,
        String caption,
        String description,
        String link,
        boolean postOK)
    {

        Achievement_Item compare = null;
        Iterator<Achievement_Item> iterator = this.achievementBoard.iterator();
        while (iterator.hasNext())
        {
            compare = iterator.next();
            if (compare.getGoal().equals(goal))
            {
                return;// Stops before duplicate achievement can be added.
            }
        }

        this.achievementBoard.add(new Achievement_Item(
            goal,
            explanation,
            postName,
            caption,
            description,
            link,
            postOK));
    }


    // ----------------------------------------------------------
    /**
     * Singleton method allows only one instance of Achievement class.
     *
     * @return achievement
     */
    public static Achievement getInstance()
    {

        if (achievement == null)
        {
            achievement = new Achievement();
        }
        return achievement;
    }


    // ----------------------------------------------------------
    /**
     * Unlocks an achievement
     *
     * @param goalOfAchievement
     *            name of the achievement.
     * @param context
     *            of the application.
     */
    public void AchievementUnlocked(String goalOfAchievement, Context context)
    {
        this.context = context;
        int id = 0;

        Achievement_Item compare = getAchievement(goalOfAchievement);
        compare.setComplete(true);

        id = this.achievementBoard.indexOf(getAchievement(goalOfAchievement));

        if (compare != null)
        {
            LayoutInflater inflater =
                (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.relative, null);

            int drawableId = 0;

            try
            {
                Class res = R.drawable.class;
                Field field =
                    res.getField("achievement_"
                        + compare.getGoal().replace(' ', '_'));
                drawableId = field.getInt(null);
            }
            catch (Exception e)
            {
                Log.e("MyTag", "Failure to get drawable id.", e);
            }

            TextView text = (TextView)view.findViewById(R.id.textView1);
            TextView text2 = (TextView)view.findViewById(R.id.textView2);
            ImageView image = (ImageView)view.findViewById(R.id.imageView1);
            image.setImageResource(drawableId);
            text.setText(compare.toString());
            text2.setText(compare.getExplanation());

            Toast toast = new Toast(context);
            toast.setView(view);

            toast.show();
        }

        try
        {
            Thread.currentThread().sleep(1000);//  pause before updating status
        }
        catch (InterruptedException ie)
        {
            System.out.println("the sleeping thread is not working");
        }
        if (compare.getFacebookOK())
        {
            updateStatus(goalOfAchievement, (Activity)context);
        }

    }


    // ----------------------------------------------------------
    /**
     * Loads the achievements placed in the text file in the folder.
     *
     * @param textFile
     *            name of achievements text file.
     * @param context
     *            of the application.
     */
    public void loadAchievements(String textFile, Context context)
    {

        AssetManager assetManager = context.getAssets();

        Scanner scanner = null;
        try
        {
            scanner = new Scanner(assetManager.open(textFile + ".txt"));

            scanner = scanner.useDelimiter(";");
            while (scanner.hasNext())
            {
                String name = scanner.next();
                String goal = scanner.next();
                String postName = scanner.next();
                String caption = scanner.next();
                String description = scanner.next();
                String link = scanner.next();
                boolean postOK = Boolean.parseBoolean(scanner.next());
                addAchievement(
                    name,
                    goal,
                    postName,
                    caption,
                    description,
                    link,
                    postOK);
                scanner.nextLine();
            }
            scanner.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    // ----------------------------------------------------------
    /**
     * Updates the status of the user for the currently completed achievement.
     *
     * @param AchievementName
     * @param activity of your application.
     */
    public void updateStatus(String AchievementName, final Activity activity)
    {
        mActivity = activity;
        final Achievement facebookInterface = new Achievement();

        final Achievement_Item item = getAchievement(AchievementName);

        // start Facebook Login
        Session.openActiveSession(activity, true, new Session.StatusCallback() {
            public void call(
                final Session session,
                SessionState state,
                Exception exception)
            {
                if (session.isOpened())
                {
                    publishFeedDialog(
                        item.getPostName(),
                        item.getCaption(),
                        item.getDescription(),
                        item.getLink(),
                        null,
                        facebookInterface);
                }
            }
        });
    }


    private void publishFeedDialog(
        String name,
        String caption,
        String description,
        String link,
        String urlPicture,
        final ListenerShareFacebook listenerShareFacebook)
    {

        Bundle params = new Bundle();
        params.putString("name", name);
        params.putString("caption", caption);
        params.putString("description", description);

        if (link != null)
        {
            params.putString("link", link);
        }
        else
        {
            params.putString("link", "https://www.google.com/");
        }

        params.putString("picture", urlPicture);

        Session session = Session.getActiveSession();

        WebDialog feedDialog =
            (new WebDialog.FeedDialogBuilder(mActivity, session, params))
                .setOnCompleteListener(new OnCompleteListener() {
                    public void onComplete(
                        Bundle values,
                        FacebookException error)
                    {
                        if (error == null)
                        {
                            final String postId = values.getString("post_id");
                            if (postId != null)
                            {
                                listenerShareFacebook.onShareFacebookSuccess();
                            }
                            else
                            {
                                // User clicked the Cancel button
                                listenerShareFacebook
                                    .onShareFacebookFailure("Publish cancelled");
                            }
                        }
                        else if (error instanceof FacebookOperationCanceledException)
                        {
                            // User clicked the "x" button
                            listenerShareFacebook
                                .onShareFacebookFailure("Publish cancelled");
                        }
                        else
                        {
                            // network error
                            listenerShareFacebook
                                .onShareFacebookFailure("Error posting story");
                        }
                    }

                }).build();

        feedDialog.show();
    }


    public void onShareFacebookSuccess()
    {
        System.out.println("Facebook update completed.");
    }


    public void onShareFacebookFailure(String error)
    {
        System.out.println("Facebook update failed");
    }

}
