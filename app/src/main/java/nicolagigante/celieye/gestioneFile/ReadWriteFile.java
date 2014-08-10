package nicolagigante.celieye.gestioneFile;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by genio_2 on 10/08/2014.
 */
public class ReadWriteFile {
    private int mode;
    private String name;
    private Context context;
    public FileOutputStream openFileOutput;
    public FileInputStream openFileInput;
//al costruttore passo il nome del file il modo e il contesto della classe  activity chiamante
    public ReadWriteFile(String name, int mode, Context context) throws FileNotFoundException {
        this.name=name;
        this.mode=mode;
        this.context=context;
        this.openFileOutput= context.openFileOutput(name,mode);//vado ad inizializzare openFileOutput con quella del contesto
        this.openFileInput=context.openFileInput(name);
    }
    public String readFile() throws IOException {
        DataInputStream dis =new DataInputStream(openFileInput);//vado ad inizializzare openFileInput con quella del contesto
        Log.e("lettura riga", dis.readUTF().toString());
        String result=dis.readUTF().toString();
        dis.close();
        return result;

    }
    public void writeFile(String dati) throws IOException {
        DataOutputStream dos=new DataOutputStream(openFileOutput);
        dos.writeUTF(dati.toString());
        dos.close();
    }




}
