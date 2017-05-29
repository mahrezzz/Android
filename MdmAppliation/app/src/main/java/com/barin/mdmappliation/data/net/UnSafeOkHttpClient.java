package com.barin.mdmappliation.data.net;

import com.squareup.okhttp.OkHttpClient;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by barin on 4/5/2016.
 */
public class UnSafeOkHttpClient {

  public static OkHttpClient getUnsafeOkHttpClient() {

    try {
      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[] {
          new X509TrustManager() {
            @Override public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            }

            @Override public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                String authType) throws CertificateException {
            }

            @Override public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return null;
            }
          }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      OkHttpClient okHttpClient = new OkHttpClient();
      okHttpClient.setSslSocketFactory(sslSocketFactory);
      okHttpClient.setHostnameVerifier((hostname, session) -> {
        // if (hostname.еquals("https://pamukkale.teknosa.com"))
          /*return true;
        else*/
        return true;
      });

      return okHttpClient;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}