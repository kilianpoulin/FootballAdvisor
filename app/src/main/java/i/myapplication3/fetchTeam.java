package i.myapplication3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchTeam extends AsyncTask<Void,Void,Void> {
    String data ="";
    String singleParsed ="";
    String teamID;
    String teamName;

    public fetchTeam(String TteamName, String TteamID) {
        super();
        this.teamID = TteamID;
        this.teamName = TteamName;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // team basic info
        try {
            String url_string = "https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + teamName;
            URL url = new URL(url_string.replaceAll(" ", "%20"));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            //events
            JSONObject jsondata = new JSONObject(data);
            JSONArray JA = jsondata.getJSONArray("teams");
            for(int i = 0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                String tmpID = JO.get("idTeam").toString();
                if(teamID.equals(tmpID)) {
                    TeamActivity.teamDetails.setDescription(JO.get("strDescriptionEN").toString());
                    break;
                }
            }
        } catch (MalformedURLException e) {

            //TeamActivity.teamDetails = new TeamDetails("test1");
            e.printStackTrace();
        } catch (IOException e) {

            //TeamActivity.teamDetails = new TeamDetails("test2");
            e.printStackTrace();
        } catch (JSONException e) {

            //TeamActivity.teamDetails = new TeamDetails("test3");
            e.printStackTrace();
        }

        data = "";
        // team last 5 results
        try {
            URL url = new URL("https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=" + teamID);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            //events
            JSONObject jsondata = new JSONObject(data);
            JSONArray JA = jsondata.getJSONArray("results");
            //JA.length()
            for(int i = 0; i < JA.length(); i++){
                //TeamActivity.lastResults.add(new MatchResult(JA.length() + "", "away", "win", 4, 0, "14-04-2019"));
                JSONObject JO = (JSONObject) JA.get(i);

                Integer homeScore = Integer.parseInt(JO.get("intHomeScore").toString());
                Integer awayScore = Integer.parseInt(JO.get("intAwayScore").toString());
                String result = "";

                String tmpHomeID = JO.get("idHomeTeam").toString();
                if(teamID.equals(tmpHomeID)){
                    if(homeScore > awayScore){
                        result = "Win";
                    } else if(homeScore < awayScore){
                        result = "Lost";
                    }
                } else {
                    if(awayScore > homeScore){
                        result = "Win";
                    } else if(awayScore < homeScore){
                        result = "Lost";
                    }
                }
                if(awayScore == homeScore){
                    result = "Draw";
                }
                TeamActivity.lastResults.add(new MatchResult(JO.get("strHomeTeam").toString(), JO.get("strAwayTeam").toString(), result, homeScore, awayScore, JO.get("dateEvent").toString()));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TeamActivity.completed = 1;
    }
}