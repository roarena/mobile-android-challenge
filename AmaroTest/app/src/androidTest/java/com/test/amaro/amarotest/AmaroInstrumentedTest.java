package com.test.amaro.amarotest;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.test.amaro.amarotest.activities.mainActivity.MainActivity;
import com.test.amaro.amarotest.helpers.Constants;
import com.test.amaro.amarotest.helpers.RecyclerViewMatcher;
import com.test.amaro.amarotest.helpers.RestHelper;
import com.test.amaro.amarotest.utils.C;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.squareup.okhttp.mockwebserver.SocketPolicy.DISCONNECT_AT_START;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AmaroInstrumentedTest extends InstrumentationTestCase {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    private MockWebServer mockWebServer;
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        appContext = InstrumentationRegistry.getTargetContext();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        C.BASE_URL = mockWebServer.url("/").toString();
    }

    @Test
    public void testJsonResponseOK() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.GENERAL_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        Thread.sleep(Constants.DEFAULT_SLEEP);

        onView(withText(appContext.getString(R.string.error_loading))).check(matches(not(isDisplayed())));
    }

    @Test
    public void testJsonResponseFail() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.FAIL_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        Thread.sleep(Constants.DEFAULT_SLEEP);

        onView(withText(appContext.getString(R.string.error_loading))).check(matches(isDisplayed()));
    }

    @Test
    public void testOnClickProduct() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.SINGLE_ITEM_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.rv_main))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(Constants.DEFAULT_SLEEP);

        onView(withId(R.id.tv_details_product_name)).check(matches(withText(containsString(Constants.TEXT_MATCH_SINGLE_ITEM))));
    }

    @Test
    public void testOnSaleFilter() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.FILTER_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.sw_main_sale)).perform(click());

        Thread.sleep(Constants.DEFAULT_SLEEP);
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_main).atPosition(0))
                .check(matches(hasDescendant(withText(Constants.TEXT_MATCH_ON_SALE))));

    }

    @Test
    public void testHigherFirstFilter() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.FILTER_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.iv_main_filter)).perform(click());
        Thread.sleep(Constants.DEFAULT_SLEEP);
        onView(withId(R.id.rb_filter_higher)).perform(click());

        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_main).atPosition(0))
                .check(matches(hasDescendant(withText(Constants.TEXT_MATCH_HIGHER_PRICE))));
    }

    @Test
    public void testLowerFirstFilter() throws Exception {
        mockWebServer.enqueue(setMockServerResponse(Constants.FILTER_JSON_RESPONSE));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.iv_main_filter)).perform(click());
        Thread.sleep(Constants.DEFAULT_SLEEP);
        onView(withId(R.id.rb_filter_lower)).perform(click());

        onView(RecyclerViewMatcher.withRecyclerView(R.id.rv_main).atPosition(0))
                .check(matches(hasDescendant(withText(Constants.TEXT_MATCH_LOWER_PRICE))));
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    private MockResponse setMockServerResponse(int response) throws Exception {
        switch (response) {
            default:
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(RestHelper.getStringFromFile(getInstrumentation().getContext(),
                                Constants.JSON_MOCK));
            case Constants.SINGLE_ITEM_JSON_RESPONSE:
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(RestHelper.getStringFromFile(getInstrumentation().getContext(),
                                Constants.SINGLE_ITEM_JSON_MOCK));
            case Constants.FILTER_JSON_RESPONSE:
                return new MockResponse()
                        .setResponseCode(200)
                        .setBody(RestHelper.getStringFromFile(getInstrumentation().getContext(),
                                Constants.FILTER_ITEMS_JSON_MOCK));
            case Constants.FAIL_JSON_RESPONSE:
                return new MockResponse().setSocketPolicy(DISCONNECT_AT_START);
        }
    }
}
