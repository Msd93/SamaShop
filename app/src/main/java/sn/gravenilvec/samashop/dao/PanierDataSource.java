package sn.gravenilvec.samashop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sn.gravenilvec.samashop.database.DataBaseHelper;
import sn.gravenilvec.samashop.entity.Panier;

public class PanierDataSource {
    // private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    public PanierDataSource(Context context) {
        dbHelper = new DataBaseHelper (context);
    }
    // Create a panier
    public boolean createPanier(Panier panier) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LIBELLE_P, panier.getLibelle ());
        values.put(DataBaseHelper.MONTANTPREVU, panier.getMontantPrevu ());
        values.put(DataBaseHelper.PERSONNEID, panier.getPersonneId ());
        values.put(DataBaseHelper.DATECREATION, panier.getDateCreation ());
        values.put(DataBaseHelper.DATEMODIFICATION, panier.getDateModification ());

        // insert row
        boolean inserted = false;
        try {

            long panier_id = database.insert (DataBaseHelper.TABLE_PANIER, null, values);
            if(panier_id != 0)
                inserted = true;

        }catch (SQLException e){  }

        return inserted;
    }

    /**
     *
     * @param id
     * @return Panier
     */
    public Panier getPanierByIDPANIER(int idPanier) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_PANIER + " WHERE " + DataBaseHelper.IDPANIER + " = '" + idPanier + "'";

        Panier panier = null;
        try{
            Cursor c = database.rawQuery(selectQuery, null);
            if (c.getCount () == 1 && c != null){
                c.moveToFirst();
                panier = new Panier ();
                panier.setIdPanier(c.getInt(c.getColumnIndex(DataBaseHelper.IDPANIER)));
                panier.setLibelle (c.getString(c.getColumnIndex(DataBaseHelper.LIBELLE_P)));
                panier.setDateCreation (c.getString(c.getColumnIndex(DataBaseHelper.DATECREATION)));
                panier.setDateModification (c.getString(c.getColumnIndex(DataBaseHelper.DATEMODIFICATION)));
                panier.setMontantPrevu (c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANTPREVU)));

            }
        }catch (SQLException e){
            return panier;
        }

        return panier;
    }
    /**
    /*
     * getting all paniers
     * */
    public List<Panier> getAllPaniers(int personneID) {
        List<Panier> paniers  = new ArrayList<Panier>();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_PANIER + " WHERE " + DataBaseHelper.PERSONNEID + " = " + personneID;

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Panier panier  = new Panier();
                panier.setIdPanier(c.getInt(c.getColumnIndex(DataBaseHelper.IDPANIER)));
                panier.setLibelle (c.getString(c.getColumnIndex(DataBaseHelper.LIBELLE_P)));
                panier.setDateCreation (c.getString(c.getColumnIndex(DataBaseHelper.DATECREATION)));
                panier.setDateModification (c.getString(c.getColumnIndex(DataBaseHelper.DATEMODIFICATION)));
                panier.setMontantPrevu (c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANTPREVU)));

                // adding to todo list
                paniers.add(panier);
            } while (c.moveToNext());
        }

        return paniers;
    }
    /*
     * Updating a panier
     */
    public int updatePanier(Panier panier) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.LIBELLE_P, panier.getLibelle ());
        values.put(DataBaseHelper.MONTANTPREVU, panier.getMontantPrevu ());
        values.put(DataBaseHelper.DATEMODIFICATION, panier.getDateModification ());

        // updating row
        return database.update(DataBaseHelper.TABLE_PANIER, values, DataBaseHelper.IDPANIER + " = ?",
                new String[] { String.valueOf(panier.getIdPanier()) });
    }
    /*
     * Deleting a panier
     */
    public void deletePanier(int panier_id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DataBaseHelper.TABLE_PANIER, DataBaseHelper.IDPANIER + " = ?",
                new String[] { String.valueOf(panier_id) });
    }
}
