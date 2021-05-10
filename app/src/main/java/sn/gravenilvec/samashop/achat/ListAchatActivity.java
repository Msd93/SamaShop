package sn.gravenilvec.samashop.achat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.adapter.AdapterArticle;
import sn.gravenilvec.samashop.adapter.AdapterPanier;
import sn.gravenilvec.samashop.entity.Article;
import sn.gravenilvec.samashop.loader.AsyncTask_Article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListAchatActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>{

    private int panierID;
    private String panierLIBELLE;
    // IDENTIFIANT a passer a lactivity suivant
    public static final String IDPANIERTOARTICLE = "ipPanierToArticle";
    public static final String LIBELLETOARTICLE = "libelleToArticle";
    private LoaderManager loaderManager;

    private TextView achatEffectue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_list_achat);


        achatEffectue = (TextView)findViewById (R.id.Achat_effectue1);
        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleModifierPanier");
        try {
            panierID = (int) extra.get (AdapterPanier.IDPANIER);
            panierLIBELLE = (String) extra.get (AdapterPanier.LIBELLEPANIER);
        }catch (Exception e){

        }

        loaderManager  = getSupportLoaderManager ();
        loaderManager.initLoader (1,null,this);

        TextView idPanierToArticle = (TextView) findViewById (R.id.idPanierToArticle);
        idPanierToArticle.append (" "+ panierID);
        TextView libellePanierToArticle = (TextView) findViewById (R.id.libellePanierToArticle);
        libellePanierToArticle.append (" "+ panierLIBELLE);


        // Ajouter un nouveau panier
        FloatingActionButton fab = ( FloatingActionButton) findViewById (R.id.fabArticle);
        fab.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),FormulaireAchatActivity.class);
                // Passer les identifiants a l'activity suivant
                Bundle bundle = new Bundle ();
                bundle.putInt (IDPANIERTOARTICLE, panierID);
                bundle.putString (LIBELLETOARTICLE, panierLIBELLE);
                intent.putExtra ("bundleFormulaireArticle", bundle);
                startActivity (intent);

            }
        });

        achatEffectue.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),AchatEffectueActivity.class);
                // Passer les identifiants a l'activity suivant
                Bundle bundle = new Bundle ();
                bundle.putInt (IDPANIERTOARTICLE, panierID);
                bundle.putString (LIBELLETOARTICLE, panierLIBELLE);
                intent.putExtra ("bundleAchatEffectue", bundle);
                startActivity (intent);
            }
        });

    }

    @NonNull
    @Override
    public Loader <List <Article>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTask_Article (this, panierID);
    }

    // Process pour charger les données de la base en arriere plan
    @Override
    public void onLoadFinished(Loader <List <Article>> loader, List<Article> data) {
        ListView listView_Article = findViewById (R.id.listView_Article);
        AdapterArticle adapterArticle = new AdapterArticle (getApplicationContext (),data);
        listView_Article.setAdapter (adapterArticle);
    }
    @Override
    public void onLoaderReset(Loader<List <Article>> loader) {

    }
}
