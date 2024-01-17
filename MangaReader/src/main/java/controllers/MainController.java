package controllers;

import Data.Parse;
import com.example.mangareader.ImageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.ChapterEntry;
import objects.MangaEntry;
import server.MangyAPI;

import java.io.IOException;
import java.util.List;

import static Data.Parse.parseMangaTitles;

public class MainController {
    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultListView;

    @FXML
    private ListView<String> chaptersListView;

    // Declare chapters as a field
    private List<ChapterEntry> chapters;

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
                chapters = Parse.parseChapters(chaptersResponse);

                // Extract chapter numbers for display in ListView
                ObservableList<String> chapterNumbers = FXCollections.observableArrayList();
                chapters.forEach(entry -> chapterNumbers.add(entry.getChapterNumber()));

                // Set the items of the chapters ListView
                chaptersListView.setItems(chapterNumbers);
            }
        });

        chaptersListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Single-click detected, get the selected chapter number
                String selectedChapter = chaptersListView.getSelectionModel().getSelectedItem();

                // Find the corresponding ChapterEntry from the previously fetched chapters
                ChapterEntry selectedChapterEntry = chapters.stream()
                        .filter(entry -> entry.getChapterNumber().equals(selectedChapter))
                        .findFirst()
                        .orElse(null);

                if (selectedChapterEntry != null) {
                    // Call the method to get chapter images using the chapter ID
                    String chapterImagesResponse = MangyAPI.getChapterImages(selectedChapterEntry.getChapterID());

                    // Parse chapter images
                    List<String> imageNames = Parse.parseImageNames(chapterImagesResponse);

                    // Open the image viewer with the selected chapter's image names
                    openImageViewer(imageNames);
                }
            }
        });


    }

    @FXML
    private void openImageViewer(List<String> imageUrls) {
        try {
            // Load the new FXML file and create a new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("image-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();

            // Set the new controller and pass necessary data
            ImageController imageViewerController = loader.getController();
            imageViewerController.showImage(0, imageUrls);

            // Set up the stage and show it
            stage.setScene(new Scene(root));
            stage.setTitle("Image Viewer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}