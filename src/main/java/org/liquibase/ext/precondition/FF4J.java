package org.liquibase.ext.precondition;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

public class FF4J {
    private String url;
    private FF4JFeature[] features;

    public FF4J(String url) {
        this.url = url;
    }

    private void fetchFeatures() throws IOException {
        HttpGet request = new HttpGet(this.url + "/api/ff4j/store/features");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Unable to connect to FF4J API.");
            }

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                this.features = gson.fromJson(result, FF4JFeature[].class);
            }
        }
    }

    public Boolean Enabled(String uuid) throws IOException {
        if (this.features == null) {
            this.fetchFeatures();
        }
        for (FF4JFeature feature : this.features) {
            if (Objects.equals(feature.uid, uuid)) {
                return Boolean.TRUE.equals(feature.enable);
            }
        }
        return false;
    }
}

class FF4JFeature {
    public String uid;
    public Boolean enable;
}