package objects;

public class ChapterEntry {

    private String id;
    private String chapter;
    private String title;

    public ChapterEntry(String id, String chapter, String title) {
        this.id = id;
        this.chapter = chapter;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
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
                "id='" + id + '\'' +
                ", chapter='" + chapter + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
