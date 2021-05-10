package sn.gravenilvec.samashop.panier;

import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.auth.LoginActivity;
import sn.gravenilvec.samashop.dao.PanierDataSource;
import sn.gravenilvec.samashop.entity.Panier;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class FormulairePanierActivity extends AppCompatActivity {

    DatePickerDialog picker;
    private int personneID;
    private PanierDataSource panierDataSource;
    private Button ajouterPanierBtn, annulerPanierBtn;
    private EditText libellePanier, montantPanier, dateCPanier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_formulaire_panier);

        DisplayMetrics dm = new DisplayMetrics ( );
        getWindowManager ().getDefaultDisplay ( ).getMetrics (dm);
        int Width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow ().setLayout ((int) (Width * .8), (int) (height * .6 ));

        panierDataSource = new PanierDataSource (getApplicationContext ());

        // Recuperer les widgets
        libellePanier = (EditText) findViewById (R.id.libellePanier);
        montantPanier = (EditText) findViewById (R.id.montantPanier);
        dateCPanier = (EditText) findViewById (R.id.dateCPanier);
        dateCPanier.setInputType(InputType.TYPE_NULL);

        // Buttons
        ajouterPanierBtn = (Button) findViewById (R.id.ajouterPanier);
        annulerPanierBtn = (Button) findViewById (R.id.annulerPanier);

        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleFormulairePanier");
        personneID = (int)extra.get (ListePanierActivity.IDPERSONNETOPANIER);

        // Parametre pour la date de creation
        dateCPanier.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance ( );
                int day = calendar.get (Calendar.DAY_OF_MONTH);
                int month = calendar.get (Calendar.MONTH);
                int year = calendar.get (Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog (FormulairePanierActivity.this,
                        new DatePickerDialog.OnDateSetListener ( ) {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateCPanier.setText (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show ( );
            }
        });


        ajouterPanierBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if ( !(
                        libellePanier.getText ().toString ().equals ("") ||
                        montantPanier.getText ().toString ().equals ("") ||
                        dateCPanier.getText ().toString ().equals (""))) {

                    // Recupere le contenu des Editext
                    final String libelleText = libellePanier.getText ().toString ();
                    final String dateCPanierText = dateCPanier.getText ().toString ();
                    final Double montantPanierDouble =  Double.parseDouble (montantPanier.getText ().toString ());

                    Panier p = new Panier (personneID, libelleText, dateCPanierText, dateCPanierText, montantPanierDouble);
                    boolean estCree = panierDataSource.createPanier (p);
                    if ( estCree == true ){
                        Toast.makeText (getApplicationContext ( ), "Liste ajoutée avec succes :)", Toast.LENGTH_SHORT).show ( );
                        finish ();
                    }
                    else
                        Toast.makeText (getApplicationContext ( ), "Erreur lors de l'ajout d'une nouvelle liste :(", Toast.LENGTH_SHORT).show ( );
                }
                else{
                    Toast.makeText (getApplicationContext ( ), "Veuillez renseigner tous les champs :(", Toast.LENGTH_SHORT).show ( );
                }
                }
        });
        annulerPanierBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                finish ();
            }
        });
    }
}
