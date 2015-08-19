package com.onlinebidding.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import android.content.Context;
import android.os.Build;

import com.onlinebidding.R;

public class CustomHttpRequestFactory extends SimpleClientHttpRequestFactory {
	
	private Context context;
	private static final String clientCertPassword = "viki123";
	 
	public CustomHttpRequestFactory(Context context) {
		this.context = context;
	}

	@Override
	protected void prepareConnection(final HttpURLConnection connection, final String httpMethod) throws IOException {
		if (connection instanceof HttpsURLConnection) {
			((HttpsURLConnection) connection).setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
			((HttpsURLConnection) connection).setSSLSocketFactory(getSocketFactory());
		}
		super.prepareConnection(connection, httpMethod);
	}
	
	private SSLSocketFactory getSocketFactory() {
		try {
			// Read our client certificate
            InputStream clientCert = context.getResources().openRawResource(R.raw.client);
            InputStream clientCertBuffered = new BufferedInputStream(clientCert);

            // Create a KeyStore containing our client certificate
            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            clientKeyStore.load(clientCertBuffered, clientCertPassword.toCharArray());

            // Create KeyManager that will use the client key in the KeyStore
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
            kmf.init(clientKeyStore, clientCertPassword.toCharArray());

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = null;
            if(Build.VERSION.SDK_INT < 16)
                sslContext = SSLContext.getInstance("TLS");
            else
                sslContext = SSLContext.getInstance("TLSv1.2");
            
            // sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            sslContext.init(kmf.getKeyManagers(), null, null);
            
            return sslContext.getSocketFactory();
            
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}




