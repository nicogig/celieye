package nicolagigante.celieye.model;

import android.app.Application;

/**
 * Created by Nico on 20/07/2014.
 */
public class Prodotto {
    private String barcode;
    private String nomeprodotto;
    private String descrizioneprodotto;
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }
    public void setNomeprodotto(String nomeprodotto){
        this.nomeprodotto = nomeprodotto;
    }
    public void setDescrizioneprodotto(String descrizioneprodotto){
        this.descrizioneprodotto = descrizioneprodotto;
    }
    public String getBarcode(){
        return this.barcode;
    }
    public String getNomeprodotto(){
        return this.nomeprodotto;
    }
    public String getDescrizioneprodotto(){
        return this.descrizioneprodotto;
    }
}
