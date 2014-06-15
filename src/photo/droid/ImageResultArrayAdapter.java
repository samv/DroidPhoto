
package photo.droid;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import photo.droid.ImageResult;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }
}
