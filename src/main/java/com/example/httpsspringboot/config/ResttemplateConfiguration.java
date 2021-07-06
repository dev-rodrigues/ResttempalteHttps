package com.example.httpsspringboot.config;

import com.example.httpsspringboot.config.properties.ResttemplateProperties;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class ResttemplateConfiguration {

    private final ResttemplateProperties properties;

    public ResttemplateConfiguration(ResttemplateProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");

        File file = ResourceUtils.getFile("classpath:devrodrigues.p12");

        clientStore.load(new FileInputStream(file), properties.getPassword().toCharArray());

        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        sslContextBuilder.setProtocol(properties.getProtocol());
        sslContextBuilder.loadKeyMaterial(clientStore, properties.getPassword().toCharArray());
        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(properties.getConnectionTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());

        return new RestTemplate(requestFactory);
    }
}