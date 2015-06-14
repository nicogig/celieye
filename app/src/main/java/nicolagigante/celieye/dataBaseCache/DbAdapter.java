package nicolagigante.celieye.dataBaseCache;

/**
 * Created by genio_2 on 01/08/2014.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
    @SuppressWarnings("unused")
    private static final String LOG_TAG = DbAdapter.class.getSimpleName();

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private DbKey dbKey= new DbKey();

    // Database fields



    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean veifyProduct(String barcode){
      //  Cursor mCursor = database.query(false,dbKey.TABLE_BARCODE,new String[]{"*"},
            //  "id_barcode = "+barcode,null,null, null, null, null, null);
       String[] arraycolonne = {
               "*"
       };
       // Cursor mCursor = database.query(false,"barcode", arraycolonne ,
       //         "id_barcode = "+barcode,null,null, null, null, null, null);

        Cursor mCursor = database.query(false,"articles", arraycolonne ,
                "ean = "+barcode,null,null, null, null, null, null);

       // Cursor mCursor=database.rawQuery("SELECT * " + " from " + dbKey.TABLE_BARCODE, new String[]{dbKey.KEY_BARCODE_ID_BARCODE + "=" + barcode});
        if (mCursor.moveToFirst()) {
            return true;
        }
        else
        return false;
    }


/*
    private ContentValues createContentValues(String id_products, String id_barcode, String flags_validate) {
        ContentValues values = new ContentValues();
        values.put(ID_PRODUCT, id_products);
        values.put(ID_BARCODE, id_barcode);
        values.put(FLAG_VALIDATE, flags_validate);

        return values;
    }

    //create a contact
    public long createContact(String id_products, String id_barcode, String flags_validate) {
        ContentValues initialValues = createContentValues(id_products, id_barcode, flags_validate);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);//da modificare
    }
    public boolean insertProduct(Prodotto prodotto){

        return false;
    }
    //update a contact
    public boolean updateContact(long contactID, String name, String surname, String sex, String birth_date) {
        ContentValues updateValues = createContentValues(name, surname, sex, birth_date);
        return database.update(DATABASE_TABLE, updateValues, KEY_CONTACTID + "=" + contactID, null) > 0;
    }

    //delete a contact
    public boolean deleteContact(long contactID) {
        return database.delete(DATABASE_TABLE, KEY_CONTACTID + "=" + contactID, null) > 0;
    }

    //fetch all contacts
    public Cursor fetchAllContacts() {
        return database.query(DATABASE_TABLE, new String[]{KEY_CONTACTID, KEY_NAME, KEY_SURNAME, KEY_SEX, KEY_BIRTH_DATE}, null, null, null, null, null);
    }
*/
    //fetch contacts filter by a string
    public Cursor searchProductByDescription(String filter) {
        String[] arraycolonne = {
                "*"
        };
        /*Cursor mCursor = database.query(false,dbKey.TABLE_PRODUCTS,arraycolonne,
                dbKey.KEY_PRODUCTS_DESCRIPTION +
                "LIKE %"+ filter +"%",null,null, null, null, null, null
        );*/
        String sql = "SELECT * FROM products WHERE description Like '%"+filter+"%'";
        Cursor mCursor= database.rawQuery(sql, null);
        return mCursor;
    }

}
