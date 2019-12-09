//Android M - Marshmallow Tutorial, Example - An Introduction to Google Chrome Custom Tabs for Android
//Chrome Custom Tabs: Displaying 3rd party content in your Android app

package mhealth.c4c;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class nascopwebsite extends AppCompatActivity {

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    //final String websiteURL = "http://viralandroid.com/";
    final String nascopwebsite = "http:/";

    CustomTabsClient mCustomTabsClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mCustomTabsClient= customTabsClient;
                mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCustomTabsClient= null;
            }
        };

        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .build();
    }

//    public void chromeCustomTabExample(View view) {
//        mCustomTabsIntent.launchUrl(this, Uri.parse(websiteURL));
//    }

    public void nascopwebsite(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            nascopwebsite));
    }
}