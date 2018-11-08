package primerproyecto.uma.es.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Marin on 07/11/2017.
 *
 * Class that contains all the functions related to the database
 */

public class CharacterDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Characters.db";
    // Variables for an easiest comprehension of the SQL query
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    // the query that creates the Database
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Character.CharacterTable.TABLE_NAME + " (" +
                    Character.CharacterTable._ID + " INTEGER PRIMARY KEY," +
                    Character.CharacterTable.COLUMN_NAME_CHARACTER_NAME + TEXT_TYPE + COMMA_SEP +
                    Character.CharacterTable.COLUMN_NAME_UNLOCK + INTEGER_TYPE + COMMA_SEP +
                    Character.CharacterTable.COLUMN_NAME_TIP_LETTERS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Character.CharacterTable.TABLE_NAME;

    public CharacterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * @param db the database to get the table from
     * @param tableName the the name of the table to parse
     * @return the table tableName as a string
     */
    public static String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d("DbHelper", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
            allRows.close();
        }
        return tableString;
    }

    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * @param appContext the context with the information of the app
     * @param newCharacterName the new value for the NAME column
     * @param newCharacterUnlock the new value for the UNLOCK column
     * @param newTipLettersUnlock the new value for the TIP LETTERS column
     *
     * @return the table tableName as a string
     */
    public static void addsCharacterToDatabase(Context appContext, String newCharacterName, String newCharacterUnlock, String newTipLettersUnlock) {

        CharacterDbHelper mDbHelper = new CharacterDbHelper(appContext);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Character.CharacterTable.COLUMN_NAME_CHARACTER_NAME, newCharacterName);
        values.put(Character.CharacterTable.COLUMN_NAME_UNLOCK, newCharacterUnlock);
        values.put(Character.CharacterTable.COLUMN_NAME_TIP_LETTERS, newTipLettersUnlock);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Character.CharacterTable.TABLE_NAME, null, values);
        db.close();
    }

    /**
     *
     *
     * @param context
     *
     * @return
     */
    public static SQLiteDatabase openDB(Context context){
        CharacterDbHelper mDbHelper = new CharacterDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        return db;
    }

    /**
     *
     *
     * @param db
     *
     * @return
     */
    public static Cursor queryForSomeCharacters(SQLiteDatabase db, int first, int last){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Character.CharacterTable.TABLE_NAME +
                        " WHERE " + Character.CharacterTable._ID + " BETWEEN '" + first + "' AND '" + last + "'",
                null);

        return cursor;
    }

    /**
     * Helper function that checks the db for locked characters.
     *
     * @param db the database to get the characters from
     * @return the query with all the locked characters as a Cursor
     */
    public static Cursor queryForLockedsCharacters(SQLiteDatabase db){
        String LOCKED_CHARACTER_VALUE = "0";
        Cursor cursorWithTheMatchs =  db.rawQuery("select * from " + Character.CharacterTable.TABLE_NAME +
                " where " + Character.CharacterTable.COLUMN_NAME_UNLOCK + "='" + LOCKED_CHARACTER_VALUE + "'" , null);
        return cursorWithTheMatchs;
    }


}
