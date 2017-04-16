package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperCurrentOrder extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="currentOrders.db";
    private static final int SCHEMA=1;
    static final String TABLE="currentOrders";
    static final String ID="id";
    static final String NAME="name";
    static final String RESTAURANT="restaurant";
    static final String ADDRESS="address"; //3
    static final String DESCRIPTION="description";
    static final String PRICE="price";
    static final String QUANTITY="quantity";
    static final String IMAGE="image"; //7

    public DatabaseHelperCurrentOrder(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE+" ("+ID+" TEXT, "+NAME+" TEXT, "+RESTAURANT+" TEXT,"+
                ADDRESS+" TEXT, "+DESCRIPTION+" TEXT, "+PRICE+" TEXT, "+QUANTITY+" TEXT, "+
                IMAGE+" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("How did we get here?");
    }
}

