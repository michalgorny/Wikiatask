package pl.michalgorny.wikiatask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paging.listview.PagingListView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.michalgorny.wikiatask.R;
import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.adapters.WikiPagingAdapter;
import pl.michalgorny.wikiatask.events.NewWikiAvailableEvent;
import pl.michalgorny.wikiatask.events.WikiDownloadFailedEvent;
import pl.michalgorny.wikiatask.services.responses.WikiItemResponse;
import pl.michalgorny.wikiatask.services.WikiManager;
import timber.log.Timber;

/**
 * Main fragment class which is responsible to display wiki posts on the list
 */
public class WikiListFragment extends Fragment implements PagingListView.Pagingable{

    @InjectView(R.id.wiki_paging_list_view)
    PagingListView mWikiPagingListView;

    @Inject
    Bus mBus;

    private WikiPagingAdapter mWikiPagingAdapter;
    private WikiManager mWikiManager;

    public WikiListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WikiApplication.doDaggerInject(this);
        mBus.register(this);

        setRetainInstance(true);

        mWikiManager = new WikiManager();
        List<WikiItemResponse> wikis = mWikiManager.getWikiList();
        mWikiPagingAdapter = new WikiPagingAdapter(getActivity().getApplicationContext(), wikis);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wiki_fragment_list, container, false);
        ButterKnife.inject(this, view);
        mWikiPagingListView.setAdapter(mWikiPagingAdapter);
        mWikiPagingListView.setPagingableListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);//TODO check is it working with configuration changed
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Override
    public void onLoadMoreItems() {
        Timber.d("onLoadMoreItems");
        mWikiManager.getMoreWikis();
    }

    @Subscribe
    public void handleNewWikis(NewWikiAvailableEvent event){
        mWikiPagingListView.onFinishLoading(false, mWikiManager.getWikiList());
    }

    @Subscribe
    public void handleErrorDownloading(WikiDownloadFailedEvent event){
        Toast.makeText(getActivity(), R.string.download_error_text, Toast.LENGTH_LONG).show();
    }

}
