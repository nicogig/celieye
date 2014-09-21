package nicolagigante.celieye.test;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import nicolagigante.celieye.dataBaseCache.DbKey;
import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DbAdapter;

public class TestGridView extends Activity {
    private DbAdapter dbHelper;
    private DbKey dbKey;
    private String [] result;
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_test_grid_view);
       // setContentView(R.layout.activity_database);
        handleIntent(getIntent());
        Intent in = getIntent();
        query = in.getStringExtra("query");
        dbHelper = new DbAdapter(this);
        dbHelper.open();
        Cursor cursor;
        cursor = dbHelper.searchProductByDescription(query);
        int i = 0;
        result = new String[cursor.getCount()];
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Log.e("descrizione", cursor.getString(cursor.getColumnIndex("description")));
                result[i++] = cursor.getString(cursor.getColumnIndex("description"));
                Log.e("array", result[0]);
            }
            while (cursor.moveToNext());
        }
        else {
            Log.e("cursor", "cursor = null");
        }
        // La valorizzazione dell'array adapter avviene attraverso il passaggio del layout della View precedentemente config. e attraverso il widget che raccoglierà le informazioni presenti nell'array passato come ultimo parametro.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.textView50, result);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
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

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
             this.query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
