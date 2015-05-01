package nicolagigante.celieye.activity;

/**
 * Created by genio_2 on 26/07/2014.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import nicolagigante.celieye.R;
import nicolagigante.celieye.dataBaseCache.DownloaderThread;

public class AndroidFileDownloader extends Activity implements OnClickListener
{

    // Used to communicate state changes in the DownloaderThread
    public static final int MESSAGE_DOWNLOAD_STARTED = 1000;
    public static final int MESSAGE_DOWNLOAD_COMPLETE = 1001;
    public static final int MESSAGE_UPDATE_PROGRESS_BAR = 1002;
    public static final int MESSAGE_DOWNLOAD_CANCELED = 1003;
    public static final int MESSAGE_CONNECTING_STARTED = 1004;
    public static final int MESSAGE_ENCOUNTERED_ERROR = 1005;


    // instance variables
    private AndroidFileDownloader thisActivity;
    private Thread downloaderThread;
    private ProgressDialog progressDialog;
    File destFile;
    File outFile;
    String fileName = "dbaic.db";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        thisActivity = this;
        downloaderThread = null;
        progressDialog = null;
        setContentView(R.layout.android_file_downloader);
        Button button = (Button) this.findViewById(R.id.download_button);
        button.setOnClickListener(this);
        outFile = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        destFile = new File(this.getApplicationContext().getFilesDir() + "/" + fileName);
        Intent e = new Intent(this, Eye.class);

    }
    public static void copyFile(File src, File dst) throws IOException
    {
        Log.i("copyFile", "entrato");

        FileChannel inChannel = new FileInputStream(src).getChannel();
        Log.i("inChannel", inChannel.toString());
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        Log.i("outChannel", outChannel.toString());
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
            Log.i("inChannel Transfer", inChannel.toString());
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static void copyFiletoSD(File src, File dst) throws IOException
    {
        Log.i("copyFileSD", "entrato");

        FileChannel inChannel = new FileInputStream(src).getChannel();
        Log.i("inChannel", inChannel.toString());
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        Log.i("outChannel", outChannel.toString());
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
            Log.i("inChannel Transfer", inChannel.toString());
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
/*
    private void copyFile(OutputStream os, InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while((length = is.read(buffer))>0){
            os.write(buffer, 0, length);
        }
        os.flush();
    }
*/
    /** Called when the user clicks on something. */
    @Override
    public void onClick(View view)
    {
        TextView urlInputField = (TextView) this.findViewById(R.id.url_input);
        String urlInput = urlInputField.getText().toString();
        downloaderThread = new DownloaderThread(thisActivity, "https://github.com/nicogig/celieye_db/raw/master/dbaic.db");
        downloaderThread.start();
    }

    public void toSD(View view){
        try {
            copyFiletoSD(destFile, outFile);
            Log.i("copyFileToSD", "copyFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This is the Handler for this activity. It will receive messages from the
     * DownloaderThread and make the necessary updates to the UI.
     */
    public Handler activityHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                                /*
                                 * Handling MESSAGE_UPDATE_PROGRESS_BAR:
                                 * 1. Get the current progress, as indicated in the arg1 field
                                 *    of the Message.
                                 * 2. Update the progress bar.
                                 */
                case MESSAGE_UPDATE_PROGRESS_BAR:
                    if(progressDialog != null)
                    {
                        int currentProgress = msg.arg1;
                        progressDialog.setProgress(currentProgress);
                    }
                    break;

                                /*
                                 * Handling MESSAGE_CONNECTING_STARTED:
                                 * 1. Get the URL of the file being downloaded. This is stored
                                 *    in the obj field of the Message.
                                 * 2. Create an indeterminate progress bar.
                                 * 3. Set the message that should be sent if user cancels.
                                 * 4. Show the progress bar.
                                 */
                case MESSAGE_CONNECTING_STARTED:
                    if(msg.obj != null && msg.obj instanceof String)
                    {
                        String url = (String) msg.obj;
                        // truncate the url
                        if(url.length() > 16)
                        {
                            String tUrl = url.substring(0, 15);
                            tUrl += "...";
                            url = tUrl;
                        }
                        String pdTitle = thisActivity.getString(R.string.progress_dialog_title_connecting);
                        String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_connecting);
                        pdMsg += " " + url;

                        dismissCurrentProgressDialog();
                        progressDialog = new ProgressDialog(thisActivity);
                        progressDialog.setTitle(pdTitle);
                        progressDialog.setMessage(pdMsg);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setIndeterminate(true);
                        // set the message to be sent when this dialog is canceled
                        Message newMsg = Message.obtain(this, MESSAGE_DOWNLOAD_CANCELED);
                        progressDialog.setCancelMessage(newMsg);
                        progressDialog.show();
                    }
                    break;

                                /*
                                 * Handling MESSAGE_DOWNLOAD_STARTED:
                                 * 1. Create a progress bar with specified max value and current
                                 *    value 0; assign it to progressDialog. The arg1 field will
                                 *    contain the max value.
                                 * 2. Set the title and text for the progress bar. The obj
                                 *    field of the Message will contain a String that
                                 *    represents the name of the file being downloaded.
                                 * 3. Set the message that should be sent if dialog is canceled.
                                 * 4. Make the progress bar visible.
                                 */
                case MESSAGE_DOWNLOAD_STARTED:
                    // obj will contain a String representing the file name
                    if(msg.obj != null && msg.obj instanceof String)
                    {
                        int maxValue = msg.arg1;
                        String fileName = (String) msg.obj;
                        String pdTitle = thisActivity.getString(R.string.progress_dialog_title_downloading);
                        String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_downloading);
                        pdMsg += " " + fileName;

                        dismissCurrentProgressDialog();
                        progressDialog = new ProgressDialog(thisActivity);
                        progressDialog.setTitle(pdTitle);
                        progressDialog.setMessage(pdMsg);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setProgress(0);
                        progressDialog.setMax(maxValue);
                        // set the message to be sent when this dialog is canceled
                        Message newMsg = Message.obtain(this, MESSAGE_DOWNLOAD_CANCELED);
                        progressDialog.setCancelMessage(newMsg);
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                    break;

                                /*
                                 * Handling MESSAGE_DOWNLOAD_COMPLETE:
                                 * 1. Remove the progress bar from the screen.
                                 * 2. Display Toast that says download is complete.
                                 */
                case MESSAGE_DOWNLOAD_COMPLETE:
                    Log.i("DownloadComplete", "Completato");
                    dismissCurrentProgressDialog();
                    Log.i("Dismesso", "Dialogo Dis");
                    displayMessage(getString(R.string.user_message_download_complete));
                    Log.i("messaggio", "messaggio mostrato");

                    try {
                        Log.i("entro", "entrato nel try");
                        copyFile(outFile, destFile);
                        Log.i("copyFile", "copyFile");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    boolean deleted = outFile.delete();
                    Intent e = new Intent(thisActivity , Eye.class);
                    startActivity(e);
                    break;

                                /*
                                 * Handling MESSAGE_DOWNLOAD_CANCELLED:
                                 * 1. Interrupt the downloader thread.
                                 * 2. Remove the progress bar from the screen.
                                 * 3. Display Toast that says download is complete.
                                 */
                case MESSAGE_DOWNLOAD_CANCELED:
                    if(downloaderThread != null)
                    {
                        downloaderThread.interrupt();
                    }
                    dismissCurrentProgressDialog();
                    displayMessage(getString(R.string.user_message_download_canceled));
                    break;

                                /*
                                 * Handling MESSAGE_ENCOUNTERED_ERROR:
                                 * 1. Check the obj field of the message for the actual error
                                 *    message that will be displayed to the user.
                                 * 2. Remove any progress bars from the screen.
                                 * 3. Display a Toast with the error message.
                                 */
                case MESSAGE_ENCOUNTERED_ERROR:
                    // obj will contain a string representing the error message
                    if(msg.obj != null && msg.obj instanceof String)
                    {
                        String errorMessage = (String) msg.obj;
                        dismissCurrentProgressDialog();
                        displayMessage(errorMessage);
                    }
                    break;

                default:
                    // nothing to do here
                    break;
            }
        }
    };

    /**
     * If there is a progress dialog, dismiss it and set progressDialog to
     * null.
     */
    public void dismissCurrentProgressDialog()
    {
        if(progressDialog != null)
        {
            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Displays a message to the user, in the form of a Toast.
     * @param message Message to be displayed.
     */
    public void displayMessage(String message)
    {
        if(message != null)
        {
            Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show();
        }
    }
    public void runUpdate(String urlInput){
        downloaderThread = new DownloaderThread(thisActivity, urlInput);
        downloaderThread.start();
    }
}
