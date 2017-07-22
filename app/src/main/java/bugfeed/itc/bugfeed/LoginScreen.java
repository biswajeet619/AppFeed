package bugfeed.itc.bugfeed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends Activity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setTitle("Login");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        progressDialog = new ProgressDialog(this);
        bLogin.setOnClickListener(this);


    }
    private  void userlogin(){

        final String Email=etEmail.getText().toString().trim();
        String Password=etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {

            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseReference=firebaseDatabase.getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    String email=dataSnapshot1.child("email").getValue().toString();
                    if(email.equals(Email)){

                        String type=dataSnapshot1.child("Type").getValue().toString();
                        if(type.equals("Developer")){

                           openuseracc("Developer",email);
                        }
                        else if(type.equals("User")){

                            openuseracc("User",email);
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                    }
                });
    }
    @Override
    public void onClick(View v) {


        if(v==bLogin){

            userlogin();
        }


    }


    public void register(View view) {


        Intent intent=new Intent(LoginScreen.this,RegisterScreen.class);
        startActivity(intent);
    }
    public void openuseracc(String type,String Email){


        if(type.equals("Developer")){

            Toast.makeText(LoginScreen.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),DeveloperProfileActivity.class);
            intent.putExtra("email",Email);
            startActivity(intent);
            finish();

        }
        else if(type.equals("User")){

            Toast.makeText(LoginScreen.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),UserProfileActivity.class);
            intent.putExtra("email",Email);
            startActivity(intent);
            finish();

        }


    }

    public void forgotpass(View view) {

            Intent intent=new Intent(LoginScreen.this,ForgotPassword.class);
            startActivity(intent);

    }
}
