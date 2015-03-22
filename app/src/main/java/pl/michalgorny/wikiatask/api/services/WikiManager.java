package pl.michalgorny.wikiatask.api.services;

import android.text.TextUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.api.DetailsSerializer;
import pl.michalgorny.wikiatask.api.responses.GetItemDetailsResponse;
import pl.michalgorny.wikiatask.events.NewWikiAvailableEvent;
import pl.michalgorny.wikiatask.events.WikiDownloadFailedEvent;
import pl.michalgorny.wikiatask.api.responses.WikiItemResponse;
import pl.michalgorny.wikiatask.api.responses.WikiGetListResponse;
import pl.michalgorny.wikiatask.pojos.Wiki;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
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

    private List<Wiki> mWikiList = new ArrayList<>();

    public WikiManager() {
        createRestService();
        WikiApplication.doDaggerInject(this);
        mBus.register(this);
    }

    private void createRestService() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GetItemDetailsResponse.class, new DetailsSerializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WIKI_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        mWikiService = restAdapter.create(WikiService.class);
    }

    public List<Wiki> getWikiList() {
        return mWikiList;
    }

    private void downloadWikis(int batch){
        mWikiService.getWikis(batch, new Callback<WikiGetListResponse>() {
            @Override
            public void success(WikiGetListResponse wiki, Response response) {
                mCurrentBatch = wiki.getCurrentBatch();
                final boolean isLastBatchDownloaded = wiki.getCurrentBatch().equals(wiki.getBatches());

                List<Integer> ids = Lists.transform(wiki.getItems(), GET_ID_FROM_ITEM_FUNCTION);
                String query = TextUtils.join(",", ids);

                mWikiService.getDetails(query, new Callback<GetItemDetailsResponse>() {
                    @Override
                    public void success(GetItemDetailsResponse getItemResponse, Response response) {
                        mBus.post(new NewWikiAvailableEvent(!isLastBatchDownloaded, getItemResponse.getWikiList()));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Timber.d("Error: " + error.getCause());
                        error.printStackTrace();

                        mBus.post(new WikiDownloadFailedEvent(error.getMessage()));
                    }
                });
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

    static Function<WikiItemResponse, Integer> GET_ID_FROM_ITEM_FUNCTION  = new Function<WikiItemResponse, Integer>() {
        @Override
        public Integer apply(WikiItemResponse item) {
            return item.getId();
        }
    };

}
