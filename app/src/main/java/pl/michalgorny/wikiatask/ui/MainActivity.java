package pl.michalgorny.wikiatask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import pl.michalgorny.wikiatask.R;
import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.events.ConnectionRestoredEvent;
import pl.michalgorny.wikiatask.events.NewWikiAvailableEvent;
import pl.michalgorny.wikiatask.events.TryAgainEvent;
import pl.michalgorny.wikiatask.utils.Utils;


public class MainActivity extends ActionBarActivity {

    private static final String ERROR_FRAGMENT_TAG = "error_fragment_tag";
    @Inject
    Bus mBus;

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WikiApplication.doDaggerInject(this);
        mBus.register(this);

        mFragmentManager = getSupportFragmentManager();

        if (Utils.isOnline(this)){
            showListFragment();
        }else{
            showErrorFragment();
        }
    }

    private void showErrorFragment() {
        changeFragments(R.id.error_fragment, R.id.list_fragment);
    }

    private void showListFragment() {
        changeFragments(R.id.list_fragment, R.id.error_fragment);
    }

    private void changeFragments(int iDFragmentToShow, int idFragmentToHide) {
        mFragmentManager
                .beginTransaction()
                .show(mFragmentManager.findFragmentById(iDFragmentToShow))
                .hide(mFragmentManager.findFragmentById(idFragmentToHide))
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Subscribe
    public void onTryAgainEvent (TryAgainEvent event){
        if (Utils.isOnline(this)) {
            mBus.post(new ConnectionRestoredEvent());
        }
        else {
            Toast.makeText(this, R.string.download_error_text, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void handleNewWikis(NewWikiAvailableEvent event){
        showListFragment();
    }
}
