
package photo.droid;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ImageResult result = (ImageResult)
            getIntent().getSerializableExtra("result");
        SmartImageView ivResult = (SmartImageView)
            findViewById(R.id.ivResult);
        ivResult.setImageUrl(result.getFullUrl());
    }
}
