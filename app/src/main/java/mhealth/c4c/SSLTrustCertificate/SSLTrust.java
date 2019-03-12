package mhealth.c4c.SSLTrustCertificate;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLTrust {
    protected static final String TAG = "NukeSSLCerts";

    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession arg1) {

                    if(!(hostname.equalsIgnoreCase("https://c4c.mhealthkenya.co.ke/Core/AppReg")||hostname.equalsIgnoreCase("https://c4c.mhealthkenya.co.ke/Core/CreateProf")||hostname.equalsIgnoreCase("https://c4c.mhealthkenya.co.ke/Core/RptExp")||hostname.equalsIgnoreCase("https://c4c.mhealthkenya.co.ke/Core/get_facility_info")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/Core/AppReg")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/Core/CreateProf")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/Core/RptExp")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/Core/get_facility_info")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/c4c-dev/core/CountySearch")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/c4c-dev/core/SubCountySearch")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/c4c-dev/core/FacSearch")||hostname.equalsIgnoreCase("https://api.mhealthkenya.co.ke/api/get_sub_county")||hostname.equalsIgnoreCase("https://c4c-test.mhealthkenya.co.ke/Core/chkMobile")||hostname.equalsIgnoreCase("https://c4c.mhealthkenya.co.ke/Core/SignUp"))){
                        return true;

                    }
                    else{
                        return false;
                    }

                }
            });
        } catch (Exception e) {
        }
    }
}
