package controllers;

import data.Parse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import objects.ChapterEntry;
import objects.MangaEntry;
import utils.MainControllerUtils;
import utils.MangyAPI;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> resultListView;
    @FXML
    private StackPane imageStackPane;

    @FXML
    private ListView<String> chaptersListView;

    @FXML
    private ImageView mangaImageView;
    private ArrayList<String> imageUrls;

    @FXML
    private void initialize() {
        MainControllerUtils.setMainController(this);

        mangaImageView.fitWidthProperty().bind(imageStackPane.widthProperty());
        mangaImageView.fitHeightProperty().bind(imageStackPane.heightProperty());
    }

    public void resetCurrentImageIndex() {
        currentImageIndex = 0;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    // Declare chapters as a field
    private List<ChapterEntry> chapters = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();
    private int currentImageIndex = 0;

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchBar.getText();
        String jsonResponse = MangyAPI.searchRequest(searchQuery);

        List<MangaEntry> mangaEntries = Parse.parseMangaTitles(jsonResponse);

        MainControllerUtils.displayMangaTitles(mangaEntries, resultListView);

        // Set up a click event listener for the ListView items
        resultListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MainControllerUtils.handleMangaEntryClick(mangaEntries, resultListView, chaptersListView, chapters);
            }
        });

        chaptersListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MainControllerUtils.handleChapterEntryClick(chapters, chaptersListView, imageNames);
                // Reset the current image index when a new chapter is selected
                currentImageIndex = 0;
            }
        });
    }

    public void updateImageView(String imageUrl) {
            Image image = new Image(imageUrl);
            mangaImageView.setImage(image);
    }

    @FXML
    public void previousButtonClicked(ActionEvent actionEvent) {
        if (!imageUrls.isEmpty()) {
            currentImageIndex = (currentImageIndex - 1 + imageUrls.size()) % imageUrls.size();
            updateImageViewWithCurrentIndex();
        }
    }

    @FXML
    public void nextButtonClicked(ActionEvent actionEvent) {
        if (!imageUrls.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % imageUrls.size();
            updateImageViewWithCurrentIndex();
        }
    }

    public void updateImageViewWithCurrentIndex() {
        if (!imageUrls.isEmpty()) {
            String imageUrl;
            synchronized (imageUrls) {
                imageUrl = imageUrls.get(currentImageIndex);
            }

            // Use Platform.runLater to update UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                Image image = new Image(imageUrl);
                ImageView newImageView = new ImageView(image);

                // Set preserveRatio to true and fitWidth/fitHeight to match the current size of the StackPane
                newImageView.setPreserveRatio(true);
                newImageView.setFitWidth(imageStackPane.getWidth());
                newImageView.setFitHeight(imageStackPane.getHeight());

                // Replace the old ImageView with the new one in your layout
                ((Pane) mangaImageView.getParent()).getChildren().set(
                        ((Pane) mangaImageView.getParent()).getChildren().indexOf(mangaImageView), newImageView);
                mangaImageView = newImageView;
            });

        }
    }



}
