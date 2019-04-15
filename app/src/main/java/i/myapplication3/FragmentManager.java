package i.myapplication3;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class FragmentManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);*/

        showFragment("This message is sent from MainActivity.");
    }

    public void showFragment(String mess){


        FragmentTransaction transact=getSupportFragmentManager().beginTransaction();
        TestFragment ff=TestFragment.newInstance(mess);
        transact.add(R.id.fragment_container,ff, "ff");
        transact.commit();

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile_btn:
                Intent broadcast = new Intent(FixturesActivity.this, ProfileActivity.class);
                startActivity(broadcast);
                break;
            case R.id.fixtures_btn:
                Intent broadcast2 = new Intent(FixturesActivity.this, FixturesActivity.class);
                startActivity(broadcast2);
                break;
            default:
                Message.message(getApplicationContext(), "An error occurred");
        }
        return super.onOptionsItemSelected(item);
    }*/
}
