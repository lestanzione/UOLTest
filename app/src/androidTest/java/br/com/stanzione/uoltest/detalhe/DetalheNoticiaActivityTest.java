package br.com.stanzione.uoltest.detalhe;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;
import io.realm.Realm;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class DetalheNoticiaActivityTest {

    @Rule
    public ActivityTestRule<DetalheNoticiaActivity> activityRule = new ActivityTestRule<>(DetalheNoticiaActivity.class, true, false);

    private Realm realm;

    @Before
    public void setUp() {
        setUpRealm();
    }

    public void setUpRealm(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> realm.deleteAll());
        });
    }

    @Test
    public void withNoDatabaseShouldShowMessage() throws InterruptedException {

        activityRule.launchActivity(new Intent());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_general_error)))
                .check(matches(isDisplayed()));

        Thread.sleep(3000);

    }

    @Test
    public void withNewsShouldLoadUrl() throws InterruptedException {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {
                    News news = new News();
                    news.setId("any-fake-id");
                    news.setWebviewUrl("https://www.uol.com.br");
                    realm.copyToRealm(news);
            });
        });

        activityRule.launchActivity(new Intent().putExtra("selectedNews", "any-fake-id"));

        Thread.sleep(3000);

    }

}