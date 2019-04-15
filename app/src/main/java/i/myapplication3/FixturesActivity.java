package i.myapplication3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixturesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static LinearLayout linear_layout;
    public static ArrayList<String> fixtures;
    static int completed = 0;
    public static ArrayList<GridLayout> fixture_views;
    public static ArrayList<String> homeTeams;
    public static ArrayList<String> awayTeams;
    public static ArrayList<String> matchDate;
    public static ArrayList<String> homePredict;
    public static ArrayList<String> awayPredict;
    public static ArrayList<String> homeTeamsID;
    public static ArrayList<String> awayTeamsID;
    Spinner dropdown;

    TextView t;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        fixtures = new ArrayList<String>();
        homeTeams = new ArrayList<String>();
        awayTeams = new ArrayList<String>();
        matchDate = new ArrayList<String>();
        homePredict = new ArrayList<String>();
        awayPredict = new ArrayList<String>();
        awayTeamsID = new ArrayList<String>();
        homeTeamsID = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        String s = new String(extras.getString("Name"));
        TextView login = findViewById(R.id.login_title);
        login.setText("Hello " + s + "!");

        dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"Competitions", "Ligue 1", "Premier League", "La Liga", "Bundesliga", "Serie A"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);


        fixture_views = new ArrayList<GridLayout>();
        GridLayout f1 = findViewById(R.id.fixture1);
        GridLayout f2 = findViewById(R.id.fixture2);
        GridLayout f3 = findViewById(R.id.fixture3);
        GridLayout f4 = findViewById(R.id.fixture4);
        GridLayout f5 = findViewById(R.id.fixture5);
        GridLayout f6 = findViewById(R.id.fixture6);
        GridLayout f7 = findViewById(R.id.fixture7);
        GridLayout f8 = findViewById(R.id.fixture8);
        GridLayout f9 = findViewById(R.id.fixture9);
        GridLayout f10 = findViewById(R.id.fixture10);
        GridLayout f11 = findViewById(R.id.fixture11);
        GridLayout f12 = findViewById(R.id.fixture12);
        GridLayout f13 = findViewById(R.id.fixture13);
        GridLayout f14 = findViewById(R.id.fixture14);
        GridLayout f15 = findViewById(R.id.fixture15);

        List<GridLayout> viewsList = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15);
        fixture_views.addAll(viewsList);

        handler = new Handler();

        linear_layout = findViewById(R.id.linear_layout);

        // loading Ligue 1
        get_fixtures(4328, 0);

    }

    protected void get_fixtures(final Integer competitionID, final Integer ini){

        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                fetchData process = new fetchData(competitionID, ini);
                process.execute();
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                while(completed == 0){
                    try {
                        Thread.sleep(200);
                    } catch(InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
                try {
                    t1.interrupt();
                    Thread.sleep(400);
                } catch(InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
                display_fixtures();
            }
        });
        t2.start();
    }

    public void teamInfo(View view){
        TextView text = (TextView) view;
        Integer teamID = (Integer) text.getTag();

        Intent broadcast = new Intent(FixturesActivity.this, TeamActivity.class);
        broadcast.putExtra("TeamActivity", text.getText().toString() + "");
        broadcast.putExtra("TeamID", teamID.toString() + "");
        startActivity(broadcast);
    }

    public void display_fixtures(){
        handler.post(new Runnable(){
            public void run() {
                for(int i = 0; i < FixturesActivity.fixtures.size(); i++){
                    TextView tmpHomeTeam = (TextView) fixture_views.get(i).getChildAt(0);
                    tmpHomeTeam.setText(FixturesActivity.homeTeams.get(i));
                    tmpHomeTeam.setTag(Integer.valueOf(FixturesActivity.homeTeamsID.get(i)));

                    TextView tmpScore = (TextView) fixture_views.get(i).getChildAt(1);
                    tmpScore.setText("0 - 0");

                    TextView tmpAwayTeam = (TextView) fixture_views.get(i).getChildAt(2);
                    tmpAwayTeam.setText(FixturesActivity.awayTeams.get(i));
                    tmpAwayTeam.setTag(Integer.valueOf(FixturesActivity.awayTeamsID.get(i)));

                    TextView tmpDate = (TextView) fixture_views.get(i).getChildAt(4);
                    tmpDate.setText(FixturesActivity.matchDate.get(i));

                    TextView tmpHomePredict = (TextView) fixture_views.get(i).getChildAt(6);
                    tmpHomePredict.setText(FixturesActivity.homePredict.get(i));

                    TextView tmpAwayPredict = (TextView) fixture_views.get(i).getChildAt(8);
                    tmpAwayPredict.setText(FixturesActivity.awayPredict.get(i));

                }
            }
        });
    }

    private void empty_fixtures(){
        FixturesActivity.fixtures = new ArrayList<String>();
        FixturesActivity.homeTeams = new ArrayList<String>();
        FixturesActivity.awayTeams = new ArrayList<String>();
        FixturesActivity.matchDate = new ArrayList<String>();
        FixturesActivity.homePredict = new ArrayList<String>();
        FixturesActivity.awayPredict = new ArrayList<String>();
        FixturesActivity.homeTeamsID = new ArrayList<String>();
        FixturesActivity.awayTeamsID = new ArrayList<String>();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String mSelected = parent.getItemAtPosition(pos).toString();
        if(mSelected == "Ligue 1"){
            empty_fixtures();
            get_fixtures(4334, 1);
        } else if(mSelected == "Premier League"){
            empty_fixtures();
            get_fixtures(4328, 1);
        } else if(mSelected == "La Liga"){
            empty_fixtures();
            get_fixtures(4335, 1);
        } else if(mSelected == "Bundesliga"){
            empty_fixtures();
            get_fixtures(4331, 1);
        } else if(mSelected == "Serie A") {
            empty_fixtures();
            get_fixtures(4332, 1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    }
}
