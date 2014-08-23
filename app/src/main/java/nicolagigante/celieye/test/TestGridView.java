package nicolagigante.celieye.test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import nicolagigante.celieye.dataBaseCache.DbKey;
import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DbAdapter;

public class TestGridView extends Activity {
    private DbAdapter dbHelper;
    private DbKey dbKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_grid_view);
        setContentView(R.layout.activity_database);
        dbHelper = new DbAdapter(this);
        dbHelper.open();
        Cursor cursor;
        GridView grv = (GridView) findViewById(R.id.grvData);
        cursor = dbHelper.searchProductByDescription("pat");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.e("descrizione", cursor.getString(cursor.getColumnIndex("description")));
            }
            while (cursor.moveToNext());
        }
        else {
            Log.e("cursor", "cursor = null");
        }

        dbHelper.close();
        /*
        if (cursor != null) {
            startManagingCursor(cursor);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    1, cursor, new String[] { "Testo1",
                    "Testo2", "Testo3" }, new int[] {
                    1, 2, 3 } );
            adapter.setViewResource(R.layout.activity_test_grid_view);
            grv.setAdapter(adapter);
        }*/
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
