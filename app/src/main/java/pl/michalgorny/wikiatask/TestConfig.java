package pl.michalgorny.wikiatask;

/**
 *  Config class to test cases. It contains predefinded time values etc.
 */
public class TestConfig {
    /**
     * Time which test will be wait for download wikis on first time
     */
    public static final int WAITING_FOR_DOWNLOAD_TIME_IN_MILLIS = 1000;

    /**
     * Quantity of wikis which are downloaded per one request
     */
    public static final int SIZE_OF_BATCH = 25;
}
