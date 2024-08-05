package app.SourceCode.Fundamental;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleTransAPI {

    private static final String TRANSLATE_URL = "https://translate.googleapis.com/translate_a/single";

    public static String translate(String text, String srcLang, String destLang) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create(TRANSLATE_URL + "?client=gtx&sl=" + srcLang + "&tl=" + destLang + "&dt=t&q=" + java.net.URLEncoder.encode(text, "UTF-8"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Unexpected response code " + response.statusCode());
        }
        return parseTranslation(response.body());
    }


    private static String parseTranslation(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode translationsNode = rootNode.get(0);
            StringBuilder translatedText = new StringBuilder();

            for (JsonNode translationNode : translationsNode) {
                String translatedSentence = translationNode.get(0).asText();
                translatedText.append(translatedSentence).append(" ");
            }

            return translatedText.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing translation.";
        }
    }
}
