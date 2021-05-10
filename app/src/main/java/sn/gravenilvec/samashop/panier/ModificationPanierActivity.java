package sn.gravenilvec.samashop.panier;

import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.adapter.AdapterPanier;
import sn.gravenilvec.samashop.dao.PanierDataSource;
import sn.gravenilvec.samashop.entity.Panier;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class ModificationPanierActivity extends AppCompatActivity {

    private PanierDataSource panierDataSource;
    private DatePickerDialog picker;
    private int panierID;
    private EditText libellePanierModification, montantPanierModification, dateMPanier;
    private Button validerPanierBtn, annulerPanierBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_modification_panier);



        DisplayMetrics dm = new DisplayMetrics ( );
        getWindowManager ().getDefaultDisplay ( ).getMetrics (dm);
        int Width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow ().setLayout ((int) (Width * .8), (int) (height * .6 ));

        panierDataSource = new PanierDataSource (this);

        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleModifierPanier");
        panierID = (int)extra.get (AdapterPanier.IDPANIER);
        //
        // Recuperer les widgets
        libellePanierModification = (EditText) findViewById (R.id.libellePanierModidifier);
        montantPanierModification = (EditText) findViewById (R.id.montantPanierModifier);
        dateMPanier = (EditText) findViewById (R.id.dateMPanierModifier);
        dateMPanier.setInputType(InputType.TYPE_NULL);
        // Buttons
        validerPanierBtn = (Button) findViewById (R.id.modifierPanier);
        annulerPanierBtn = (Button) findViewById (R.id.annulerPanierModifier);

        // Parametre pour la date de creation
        dateMPanier.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance ( );
                int day = calendar.get (Calendar.DAY_OF_MONTH);
                int month = calendar.get (Calendar.MONTH);
                int year = calendar.get (Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog (ModificationPanierActivity.this,
                        new DatePickerDialog.OnDateSetListener ( ) {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateMPanier.setText (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show ( );
            }
        });

        Panier panier = null;
        try {
            panier = panierDataSource.getPanierByIDPANIER (panierID);
            if( panier != null ){
                libellePanierModification.setText ( panier.getLibelle () );
                montantPanierModification.setText (String.valueOf (panier.getMontantPrevu ()));
                dateMPanier.setText (panier.getDateModification ());
            }
        }catch (SQLException e){}

        validerPanierBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if ( !(
                        libellePanierModification.getText ().toString ().equals ("") ||
                        montantPanierModification.getText ().toString ().equals ("") ||
                        dateMPanier.getText ().toString ().equals (""))) {
                    //
                    Panier p = new Panier ();
                    p.setIdPanier (panierID);
                    p.setLibelle (libellePanierModification.getText ().toString ());
                    p.setMontantPrevu (Double.parseDouble (montantPanierModification.getText ().toString ()));
                    p.setDateModification (dateMPanier.getText ().toString ());
                    int result = panierDataSource.updatePanier (p);
                    if( result != 0 ){
                    Toast.makeText (getApplicationContext ( ), "Liste modifiée avec succes :)", Toast.LENGTH_SHORT).show ( );
                    finish ();
                    }else {
                        Toast.makeText (getApplicationContext ( ), "Echec de la modification :(" ,Toast.LENGTH_SHORT).show ( );
                    }

                }else{
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
