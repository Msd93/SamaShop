package sn.gravenilvec.samashop.auth;

import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.R;
import sn.gravenilvec.samashop.dao.PersonneDataSource;
import sn.gravenilvec.samashop.entity.Personne;
import sn.gravenilvec.samashop.panier.ListePanierActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // IDENTIFIANT a passer a lactivity suivant
    public static final String IDPERSONNE= "idPersonne";
    public static final String LOGIN = "login";
    private Personne personne;
    // Utiliser pour acceder à la base de données
    private PersonneDataSource personneDataSource;

    private EditText login, passorwd;
    private Button connexionBtn, inscripionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);


        personneDataSource = new PersonneDataSource (getApplicationContext ());

        login  = (EditText) findViewById (R.id.loginConnection);
        passorwd = (EditText) findViewById (R.id.pwdConnection);

        connexionBtn  = (Button) findViewById (R.id.connexionBtn);
        inscripionBtn = (Button) findViewById (R.id.inscriptionBtn);

        connexionBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                String loginText = login.getText ().toString ();
                String pwdText = passorwd.getText ().toString ();

                if ( !(loginText.equals ("") || pwdText.equals (""))) {
                    /**
                     * Bloc utilisé pour capturer les erreurs de null pointeur
                     */
                    Personne p = null;
                    try {
                        p = personneDataSource.getPersonnneByLoginAndPassword (loginText, pwdText);
                        if ( p != null ) {
                            Intent intent = new Intent (getApplicationContext (), ListePanierActivity.class);
                            // Passer les identifiants a l'activity suivant
                            Bundle bundle = new Bundle ();
                            bundle.putInt (IDPERSONNE, p.getIdPersonne ());
                            bundle.putCharSequence (LOGIN, p.getLogin ());
                            intent.putExtra ("bundleConnection", bundle);
                            startActivity (intent);
                        } else {
                            Toast.makeText (getApplicationContext ( ), "verifier vos identifiant de connexion :(", Toast.LENGTH_SHORT).show ( );
                        }
                    } catch (Exception e) {    }

                }else{
                    Toast.makeText (getApplicationContext ( ), "Veuillez renseigner tous les champs :(", Toast.LENGTH_SHORT).show ( );

                }

            }
        });
        inscripionBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                Intent inscriptionActivity = new Intent (getApplicationContext (), Inscription.class);
                startActivity (inscriptionActivity);

            }
        });
    }
}
