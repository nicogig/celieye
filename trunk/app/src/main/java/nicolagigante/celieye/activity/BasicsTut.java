package nicolagigante.celieye.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nicolagigante.celieye.R;

public class BasicsTut extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_basics_tut);
    }

    public void stepnext(View view){
        Intent enabler=new Intent(this, FinishTutorial.class);
        startActivity(enabler);
    }
}
