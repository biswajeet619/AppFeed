package bugfeed.itc.bugfeed;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.button;
import static android.R.attr.data;

public class UserComplaintActivity extends AppCompatActivity {


    private CheckBox crash,virus,hanging,authentication;
    private EditText problemdesc;
    private Button image;
    private Button submit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String crashstr,virustsr,hangingstr,authenticationstr;
    private String problemdescstr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        crash=(CheckBox)findViewById(R.id.crash);
        virus=(CheckBox)findViewById(R.id.virus);
        hanging=(CheckBox)findViewById(R.id.hanging);
        authentication=(CheckBox)findViewById(R.id.auth);
        problemdesc=(EditText)findViewById(R.id.problemdesc);
        submit=(Button)findViewById(R.id.submit);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(virus.isChecked()){

                    virustsr=virus.getText().toString();
                }
                if(crash.isChecked()){

                    crashstr=crash.getText().toString();
                }
                if(hanging.isChecked()){

                    hangingstr=hanging.getText().toString();
                }
                if(authentication.isChecked()){

                    authenticationstr=authentication.getText().toString();
                }
                problemdescstr=problemdesc.getText().toString();
                Bundle bundle = getIntent().getExtras();
                final String useremail = bundle.getString("email");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            String dbemail=dataSnapshot1.child("email").getValue().toString();
                            if(dbemail.equals(useremail)) {

                                String type = dataSnapshot1.child("Type").getValue().toString();
                                if(type.equals("User")){

                                    DatabaseReference dbref=firebaseDatabase.getReference().child("Users").child(dataSnapshot1.getKey()).child("ISSUES");
                                    DatabaseReference app=dbref.child(problemdescstr);
                                    app.child("problem1").setValue(virustsr);
                                    app.child("problem2").setValue(crashstr);
                                    app.child("problem3").setValue(authenticationstr);
                                    app.child("problem4").setValue(hangingstr);
                                    Toast.makeText(getApplicationContext(), "Problem submitted.", Toast.LENGTH_SHORT).show();
                                    return;


                                }
                            }
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

}
