package utils;

import data.Compress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class MangyAPI {

    private static final String API_ENDPOINT = "https://api.mangadex.org/manga";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:121.0) Gecko/20100101 Firefox/121.0";
    private static final int PAGE_SIZE = 100;

    public static String searchRequest(String searchQuery) {
        try {
            // Replace spaces with %20 in the search query
            String formattedSearchQuery = searchQuery.replace(" ", "%20");

            // Construct the API request URL dynamically
            String apiUrl = API_ENDPOINT + "?title=" + formattedSearchQuery + "&limit=20" +
                    "&contentRating%5B%5D=safe&contentRating%5B%5D=suggestive&contentRating%5B%5D=erotica" +
                    "&includes%5B%5D=cover_art&order%5Brelevance%5D=desc";

            // Send the HTTP request and return the response body
            return sendHttpRequest(apiUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getAllChapters(String mangaId) {
        List<String> allChapters = new ArrayList<>();
        int currentPage = 1;

        try {
            while (true) {
                // Construct the API request URL for manga chapters for the current page
                String apiUrl = "https://api.mangadex.org/manga/" + mangaId + "/feed" +
                        "?translatedLanguage[]=en" +
                        "&limit=" + PAGE_SIZE +
                        "&offset=" + ((currentPage - 1) * PAGE_SIZE) +
                        "&includes[]=scanlation_group" +
                        "&includes[]=user" +
                        "&order[volume]=desc" +
                        "&order[chapter]=desc";

                // Add the chapters from the current page to the list
                allChapters.add(sendHttpRequest(apiUrl));

                // Break the loop if there are no more chapters
                if (allChapters.get(allChapters.size() - 1) == null || allChapters.get(allChapters.size() - 1).isEmpty()) {
                    break;
                }

                // Move to the next page
                currentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allChapters;
    }

    public static String getChapters(String mangaId) {
        try {
            // Construct the API request URL for manga chapters
            String apiUrl = "https://api.mangadex.org/manga/" + mangaId + "/feed" +
                    "?translatedLanguage[]=en" +
                    "&limit=500" +
                    "&includes[]=scanlation_group" +
                    "&includes[]=user" +
                    "&order[volume]=desc" +
                    "&order[chapter]=desc";

            // Send the HTTP request and return the response body
            return sendHttpRequest(apiUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getChapterImages(String chapterId) {
        try {
            // Construct the API request URL for chapter images
            String apiUrl = "https://api.mangadex.org/at-home/server/" + chapterId;

            // Send the HTTP request and return the response body
            return sendHttpRequest(apiUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String sendHttpRequest(String apiUrl) throws Exception {
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
        HttpResponse<byte[]> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());

        // Check if the response is compressed and decompress if needed
        if ("gzip".equals(response.headers().firstValue("Content-Encoding").orElse(null))) {
            // Decompress the response body using the Compress class
            return Compress.decompress(response.body());
        } else {
            // If not compressed, return the response body as is
            return new String(response.body(), StandardCharsets.UTF_8);
        }
    }
}
