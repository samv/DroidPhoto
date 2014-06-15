
package photo.droid;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImageResult {
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
