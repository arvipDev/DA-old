package database;

import android.provider.BaseColumns;

public class DBTable {

    public DBTable()
    {

    }

    public static abstract class TableCredentials implements BaseColumns
    {
        public static final String USER_NAME = "user_name";
        public static final String USER_ID = "user_id";
        public static final String DATABASE_NAME = "user_cred";
        public static final String TABLE_NAME = "login_table";
        public static final String CRED_TABLE_PK = "credTable_pk";

    }
}
