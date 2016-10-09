package company.drink.com.drinkingsveeg;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout activityMain;
    private EditText email;
    private EditText password;
    private Button singIn;
    private TextView register;

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

        singIn.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-10-10 00:19:47 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == singIn ) {
        }
    }

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
