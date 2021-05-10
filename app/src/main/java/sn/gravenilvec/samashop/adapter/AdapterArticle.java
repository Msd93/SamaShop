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

public class AdapterArticle extends BaseAdapter {
    public static final String IDARTICLE = "idArticle";
    private int articleID;
    private Context context;
    private ArticleDataSource articleDataSource;
    private List <Article> articleList;
    private Article article;
    private LayoutInflater layoutInflater;

    public AdapterArticle(Context context, List <Article> articleList) {
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

        view = layoutInflater.inflate (R.layout.item_achat, null);

        article = getItem (i);
        articleID  = article.getIdArticle ();
        final int id = article.getIdArticle ();
        int quantite = article.getQuantite ();
        Double monatnt = article.getMontant ();
        String libelle = article.getLibelle ();
        String photo = article.getPhoto ();
        int panierID = article.getPanierId ();
        final int achete = article.getEstAchete ();

        // Widgets

        //
        LinearLayout linearLayout = view.findViewById (R.id.linearLayoutItemArticle);
        // Appuis long pour modifier
        linearLayout.setOnLongClickListener (new View.OnLongClickListener ( ) {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent (context, ModifierAchatActivity.class);
                // Passer l'identifiant article l'activity de modification
                Bundle bundle = new Bundle ();
                bundle.putInt (IDARTICLE, articleID);
                intent.putExtra ("bundleModifierArticle", bundle);
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity (intent);
                return true;
            }
        });

        //
        TextView libelleItemArticle = view.findViewById (R.id.libelleItemArticle);
        libelleItemArticle.append (" : " + libelle);

        TextView montantItemArticle = view.findViewById (R.id.montantItemArticle);
        montantItemArticle.append (" \n " + monatnt);
        TextView quantiteArticle = view.findViewById (R.id.quantiteItemArticle);
        quantiteArticle.append (" \n " + quantite);
        //
        final CheckBox checkboxxItemArticle = view.findViewById (R.id.checkboxxItemArticle);
        if( achete == 0 ){
            checkboxxItemArticle.setChecked ( false );
        }else{
            checkboxxItemArticle.setChecked ( true );
        }

        checkboxxItemArticle.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if( checkboxxItemArticle.isChecked () == true){
                  article.setEstAchete (1);
                  articleDataSource.updateArticle (article);
                  Toast.makeText (context, "Achat de l'article :  " + id + " validé ",Toast.LENGTH_LONG ).show ();
                  AdapterArticle.this.notifyDataSetChanged ();

              }
              else{
                  article.setEstAchete (0);
                  articleDataSource.updateArticle (article);
                  Toast.makeText (context, "Achat de l'article :  " + id + " en attente ",Toast.LENGTH_LONG ).show ();
                  AdapterArticle.this.notifyDataSetChanged ();

              }
            }
        });

        //
        ImageView photoItemArticle = view.findViewById (R.id.photoItemArticle);

        // Les images sont enregistres dans le dossier assets

        if(photo != null) {
            Bitmap bitmap = BitmapFactory.decodeFile (photo);
            photoItemArticle.setImageBitmap (bitmap);
        }else{
            AssetManager assetManager = context.getAssets();
            try{
                InputStream inputStream = assetManager.open("missing.png");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                photoItemArticle.setImageBitmap(bitmap);
            }catch (IOException e){}
        }

        ImageView deleteItemArticle = view.findViewById (R.id.deleteItemArticle);

        deleteItemArticle.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                articleDataSource.deleteArticle (id);
                Toast.makeText (context, "Vous avez retirer l'article :  " + id + " des achats",Toast.LENGTH_LONG ).show ();
                //Pour mettre a jour la listView
                articleList.remove (i);
                AdapterArticle.this.notifyDataSetChanged ();
            }
        });



        return view;
    }
}
