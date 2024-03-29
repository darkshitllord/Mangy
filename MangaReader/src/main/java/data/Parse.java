package data;

import objects.ImageInfo;
import objects.MangaEntry;
import objects.ChapterEntry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parse {
    public static List<MangaEntry> parseMangaTitles(String jsonResponse) {
        List<MangaEntry> mangaEntries = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray results = json.getJSONArray("data");

            for (int i = 0; i < results.length(); i++) {
                JSONObject manga = results.getJSONObject(i);
                JSONObject attributes = manga.getJSONObject("attributes");
                JSONObject titleObject = attributes.getJSONObject("title");

                String mangaId = manga.getString("id");
                String englishTitle = titleObject.getString("en");

                mangaEntries.add(new MangaEntry(englishTitle, mangaId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mangaEntries;
    }

    public static List<ChapterEntry> parseChapters(String jsonResponse) {
        List<ChapterEntry> chapters = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject chapterObject = data.getJSONObject(i);
                JSONObject attributes = chapterObject.getJSONObject("attributes");

                    String chapterId = chapterObject.getString("id");
                    String chapterNumber = attributes.getString("chapter");

                    // Check if the "title" field is a string
                    String chapterTitle = attributes.optString("title", "");

                    ChapterEntry chapterEntry = new ChapterEntry(chapterId, chapterNumber, chapterTitle);
                    chapters.add(chapterEntry);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chapters;
    }

    public static List<String> parseImageNames(String jsonResponse) {
        List<String> imageNames = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResponse);

            // Check if the result is "ok"
            if ("ok".equalsIgnoreCase(json.getString("result"))) {
                JSONObject chapter = json.getJSONObject("chapter");
                JSONArray data = chapter.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    imageNames.add(data.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageNames;
    }

    public static ImageInfo parseImageInfo(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);

            // Check if the result is "ok"
            if ("ok".equalsIgnoreCase(json.getString("result"))) {
                String baseUrl = json.getString("baseUrl");
                JSONObject chapter = json.getJSONObject("chapter");
                String hash = chapter.getString("hash");

                return new ImageInfo(baseUrl, hash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
