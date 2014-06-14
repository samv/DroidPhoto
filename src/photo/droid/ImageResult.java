
package photo.droid;

public class ImageResult {
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
        }
        catch (JSONException e) {
            this.fullUrl = null;
            this.thumbUrl = null;
        }
    }

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
