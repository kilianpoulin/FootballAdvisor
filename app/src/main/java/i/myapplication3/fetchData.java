package i.myapplication3;

import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String singleParsed ="";
    Integer competitionID;

    public fetchData(Integer compID) {
        super();
        this.competitionID = compID;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=" + competitionID);
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
            JSONArray JA = jsondata.getJSONArray("events");
            for(int i = 0 ;i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                Fixtures.homeTeams.add(JO.get("strHomeTeam") + " ");
                Fixtures.awayTeams.add(JO.get("strAwayTeam") + " ");
                Fixtures.matchDate.add("Matchday " + JO.get("intRound") + "\n" + JO.get("strDate") + "\n" + JO.get("strTime"));
                Fixtures.homePredict.add("Prediction \n 52%");
                Fixtures.awayPredict.add("Prediction \n 48%");
                Fixtures.fixtures.add(singleParsed);
                Fixtures.homeTeamsID.add(JO.get("idHomeTeam") + "");
                Fixtures.awayTeamsID.add(JO.get("idAwayTeam") + "");
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