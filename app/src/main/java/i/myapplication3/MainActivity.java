package i.myapplication3;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getSupportActionBar().setTitle("Football Advisor");
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);

        helper = new myDbAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile_btn:
                Intent broadcast = new Intent(MainActivity.this, Profile.class);
                startActivity(broadcast);
                break;

            default:
                Message.message(getApplicationContext(), "An error occurred");
        }
        return super.onOptionsItemSelected(item);
    }


    public void addUser(View view)
    {
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Message.message(getApplicationContext(),"Error : Username or password is empty");
        }
        else
        {
            Integer result = helper.checkUsernameExists(t1);
            if(result <= 0) {
                long id = helper.insertData(t1, t2);
                if (id <= 0) {
                    Message.message(getApplicationContext(), "Error : Registration failed");
                    Name.setText("");
                    Pass.setText("");
                } else {
                    Message.message(getApplicationContext(), "Registration successful");
                    Name.setText("");
                    Pass.setText("");
                }
            } else {
                Message.message(getApplicationContext(), "Error : Username already exists");
            }
        }
    }

    public void checkLogin(View view)
    {
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Message.message(getApplicationContext(),"Error : Username or password is empty");
        }
        else
        {
        Integer result = helper.checkUserExists(t1, t2);
        if(result <= 0)
        {
            Message.message(getApplicationContext(),"Error - Wrong username or password");
            Name.setText("");
            Pass.setText("");
        } else
        {
            Message.message(getApplicationContext(),"Login Successful");
            String login_value = Name.getText().toString();
            Name.setText("");
            Pass.setText("");
            Intent broadcast = new Intent(MainActivity.this, Fixtures.class);
            broadcast.putExtra("Name", login_value + "");

            startActivity(broadcast);
        }
    }
    }
}
