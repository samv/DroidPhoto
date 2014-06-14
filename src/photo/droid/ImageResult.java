
package photo.droid;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ImageResult {
    public String fullUrl;
    public String thumbUrl;

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
