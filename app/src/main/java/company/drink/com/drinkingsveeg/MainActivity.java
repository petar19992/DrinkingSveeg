package company.drink.com.drinkingsveeg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import company.drink.com.drinkingsveeg.helpers.Constants;
import company.drink.com.drinkingsveeg.helpers.SharedPreferenceManager;

public class MainActivity extends Activity implements View.OnClickListener {

    private ConstraintLayout activityMain;
    private EditText email;
    private EditText password;
    private Button singIn;
    private TextView register;
    private ProgressBar progressBar;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-10-10 00:19:47 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        activityMain = (ConstraintLayout)findViewById( R.id.activity_main );
        email = (EditText)findViewById( R.id.email );
        password = (EditText)findViewById( R.id.password );
        singIn = (Button)findViewById( R.id.singIn );
        register = (TextView)findViewById( R.id.textView );
        progressBar = (ProgressBar) findViewById( R.id.progressBar );

        singIn.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        if ( v == singIn )
        {
            List<Pair<String,String>> params=new ArrayList<>();
            params.add(new Pair<String, String>("email",email.getText().toString()));
            params.add(new Pair<String, String>("password",password.getText().toString()));
            Constants.getInstance().POST("http://drinking.sveeg.com/api/login", params, new Constants.IGetInterface() {
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
                    Intent i=new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(i);
                }
            });
        }
    }
    //Value null{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMsImlzcyI6Imh0dHA6XC9cL2RyaW5raW5nLnN2ZWVnLmNvbVwvYXBpXC9sb2dpbiIsImlhdCI6MTQ3NjEzOTk2NSwiZXhwIjoxNDc2MTQzNTY1LCJuYmYiOjE0NzYxMzk5NjUsImp0aSI6ImUxZTgyOGNlZTc3ZjkzNTcxYTFiMzM4NGI5NmJiMjc1In0.bzukFmVJJ16Dlh2J-KU1w5CURn26gz7FtnfajFdg1Xg"}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}
