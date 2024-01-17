package com.example.mangareader;

import Data.Parse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import objects.ChapterEntry;
import objects.MangaEntry;
import server.MangyAPI;

import java.util.List;

import static Data.Parse.parseMangaTitles;

public class HelloController {
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultListView;

    @FXML
    private ListView<String> chaptersListView;

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchBar.getText();
        String jsonResponse = MangyAPI.searchRequest(searchQuery);

        List<MangaEntry> mangaEntries = parseMangaTitles(jsonResponse);

        // Extract titles for display in ListView
        ObservableList<String> titles = FXCollections.observableArrayList();
        mangaEntries.forEach(entry -> titles.add(entry.getTitle()));

        // Set the items of the ListView
        resultListView.setItems(titles);

        // Set up a click event listener for the ListView items
        resultListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Single-click detected, get the selected MangaEntry
                MangaEntry selectedEntry = mangaEntries.get(resultListView.getSelectionModel().getSelectedIndex());

                // Retrieve the manga ID and title from the selected entry
                String mangaId = selectedEntry.getMangaID();
                String title = selectedEntry.getTitle();

                // Call the method to get chapters using the manga ID
                String chaptersResponse = MangyAPI.getChapters(mangaId);

                // Parse chapters
                List<ChapterEntry> chapters = Parse.parseChapters(chaptersResponse);

                // Extract chapter numbers for display in ListView
                ObservableList<String> chapterNumbers = FXCollections.observableArrayList();
                chapters.forEach(entry -> chapterNumbers.add(entry.getChapter()));

                // Set the items of the chapters ListView
                chaptersListView.setItems(chapterNumbers);
            }
        });

    }

}