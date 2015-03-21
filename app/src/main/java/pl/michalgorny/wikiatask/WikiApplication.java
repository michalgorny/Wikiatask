package pl.michalgorny.wikiatask;

import android.app.Application;

import dagger.ObjectGraph;
import pl.michalgorny.wikiatask.modules.AppModule;
import timber.log.Timber;

public class WikiApplication extends Application{

    private static ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new AppModule());
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static void doDaggerInject(Object o){
        mObjectGraph.inject(o);
    }
}
