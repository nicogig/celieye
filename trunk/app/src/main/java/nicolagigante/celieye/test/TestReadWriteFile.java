package nicolagigante.celieye.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;
import java.io.IOException;

import nicolagigante.celieye.R;
import nicolagigante.celieye.gestioneFile.ReadWriteFile;

public class TestReadWriteFile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_read_write_file);
        try {
            ReadWriteFile  writeFile= new ReadWriteFile("test",MODE_APPEND,this);
            writeFile.writeFile("test di scrittura\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ReadWriteFile  readFile= new ReadWriteFile("test",MODE_APPEND,this);
            String read=readFile.readFile();
            Log.e("lettura file", read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_read_write_file, menu);
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
