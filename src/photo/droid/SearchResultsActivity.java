package photo.droid;

import java.util.ArrayList;
import java.io.IOException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import photo.droid.ImageResult;
import photo.droid.ImageSearchRS;

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
        imageResults = new ArrayList<ImageResult>();
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
        AsyncHttpClient client = new AsyncHttpClient();

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) throws IOException {

                    ObjectMapper jackson = new ObjectMapper();
                    jackson.configure
                        (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ImageSearchRS response = jackson.readValue
                        (responseBody, ImageSearchRS.class);
                    imageResults.clear();
                    imageResults.addAll(response.getData().results);
                }
            };

        client.get
            ("https://ajax.googleapis.com/ajax/services/search/images?" +
             "rsz=8&start=" + 0 + "&v=1.0&q=" + Uri.encode(query),
             handler);
    }
}
