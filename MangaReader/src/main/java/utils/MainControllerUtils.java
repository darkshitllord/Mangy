package utils;

import controllers.MainController;
import data.Parse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import objects.ChapterEntry;
import objects.ImageInfo;
import objects.MangaEntry;

import java.util.ArrayList;
import java.util.List;

public class MainControllerUtils {

    private static Stage primaryStage;
    private static MainController mainController;
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    public static void setMainController(MainController controller) {
        mainController = controller;
    }

    public static void displayMangaTitles(List<MangaEntry> mangaEntries, ListView<String> resultListView) {
        // Extract titles for display in ListView
        ObservableList<String> titles = FXCollections.observableArrayList();
        mangaEntries.forEach(entry -> titles.add(entry.getTitle()));

        // Set the items of the ListView
        resultListView.setItems(titles);
    }

    public static void handleMangaEntryClick(List<MangaEntry> mangaEntries, ListView<String> resultListView,
                                             ListView<String> chaptersListView, List<ChapterEntry> chapters) {
        if (resultListView.getSelectionModel().getSelectedItem() != null) {
            // Single-click detected, get the selected MangaEntry
            MangaEntry selectedEntry = mangaEntries.get(resultListView.getSelectionModel().getSelectedIndex());

            // Retrieve the manga ID and title from the selected entry
            String mangaId = selectedEntry.getMangaID();

            // Call the method to get chapters using the manga ID
            String chaptersResponse = MangyAPI.getChapters(mangaId);

            // Parse chapters
            chapters.clear();
            chapters.addAll(Parse.parseChapters(chaptersResponse));

            MainControllerUtils.displayChapterNumbers(chapters, chaptersListView);
        }
    }

    public static void displayChapterNumbers(List<ChapterEntry> chapters, ListView<String> chaptersListView) {
        // Extract chapter numbers for display in ListView
        ObservableList<String> chapterNumbers = FXCollections.observableArrayList();
        chapters.forEach(entry -> chapterNumbers.add(entry.getChapterNumber()));

        // Set the items of the chapters ListView
        chaptersListView.setItems(chapterNumbers);
    }

    public static void handleChapterEntryClick(List<ChapterEntry> chapters, ListView<String> chaptersListView,
                                               List<String> imageNames) {
        if (chaptersListView.getSelectionModel().getSelectedItem() != null) {
            // Reset the current image index when switching to a new chapter
            mainController.resetCurrentImageIndex();

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
                imageNames.clear();
                imageNames.addAll(Parse.parseImageNames(chapterImagesResponse));

                ImageInfo imageInfo = Parse.parseImageInfo(chapterImagesResponse);

                if (!imageNames.isEmpty()) {
                    // Create image URLs
                    ArrayList<String> imageUrls = createImageURLs(imageInfo, imageNames);

                    // Set imageUrls in the MainController instance
                    mainController.setImageUrls(imageUrls);

                    // Update the image view based on the current index
                    mainController.updateImageViewWithCurrentIndex();
                }
            }
        }
    }

    public static ArrayList<String> createImageURLs(ImageInfo imageInfo, List<String> imageNames) {
        ArrayList<String> imageUrls = new ArrayList<>();

        for (String imageName : imageNames) {
            String imageUrl = imageInfo.getBaseURL() + "/data/" + imageInfo.getHash() + "/" + imageName;
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

}