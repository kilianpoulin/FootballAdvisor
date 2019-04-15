package i.myapplication3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    Handler handler;
    TeamActivity listener;
    private GridLayout r1, r2, r3, r4, r5;
    static int completed = 0;
    public static ArrayList<MatchResult> lastResults;
    public static TeamDetails teamDetails;
    public static ArrayList<GridLayout> resultViews;
    String teamID;
    String teamName;
    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, true);

        context = getActivity();
        /*lastResults = new ArrayList<MatchResult>();
        TeamActivity.teamDetails = new TeamDetails("No description available in English");

        Bundle arguments = getArguments();
        teamID = arguments.getString("teamID");
        teamName = arguments.getString("teamName");

        TextView teamIDTmp = (TextView) rootView.findViewById(R.id.teamName);
        teamIDTmp.setText(teamName + "blabla");

        /*
        resultViews = new ArrayList<GridLayout>();
        r1 = rootView.findViewById(R.id.result1);
        r2 = rootView.findViewById(R.id.result2);
        r3 = rootView.findViewById(R.id.result3);
        r4 = rootView.findViewById(R.id.result4);
        r5 = rootView.findViewById(R.id.result5);

        List<GridLayout> viewsList = Arrays.asList(r1, r2, r3, r4, r5);
        resultViews.addAll(viewsList);

        handler = new Handler();

        get_team(rootView, teamName, teamID);
        */
        return rootView;
    }

    public static FirstFragment newInstance(String teamID, String teamName){
        FirstFragment ff = new FirstFragment();

        Bundle b=new Bundle();
        b.putString("teamID", teamID);
        b.putString("teamName", teamName);
        ff.setArguments(b);
        return(ff);
    }

    public void onStart(){
        super.onStart();
        //TextView tv= (TextView) context.findViewById(R.id.txt_view);
        //read argument data and show it in the TextVview
        //tv.setText(getArguments().getString("mess"));
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ProfileActivity){
            listener = (TeamActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement TeamActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void empty_team(){
        TeamActivity.teamDetails = new TeamDetails("No description available in English");
        TeamActivity.lastResults = new ArrayList<MatchResult>();
        TeamActivity.completed = 0;
    }


    protected void get_team(final View view, final String teamName, final String teamID){
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
                display_team(view);
            }
        });
        t2.start();
    }

    public void display_team(final View view){
        handler.post(new Runnable(){
            public void run() {
                TextView tmpDescription = view.findViewById(R.id.team_description) ;
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
    }*/

}
