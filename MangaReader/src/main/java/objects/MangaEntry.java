package objects;

public class MangaEntry {

    private String title;
    private String mangaID;

    public MangaEntry(String title, String mangaID) {
        this.title = title;
        this.mangaID = mangaID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMangaID() {
        return mangaID;
    }

    public void setMangaID(String mangaID) {
        this.mangaID = mangaID;
    }

    @Override
    public String toString() {
        return "MangaEntry{" +
                "title='" + title + '\'' +
                ", mangaID='" + mangaID + '\'' +
                '}';
    }

}
