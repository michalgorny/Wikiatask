package pl.michalgorny.wikiatask;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import pl.michalgorny.wikiatask.ui.MainActivity;
import timber.log.Timber;

import static pl.michalgorny.wikiatask.utils.Utils.*;

/**
 *  Test cases for wikis list
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mSolo;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }


    @Override
    protected void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
        super.tearDown();
    }

    public void testShouldWikiAreDownloaded() throws Exception{
        checkInternetStatus();
        final ListView list = (ListView) mSolo.getView(R.id.wiki_paging_list_view);
        boolean timeoutReached = downloadWikis(list);

        assertTrue(String.valueOf("Wikis have not downloaded in time " +
                        TestConfig.WAITING_FOR_DOWNLOAD_TIME_IN_MILLIS + " ms"), timeoutReached);
    }

    public void testDownloadedWikiShouldHaveExactlyOneBatchSize() {
        checkInternetStatus();
        final ListView list = (ListView) mSolo.getView(R.id.wiki_paging_list_view);
        downloadWikis(list);

        boolean hasExactlyOneBatch = list.getCount() == TestConfig.SIZE_OF_BATCH + 1; // +1 because footer is also calculated as item
        assertTrue(String.valueOf("List has " + (list.getCount() - 1) + " items"), hasExactlyOneBatch); // -1 because as above
    }

    public void testMoreWikiShouldDownloadAfterScrolling() throws Exception{
        checkInternetStatus();
        final ListView list = (ListView) mSolo.getView(R.id.wiki_paging_list_view);
        final int sizeBeforeScrolling = list.getCount();
        mSolo.scrollListToLine(list, 25);
        boolean moreWikisDownloaded = mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return list.getCount() > sizeBeforeScrolling;
            }
        }, TestConfig.WAITING_FOR_DOWNLOAD_TIME_IN_MILLIS);

        assertTrue("More wikis have not been downloaded", moreWikisDownloaded);
    }

    private boolean downloadWikis(final ListView list) {
        return mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return list.getCount() > 0;
            }
        }, TestConfig.WAITING_FOR_DOWNLOAD_TIME_IN_MILLIS);
    }

    private void checkInternetStatus() {
        assertTrue("No internet connection", isOnline(getActivity()));
    }

}
