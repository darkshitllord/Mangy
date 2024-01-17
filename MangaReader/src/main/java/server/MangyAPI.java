package server;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class MangyAPI {

    private static final String API_ENDPOINT = "https://api.mangadex.org/manga";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:121.0) Gecko/20100101 Firefox/121.0";

    public static String searchRequest(String searchQuery) {
        try {
            // Construct the API request URL dynamically
            String apiUrl = API_ENDPOINT + "?title=" + searchQuery + "&limit=20" +
                    "&contentRating%5B%5D=safe&contentRating%5B%5D=suggestive&contentRating%5B%5D=erotica" +
                    "&includes%5B%5D=cover_art&order%5Brelevance%5D=desc";

            // Create the HTTP request without setting the "Connection" header explicitly
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("User-Agent", USER_AGENT)
                    .header("Accept", "*/*")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Referer", "https://mangadex.org/")
                    .header("Origin", "https://mangadex.org")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-site")
                    .header("DNT", "1")
                    .header("Sec-GPC", "1")
                    .header("TE", "trailers")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            // Send the HTTP request and return the response body
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during the request.";
        }
    }

}
