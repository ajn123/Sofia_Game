package achievements;

// -------------------------------------------------------------------------
/**
 * Interface to share facebook which updates based on the status of the
 * attempted facebook update.
 *
 * @author Aj Norton
 * @version May 12, 2013
 */
public interface ListenerShareFacebook
{
    // ----------------------------------------------------------
    /**
     * Prints out a message based on the facebook posts status failure..
     */
    void onShareFacebookSuccess();


    // ----------------------------------------------------------
    /**
     * Prints out a message based on the facebook posts status failure..
     *
     * @param error
     */
    void onShareFacebookFailure(String error);
}
