package br.com.stanzione.uoltest.main;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import br.com.stanzione.uoltest.App;
import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.RecyclerViewItemCountAssertion;
import br.com.stanzione.uoltest.di.ApplicationComponent;
import br.com.stanzione.uoltest.di.DaggerApplicationComponent;
import br.com.stanzione.uoltest.di.MockNetworkModule;
import io.realm.Realm;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private MockWebServer server = new MockWebServer();
    private Realm realm;

    @Before
    public void setUp() throws Exception {
        setUpServer();
        setUpRealm();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    public void setUpServer() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new MockNetworkModule(server))
                .build();

        ((App) getTargetContext().getApplicationContext())
                .setApplicationComponent(applicationComponent);
    }

    public void setUpRealm(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> realm.deleteAll());
        });
    }

    @Test
    public void withNewsTabSelectedShouldShowNewsFeed() throws InterruptedException, IOException {

        server.enqueue(new MockResponse()
                .setBody(readFile("news_feed_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.navNews));

        // Need this to wait NavigationDrawer to close so the first ViewPager swipe can work
        Thread.sleep(1000);

        onView(withId(R.id.tabLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        Thread.sleep(3000);

        onView(withId(R.id.newsRecyclerView))
                .check(matches(isDisplayed()));

        // Counting the banner too
        onView(withId(R.id.newsRecyclerView))
                .check(new RecyclerViewItemCountAssertion(11));

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())));

    }

    @Test
    public void withNewsTabSelectedAndGeneralErrorShouldShowGeneralErrorMessage() throws InterruptedException {

        server.enqueue(new MockResponse()
                .setResponseCode(500));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.navNews));

        // Need this to wait NavigationDrawer to close so the first ViewPager swipe can work
        Thread.sleep(1000);

        onView(withId(R.id.tabLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        Thread.sleep(3000);

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_general_error)))
                .check(matches(isDisplayed()));

    }

    @Test
    public void withNewsTabSelectedAndNoConnectionShouldShowNoConnectionMessage() throws InterruptedException, IOException {

        server.shutdown();

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.navNews));

        // Need this to wait NavigationDrawer to close so the first ViewPager swipe can work
        Thread.sleep(1000);

        onView(withId(R.id.tabLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        Thread.sleep(3000);

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_network_error)))
                .check(matches(isDisplayed()));

    }

    @Test
    public void withNewsTabSelectedAndNoConnectionAndDatabaseShouldShowDatabaseMessage() throws InterruptedException, IOException {

        server.enqueue(new MockResponse()
                .setBody(readFile("news_feed_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.navNews));

        // Need this to wait NavigationDrawer to close so the first ViewPager swipe can work
        Thread.sleep(1000);

        onView(withId(R.id.tabLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        onView(withId(R.id.viewPager))
                .perform(ViewActions.swipeLeft());

        Thread.sleep(3000);

        onView(withId(R.id.newsRecyclerView))
                .check(matches(isDisplayed()));

        // Counting the banner too
        onView(withId(R.id.newsRecyclerView))
                .check(new RecyclerViewItemCountAssertion(11));

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())));

        server.shutdown();

        Thread.sleep(3000);

        onView(withId(R.id.swipeRefresh))
                .perform(ViewActions.swipeDown());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_from_database)))
                .check(matches(isDisplayed()));


    }

    private String readFile(String fileName) throws IOException {
        InputStream stream = InstrumentationRegistry.getContext()
                .getAssets()
                .open(fileName);

        Source source = Okio.source(stream);
        BufferedSource buffer = Okio.buffer(source);

        return buffer.readUtf8();
    }

}