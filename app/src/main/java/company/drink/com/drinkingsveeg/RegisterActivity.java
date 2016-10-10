package company.drink.com.drinkingsveeg;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import company.drink.com.drinkingsveeg.helpers.Constants;
import company.drink.com.drinkingsveeg.helpers.SharedPreferenceManager;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ConstraintLayout activityRegister;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText retypePassword;
    private Button button;
    private ProgressBar progressBar;

    private void findViews() {
        activityRegister = (ConstraintLayout)findViewById( R.id.activity_register );
        name = (EditText)findViewById( R.id.editText );
        email = (EditText)findViewById( R.id.editText2 );
        password = (EditText)findViewById( R.id.editText3 );
        retypePassword = (EditText)findViewById( R.id.editText4 );
        button = (Button)findViewById( R.id.button );
        progressBar = (ProgressBar) findViewById( R.id.progressBar );

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
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish(String result) {
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        String token= (String) jsonObject.get("token");
                        SharedPreferenceManager.getInstance(getApplicationContext()).setString(SharedPreferenceManager.TOKEN,token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
    //null{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUsImlzcyI6Imh0dHA6XC9cL2RyaW5raW5nLnN2ZWVnLmNvbVwvYXBpXC9yZWdpc3RlciIsImlhdCI6MTQ3NjEzOTY4NCwiZXhwIjoxNDc2MTQzMjg0LCJuYmYiOjE0NzYxMzk2ODQsImp0aSI6IjZhNzIwYzM4NzFmZmEwNjc3MzEyMWZjZDA3NWY5ZDQyIn0.7xj-441xvMf4XkEvypAi-Ms1dGe2Gk5dXaedljibLOU"}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
    }
}
