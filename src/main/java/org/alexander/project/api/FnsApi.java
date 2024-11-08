package org.alexander.project.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.alexander.project.api.dto.InnPersonDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FnsApi {
    private static final HttpClient client = HttpClient.newHttpClient();

    @SneakyThrows
    public static InnPersonDto findOrganizationData(String inn) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-fns.ru/api/egr?req=" + inn + "&key=7d7e6d71455d79d3c8f487eda397ff7f5e40042a"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), InnPersonDto.class);
    }
}
