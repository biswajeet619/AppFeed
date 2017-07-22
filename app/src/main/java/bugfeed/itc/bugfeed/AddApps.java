package bugfeed.itc.bugfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddApps extends AppCompatActivity {

    private EditText appname,appdesc,applink;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apps);
        inisitialseviews();




    }

    public void addapplication(View view) {

        final String appnaam=appname.getText().toString();
        final String appdescription=appdesc.getText().toString();
        final String applicationlink=applink.getText().toString();
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    String dbemail=dataSnapshot1.child("email").getValue().toString();
                    if(dbemail.equals(email)){


                        addtodb(appnaam,appdescription,applicationlink,dataSnapshot1.getKey());
                        return;

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
    public void inisitialseviews(){

        appdesc=(EditText)findViewById(R.id.appdesc);
        appname=(EditText)findViewById(R.id.appname);
        applink=(EditText)findViewById(R.id.applink);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Users");

    }
    public void addtodb(String name,String desc,String link,String key){

        DatabaseReference dbref=firebaseDatabase.getReference().child("Users").child(key).child("APPS");
        DatabaseReference app=dbref.child(name);
        app.child("appname").setValue(name);
        app.child("appdesc").setValue(desc);
        app.child("applink").setValue(link);
        Toast.makeText(this, "App Added.", Toast.LENGTH_SHORT).show();
    }
}
