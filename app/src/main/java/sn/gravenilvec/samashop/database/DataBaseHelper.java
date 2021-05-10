package sn.gravenilvec.samashop.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper{

    // Database Name
    public static final String DATABASE_NAME = "samashop.db";
    // Database version
    public static final int DATABASE_VERSION = 1 ;
    // Table Names
    public static final String TABLE_PERSONNE = "personne";
    public static final String TABLE_PANIER = "panier";
    public static final String TABLE_ARTICLE = "article";

    // PERSONNE Table - column names
    public static final String IDPERSONNE = "idPersonne";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    // PANIER Table - column names
    public static final String IDPANIER = "idPanier";
    public static final String PERSONNEID = "personneID";
    public static final String LIBELLE_P = "libelle";
    public static final String DATECREATION = "dateCreation";
    public static final String DATEMODIFICATION = "dateModification";
    public static final String MONTANTPREVU = "montantPrevu";

    // ARTICLE Table - column names
    public static final String IDARTICLE = "idArticle";
    public static final String PANIERID  = "panierID";
    public static final String LIBELLE_A = "libelle";
    public static final String DATE_ACHAT = "date";
    public static final String MONTANT   = "montant";
    public static final String QUANTITE  = "quantite";
    public static final String PHOTO     = "photo";
    public static final String ESTACHETE = "estachete";

    // Table Create Statements
    // Personne table create statement
    private static final String CREATE_TABLE_PERSONNE = "CREATE TABLE "
                                                        + TABLE_PERSONNE + " ("
                                                        + IDPERSONNE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                        + NOM + " TEXT,"
                                                        + PRENOM + " TEXT,"
                                                        + LOGIN + " TEXT,"
                                                        + PASSWORD +  " TEXT " + ");";
    // Panier table create statement
    private static final String CREATE_TABLE_PANIER = "CREATE TABLE "
                                                    + TABLE_PANIER + "("
                                                    + IDPANIER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                    + LIBELLE_P + " TEXT,"
                                                    + PERSONNEID + " INTEGER,"
                                                    + DATECREATION + " TEXT,"
                                                    + DATEMODIFICATION + " TEXT,"
                                                    + MONTANTPREVU + " NUMERIC,"
                                                    + " FOREIGN KEY ("+PERSONNEID+") REFERENCES "+TABLE_PERSONNE+"("+IDPERSONNE+") )";

    // Panier table create statement
    private static final String CREATE_TABLE_ARTICLE = " CREATE TABLE "
                                                      + TABLE_ARTICLE + "("
                                                       + IDARTICLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                       + LIBELLE_A + " TEXT,"
                                                       + DATE_ACHAT + " TEXT,"
                                                       + ESTACHETE + " INTEGER,"
                                                       + PANIERID +  " INTEGER,"
                                                       + MONTANT +   " NUMERIC,"
                                                       + QUANTITE +  " INTEGER,"
                                                       + PHOTO +  " TEXT,"
                                                       + " FOREIGN KEY ("+PANIERID+") REFERENCES "+TABLE_PANIER+"("+IDPANIER+") ON DELETE CASCADE)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         // creating required tables
        sqLiteDatabase.execSQL(CREATE_TABLE_PERSONNE);
        sqLiteDatabase.execSQL(CREATE_TABLE_PANIER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ARTICLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PANIER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);

        // create new tables
        onCreate(sqLiteDatabase);
    }
}