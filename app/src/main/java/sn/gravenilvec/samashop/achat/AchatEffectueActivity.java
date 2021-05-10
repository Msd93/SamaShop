package sn.gravenilvec.samashop.achat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.adapter.AdapterArticle;
import sn.gravenilvec.samashop.adapter.AdapterArticle1;
import sn.gravenilvec.samashop.adapter.AdapterPanier;
import sn.gravenilvec.samashop.entity.Article;
import sn.gravenilvec.samashop.loader.AsyncTask_Article;
import sn.gravenilvec.samashop.loader.AsyncTask_Article1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AchatEffectueActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List <Article>>{

    private int panierID;
    private String panierLIBELLE;
    private LoaderManager loaderManager;

    private TextView achatEncours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_achat_effectue);

        achatEncours = (TextView)findViewById (R.id.Achat_En_Cours2);
        // Recuperer les identifiants passés apres la connection
        Bundle extra = getIntent ().getBundleExtra ("bundleAchatEffectue");
        panierID = (int)extra.get (ListAchatActivity.IDPANIERTOARTICLE);
        panierLIBELLE = (String)extra.get (ListAchatActivity.LIBELLETOARTICLE);

        loaderManager  = getSupportLoaderManager ();
        loaderManager.initLoader (1,null,this);

        TextView idPanierToArticle = (TextView) findViewById (R.id.idPanierToArticle1);
        idPanierToArticle.append (" "+ panierID);
        TextView libellePanierToArticle = (TextView) findViewById (R.id.libellePanierToArticle1);
        libellePanierToArticle.append (" "+ panierLIBELLE);

        achatEncours.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext (),ListAchatActivity.class);
                // Passer les identifiants a l'activity suivant
                Bundle bundle = new Bundle ();
                bundle.putInt (AdapterPanier.IDPANIER, panierID);
                bundle.putString (AdapterPanier.LIBELLEPANIER, panierLIBELLE);
                intent.putExtra ("bundleModifierPanier", bundle);
                startActivity (intent);
            }
        });
    }
    @NonNull
    @Override
    public Loader <List <Article>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTask_Article1 (this, panierID);
    }

    // Process pour charger les données de la base en arriere plan
    @Override
    public void onLoadFinished(Loader <List <Article>> loader, List<Article> data) {
        ListView listView_Article = findViewById (R.id.listView_Article1);
        AdapterArticle1 adapterArticle = new AdapterArticle1 (getApplicationContext (),data);
        listView_Article.setAdapter (adapterArticle);
    }
    @Override
    public void onLoaderReset(Loader<List <Article>> loader) {

    }
}
