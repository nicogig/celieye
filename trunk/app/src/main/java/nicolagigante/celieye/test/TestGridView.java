package nicolagigante.celieye.test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DbAdapter;

public class TestGridView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_grid_view);
        DbAdapter dbh = new DbAdapter(this);
        dbh.open();
        Cursor cursor;
        GridView grv = (GridView) findViewById(R.id.grvData);
        cursor = dbh.searchProductByDescription("testo da ricercare");
        dbh.close();
        if (cursor != null) {
            startManagingCursor(cursor);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    1, cursor, new String[] { "Testo1",
                    "Testo2", "Testo3" }, new int[] {
                    1, 2, 3 } );
            adapter.setViewResource(R.layout.activity_test_grid_view);
            grv.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
