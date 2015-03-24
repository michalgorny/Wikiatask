package pl.michalgorny.wikiatask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.michalgorny.wikiatask.R;
import pl.michalgorny.wikiatask.WikiApplication;
import pl.michalgorny.wikiatask.events.TryAgainEvent;

public class ErrorFragment extends Fragment {

    @Inject
    Bus mBus;
    private View view;

    public ErrorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WikiApplication.doDaggerInject(this);
        mBus.register(this);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_error, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.error_button)
    public void retryClicked(){
        mBus.post(new TryAgainEvent());
    }

}
