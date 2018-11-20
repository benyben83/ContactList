package fr.formation.annuaire;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.text.method.TextKeyListener.clear;

public class MainActivity extends AppCompatActivity implements AlertDialog.OnClickListener {
    SQLiteDatabase db;
    EditText etName;
    EditText etTel;
    EditText etSearch;
    EditText etId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbInit dbInit = new dbInit(this);
        db = dbInit.getWritableDatabase();
        etTel = findViewById(R.id.etTel);
        etName = findViewById(R.id.etName);
        etSearch = findViewById(R.id.etSearch);
        etId = findViewById(R.id.etId);
    }

    public void chercher(View view) {
        String colonnes[] = {"id", "name", "tel"};
        String nom = etSearch.getText().toString();
        nom = nom.toUpperCase();          // changer tout en maj
        nom = nom.replace("'", "''");           // remplacer apostrophes pour compatibilite requete sql
        String sql = "UPPER(name) = '" + nom + "'";
        Cursor cursor = db.query("contacts", colonnes, sql, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            etId.setText(cursor.getString(0));
            etName.setText(cursor.getString(1));
            etTel.setText(cursor.getString(2));
        } else {
            Toast.makeText(this, "Nom introuvable", Toast.LENGTH_LONG);
        }

    }

    public void enregistrer(View view) {

        ContentValues values = new ContentValues();
        values.put("name", etName.getText().toString());
        values.put("tel", etTel.getText().toString());
        String id = etId.getText().toString();
        if (id.equals("")) {
            db.insert("contacts", null, values);
        } else {
            String critere = "id=" + id;
            db.update("contacts", values, critere, null);
        }

    }

    public void delete(View view) {
        createAndShowDialog();


    }

    void createAndShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suppresion");
        builder.setMessage("Confirmez-vous la suppression ?");
        builder.setPositiveButton("OUI", this);
        builder.setNegativeButton("NON", this);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_NEGATIVE:
                // int which = -2
                dialog.dismiss();
                break;
            case BUTTON_NEUTRAL:
                // int which = -3
                dialog.dismiss();
                break;
            case BUTTON_POSITIVE:
                // int which = -1
                String critere = "id =" + etId.getText().toString();
                db.delete("contacts", critere, null);
                vider(null);
                Toast.makeText(this, "Contact Supprimé", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                break;
        }
    }
    public void vider(View view){
        etId.setText("");
        etTel.setText("");
        etName.setText("");
    }
}