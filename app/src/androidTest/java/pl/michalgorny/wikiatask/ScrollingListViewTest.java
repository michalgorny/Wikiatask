package pl.michalgorny.wikiatask;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import pl.michalgorny.wikiatask.ui.MainActivity;

/**
 *  Test when list view scrolled to bottom then more wikis are downloaded
 */
public class ScrollingListViewTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String PACKAGE_NAME = "pl.michalgorny.wikiatask";
    private Solo mSolo;

    public ScrollingListViewTest(){
        super(PACKAGE_NAME, MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation());
        getActivity();
    }


    @Override
    protected void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
        super.tearDown();
    }

    public void testMoreWikiAreDownloadedAfterScrolling(){
        mSolo.waitForActivity(MainActivity.class.getSimpleName(), 2000);
        boolean moreScrollingCanBePerformed =
                mSolo.scrollListToBottom(0);
        assertTrue(moreScrollingCanBePerformed);
    }
}
