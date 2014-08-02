package nicolagigante.celieye.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DbAdapter;
import nicolagigante.celieye.dataBaseCache.DatabaseHelper;

public class DatabaseActivity extends Activity {

    private DbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent t=getIntent();
        String barcode=t.getStringExtra("barcode");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        dbHelper = new DbAdapter(this);
        dbHelper.open();
        boolean flag = dbHelper.veifyProduct(barcode);
        //cursor = dbHelper.fetchAllContacts();
        dbHelper.close();
        if (flag) {
            Intent good=new Intent(this, Good.class);
            startActivity(good);
        }
        else{
            Intent bad=new Intent(this, bad.class);
            startActivity(bad);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.database, menu);
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
