package controllers;

import data.Parse;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import objects.ChapterEntry;
import objects.MangaEntry;
import utils.MangaControllerUtils;
import utils.MangyAPI;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> resultListView;

    @FXML
    private ListView<String> chaptersListView;

    // Declare chapters as a field
    private List<ChapterEntry> chapters = new ArrayList<>();

    private List<String> imageNames = new ArrayList<>();

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchBar.getText();
        String jsonResponse = MangyAPI.searchRequest(searchQuery);

        List<MangaEntry> mangaEntries = Parse.parseMangaTitles(jsonResponse);

        MangaControllerUtils.displayMangaTitles(mangaEntries, resultListView);

        // Set up a click event listener for the ListView items
        resultListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MangaControllerUtils.handleMangaEntryClick(mangaEntries, resultListView, chaptersListView, chapters);
            }
        });

        chaptersListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MangaControllerUtils.handleChapterEntryClick(chapters, chaptersListView, imageNames);
            }
        });
    }
}
