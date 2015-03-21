package pl.michalgorny.wikiatask.services;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.events.NewWikiAvailableEvent;
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

    @Inject
    Bus mBus;

    private static final String WIKI_URL = "http://www.wikia.com";
    private WikiService mWikiService;
    private int mCurrentBatch = 1;

    private List<WikiItemResponse> mWikiList = new ArrayList<>();

    public WikiManager() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(WIKI_URL).build();
        mWikiService = restAdapter.create(WikiService.class);
        WikiApplication.doDaggerInject(this);
        mBus.register(this);
    }

    public List<WikiItemResponse> getWikiList() {
        if (mWikiList.isEmpty()){
            downloadWikis();
        }
        return mWikiList;
    }

    private void downloadWikis(){
        mWikiService.getWikis(mCurrentBatch, new Callback<WikiGetListResponse>() {
            @Override
            public void success(WikiGetListResponse wiki, Response response) {
                Timber.d("Success! Wikis downloaded.");
                mWikiList.addAll(wiki.getItems());
                mBus.post(new NewWikiAvailableEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e("Error! Wikis download failed. Cause: " + error.getCause());
                error.printStackTrace();
            }
        });
    }

    public void getMoreWikis(){
        mCurrentBatch++;
        getWikiList();
    }

}
