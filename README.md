Sofia_Game
==========

The two implementations in this project are as follows:

LEADERBOARD:

The leader board is taken care of by calling saveScores() and loadScores() and 
loading a class extended by the LeaderBoardScreen class shown by sofia or plain 
android.

To load a score you must save a score in integer form and a title, preferably 
the name of the player.  To be displayed when the leader board screen is loaded.

You can also use deleteScores() to delete the scores as the top 20 are saved for the duration of the application.


ACHIEVEMENTS

The achievements scheme has two main features.  One that allows for a toast to come up when 
the user has unlocked their achievement.  Second, a post to facebook feature that allows the user to post
their achievement to facebook as a status update.

To set up the achievements package, all you must do is call loadAchievements(String textFile, Context context)
which takes a string of the properly formatted text file and the current context of the application.
Then when you unlock an achievement, you call  public void AchievementUnlocked(String Achievement, Context context)
which takes the string name of the achievement (The goal)  and unlocks it.  Then a toast and a facebook status update will
be presented if it is enabled.  Also you will be able to tell if you visit the Achievement screen menu which will contain
a checkbox recording if you have unlocked the achievement or not.

Also if the goal of the achievement matches an image in the drawable folder, then that image will be shown in the
achievement screen.  This only works if the image is in the libraries directory and not the user's application folder.


The example input for the achievements text file for the user to display is shown below:
(String:Goal);(String:Explanation);(String:Post Head);(String:Caption);(String:Description);(String:Link);(Boolean:Post to facebook?)
(Example Below):
pig king;kill 70 pigs;Pig King Games;Just unlocked an achievement;This is the best APP ever;http://cssdog.com/css_shortcuts.html;false;
