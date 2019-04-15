package i.myapplication3;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamActivity extends AppCompatActivity {

    Handler handler;
    static int completed = 0;
    public static ArrayList<MatchResult> lastResults;
    public static TeamDetails teamDetails;
    public static ArrayList<GridLayout> resultViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Fragment selectedFragment = new FirstFragment();
        Bundle fragmentParams = new Bundle();

        Bundle extras = getIntent().getExtras();
        String s = new String(extras.getString("TeamActivity"));
        String id = new String(extras.getString("TeamID"));

        lastResults = new ArrayList<MatchResult>();
        TeamActivity.teamDetails = new TeamDetails("No description available in English");

        TextView teamTitle = findViewById(R.id.team_title);
        teamTitle.setText(s);

        resultViews = new ArrayList<GridLayout>();
        GridLayout r1 = findViewById(R.id.result1);
        GridLayout r2 = findViewById(R.id.result2);
        GridLayout r3 = findViewById(R.id.result3);
        GridLayout r4 = findViewById(R.id.result4);
        GridLayout r5 = findViewById(R.id.result5);

        List<GridLayout> viewsList = Arrays.asList(r1, r2, r3, r4, r5);
        resultViews.addAll(viewsList);

        handler = new Handler();

        fragmentParams.putString("teamID", id);
        fragmentParams.putString("teamName", s);
        selectedFragment.setArguments(fragmentParams);
        get_team(s, id);
    }

    public void empty_team(){
        TeamActivity.teamDetails = new TeamDetails("No description available in English");
        TeamActivity.lastResults = new ArrayList<MatchResult>();
        TeamActivity.completed = 0;
    }


    protected void get_team(final String teamName, final String teamID){
        final Thread t1 = new Thread(new Runnable() {
            public void run() {
                fetchTeam process = new fetchTeam(teamName, teamID);
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
                display_team();
            }
        });
        t2.start();
    }

    public void display_team(){
        handler.post(new Runnable(){
            public void run() {
                TextView tmpDescription = findViewById(R.id.team_description) ;
                tmpDescription.setText(TeamActivity.teamDetails.getDescription());
               for(int i = 0; i < lastResults.size(); i++){
                    TextView tmpHomeTeam = (TextView) resultViews.get(i).getChildAt(0);
                    tmpHomeTeam.setText(lastResults.get(i).getHomeTeam());

                    TextView tmpScore = (TextView) resultViews.get(i).getChildAt(1);
                    tmpScore.setText(lastResults.get(i).getHomeScore()
                            + " - "
                            + lastResults.get(i).getAwayScore()
                            + "\n" + lastResults.get(i).getDate()
                    + "\n" + lastResults.get(i).getResult());

                    TextView tmpAwayTeam = (TextView) resultViews.get(i).getChildAt(2);
                    tmpAwayTeam.setText(lastResults.get(i).getAwayTeam());
                }

                empty_team();
            }
        });
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
                Intent broadcast = new Intent(TeamActivity.this, ProfileActivity.class);
                startActivity(broadcast);
                break;

            default:
                Message.message(getApplicationContext(), "An error occurred");
        }
        return super.onOptionsItemSelected(item);
    }

}
