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
    Integer teamID;
    String teamName;

    public fetchTeam(String TteamName, Integer TteamID) {
        super();
        this.teamID = TteamID;
        this.teamName = TteamName;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // team basic info
        try {
            URL url = new URL("https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + teamName);
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
                if(JO.get("idTeam") == teamID){
                    Team.teamDetails = new TeamDetails(JO.get("strDescriptionEN").toString());
                    break;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
            for(int i = 0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                if(JO.get("idTeam") == teamID){
                    Integer homeScore = (Integer) JO.get("intHomeScore");
                    Integer awayScore = (Integer) JO.get("intAwayScore");
                    String result = "";
                    if(JO.get("strHomeTeam") == teamName){
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

                    Team.lastResults.add(new MatchResult(JO.get("strHomeTeam").toString(), JO.get("strAwayTeam").toString(), result, homeScore, awayScore, JO.get("dateEvent").toString()));
                    break;
                }
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
        Fixtures.completed = 1;
    }
}