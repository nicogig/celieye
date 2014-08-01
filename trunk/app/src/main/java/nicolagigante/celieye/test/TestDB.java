package nicolagigante.celieye.test;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DbAdapter;

public class TestDB extends Activity {
    private DbAdapter dbHelper;
    private Cursor cursor;
    String TAG="test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_db, menu);

        dbHelper = new DbAdapter(this);
        dbHelper.open();
        cursor = dbHelper.fetchAllContacts();
        dbHelper.close();

        startManagingCursor(cursor);

        while ( cursor.moveToNext() ) {

            String contactID = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_CONTACTID) );
            Log.d(TAG, "contact id = " + contactID);
        }
        cursor.close();

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
