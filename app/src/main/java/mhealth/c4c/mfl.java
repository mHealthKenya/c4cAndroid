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

public class mfl extends AppCompatActivity {

    final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    final String mfl = "http://41.215.81.58/mfl/";
    // final String live = "http://fcas.mhealthkenya.org/index.php/392289?lang=en";

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
                .setShowTitle(false)
                .build();
    }

//    public void live(View view) {
//        mCustomTabsIntent.launchUrl(this, Uri.parse(live));
//    }

    public void mfl(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(mfl));
    }
}
