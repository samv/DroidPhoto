package photo.droid;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import photo.droid.ImageResult;

public class ImageSearchRSData {

    public ArrayList<ImageResult> results;

    public ImageSearchRSData() {
        results = new ArrayList<ImageResult>();
    }

    public ArrayList<ImageResult> addImageResult(ImageResult ir) {
        results.add(ir);
        return results;
    }
}
