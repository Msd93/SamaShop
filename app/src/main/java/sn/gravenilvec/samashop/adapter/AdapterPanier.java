package sn.gravenilvec.samashop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.achat.ListAchatActivity;
import sn.gravenilvec.samashop.dao.ArticleDataSource;
import sn.gravenilvec.samashop.dao.PanierDataSource;
import sn.gravenilvec.samashop.entity.Panier;
import sn.gravenilvec.samashop.panier.ModificationPanierActivity;

public class AdapterPanier extends BaseAdapter {

    // IDENTIFIANT a passer a l'activity suivant
    public static final String IDPANIER = "idPanier";
    public static final String LIBELLEPANIER = "libellePanier";
    private Panier panier;
    private Context context;
    private PanierDataSource panierDataSource;
    private ArticleDataSource articleDataSource;
    private List<Panier> panierList;
    private LayoutInflater layoutInflater;

    public AdapterPanier(Context context, List <Panier> panierList) {
        this.context = context;
        this.panierDataSource = new PanierDataSource (context);
        this.articleDataSource = new ArticleDataSource (context);
        this.panierList = panierList;
        this.layoutInflater = layoutInflater.from (context);
    }

    @Override
    public int getCount() {
        return panierList.size ( );
    }

    @Override
    public Panier getItem(int i) {
        return panierList.get (i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate (R.layout.item_panier, null);

        panier = getItem (i);
        final int panierId = panier.getIdPanier ();
        final String libelle = panier.getLibelle ();
        String dateCreation = panier.getDateCreation ();
        String dateMreation = panier.getDateModification ();
        Double montant = panier.getMontantPrevu ();

        // Recuperation des widgets de la liste
        // TextView || Text
        TextView libelleTextView = view.findViewById (R.id.libelleItemPanier);
        libelleTextView.append (" \t"+ libelle);

        TextView dateCreationTextView = view.findViewById (R.id.dateCItemPanier);
        dateCreationTextView.append (" \n"+ dateCreation);

        TextView dateModificationTextView = view.findViewById (R.id.dateMItemPanier);
        dateModificationTextView.append (" \n"+ dateMreation );

        TextView montantTextView = view.findViewById (R.id.montantItemPanier);
        montantTextView.append (" \n "+ montant);

        // Calcul pour les depenses
        Double depense = articleDataSource.getDepenses (panierId);
        TextView depenseItemPanier = view.findViewById (R.id.depenseItemPanier);
        depenseItemPanier.append (" \n "+ depense);

        TextView soldeItemPanier = view.findViewById (R.id.soldeItemPanier);
        if ( montant - depense < 0) {
            soldeItemPanier.setTextColor (R.color.colorAccent);
        }else{
            soldeItemPanier.setTextColor (R.color.colorPrimaryDark);
        }

        soldeItemPanier.append (" \n " + (montant - depense));

        // LinearLayout pour modifier la liste | panier
        LinearLayout linearLayoutItemPanier = view.findViewById (R.id.linearLayoutItemPanier);



        ImageView imageItemPanierImageView = view.findViewById (R.id.imageItemPanier);

        // Suppression d'un panier
        imageItemPanierImageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                panierDataSource.deletePanier (panierId);
                Toast.makeText (context, "Vous avez retirer le panier :  " + panierId + " de la liste",Toast.LENGTH_LONG ).show ();
                //Pour mettre a jour la listView
                panierList.remove (i);
                AdapterPanier.this.notifyDataSetChanged ();

            }
        });

        linearLayoutItemPanier.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, ListAchatActivity.class);
                // Passer l'identifiants panier l'activity d'achat
                Bundle bundle = new Bundle ();
                bundle.putInt (IDPANIER, panierId);
                bundle.putString (LIBELLEPANIER, libelle);
                intent.putExtra ("bundleModifierPanier", bundle);
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity (intent);
            }
        });
        linearLayoutItemPanier.setOnLongClickListener (new View.OnLongClickListener ( ) {
            @Override
            public boolean onLongClick(View view) {

                Intent intent = new Intent (context, ModificationPanierActivity.class);
                // Passer l'identifiants panier l'activity de modification
                Bundle bundle = new Bundle ();
                bundle.putInt (IDPANIER, panierId);
                intent.putExtra ("bundleModifierPanier", bundle);
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity (intent);
                return true;
            }
        });

        return view;
    }
}
