package photo.droid;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

import photo.droid.ImageSearchRSData;

public class ImageSearchRS {

    int _status;
    ImageSearchRSData _data;

    public ImageSearchRS(@JsonProperty("responseStatus") int status,
                         @JsonProperty("responseData") ImageSearchRSData data) {
        _status = status;
        _data = data;
    }

    @JsonProperty("responseStatus")
    public int getStatus() {
        return _status;
    }

    @JsonProperty("responseData")
    public ImageSearchRSData getData() {
        return _data;
    }
}
