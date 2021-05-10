package sn.gravenilvec.samashop.achat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.entity.Article;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class FormulaireAchatActivity extends AppCompatActivity {


    private DatePickerDialog picker;

    // Load image
    private static int RESULT_LOAD_IMAGE = 1;
    // Chemin
    private String picturePath;

    private int panierID;
    private Button ajouterArticleBtn, annulerArticleBtn, photoArticleBtn;
    private EditText libelleArticleEditText, montantArticleEditText, quantiteArtiEditText, dateAchatArticleEditText;
    private ImageView showImageView;
    private ArticleDataSource articleDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_formulaire_achat);
        // Permission pour acceder aux photos
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        DisplayMetrics dm = new DisplayMetrics ( );
        getWindowManager ().getDefaultDisplay ( ).getMetrics (dm);
        int Width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow ().setLayout ((int) (Width * .8), (int) (height * .6 ));

        articleDataSource = new ArticleDataSource (this);

        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleFormulaireArticle");
        panierID = (int)extra.get (ListAchatActivity.IDPANIERTOARTICLE);

        //Image
        showImageView = (ImageView) findViewById (R.id.showPhotoArticle);
        //Editext
        libelleArticleEditText = (EditText) findViewById (R.id.libelleArticle);
        montantArticleEditText = (EditText) findViewById (R.id.montantArticle);
        quantiteArtiEditText = (EditText) findViewById (R.id.quantiteArticle);

        // Configuration du date d'achat
        dateAchatArticleEditText = (EditText) findViewById (R.id.dateAchatArticle);
        dateAchatArticleEditText.setInputType(InputType.TYPE_NULL);
        // Parametre pour la date de creation
        dateAchatArticleEditText.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ( );
                int day = calendar.get (Calendar.DAY_OF_MONTH);
                int month = calendar.get (Calendar.MONTH);
                int year = calendar.get (Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog (FormulaireAchatActivity.this,
                        new DatePickerDialog.OnDateSetListener ( ) {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateAchatArticleEditText.setText (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show ( );
            }
        });
        // Buttons
        ajouterArticleBtn = (Button) findViewById (R.id.ajouterArticle);
        annulerArticleBtn = (Button) findViewById (R.id.annulerArticle);
        photoArticleBtn = (Button) findViewById (R.id.loadPhotoArticle);

        // Load image
        photoArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult (i, RESULT_LOAD_IMAGE);
            }
        });

        ajouterArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if ( !(
                        libelleArticleEditText.getText ( ).toString ( ).equals ("") ||
                        montantArticleEditText.getText ( ).toString ( ).equals ("") ||
                        quantiteArtiEditText.getText ( ).toString ( ).equals ("") ||
                        dateAchatArticleEditText.getText ().toString ().equals ("")
                ) ) {

                    String libelle = libelleArticleEditText.getText ( ).toString ( );
                    Double montant = Double.parseDouble (montantArticleEditText.getText ( ).toString ( ));
                    int quanite = Integer.parseInt (quantiteArtiEditText.getText ( ).toString ( ));
                    String date = dateAchatArticleEditText.getText ().toString ();

                    Article article = new Article (panierID, libelle, picturePath, date , montant, quanite, 0);
                    boolean estCree = articleDataSource.createArticle (article);
                    if ( estCree == true ) {
                        Toast.makeText (getApplicationContext ( ), "Article ajouté avec succes :)", Toast.LENGTH_SHORT).show ( );
                        finish ( );
                    } else {
                        Toast.makeText (getApplicationContext ( ), "Erreur lors de l'ajout d'une nouveau achat :(", Toast.LENGTH_SHORT).show ( );
                    }
                } else {
                    Toast.makeText (getApplicationContext ( ), "Veuillez renseigner tous les champs :(", Toast.LENGTH_SHORT).show ( );
                }
            }
        });
        annulerArticleBtn.setOnClickListener (new View.OnClickListener ( ) {
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

            showImageView.setImageBitmap (BitmapFactory.decodeFile (picturePath));
            // showImageView.setText (picturePath);



        }
    }
    // Resultat obtenu apres la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }
}
