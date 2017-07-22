package bugfeed.itc.bugfeed;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends Activity {
    private EditText EmailAdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        EmailAdEditText =(EditText)findViewById(R.id.EmailAd);
    }

    public void resend(View view) {
        String EnterEmail=EmailAdEditText.getText().toString().trim();
        if(!EnterEmail.equals("")){
            FirebaseAuth.getInstance().sendPasswordResetEmail(EnterEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(ForgotPasswordActivity.this, "Reset Link Sent to Registered Mail", Toast.LENGTH_SHORT).show();
                    ForgotPasswordActivity.this.finish();
                }
            });
        }

    }
}
