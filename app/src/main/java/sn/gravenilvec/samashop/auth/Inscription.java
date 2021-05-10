package sn.gravenilvec.samashop.auth;

import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.dao.PersonneDataSource;
import sn.gravenilvec.samashop.entity.Personne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends AppCompatActivity {

    private PersonneDataSource personneDataSource;
    private EditText nom, prenom, login, pwd;
    private Button validerBtn, annulerBtBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_inscription);

        nom  = (EditText) findViewById (R.id.nom);
        prenom  = (EditText) findViewById (R.id.prenom);
        login  = (EditText) findViewById (R.id.login);
        pwd  = (EditText) findViewById (R.id.pwd);
        personneDataSource = new PersonneDataSource (getApplicationContext ());


        validerBtn = (Button) findViewById (R.id.validerBtn);
        annulerBtBtn = (Button) findViewById (R.id.annulerBtn);

        validerBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                String nomText = nom.getText ( ).toString ( );
                String prenomText = nom.getText ( ).toString ( );
                String loginText = login.getText ( ).toString ( );
                String pwdText = pwd.getText ( ).toString ( );

                if ( !(nomText.equals ("") || prenomText.equals ("") || loginText.equals ("") || pwdText.equals (""))){
                    Personne personne = new Personne (nomText, prenomText, loginText, pwdText);
                    boolean estCree = personneDataSource.createPersonne (personne);
                    if ( estCree == true ){
                        Toast.makeText (getApplicationContext ( ), "Personne ajouté avec succes :)", Toast.LENGTH_SHORT).show ( );
                        }
                    else
                        Toast.makeText (getApplicationContext ( ), "Erreur lors de l'ajout d'une nouvelle personne :(", Toast.LENGTH_SHORT).show ( );
                }else{
                    Toast.makeText (getApplicationContext ( ), "Veuillez renseigner tous les champs :(", Toast.LENGTH_SHORT).show ( );

                }
            }
        });
        annulerBtBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent inscriptionActivity = new Intent (getApplicationContext (), LoginActivity.class);
                startActivity (inscriptionActivity);
                finish ();
            }
        });

    }

}
