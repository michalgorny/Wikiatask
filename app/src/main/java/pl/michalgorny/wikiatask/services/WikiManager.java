package pl.michalgorny.wikiatask.services;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.events.NewWikiAvailableEvent;
import pl.michalgorny.wikiatask.events.WikiDownloadFailedEvent;
import pl.michalgorny.wikiatask.services.responses.WikiItemResponse;
import pl.michalgorny.wikiatask.services.responses.WikiGetListResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * Manager handling downloading wiki from server
 */
public class WikiManager {

    private static final int DEFAULT_BATCH_SIZE = 25;
    private static final int DEFAULT_BATCH_INDEX = 1;

    @Inject
    Bus mBus;

    private static final String WIKI_URL = "http://www.wikia.com";
    private WikiService mWikiService;
    private int mCurrentBatch = DEFAULT_BATCH_INDEX;

    private List<WikiItemResponse> mWikiList = new ArrayList<>();

    public WikiManager() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(WIKI_URL).build();
        mWikiService = restAdapter.create(WikiService.class);
        WikiApplication.doDaggerInject(this);
        mBus.register(this);
    }

    public List<WikiItemResponse> getWikiList() {
        return mWikiList;
    }

    private void downloadWikis(int batch){
        mWikiService.getWikis(batch, new Callback<WikiGetListResponse>() {
            @Override
            public void success(WikiGetListResponse wiki, Response response) {
                Timber.d("Success! Wikis downloaded.");

                mCurrentBatch = wiki.getCurrentBatch();
                boolean isLastBatchDownloaded = wiki.getCurrentBatch().equals(wiki.getBatches());

                mBus.post(new NewWikiAvailableEvent(!isLastBatchDownloaded, wiki.getItems()));
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Error! Wikis download failed. Cause: " + error.getCause());
                error.printStackTrace();

                mBus.post(new WikiDownloadFailedEvent(error.getMessage()));
            }
        });
    }

    public void getWikis(boolean incrementBatch){
        downloadWikis(incrementBatch ? ++mCurrentBatch : mCurrentBatch);
    }

}
