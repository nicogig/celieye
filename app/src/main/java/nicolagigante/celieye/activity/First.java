package nicolagigante.celieye.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DownloaderThread;

public class First extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_first);

        //-------------------------------------------------

        //-------------------------------------------------


    }

        public void first(View view){
        Intent enabler=new Intent(this, BasicsTut.class);
        startActivity(enabler);
    }
}
