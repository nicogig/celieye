package nicolagigante.celieye.dataBaseCache;

/**
 * Created by Nico on 02/08/2014.
 */
public class DbKey {
    public  final String TABLE_CATEGORIES = "categories";
    public  final String KEY_CATEGORIES_CATEGORY = "category";
    public  final String KEY_CATEGORIES_NAME = "name";
    public  final String KEY_CATEGORIES_ENGLISH = "english";
    public  final String TABLE_COMPANIES = "companies";
    public  final String KEY_COMPANIES_ID_COMPANY = "id_company";
    public  final String KEY_COMPANIES_NAME = "name";
    public  final String TABLE_BRANDS = "brands";
    public  final String KEY_BRANDS_ID_BRAND = "id_brand";
    public  final String KEY_BRANDS_ID_COMPANY = "id_company";
    public  final String KEY_BRANDS_NAME = "name";
    public  final String TABLE_PRODUCTS = "products";
    public  final String KEY_PRODUCTS_ID_PRODUCT = "id_product";
    public  final String KEY_PRODUCTS_ID_COMPANY = "id_company";
    public  final String KEY_PRODUCTS_ID_CATEGORY = "id_category";
    public  final String KEY_PRODUCTS_ID_BRAND = "id_brand";
    public  final String KEY_PRODUCTS_TYPE = "type";
    public  final String KEY_PRODUCTS_DESCRIPTION = "description";
    public  final String TABLE_BARCODE = "barcode";
    public  final String KEY_BARCODE_ID_PRODUCT = "id_product";
    public  final String KEY_BARCODE_ID_BARCODE = "id_barcode";
    public  final String KEY_BARCODE_FLAG_VALIDATE = "flag_validate";
    public DbKey (){

    }
}
