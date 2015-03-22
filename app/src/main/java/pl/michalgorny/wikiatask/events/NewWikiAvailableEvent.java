package pl.michalgorny.wikiatask.events;

import java.util.List;

import pl.michalgorny.wikiatask.api.responses.WikiItemResponse;
import pl.michalgorny.wikiatask.pojos.Wiki;

/**
 *  Event triggered when new wikis was downloaded from server
 */
public class NewWikiAvailableEvent {

    private final boolean mHasMoreWikisToDownload;

    private final List<Wiki> mNewItemsList;

    /**
     * Default constructor
     * @param hasMoreWikis indicator whether on server are more wikis to download
     * @param newItems list of new downloaded items
     */
    public NewWikiAvailableEvent(boolean hasMoreWikis, List<Wiki> newItems) {
        mHasMoreWikisToDownload = hasMoreWikis;
        mNewItemsList = newItems;
    }

    public boolean hasMoreWikisToDownload() {
        return mHasMoreWikisToDownload;
    }

    public List<Wiki> getNewItemsList() {
        return mNewItemsList;
    }

}
