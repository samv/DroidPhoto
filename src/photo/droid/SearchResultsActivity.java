package photo.droid;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import photo.droid.ImageResult;

public class SearchResultsActivity extends Activity
{
    EditText etSearchString;
    GridView gvSearchResults;
    Button btnSearch;

    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setupViews();
    }

    private void setupViews()
    {
        etSearchString = (EditText) findViewById(R.id.etSearchString);
        gvSearchResults = (GridView) findViewById(R.id.gvSearchResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }

    public void onImageSearch(View v) {
        String query = etSearchString.getText().toString();
        Toast.makeText
            (this, "Searching for " + query, Toast.LENGTH_LONG)
            .show();
    }
}
