
package photo.droid;

import java.io.Serializable;

import android.net.Uri;

public class ImageSearchOptions implements Serializable {
    public enum IMAGE_SIZE {
        ANY, ICON, MEDIUM, XXLARGE, HUGE,
    }
    public enum IMAGE_COLOR {
        ANY,
        BLACK, BLUE, BROWN, GRAY, GREEN, ORANGE,
        PINK, PURPLE, RED, TEAL, WHITE, YELLOW,
    }
    public enum IMAGE_TYPE {
        ANY, FACE, PHOTO, CLIPART, LINEART,
    }
    public enum IMAGE_RIGHTS {
        ANY(""),
        CC_PUBLICDOMAIN("cc_publicdomain"),
        CC_ATTRIBUTE("cc_attribute"),
        CC_SHAREALIKE("cc_sharealike"),
        CC_NONDERIVED("cc_nonderived"),
        CC_NONCOMMERCIAL("cc_noncommercial"),
        CC_NC_ND("cc_noncommercial|cc_nonderived");

        public String as_rights;
        private IMAGE_RIGHTS(String _api_val) {
            this.as_rights = _api_val;
        }
    }

    public IMAGE_SIZE imageSize = IMAGE_SIZE.ANY;
    public IMAGE_COLOR imageColor = IMAGE_COLOR.ANY;
    public IMAGE_TYPE imageType = IMAGE_TYPE.ANY;
    public IMAGE_RIGHTS imageRights = IMAGE_RIGHTS.ANY;
    public String site = "";

    public ImageSearchOptions() { }

    public Uri uri(int start, String query) {
        Uri.Builder uriBuilder = new Uri.Builder();
        
        uriBuilder.scheme("https").authority("ajax.googleapis.com")
            .appendPath("ajax")
            .appendPath("services")
            .appendPath("search")
            .appendPath("images");

        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("v", "1.0");
        uriBuilder.appendQueryParameter("rsz", "8");

        if (start > 0)
            uriBuilder.appendQueryParameter("start", start + "");

        if (imageSize != IMAGE_SIZE.ANY)
            uriBuilder.appendQueryParameter
                ("imgsz", imageSize.name().toLowerCase());

        if (imageColor != IMAGE_COLOR.ANY)
            uriBuilder.appendQueryParameter
                ("imgcolor", imageColor.name().toLowerCase());
            
        if (imageType != IMAGE_TYPE.ANY)
            uriBuilder.appendQueryParameter
                ("imgtype", imageType.name().toLowerCase());

        if (imageRights != IMAGE_RIGHTS.ANY)
            uriBuilder.appendQueryParameter
                ("as_rights", imageRights.as_rights);

        if (site.length() > 0)
            uriBuilder.appendQueryParameter("as_sitesearch", site);
        
        return uriBuilder.build();
    }
}
