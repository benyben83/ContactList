package fr.formation.annuaire;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.EditText;

public class dbInit extends SQLiteOpenHelper {

    //le nom de la db est fixé a "annuaire
    // le numero de version est fixé a 1

    public dbInit(Context ctxt) {
        super(ctxt, "annuaire", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE contacts (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                ", name TEXT NOT NULL" +
                ", tel TEXT" + ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
