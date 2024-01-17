package objects;

public class ChapterEntry {

    private String chapterID;
    private String chapterNumber;
    private String title;

    public ChapterEntry(String id, String chapter, String title) {
        this.chapterID = id;
        this.chapterNumber = chapter;
        this.title = title;
    }

    public String getChapterID() {
        return chapterID;
    }

    public void setChapterID(String chapterID) {
        this.chapterID = chapterID;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ChapterEntry{" +
                "id='" + chapterID + '\'' +
                ", chapter='" + chapterNumber + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
