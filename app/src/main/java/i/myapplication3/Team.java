package i.myapplication3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Team extends AppCompatActivity {

    Handler handler;
    static int completed = 0;
    public static ArrayList<MatchResult> lastResults;
    public static TeamDetails teamDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String s = new String(extras.getString("Team"));
        String id = new String(extras.getString("TeamID"));

        lastResults = new ArrayList<MatchResult>();
        teamDetails = new TeamDetails("no description");

        TextView teamTitle = findViewById(R.id.team_title);
        teamTitle.setText(s);

        handler = new Handler();

        get_team(s, Integer.valueOf(id));
    }

    protected void get_team(final String teamName, final Integer teamID){
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
                TextView tmpDescription = (TextView) findViewById(R.id.team_description);
                tmpDescription.setText(Team.teamDetails.getDescription());
                //for(int i = 0; i < Fixtures.fixtures.size(); i++){
                    /*TextView tmpHomeTeam = (TextView) fixture_views.get(i).getChildAt(0);
                    tmpHomeTeam.setText(Fixtures.homeTeams.get(i));
                    tmpHomeTeam.setTag(Integer.valueOf(Fixtures.homeTeamsID.get(i)));

                    TextView tmpScore = (TextView) fixture_views.get(i).getChildAt(1);
                    tmpScore.setText("0 - 0");

                    TextView tmpAwayTeam = (TextView) fixture_views.get(i).getChildAt(2);
                    tmpAwayTeam.setText(Fixtures.awayTeams.get(i));
                    tmpHomeTeam.setTag(Integer.valueOf(Fixtures.awayTeamsID.get(i)));

                    TextView tmpDate = (TextView) fixture_views.get(i).getChildAt(4);
                    tmpDate.setText(Fixtures.matchDate.get(i));

                    TextView tmpHomePredict = (TextView) fixture_views.get(i).getChildAt(6);
                    tmpHomePredict.setText(Fixtures.homePredict.get(i));

                    TextView tmpAwayPredict = (TextView) fixture_views.get(i).getChildAt(8);
                    tmpAwayPredict.setText(Fixtures.awayPredict.get(i));
                    */
                //}
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
                Intent broadcast = new Intent(Team.this, Profile.class);
                startActivity(broadcast);
                break;

            default:
                Message.message(getApplicationContext(), "An error occurred");
        }
        return super.onOptionsItemSelected(item);
    }


}
