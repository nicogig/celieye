package nicolagigante.celieye.dataBaseCache;

/**
 * Created by Nico on 02/08/2014.
 */
public class DbKey {
    public static final String TABLE_CATEGORIES = "categories";
    public static final String KEY_CATEGORIES_CATEGORY = "category";
    public static final String KEY_CATEGORIES_NAME = "name";
    public static final String KEY_CATEGORIES_ENGLISH = "english";
    public static final String TABLE_COMPANIES = "companies";
    public static final String KEY_COMPANIES_ID_COMPANY = "id_company";
    public static final String KEY_COMPANIES_NAME = "name";
    public static final String TABLE_BRANDS = "brands";
    public static final String KEY_BRANDS_ID_BRAND = "id_brand";
    public static final String KEY_BRANDS_ID_COMPANY = "id_company";
    public static final String KEY_BRANDS_NAME = "name";
    public static final String TABLE_PRODUCTS = "products";
    public static final String KEY_PRODUCTS_ID_PRODUCT = "id_product";
    public static final String KEY_PRODUCTS_ID_COMPANY = "id_company";
    public static final String KEY_PRODUCTS_ID_CATEGORY = "id_category";
    public static final String KEY_PRODUCTS_ID_BRAND = "id_brand";
    public static final String KEY_PRODUCTS_TYPE = "type";
    public static final String KEY_PRODUCTS_DESCRIPTION = "description";
    public static final String TABLE_BARCODE = "barcode";
    public static final String KEY_BARCODE_ID_PRODUCT = "id_product";
    public static final String KEY_BARCODE_ID_BARCODE = "id_barcode";
    public static final String KEY_BARCODE_FLAG_VALIDATE = "flag_validate";

}
