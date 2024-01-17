package com.example.mangareader;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.MangyAPI;

public class HelloController {
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchBar.getText();
        System.out.println(MangyAPI.searchRequest(searchQuery));
    }
}