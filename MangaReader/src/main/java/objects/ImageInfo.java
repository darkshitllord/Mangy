package objects;

public class ImageInfo {

    private String baseURL;
    private String hash;

    public ImageInfo(String baseURL, String hash) {
        this.baseURL = baseURL;
        this.hash = hash;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "baseURL='" + baseURL + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

}
