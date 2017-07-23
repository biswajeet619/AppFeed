package bugfeed.itc.bugfeed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView displayemail;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    public String email;
    public ArrayList<DeveloperApps> developerAppses = new ArrayList<DeveloperApps>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        displayemail = (TextView) header.findViewById(R.id.displayemail);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        displayemail.setText("Welcome \n" + email);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.keepSynced(true);
        bundle = getIntent().getExtras();
        email = bundle.getString("email");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String dbemail = dataSnapshot1.child("email").getValue().toString();
                    if (dbemail.equals(email)) {

                        String type = dataSnapshot1.child("Type").getValue().toString();
                        if (("User").equals(type)) {

                        } else if (("Developer").equals(type)) {

                            for (DataSnapshot snapshot : dataSnapshot1.getChildren()) {


                                for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                                    DeveloperApps developerApps = new DeveloperApps();
                                    developerApps = dataSnapshot2.getValue(DeveloperApps.class);
                                    developerAppses.add(developerApps);
                                }
                                ArrayAdapter<DeveloperApps> adapter = new ArrayAdapter<DeveloperApps>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1, developerAppses) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        /// Get the Item from ListView
                                        View view = super.getView(position, convertView, parent);

                                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                        // Set the text size 25 dip for ListView each item
                                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                                        tv.setTextColor(Color.BLACK);

                                        // Return the view
                                        return view;
                                    }
                                };

                                listView.setAdapter(adapter);
                                return;
                            }

                        }
                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.reviewstatus) {


            // Handle the camera action
        } else if (id == R.id.listofDevs) {

        } else if (id == R.id.userlogout) {

            auth = FirebaseAuth.getInstance();
            auth.signOut();
            Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserProfileActivity.this, LoginScreen.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}








