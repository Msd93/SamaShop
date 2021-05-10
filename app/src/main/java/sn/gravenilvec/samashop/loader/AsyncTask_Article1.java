package sn.gravenilvec.samashop.loader;

import android.content.Context;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.entity.Article;

public class AsyncTask_Article1 extends AsyncTaskLoader<List<Article>> {

    private ArticleDataSource articleDataSource;
    private int panierID;

    public AsyncTask_Article1(Context context, int panierID) {
        super (context);
        articleDataSource = new ArticleDataSource (context);
        this.panierID = panierID;
    }

    @Override
    public List <Article> loadInBackground() {

        return articleDataSource.getAllArticles1 (panierID);
    }

    @Override
    protected void onStartLoading() {

        forceLoad ( );
    }
}