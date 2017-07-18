package bugfeed.itc.bugfeed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends Activity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private TextView registerLink;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        progressDialog = new ProgressDialog(this);
        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);


    }
    private  void userlogin(){

        String Email=etEmail.getText().toString().trim();
        String Password=etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            finish();
                            //startActivity(new Intent(getApplicationContext(),UserAreaActivity.class));
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {


        if(v==bLogin){

            userlogin();
        }
        else if(v==registerLink){

            finish();
            startActivity(new Intent(this,RegisterDeveloperScreen.class));
        }

    }


}
