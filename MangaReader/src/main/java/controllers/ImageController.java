package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.List;

public class ImageController {

    @FXML
    private StackPane imagePane;

    private int currentIndex = 0;
    private List<String> imageNames;

    public ImageController() {
        // Initialize your imageNames list if needed
        // For example: imageNames = new ArrayList<>();
    }

    @FXML
    private void initialize() {
        // Add initialization code if needed
    }

    public void displayChapterImages(List<String> imageNames) {
        this.imageNames = imageNames;
        showImage();
    }

    @FXML
    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            showImage();
        }
    }

    @FXML
    private void showNextImage() {
        if (currentIndex < imageNames.size() - 1) {
            currentIndex++;
            showImage();
        }
    }

    private void showImage() {
        if (imageNames != null && !imageNames.isEmpty()) {
            String imageUrl = "https://uploads.mangadex.org/data/cab70eac9f139d6525340c061eedac83/" + imageNames.get(currentIndex);
            Image image = new Image(imageUrl);
            ImageView imageView = new ImageView(image);

            // Clear existing content and add the new image
            imagePane.getChildren().clear();
            imagePane.getChildren().add(imageView);
        }
    }
}
