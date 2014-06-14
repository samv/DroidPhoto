package photo.droid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class SearchResultsActivity extends Activity
{
    EditText etSearchString;
    GridView gvSearchResults;
    Button btnSearch;

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
}
