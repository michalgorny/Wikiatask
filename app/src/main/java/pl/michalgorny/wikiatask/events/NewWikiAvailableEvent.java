package pl.michalgorny.wikiatask.events;

import java.util.List;

import pl.michalgorny.wikiatask.services.responses.WikiItemResponse;

/**
 *  Event triggered when new wikis was dowloaded from server
 */
public class NewWikiAvailableEvent {

    private final boolean mHasMoreWikisToDownload;

    private final List<WikiItemResponse> mNewItemsList;

    /**
     * Default constructor
     * @param hasMoreWikis indicator whether on server are more wikis to download
     * @param newItems list of new downloaded items
     */
    public NewWikiAvailableEvent(boolean hasMoreWikis, List<WikiItemResponse> newItems) {
        mHasMoreWikisToDownload = hasMoreWikis;
        mNewItemsList = newItems;
    }

    public boolean hasMoreWikisToDownload() {
        return mHasMoreWikisToDownload;
    }

    public List<WikiItemResponse> getNewItemsList() {
        return mNewItemsList;
    }

}
