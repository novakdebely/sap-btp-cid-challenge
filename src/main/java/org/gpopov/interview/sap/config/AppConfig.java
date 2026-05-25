package org.gpopov.interview.sap.config;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslManagerBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

	@Bean
    RestClient restClient(RestClient.Builder builder) {
        final SslManagerBundle sslManagerBundle = SslManagerBundle.from(new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // noop
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // noop
            }
        });
        final SslBundle sslBundle = SslBundle.of(null, null, null, "TLS", sslManagerBundle);
        final  HttpClientSettings settings =  HttpClientSettings.ofSslBundle(sslBundle);
        final ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.detect().build(settings);
        return builder.requestFactory(requestFactory).build();
    }
}
