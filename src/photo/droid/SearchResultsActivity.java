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
    implements MenuItem.OnMenuItemClickListener,
               AbsListView.OnScrollListener
{
    EditText etSearchString;
    GridView gvSearchResults;
    Button btnSearch;

    ImageSearchOptions options = new ImageSearchOptions();
    String query = "";

    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;

    int lastImageVisible = 0;
    int nextPage = 0;
    AsyncHttpClient client;
    ResponseCallback handler;

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
        gvSearchResults.setOnScrollListener(this);
    }

    private void setupViews()
    {
        etSearchString = (EditText) findViewById(R.id.etSearchString);
        gvSearchResults = (GridView) findViewById(R.id.gvSearchResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }

    public void onImageSearch(View v) {
        String q = etSearchString.getText().toString();
        query = q;
        imageAdapter.clear();
        lastImageVisible = 0;
        nextPage = 0;
        Toast.makeText
            (this, "Searching for " + q, Toast.LENGTH_LONG)
            .show();
        client = new AsyncHttpClient();
        Log.d("DEBUG", "Searching for " + q);

        String uri = options.uri(0, q).toString();
        handler = new ResponseCallback(q, 0, uri);
        Log.d("DEBUG", "uri is " + uri);
        client.get(uri, handler);
    }

   private class ResponseCallback extends AsyncHttpResponseHandler {
       private String _query;
       private int _startidx;
       private String _uri;
       private boolean triggered;
       public ResponseCallback(String query, int start, String uri) {
           _query = query;
           recycle(start, uri);
       }

       public void recycle(int start, String uri) {
           _startidx = start;
           _uri = uri;
           triggered = false;
       }

       public boolean active() {
           return !triggered;
       }

       @Override
       public void onSuccess
           (int statusCode, Header[] headers, byte[] responseBody) {
           triggered = true;
           if (query != _query)
               return;

           ObjectMapper jackson = new ObjectMapper();
           jackson.configure
               (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

           try {
               ImageSearchRS response = jackson.readValue
                   (responseBody, ImageSearchRS.class);

               imageAdapter.addAll(response.getData().results);
               lastImageVisible = gvSearchResults.getLastVisiblePosition();
               nextPage = response.getData().cursor.next_start();
               Log.d("DEBUG", "Next page starts at " + nextPage + 
                     ", last Image Visible = " + lastImageVisible +
                     ", images in adapter = " + imageAdapter.getCount());

               if (((lastImageVisible + 6) > imageAdapter.getCount()) ||
                   ((lastImageVisible == -1) && (imageAdapter.getCount() < 32))
                   ) {
                   getNextPage();
               }
               else {
                   Log.d("DEBUG", "All done!  lastImageVisible = " +
                         lastImageVisible + ", imageAdapter.getCount() = " +
                         imageAdapter.getCount());
               }
           }
           catch (IOException e) {
               Log.d("DEBUG", "Caught IOException :-(");
           }
       }
        
       @Override
       public void onFailure(int statusCode, Header[] headers,
                             byte[] responseBody, Throwable error)
       {
           triggered = true;
           showMessage("Failed to load " + _uri);
       }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    
    private void getNextPage() {
        if (nextPage > 0) {
            if (handler.active()) {
                Log.d("DEBUG", "request already in progress");
            }
            else {
                String uri = options.uri(nextPage, query).toString();
                handler.recycle(nextPage, query);
                Log.d("DEBUG", "uri is " + uri);
                client.get(uri, handler);
            }
        }
        else {
            Log.d("DEBUG", "End of results!");
        }
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

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        Log.d("DEBUG", "onScroll(" + view + ", " + firstVisibleItem +
              ", " + visibleItemCount + ", " + totalItemCount + ")");
        if (firstVisibleItem + visibleItemCount == imageAdapter.getCount()) {
            Log.d("DEBUG", "let's get some more");
            getNextPage();
        }
        else {
            Log.d("DEBUG", "We already have " + imageAdapter.getCount() +
                  " items");
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d("DEBUG", "onScrollStateChanged(" + view + ", " +
              scrollState + ")");
    }
}
