package bugfeed.itc.bugfeed;

import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity implements OnCheckedChangeListener {

    private EditText etname, etemail, etphone, etpass;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button registerButton;
    private String usertype;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        instilaiseView();
        radioGroup.setOnCheckedChangeListener(this);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerUser();

            }

        });


    }
    public void instilaiseView(){

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");
        registerButton = (Button) findViewById(R.id.RegisterButton);
        radioGroup = (RadioGroup) findViewById(R.id.usertypeRadio);
        etname=(EditText)findViewById(R.id.name);
        etemail=(EditText)findViewById(R.id.Email);
        etphone=(EditText)findViewById(R.id.Phone);
        etpass=(EditText)findViewById(R.id.etPassword);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


        usertype=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(RegisterScreen.this, "You Selected "+usertype, Toast.LENGTH_SHORT).show();


    }
    public void registerUser(){

        String name=etname.getText().toString();
        String email=etemail.getText().toString();
        String phone=etphone.getText().toString();
        String password=etpass.getText().toString();
        if(TextUtils.isEmpty(name)){


            etname.setError("Name Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(email)){


            etemail.setError("Email Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(phone)){


            etphone.setError("Phone Can't be Empty");
            return;
        }
        if(TextUtils.isEmpty(password)){


            etpass.setError("Password Can't be Empty");
            return;
        }
        String id=databaseReference.push().getKey();
        DatabaseReference d =databaseReference.child(id);
        d.child("email").setValue(email);
        d.child("Type").setValue(usertype);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    Toast.makeText(RegisterScreen.this, "Succesfully Regsitered as "+usertype, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}