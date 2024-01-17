package Data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Parse {
    public static ObservableList<String> parseMangaTitles(String jsonResponse) {
        ObservableList<String> titles = FXCollections.observableArrayList();

        try {
            System.out.println("JSON Response: " + jsonResponse); // Add this line for debugging

            JSONObject json = new JSONObject(jsonResponse);
            JSONArray results = json.getJSONArray("data");

            for (int i = 0; i < results.length(); i++) {
                JSONObject manga = results.getJSONObject(i);
                JSONObject attributes = manga.getJSONObject("attributes");
                JSONObject titleObject = attributes.getJSONObject("title");

                // Extract only the English title
                String englishTitle = titleObject.getString("en");
                titles.add(englishTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return titles;
    }
}
