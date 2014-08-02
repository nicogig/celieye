package nicolagigante.celieye.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageButton;

import nicolagigante.celieye.R;
import nicolagigante.celieye.httpPost.AsyncTaskHttpPost;
import nicolagigante.celieye.jsonService.ReadJson;
import nicolagigante.celieye.model.ApplicationProdotti;
import nicolagigante.celieye.model.Prodotto;

import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Eye extends Activity implements OnClickListener {
   private TextView formatTxt, contentTxt, resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(1200);
        ImageButton b=(ImageButton)findViewById(R.id.imageButton);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        b.startAnimation(animation);
       b.setOnClickListener((View.OnClickListener) this);
        formatTxt = (TextView)findViewById(R.id.textView);
       contentTxt = (TextView)findViewById(R.id.textView2);
        resultTxt = (TextView)findViewById(R.id.textView3);
        ApplicationProdotti applicationProdotti = (ApplicationProdotti) getApplication();
        Prodotto prodotto=applicationProdotti.getProdotto();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.eye, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            clickSettings();
            return true; //Potrebbe essere eliminato(?)
        }
        if (id == R.id.action_cart) {
            clickCart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void clickSettings(){
        Intent enabler=new Intent(this, Settings.class);
        startActivity(enabler);
    }
    private void clickCart(){
        Intent enabler=new Intent(this, Cart.class);
        startActivity(enabler);
    }
   @Override
    public void onClick(View v) {
       if(v.getId()==R.id.imageButton){
            //Controlla per un click del bottone, e procede alla scansione.
           IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

        }
     }
    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        String url;
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
         if (scanningResult != null) {
//we have a result
             String scanContent = scanningResult.getContents();
             String scanFormat = scanningResult.getFormatName();
             formatTxt.setText("FORMAT: " + scanFormat);
             contentTxt.setText("CONTENT: " + scanContent);
             url = "https://api.scandit.com/v2/products/" + scanContent + "?key=C6NTW7yipvCc3NsOCRGMFwL0v30btTfc-f3D7i4E3Qz&sources=basic&timeout=2000";
             //-------------------------------change intent---------------------
          //  Intent i=new Intent(this, AsyncTaskHttpPost.class);
          //   i.putExtra("url", url);
           //  startActivity(i);
             //---------------------------------------------------------------
             Intent t=new Intent(this, DatabaseActivity.class);
             t.putExtra("barcode", scanContent);
             startActivity(t);



         }
         else{
             Toast toast = Toast.makeText(getApplicationContext(),
                     "No scan data received!", Toast.LENGTH_SHORT);
             toast.show();
            }
     }

}