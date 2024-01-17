package utils;

import data.Parse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import objects.ChapterEntry;
import objects.MangaEntry;
import utils.MangyAPI;

import java.util.List;

public class MangaControllerUtils {

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
            String title = selectedEntry.getTitle();

            // Call the method to get chapters using the manga ID
            String chaptersResponse = MangyAPI.getChapters(mangaId);

            // Parse chapters
            chapters.clear();
            chapters.addAll(Parse.parseChapters(chaptersResponse));

            MangaControllerUtils.displayChapterNumbers(chapters, chaptersListView);
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
            }
        }
    }
}