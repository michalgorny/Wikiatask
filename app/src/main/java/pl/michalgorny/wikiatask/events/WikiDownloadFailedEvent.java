package pl.michalgorny.wikiatask.events;

/**
 *  Event triggered when problem with downloading wikis occurs
 */
public class WikiDownloadFailedEvent {
    private final String mMessage;

    public WikiDownloadFailedEvent(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
