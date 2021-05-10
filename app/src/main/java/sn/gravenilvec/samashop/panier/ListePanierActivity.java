package sn.gravenilvec.samashop.panier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.adapter.AdapterPanier;
import sn.gravenilvec.samashop.auth.LoginActivity;
import sn.gravenilvec.samashop.entity.Panier;
import sn.gravenilvec.samashop.loader.AsyncTask_Panier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListePanierActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Panier>> {
    // IDENTIFIANT a passer a lactivity suivant
    public static final String IDPERSONNETOPANIER= "ipPersonneToPanier";
    private LoaderManager loaderManager;
    private int personneID;
    private String  personneLOGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_liste_panier);

        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleConnection");
        personneID = (int)extra.get (LoginActivity.IDPERSONNE);
        personneLOGIN = (String)extra.get (LoginActivity.LOGIN);

        // Charger les donnees en arriere plan
        loaderManager  = getSupportLoaderManager ();
        loaderManager.initLoader (1,null,this);

        TextView idPersonneTextView  = findViewById (R.id.idPersonne);
        TextView loginPersonneTextView = findViewById (R.id.loginPersonne);
        // Afficher les identifiants de la personne connectée
        idPersonneTextView.setText ("ID : " + personneID);
        loginPersonneTextView.setText("Login : "+ personneLOGIN);

        // Ajouter un nouveau panier
        FloatingActionButton fab = ( FloatingActionButton) findViewById (R.id.fabPanier);
        fab.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),FormulairePanierActivity.class);
                // Passer les identifiants a l'activity suivant
                Bundle bundle = new Bundle ();
                bundle.putInt (IDPERSONNETOPANIER, personneID);
                intent.putExtra ("bundleFormulairePanier", bundle);
                startActivity (intent);

            }
        });
    }

    @NonNull
    @Override
    public Loader <List <Panier>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTask_Panier (this, personneID);
    }

    // Process pour charger les données de la base en arriere plan
    @Override
    public void onLoadFinished(Loader <List <Panier>> loader, List<Panier> data) {
        ListView listView_Panier = findViewById (R.id.listView_Panier);
        AdapterPanier adapterPanier = new AdapterPanier (getApplicationContext (),data);
        listView_Panier.setAdapter (adapterPanier);
    }
    @Override
    public void onLoaderReset(Loader<List <Panier>> loader) {

    }


}
