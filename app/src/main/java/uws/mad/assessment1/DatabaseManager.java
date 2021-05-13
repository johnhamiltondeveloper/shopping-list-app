package uws.mad.assessment1;

        import android.database.sqlite.SQLiteDatabase;
        import android.content.Context;
        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "shopping";
    public static final int DB_VERSION = 5;
    private static final String CREATE_pantry_TABLE = "CREATE TABLE pantry (ItemID TEXT PRIMARY KEY , name TEXT, price INTEGER, quinary INTEGER, location TEXT, Image BLOB);";
    private static final String CREATE_shopping_TABLE = "CREATE TABLE shopping (ItemID TEXT PRIMARY KEY, name TEXT, price INTEGER, quinary INTEGER, location TEXT, Image BLOB);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(String table,String ItemID,String name,int price, int quinary, String location, String image) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("ItemID", ItemID);
            newProduct.put("name", name);
            newProduct.put("price", price);
            newProduct.put("quinary", quinary);
            newProduct.put("location", location);
            newProduct.put("image", image);
            try {
                db.insertOrThrow(table, null, newProduct);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean updateRow(String table,String ItemID,String name,int price, int quinary, String location, String image) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("ItemID", ItemID);
            newProduct.put("name", name);
            newProduct.put("price", price);
            newProduct.put("quinary", quinary);
            newProduct.put("location", location);
            newProduct.put("image", image);



            try {
                db.update(table,newProduct,"ItemID = '"+ItemID+"'",null);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean removeRow(String table,String ItemID) {
        synchronized(this.db) {

            try {
                db.delete(table,"ItemID = '"+ItemID+"'",null);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public item retrieveItem(String Table,String ID) {

        item product = new item("0","unnamed",0,0,"NULL","");
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table + " WHERE ItemID = '" + ID + "';",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            product = new item(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return product;

    }

    public ArrayList<item> retrieveRows(String Table) {
        ArrayList<item> productRows = new ArrayList<item>();
        String[] columns = new String[] {"ItemID", "name", "price", "quinary", "location","image"};
        Cursor cursor = db.query(Table, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(new item(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5)));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public void clearRecords(String Table)
    {
        db = helper.getWritableDatabase();
        db.delete(Table, null, null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_pantry_TABLE);
            db.execSQL(CREATE_shopping_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TALBE IF EXISTS pantry");
            db.execSQL("DROP TALBE IF EXISTS shopping");
            onCreate(db);
        }
    }
}