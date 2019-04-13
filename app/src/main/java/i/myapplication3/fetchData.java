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
                singleParsed = JO.get("strEvent") + "\n" +  "Matchday " + JO.get("intRound") + "\n\n" + JO.get("strDate") + " at " + JO.get("strTime") + "\n";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    singleParsed = singleParsed + Html.fromHtml("<table width='100%'><tr><td width='50%'>Home</td><td width='50%'>Away</td></tr></table>", Html.FROM_HTML_MODE_COMPACT);
                } else {
                    singleParsed = singleParsed + Html.fromHtml("<table width='100%'><tr><td width='50%'>Home</td><td width='50%'>Away</td></tr></table>");
                }

                Fixtures.fixtures.add(singleParsed);
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