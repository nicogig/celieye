package nicolagigante.celieye.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nicolagigante.celieye.R;

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
    }
}
    public void Info(View view) {
// Do something in response to button
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);

    }
    public void ReleaseNotes(View view) {
// Do something in response to button
        Intent intent = new Intent(this, ReleaseNotes.class);
        startActivity(intent);

    }
    public void ToDo(View view) {
// Do something in response to button
        Intent intent = new Intent(this, ToDo.class);
        startActivity(intent);

    }
    public void Design(View view) {
// Do something in response to button
        Intent intent = new Intent(this, Design.class);
        startActivity(intent);

    }
    public void Good(View view) {
// Do something in response to button
        Intent intent = new Intent(this, Good.class);
        startActivity(intent);

    }
    public void Bad(View view) {
// Do something in response to button
        Intent intent = new Intent(this, bad.class);
        startActivity(intent);

    }
    public void DatabaseUpdate(View view) {
// Do something in response to button
        Intent intent = new Intent(this, AndroidFileDownloader.class);
        startActivity(intent);

    }
}
