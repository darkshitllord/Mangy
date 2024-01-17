package com.example.mangareader;

import Data.Parse;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.MangyAPI;

public class HelloController {
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultListView;

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchBar.getText();
        String jsonResponse = MangyAPI.searchRequest(searchQuery);
        ObservableList<String> titles = Parse.parseMangaTitles(jsonResponse);
        resultListView.setItems(titles);
    }
}