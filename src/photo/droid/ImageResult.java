
package photo.droid;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResult implements Serializable {
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(@JsonProperty("url") String full,
                       @JsonProperty("tbUrl") String thumb) {
        fullUrl = full;
        thumbUrl = thumb;
    }

    @JsonProperty("url")
    public String getFullUrl() {
        return fullUrl;
    }

    @JsonProperty("tbUrl")
    public String getThumbUrl() {
        return thumbUrl;
    }

    public String toString() {
        return this.thumbUrl;
    }
}
