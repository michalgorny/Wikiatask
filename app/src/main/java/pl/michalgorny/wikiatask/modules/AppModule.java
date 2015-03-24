package pl.michalgorny.wikiatask.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.michalgorny.wikiatask.api.services.WikiManager;
import pl.michalgorny.wikiatask.ui.ErrorFragment;
import pl.michalgorny.wikiatask.ui.MainActivity;
import pl.michalgorny.wikiatask.ui.WikiListFragment;

/**
 *  Module which satisfying application dependencies
 */

@Module(
        injects = {
                WikiManager.class,
                WikiListFragment.class,
                ErrorFragment.class,
                MainActivity.class
        }
)
public class AppModule {
    @Provides
    @Singleton
    public Bus provideBus(){
        return new Bus();
    }

}
