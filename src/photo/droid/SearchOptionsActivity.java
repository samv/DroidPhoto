
package photo.droid;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import photo.droid.ImageSearchOptions;

public class SearchOptionsActivity extends Activity
    implements AdapterView.OnItemSelectedListener
{
    ImageSearchOptions options;

    Spinner spImageSize;
    Spinner spImageRights;
    Spinner spImageColor;
    Spinner spImageType;
    EditText etSite;
    Button btnSave;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findWidgets();
        connectSpinners();
        options = (ImageSearchOptions)
            getIntent().getSerializableExtra("options");
        setWidgets();
    }

    private void findWidgets() {
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageRights = (Spinner) findViewById(R.id.spImageRights);
        spImageColor = (Spinner) findViewById(R.id.spImageColor);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSite = (EditText) findViewById(R.id.etSite);
        btnSave = (Button) findViewById(R.id.btnSave);
    }

    private void connectSpinners() {
        connectSpinner(spImageSize, R.array.image_sizes);
        connectSpinner(spImageColor, R.array.image_colors);
        connectSpinner(spImageType, R.array.image_types);
        connectSpinner(spImageRights, R.array.image_rights);
    }

    private void setWidgets() {
        spImageSize.setSelection(options.imageSize.ordinal());
        spImageRights.setSelection(options.imageRights.ordinal());
        spImageColor.setSelection(options.imageColor.ordinal());
        spImageType.setSelection(options.imageType.ordinal());
        etSite.setText(options.site);
    }

    private void connectSpinner(Spinner sp, int resId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
            (this, resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
            (android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                long id)
    {
        if (options == null)
            return;
        Spinner spinner = (Spinner) parent;
        // XXX - this looked so much less ugly as a switch statement.
        // XXX2 - this is also so unnecessary.  Why not just read the spinner
        //        when the button is pressed.  duh.
        if (spinner == spImageSize) {
            options.imageSize = ImageSearchOptions.IMAGE_SIZE.values()[pos];
        }
        else if (spinner == spImageRights) {
            options.imageRights = ImageSearchOptions.IMAGE_RIGHTS.values()[pos];
        }
        else if (spinner == spImageColor) {
            options.imageColor = ImageSearchOptions.IMAGE_COLOR.values()[pos];
        }
        else if (spinner == spImageType) {
            options.imageType = ImageSearchOptions.IMAGE_TYPE.values()[pos];
        }      
        else {
            Log.d("DEBUG", "Doing nothing");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onSave(View v) {
        options.site = etSite.getText().toString();
        Intent i = new Intent();
        i.putExtra("options", options);
        setResult(RESULT_OK, i);
        this.finish();
    }
}
