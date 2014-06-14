
package photo.droid;

public class ImageResult {
    private String fullUrl;
    private String thumbUrl;

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String toString() {
        return this.thumbUrl;
    }
}
