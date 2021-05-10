package sn.gravenilvec.samashop.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sn.gravenilvec.samashop.database.DataBaseHelper;
import sn.gravenilvec.samashop.entity.Article;
import sn.gravenilvec.samashop.entity.Panier;

public class ArticleDataSource {
    // private SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    public ArticleDataSource(Context context) {
        dbHelper = new DataBaseHelper (context);
    }
    // Create a Artcile
    public boolean createArticle(Article article) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.PANIERID ,  article.getPanierId ());
        values.put(DataBaseHelper.LIBELLE_A,  article.getLibelle ());
        values.put(DataBaseHelper.DATE_ACHAT, article.getDate ());
        values.put(DataBaseHelper.MONTANT   , article.getMontant ());
        values.put(DataBaseHelper.QUANTITE  , article.getQuantite ());
        values.put(DataBaseHelper.PHOTO      ,article.getPhoto ());
        values.put(DataBaseHelper.ESTACHETE , article.getEstAchete ());

        // insert row
        boolean inserted = false;
        try {

            long article_id = database.insert (DataBaseHelper.TABLE_ARTICLE, null, values);
            if(article_id != 0)
                inserted = true;

        }catch (SQLException e){  }

        return inserted;
    }

    /**
     *
     * @param id
     * @return Article
     */
    public Article getPArticleByIDARTICLE(int idArticle) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_ARTICLE + " WHERE " + DataBaseHelper.IDARTICLE + " = '" + idArticle + "'";

        Article article = null;
        try{
            Cursor c = database.rawQuery(selectQuery, null);
            if (c.getCount () == 1 && c != null){
                c.moveToFirst();
                article = new Article();
                article.setIdArticle (c.getInt(   c.getColumnIndex(DataBaseHelper.IDARTICLE)));
                article.setPanierId  (c.getInt(   c.getColumnIndex(DataBaseHelper.PANIERID )));
                article.setLibelle   (c.getString(c.getColumnIndex(DataBaseHelper.LIBELLE_A)));
                article.setDate      (c.getString(c.getColumnIndex(DataBaseHelper.DATE_ACHAT)));
                article.setMontant   (c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANT  )));
                article.setQuantite  (c.getInt (  c.getColumnIndex(DataBaseHelper.QUANTITE )));
                article.setPhoto     (c.getString(c.getColumnIndex(DataBaseHelper.PHOTO    )));
                article.setEstAchete (c.getInt (  c.getColumnIndex(DataBaseHelper.ESTACHETE)));

            }
        }catch (SQLException e){
            return article;
        }

        return article;
    }
    /**
    /*
     * getting all articles
     * */
    public List<Article> getAllArticles(int panierID) {
        List<Article> articles  = new ArrayList<Article>();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_ARTICLE + " WHERE " + DataBaseHelper.PANIERID + " = " + panierID + " AND " + DataBaseHelper.ESTACHETE + " = 0";

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Article article = new Article();
                article.setIdArticle (c.getInt(   c.getColumnIndex(DataBaseHelper.IDARTICLE)));
                article.setPanierId  (c.getInt(   c.getColumnIndex(DataBaseHelper.PANIERID )));
                article.setLibelle   (c.getString(c.getColumnIndex(DataBaseHelper.LIBELLE_A)));
                article.setDate      (c.getString(c.getColumnIndex(DataBaseHelper.DATE_ACHAT)));
                article.setMontant   (c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANT  )));
                article.setQuantite  (c.getInt (  c.getColumnIndex(DataBaseHelper.QUANTITE )));
                article.setPhoto     (c.getString(c.getColumnIndex(DataBaseHelper.PHOTO    )));
                article.setEstAchete (c.getInt (  c.getColumnIndex(DataBaseHelper.ESTACHETE)));

                // adding to todo list
                articles.add(article);
            } while (c.moveToNext());
        }

        return articles;
    }
    public List<Article> getAllArticles1(int panierID) {
        List<Article> articles  = new ArrayList<Article>();
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_ARTICLE + " WHERE " + DataBaseHelper.PANIERID + " = " + panierID + " AND " + DataBaseHelper.ESTACHETE + " = 1";

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Article article = new Article();
                article.setIdArticle (c.getInt(   c.getColumnIndex(DataBaseHelper.IDARTICLE)));
                article.setPanierId  (c.getInt(   c.getColumnIndex(DataBaseHelper.PANIERID )));
                article.setLibelle   (c.getString(c.getColumnIndex(DataBaseHelper.LIBELLE_A)));
                article.setDate      (c.getString(c.getColumnIndex(DataBaseHelper.DATE_ACHAT)));
                article.setMontant   (c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANT  )));
                article.setQuantite  (c.getInt (  c.getColumnIndex(DataBaseHelper.QUANTITE )));
                article.setPhoto     (c.getString(c.getColumnIndex(DataBaseHelper.PHOTO    )));
                article.setEstAchete (c.getInt (  c.getColumnIndex(DataBaseHelper.ESTACHETE)));

                // adding to todo list
                articles.add(article);
            } while (c.moveToNext());
        }

        return articles;
    }

    /**
     * Depense
     * @param panierID
     * @return
     */
    public Double getDepenses(int panierID) {
        Double somme = 0.0;
        String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_ARTICLE + " WHERE " + DataBaseHelper.PANIERID + " = " + panierID;

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Double montant = c.getDouble(c.getColumnIndex(DataBaseHelper.MONTANT  ));
                int quantite   =   c.getInt (  c.getColumnIndex(DataBaseHelper.QUANTITE ));

                somme = somme + ( montant * quantite );

            } while (c.moveToNext());
        }

        return somme;
    }

    /**
     * Updating a panier
     * @param article
     * @return
     */
    public int updateArticle(Article article) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.LIBELLE_A,  article.getLibelle ());
        values.put(DataBaseHelper.DATE_ACHAT, article.getDate ());
        values.put(DataBaseHelper.MONTANT   , article.getMontant ());
        values.put(DataBaseHelper.QUANTITE  , article.getQuantite ());
        values.put(DataBaseHelper.PHOTO      ,article.getPhoto ());
        values.put(DataBaseHelper.ESTACHETE , article.getEstAchete ());

        // updating row
        return database.update(DataBaseHelper.TABLE_ARTICLE, values, DataBaseHelper.IDARTICLE + " = ?",
                new String[] { String.valueOf(article.getIdArticle ()) });
    }
    /*
     * Deleting a panier
     */
    public void deleteArticle(int article_id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DataBaseHelper.TABLE_ARTICLE, DataBaseHelper.IDARTICLE + " = ?",
                new String[] { String.valueOf(article_id) });
    }
}
