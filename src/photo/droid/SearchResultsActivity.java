package photo.droid;

import java.util.ArrayList;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import photo.droid.ImageDisplayActivity;
import photo.droid.ImageResult;
import photo.droid.ImageResultArrayAdapter;
import photo.droid.ImageSearchOptions;
import photo.droid.ImageSearchRS;
import photo.droid.SearchOptionsActivity;

public class SearchResultsActivity extends Activity
    implements MenuItem.OnMenuItemClickListener
    implements AbsListView.OnScrollListener
{
    EditText etSearchString;
    GridView gvSearchResults;
    Button btnSearch;

    ImageSearchOptions options = new ImageSearchOptions();
    String query = "";

    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    int lastImageVisible = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setupViews();
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvSearchResults.setAdapter(imageAdapter);
        gvSearchResults.setOnItemClickListener
            (new AdapterView.OnItemClickListener() {
                    @Override public void onItemClick
                        (AdapterView<?> adapter, View parent, int position,
                         long rowid) {
                        Intent i = new Intent
                            (getApplicationContext(), ImageDisplayActivity.class);
                        ImageResult imageResult = imageResults.get(position);
                        i.putExtra("result", imageResult);
                        startActivity(i);
                    }
             });
    }

    private void setupViews()
    {
        etSearchString = (EditText) findViewById(R.id.etSearchString);
        gvSearchResults = (GridView) findViewById(R.id.gvSearchResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }

    public void onImageSearch(View v) {
        query = etSearchString.getText().toString();
        Toast.makeText
            (this, "Searching for " + query, Toast.LENGTH_LONG)
            .show();
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("DEBUG", "Searching for " + query);

        final Toast successToast = Toast.makeText
            (this, "Succeed!", Toast.LENGTH_LONG);
        final Toast failureToast = Toast.makeText
            (this, "FAIL!", Toast.LENGTH_LONG);

        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
                public void onSuccess
                    (int statusCode, Header[] headers, byte[] responseBody) {
                    successToast.show();
                    ObjectMapper jackson = new ObjectMapper();
                    jackson.configure
                        (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    //Log.d("DEBUG", "Got response: " + new String(responseBody));
                    try {
                        ImageSearchRS response = jackson.readValue
                            (responseBody, ImageSearchRS.class);
                        imageResults.clear();
                        imageAdapter.addAll(response.getData().results);
                        //imageAdapter.notify();
                    }
                    catch (IOException e) {
                    }
                    //Log.d("DEBUG", "results are now: " + imageResults.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                {
                    failureToast.show();
                }
            };

        String uri = options.uri(0, query).toString();
        Log.d("DEBUG", "uri is " + uri);

        client.get(uri, handler);
    }

    private void setLastImage(int pos) {
        lastImageVisible = pos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.miSettings);
        item.setOnMenuItemClickListener(this);
        return true;
    }

    private final int REQUEST_OPTIONS = 42;

    public boolean onMenuItemClick(MenuItem mi) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, SearchOptionsActivity.class);
        i.putExtra("options", options);
        Log.d("DEBUG", "sent size " + options.imageSize);
        startActivityForResult(i, REQUEST_OPTIONS);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_OPTIONS) {
            Log.d("DEBUG", "Updating options");
            options = (ImageSearchOptions)
                data.getSerializableExtra("options");
            Log.d("DEBUG", "received size " + options.imageSize);
        }
    } 

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        if (lastImageVisible < totalItemCount) {
            setLastImage(totalItemCount);
        }
    }
}
