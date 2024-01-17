package com.example.mangareader;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ImageController {
    @FXML
    private ImageView imageView;

    private int currentIndex;
    private List<String> imageUrls;

    public void showImage(int index, List<String> imageUrls) {
        this.currentIndex = index;
        this.imageUrls = imageUrls;

        if (index >= 0 && index < imageUrls.size()) {
            String imageUrl = imageUrls.get(index);

            // Load and display the image
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        }
    }

    @FXML
    private void showPreviousImage() {
        if (currentIndex > 0) {
            showImage(currentIndex - 1, imageUrls);
        }
    }

    @FXML
    private void showNextImage() {
        if (currentIndex < imageUrls.size() - 1) {
            showImage(currentIndex + 1, imageUrls);
        }
    }
}
