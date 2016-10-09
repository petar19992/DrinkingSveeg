package company.drink.com.drinkingsveeg;

import android.provider.SyncStateContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import company.drink.com.drinkingsveeg.helpers.Constants;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout activityRegister;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText retypePassword;
    private Button button;

    private void findViews() {
        activityRegister = (ConstraintLayout)findViewById( R.id.activity_register );
        name = (EditText)findViewById( R.id.editText );
        email = (EditText)findViewById( R.id.editText2 );
        password = (EditText)findViewById( R.id.editText3 );
        retypePassword = (EditText)findViewById( R.id.editText4 );
        button = (Button)findViewById( R.id.button );

        button.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if ( v == button ) {
            List<Pair<String,String>> params=new ArrayList<>();
            params.add(new Pair<String, String>("name",name.getText().toString()));
            params.add(new Pair<String, String>("email",email.getText().toString()));
            params.add(new Pair<String, String>("password",password.getText().toString()));
            params.add(new Pair<String, String>("password_confirmation",retypePassword.getText().toString()));
            Constants.getInstance().POST("http://drinking.sveeg.com/api/register", params, new Constants.IGetInterface() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish(String result) {
                    Log.i("result",result);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
    }
}
