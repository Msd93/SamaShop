package sn.gravenilvec.samashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.achat.ModifierAchatActivity;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.entity.Article;

public class AdapterArticle1 extends BaseAdapter {
    public static final String IDARTICLE = "idArticle";
    private int articleID;
    private Context context;
    private ArticleDataSource articleDataSource;
    private List <Article> articleList;
    private Article article;
    private LayoutInflater layoutInflater;

    public AdapterArticle1(Context context, List <Article> articleList) {
        this.context = context;
        this.articleDataSource = new ArticleDataSource (context);
        this.articleList = articleList;
        this.layoutInflater = layoutInflater.from (context);
    }

    @Override
    public int getCount() {
        return articleList.size ( );
    }

    @Override
    public Article getItem(int i) {
        return articleList.get (i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate (R.layout.item_achat1, null);

        article = getItem (i);
        articleID  = article.getIdArticle ();
        final int id = article.getIdArticle ();
        int quantite = article.getQuantite ();
        Double monatnt = article.getMontant ();
        String libelle = article.getLibelle ();
        String date = article.getDate ();
        String photo = article.getPhoto ();
        int panierID = article.getPanierId ();
        final int achete = article.getEstAchete ();
        //
        TextView libelleItemArticle = view.findViewById (R.id.libelleItemArticle);
        libelleItemArticle.append (" : " + libelle);

        TextView dateItemArticle = view.findViewById (R.id.dateItemArticle);
        dateItemArticle.append (" : " + date);

        TextView montantItemArticle = view.findViewById (R.id.montantItemArticle);
        montantItemArticle.append (" \n " + monatnt);
        TextView quantiteArticle = view.findViewById (R.id.quantiteItemArticle);
        quantiteArticle.append (" \n " + quantite);

        return view;
    }
}
