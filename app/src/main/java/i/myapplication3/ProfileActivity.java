package i.myapplication3;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class ProfileActivity extends FragmentActivity {
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);*/
    /*
        dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"Competitions", "Ligue 1", "Premier League", "La Liga", "Bundesliga", "Serie A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);*/

        showFragment();

    }

    public void showFragment(){

        //create FragmentTransaction instance
        FragmentTransaction transact=getSupportFragmentManager().beginTransaction();
        //create an instance of the TestFragment
        FirstFragment ff= FirstFragment.newInstance("133604", "Arsenal");
        //add fragment to transaction
        transact.add(R.id.fragment,ff, "ff");
        //commit the transaction
        transact.commit();

    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String mSelected = parent.getItemAtPosition(pos).toString();
        /*if(mSelected == "Ligue 1"){
            empty_fixtures();
            get_fixtures(4334);
        } else if(mSelected == "Premier League"){
            empty_fixtures();
            get_fixtures(4328);
        } else if(mSelected == "La Liga"){
            empty_fixtures();
            get_fixtures(4335);
        } else if(mSelected == "Bundesliga"){
            empty_fixtures();
            get_fixtures(4331);
        } else if(mSelected == "Serie A") {
            empty_fixtures();
            get_fixtures(4332);
        }*//*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment selectedFragment = new FirstFragment();
        Bundle fragmentParams = new Bundle();

        fragmentParams.putString("teamID", "133604");
        fragmentParams.putString("teamName", "Arsenal");
        selectedFragment.setArguments(fragmentParams);

        fragmentTransaction.add(R.id.fragment, selectedFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
