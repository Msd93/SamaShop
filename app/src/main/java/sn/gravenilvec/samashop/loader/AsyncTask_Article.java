package sn.gravenilvec.samashop.loader;

import android.content.Context;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.dao.PanierDataSource;
import sn.gravenilvec.samashop.entity.Article;
import sn.gravenilvec.samashop.entity.Panier;

public class AsyncTask_Article extends AsyncTaskLoader<List<Article>> {

    private ArticleDataSource articleDataSource;
    private int panierID;

    public AsyncTask_Article(Context context, int panierID) {
        super (context);
        articleDataSource = new ArticleDataSource (context);
        this.panierID = panierID;
    }

    @Override
    public List <Article> loadInBackground() {

        return articleDataSource.getAllArticles (panierID);
    }

    @Override
    protected void onStartLoading() {

        forceLoad ( );
    }
}