package sn.gravenilvec.samashop.loader;

import android.content.Context;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;
import sn.gravenilvec.samashop.dao.PanierDataSource;
import sn.gravenilvec.samashop.entity.Panier;

public class AsyncTask_Panier extends AsyncTaskLoader<List<Panier>> {

    private PanierDataSource panierDataSource;
    private int personneID;

    public AsyncTask_Panier(Context context, int personneID) {
        super (context);
        panierDataSource = new PanierDataSource (context);
        this.personneID = personneID;
    }

    @Override
    public List <Panier> loadInBackground() {

        return panierDataSource.getAllPaniers ( personneID );
    }

    @Override
    protected void onStartLoading() {

        forceLoad ( );
    }
}