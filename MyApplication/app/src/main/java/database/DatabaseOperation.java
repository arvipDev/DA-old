package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation extends SQLiteOpenHelper
{

    private static final int database_version = 1;
    private String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + DBTable.TableCredentials.TABLE_NAME + "("
            + DBTable.TableCredentials.USER_ID
            + " INTEGER, "+ DBTable.TableCredentials.USER_NAME + " TEXT);";

    private String DELETEALL_QUERY = "DELETE FROM " + DBTable.TableCredentials.TABLE_NAME + ";";
    private String DROP_TABLE = "DROP TABLE " + DBTable.TableCredentials.TABLE_NAME + ";";

    public DatabaseOperation(Context context)
    {
        super(context, DBTable.TableCredentials.DATABASE_NAME, null, database_version);
        Log.d("Database Operations", "Database created");

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "DBTable created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //------------------------------------------------------------------------------------------------------
    public void createTableIfNotExists(DatabaseOperation dbo)
    {
        SQLiteDatabase sqdb = dbo.getReadableDatabase();
        sqdb.execSQL(CREATE_QUERY);
    }
    public void insertLoginCred(DatabaseOperation dbo, int id, String userName)
    {
        SQLiteDatabase sqdb = dbo.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBTable.TableCredentials.USER_ID, id);
        cv.put(DBTable.TableCredentials.USER_NAME, userName);
        long k = sqdb.insertWithOnConflict(DBTable.TableCredentials.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        Log.d("Database Operations", "one row inserted into table");
    }

    public Cursor retrieveLoginCred(DatabaseOperation dbo)
    {
        SQLiteDatabase sqdb = dbo.getReadableDatabase();
        String[] columns = {DBTable.TableCredentials.USER_NAME};
        Cursor cursor = sqdb.query(DBTable.TableCredentials.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public long getDBRowSize(DatabaseOperation dbo)
    {
        SQLiteDatabase sqdb = dbo.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(sqdb, DBTable.TableCredentials.TABLE_NAME);
        sqdb.close();
        return count;
    }

    public List<String> getUserNameListfromDB(DatabaseOperation dbo)
    {
        List<String> userNameList = new ArrayList<>();
        Cursor cursor = dbo.retrieveLoginCred(dbo);
        cursor.moveToFirst();
        if(dbo.getDBRowSize(dbo) != 0)
        {
            do userNameList.add(cursor.getString(0));
            while (cursor.moveToNext());
        } else  Log.d("Empty DB", "Table empty");
        return userNameList;
    }

    //-------------------------------------------------------------------------------------------------------------------
    // This is just a method used while testing the app to delete the previous entries in the database.
    //Later add delete option to log in activity in the navigation drawer

    public void deleteAllRows (DatabaseOperation dbo)
    {
        SQLiteDatabase sqdb = dbo.getReadableDatabase();
        sqdb.execSQL(DELETEALL_QUERY);
        sqdb.close();
    }

    public void dropTable (DatabaseOperation dbo)
    {
        SQLiteDatabase sqdb = dbo.getReadableDatabase();
        sqdb.execSQL(DROP_TABLE);
        sqdb.close();
    }
}
