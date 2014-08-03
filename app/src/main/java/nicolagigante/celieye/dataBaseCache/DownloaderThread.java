package nicolagigante.celieye.dataBaseCache;

/**
 * Created by genio_2 on 26/07/2014.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import nicolagigante.celieye.R;
import nicolagigante.celieye.activity.AndroidFileDownloader;

/**
 * Downloads a file in a thread. Will send messages to the
 * AndroidFileDownloader activity to update the progress bar.
 */
public class DownloaderThread extends Thread
{
    // constants
    private static final int DOWNLOAD_BUFFER_SIZE = 4096;

    // instance variables
    private AndroidFileDownloader parentActivity;
    private String downloadUrl;
    private final Context myContext;
    private static String DB_PATH = "/data/data/nicolagigante.celieye/databases/";
    private static String DB_NAME = "dbaic.db";

    public DownloaderThread(AndroidFileDownloader inParentActivity, String inUrl)
    {
        downloadUrl = "";
        if(inUrl != null)
        {
            downloadUrl = inUrl;
        }
        parentActivity = inParentActivity;
        myContext = null;
    }

//---------------------------copy db-------------------
private void copyDataBase() throws IOException{
     Log.i( "copyDataBase", "sono entrato, e tante grazie.");
    //Open your local db as the input stream
    InputStream myInput = myContext.openFileInput("/sdcard/dbaic.db");
    Log.i("myInput", myInput.toString());

    // Path to the just created empty db
    String outFileName = "/data/data/nicolagigante.celieye/databases/" + "dbaic.db";
Log.i("outFileName", outFileName);

    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);
    Log.i("myOutput", myOutput.toString());

    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
        myOutput.write(buffer, 0, length);
    }

    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();

}
//-----------------------------------------------------


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



    /**
     * Connects to the URL of the file, begins the download, and notifies the
     * AndroidFileDownloader activity of changes in state. Writes the file to
     * the root of the SD card.
     */
    @Override
    public void run()
    {
        URL url;
        URLConnection conn;
        int fileSize, lastSlash;
        String fileName;
        BufferedInputStream inStream;
        BufferedOutputStream outStream;
        File outFile;
        FileOutputStream fileStream;
        Message msg;
        File destFile;

        // we're going to connect now
        msg = Message.obtain(parentActivity.activityHandler,
                AndroidFileDownloader.MESSAGE_CONNECTING_STARTED,
                0, 0, downloadUrl);
        parentActivity.activityHandler.sendMessage(msg);

        try
        {
            url = new URL(downloadUrl);
            conn = url.openConnection();
            conn.setUseCaches(false);
            fileSize = conn.getContentLength();

            // get the filename
            lastSlash = url.toString().lastIndexOf('/');
            fileName = "file.bin";
            if(lastSlash >=0)
            {
                fileName = url.toString().substring(lastSlash + 1);
            }
            if(fileName.equals(""))
            {
                fileName = "file.bin";
            }

            // notify download start
            int fileSizeInKB = fileSize / 1024;
            msg = Message.obtain(parentActivity.activityHandler,
                    AndroidFileDownloader.MESSAGE_DOWNLOAD_STARTED,
                    fileSizeInKB, 0, fileName);
            parentActivity.activityHandler.sendMessage(msg);

            // start download
            inStream = new BufferedInputStream(conn.getInputStream());
            //outFile = new File(Environment.getDataDirectory() + "/nicolagigante.celieye/databases/" + fileName);
            outFile = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
            //destFile = new File(Context.getApplicationContext().getFilesDir() + "/");
            fileStream = new FileOutputStream(outFile);
            outStream = new BufferedOutputStream(fileStream, DOWNLOAD_BUFFER_SIZE);
            byte[] data = new byte[DOWNLOAD_BUFFER_SIZE];
            int bytesRead = 0, totalRead = 0;
            while(!isInterrupted() && (bytesRead = inStream.read(data, 0, data.length)) >= 0)
            {
                outStream.write(data, 0, bytesRead);

                // update progress bar
                totalRead += bytesRead;
                int totalReadInKB = totalRead / 1024;
                msg = Message.obtain(parentActivity.activityHandler,
                        AndroidFileDownloader.MESSAGE_UPDATE_PROGRESS_BAR,
                        totalReadInKB, 0);
                parentActivity.activityHandler.sendMessage(msg);
            }

            outStream.close();
            fileStream.close();
            inStream.close();

            if(isInterrupted())
            {
                // the download was canceled, so let's delete the partially downloaded file
                outFile.delete();
            }
            else
            {
                // notify completion
                msg = Message.obtain(parentActivity.activityHandler,
                        AndroidFileDownloader.MESSAGE_DOWNLOAD_COMPLETE);
                parentActivity.activityHandler.sendMessage(msg);
                //copyFile(outFile, destFile);
            }
        }
        catch(MalformedURLException e)
        {
            String errMsg = parentActivity.getString(R.string.error_message_bad_url);
            msg = Message.obtain(parentActivity.activityHandler,
                    AndroidFileDownloader.MESSAGE_ENCOUNTERED_ERROR,
                    0, 0, errMsg);
            parentActivity.activityHandler.sendMessage(msg);
        }
        catch(FileNotFoundException e)
        {
            String errMsg = parentActivity.getString(R.string.error_message_file_not_found);
            msg = Message.obtain(parentActivity.activityHandler,
                    AndroidFileDownloader.MESSAGE_ENCOUNTERED_ERROR,
                    0, 0, errMsg);
            parentActivity.activityHandler.sendMessage(msg);
        }
        catch(Exception e)
        {
            String errMsg = parentActivity.getString(R.string.error_message_general);
            msg = Message.obtain(parentActivity.activityHandler,
                    AndroidFileDownloader.MESSAGE_ENCOUNTERED_ERROR,
                    0, 0, errMsg);
            parentActivity.activityHandler.sendMessage(msg);
        }
    }
}
