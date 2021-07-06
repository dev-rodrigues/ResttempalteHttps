package com.example.httpsspringboot.gateway;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
public class RequestGateway {

    private final RestTemplate restTemplate;

    public RequestGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void mustPerformHttpsCall() {
        executeRequest(() -> {
            var URL = "https://legis.senado.leg.br/dadosabertos/materia/assuntos?indAtivos=S";
            var URI = convertToUri(URL);

            ResponseEntity<String> response = restTemplate.exchange(
                    URI,
                    HttpMethod.GET,
                    null,
                    String.class);

            System.out.println(response);
        });
     }

     private void executeRequest(Runnable runnable) {
        try {
            runnable.run();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            e.printStackTrace();
        }
     }

     private String convertToUri(String url) {
         UriComponentsBuilder builder = fromHttpUrl(url);
         return builder.toUriString();
     }
}
