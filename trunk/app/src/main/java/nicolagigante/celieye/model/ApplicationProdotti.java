package nicolagigante.celieye.model;

import android.app.Application;

/**
 * Created by Nico on 20/07/2014.
 */
public class ApplicationProdotti extends Application{
    private Prodotto prodotto;
    public void setProdotto(Prodotto prodotto){
        this.prodotto = prodotto;
    }
    public Prodotto getProdotto(){
        return this.prodotto;
    }
}
