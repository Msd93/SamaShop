package sn.gravenilvec.samashop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import sn.gravenilvec.samashop.database.DataBaseHelper;
import sn.gravenilvec.samashop.entity.Personne;

public class PersonneDataSource {

    // private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    public PersonneDataSource(Context context) {
        dbHelper = new DataBaseHelper (context);
    }

     /**
     *
     * @param personne
     * @return
     */
    public boolean createPersonne(Personne personne) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NOM, personne.getNom ());
        values.put(DataBaseHelper.PRENOM, personne.getPrenom ());
        values.put(DataBaseHelper.LOGIN, personne.getLogin ());
        values.put(DataBaseHelper.PASSWORD, personne.getPassword ());

        // insert row
        boolean inserted = false;
        try {

            long personne_id = database.insert (DataBaseHelper.TABLE_PERSONNE, null, values);
            if(personne_id != 0)
                inserted = true;

        }catch (SQLException e){  }

        return inserted;
    }

    /**
     *
     * @param login
     * @return
     */
    public Personne getPersonnneByLogin(String login) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_PERSONNE + " WHERE " + DataBaseHelper.LOGIN + " = '" + login + "'";

        Personne personne = null;
        try{
            Cursor c = database.rawQuery(selectQuery, null);
            if (c.getCount () == 1 && c != null){
                c.moveToFirst();
                personne = new Personne ();
                personne.setIdPersonne (c.getInt (c.getColumnIndex(DataBaseHelper.IDPERSONNE)));
                personne.setNom(c.getString(c.getColumnIndex(DataBaseHelper.NOM)));
                personne.setPrenom(c.getString(c.getColumnIndex(DataBaseHelper.PRENOM)));
                personne.setLogin(c.getString(c.getColumnIndex(DataBaseHelper.LOGIN)));
                personne.setPassword(c.getString(c.getColumnIndex(DataBaseHelper.PASSWORD)));
            }
        }catch (SQLException e){
            return personne;
        }

        return personne;
    }

    /**
     *
     * @param login
     * @param password
     * @return Boolean
     */
    public Personne getPersonnneByLoginAndPassword(String login, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_PERSONNE + " WHERE " + DataBaseHelper.LOGIN + " = '" + login + "' AND " + DataBaseHelper.PASSWORD + " = '" + password + "'";

        Personne personne = null;
        try{
            Cursor c = database.rawQuery(selectQuery, null);
            if (c.getCount () == 1 && c != null) {
                c.moveToFirst ( );
                personne = new Personne ( );
                personne.setIdPersonne (c.getInt (c.getColumnIndex (DataBaseHelper.IDPERSONNE)));
                personne.setNom (c.getString (c.getColumnIndex (DataBaseHelper.NOM)));
                personne.setPrenom (c.getString (c.getColumnIndex (DataBaseHelper.PRENOM)));
                personne.setLogin (c.getString (c.getColumnIndex (DataBaseHelper.LOGIN)));
                personne.setPassword (c.getString (c.getColumnIndex (DataBaseHelper.PASSWORD)));
            }
        }catch (SQLException e){
            return personne;
        }

        return personne;
    }
}
