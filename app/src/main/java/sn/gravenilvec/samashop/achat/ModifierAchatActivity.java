package sn.gravenilvec.samashop.achat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.adapter.AdapterArticle;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.entity.Article;
import sn.gravenilvec.samashop.panier.ModificationPanierActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class ModifierAchatActivity extends AppCompatActivity {



    private DatePickerDialog picker;

    private ArticleDataSource articleDataSource;
    private int articleID;

    // Load image
    private static int RESULT_LOAD_IMAGE = 1;
    // Chemin
    private String picturePath;

    private String photo;

    private int achete;

    private Button validerModifierArticleBtn, annulerModifierArticleBtn, photoModifierArticleBtn;
    private EditText libelleModifierArticleEditText, montantModifierArticleEditText, quantiteModifierArtiEditText, dateAchatModifierEditText;
    private ImageView showModifierImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_modifier_achat);

        // Permission pour acceder aux photos
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        DisplayMetrics dm = new DisplayMetrics ( );
        getWindowManager ().getDefaultDisplay ( ).getMetrics (dm);
        int Width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow ().setLayout ((int) (Width * .8), (int) (height * .6 ));

        articleDataSource = new ArticleDataSource (this);

        // Recuperer les identifiants passés
        Bundle extra = getIntent ().getBundleExtra ("bundleModifierArticle");
        articleID = (int)extra.get (AdapterArticle.IDARTICLE);

        //Image
        showModifierImageView = (ImageView) findViewById (R.id.showPhotoModifierArticle);
        //Editext
        libelleModifierArticleEditText = (EditText) findViewById (R.id.libelleModifierArticle);
        montantModifierArticleEditText = (EditText) findViewById (R.id.montantModifierArticle);
        quantiteModifierArtiEditText = (EditText) findViewById (R.id.quantiteModifierArticle);

        // configuration de la date
        dateAchatModifierEditText = (EditText) findViewById (R.id.dateAchatModifier);
        dateAchatModifierEditText.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance ( );
                int day = calendar.get (Calendar.DAY_OF_MONTH);
                int month = calendar.get (Calendar.MONTH);
                int year = calendar.get (Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog (ModifierAchatActivity.this,
                        new DatePickerDialog.OnDateSetListener ( ) {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateAchatModifierEditText.setText (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show ( );
            }
        });
        // Buttons
        validerModifierArticleBtn = (Button) findViewById (R.id.ajouterModifierArticle);
        annulerModifierArticleBtn = (Button) findViewById (R.id.annulerModifierArticle);
        photoModifierArticleBtn = (Button) findViewById (R.id.loadPhotoModifierArticle);

        // Renseigner les champs au chargement de l'activity
        Article article = null;
        try{
            article = articleDataSource.getPArticleByIDARTICLE (articleID);
            if( article != null){
                libelleModifierArticleEditText.setText (article.getLibelle ());
                montantModifierArticleEditText.setText (String.valueOf (article.getMontant ()));
                quantiteModifierArtiEditText.setText (String.valueOf (article.getQuantite ()));
                dateAchatModifierEditText.setText (article.getDate ());

                achete = article.getEstAchete ();
                photo = article.getPhoto ();

                if( article.getPhoto () != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile ( article.getPhoto ());
                    showModifierImageView.setImageBitmap (bitmap);
                }else{
                    AssetManager assetManager = getAssets ();
                    try{
                        InputStream inputStream = assetManager.open("missing.png");
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        showModifierImageView.setImageBitmap(bitmap);
                    }catch (IOException e){}
                }
            }
        }catch (SQLException e){

        }
        // Load image
        photoModifierArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        validerModifierArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if ( !(
                        libelleModifierArticleEditText.getText ( ).toString ( ).equals ("") ||
                        montantModifierArticleEditText.getText ( ).toString ( ).equals ("") ||
                        quantiteModifierArtiEditText.getText ( ).toString ( ).equals ("")
                ) ) {
                    if ( picturePath != null ) {
                        Article a = new Article ( );
                        a.setIdArticle (articleID);
                        a.setEstAchete (achete);
                        a.setLibelle (libelleModifierArticleEditText.getText ( ).toString ( ));
                        a.setMontant (Double.parseDouble (montantModifierArticleEditText.getText ( ).toString ( )));
                        a.setQuantite (Integer.parseInt (quantiteModifierArtiEditText.getText ( ).toString ( )));
                        a.setDate (dateAchatModifierEditText.getText ().toString ());
                        a.setPhoto (picturePath);
                        int result = articleDataSource.updateArticle (a);
                        if ( result != 0 ) {
                            Toast.makeText (getApplicationContext ( ), "Achat modifié avec succes :)", Toast.LENGTH_SHORT).show ( );
                            finish ( );
                        } else {
                            Toast.makeText (getApplicationContext ( ), "Echec de la modification :(", Toast.LENGTH_SHORT).show ( );
                        }
                    } else {
                        Article a = new Article ( );
                        a.setIdArticle (articleID);
                        a.setEstAchete (achete);
                        a.setLibelle (libelleModifierArticleEditText.getText ( ).toString ( ));
                        a.setMontant (Double.parseDouble (montantModifierArticleEditText.getText ( ).toString ( )));
                        a.setQuantite (Integer.parseInt (quantiteModifierArtiEditText.getText ( ).toString ( )));
                        a.setDate (dateAchatModifierEditText.getText ().toString ());
                        a.setPhoto (photo);
                        int result = articleDataSource.updateArticle (a);
                        if ( result != 0 ) {
                            Toast.makeText (getApplicationContext ( ), "Achat modifié avec succes :)", Toast.LENGTH_SHORT).show ( );
                            finish ( );
                        } else {
                            Toast.makeText (getApplicationContext ( ), "Echec de la modification :(", Toast.LENGTH_SHORT).show ( );
                        }
                    }
                } else {
                    Toast.makeText (getApplicationContext ( ), "Veuillez renseigner tous les champs :(", Toast.LENGTH_SHORT).show ( );
                }
            }
            });
        annulerModifierArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                finish ();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if ( requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data ) {

            Uri selectedImage = data.getData ( );
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver ( ).query (selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst ( );

            int columnIndex = cursor.getColumnIndex (filePathColumn[0]);
            picturePath = cursor.getString (columnIndex);
            cursor.close ( );

            showModifierImageView.setImageBitmap (BitmapFactory.decodeFile (picturePath));



        }
    }
    // Resultat obtenu apres la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }
}
