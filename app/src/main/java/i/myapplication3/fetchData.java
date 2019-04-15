package i.myapplication3;

import android.os.AsyncTask;
import android.text.TextUtils;
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
import java.text.DecimalFormat;


public class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String singleParsed ="";
    Integer competitionID;
    Integer ini;

    public fetchData(Integer compID, Integer ini) {
        super();
        this.competitionID = compID;
        this.ini = ini;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String teamHomeID = "";
        String teamAwayID = "";
        Double totalHome = 0.0;
        Double totalAway = 0.0;
        URL url2, url3;
        HttpURLConnection httpURLConnection2, httpURLConnection3;
        InputStream inputStream2, inputStream3;
        BufferedReader bufferedReader2, bufferedReader3;
        String line2, line3;
        JSONObject jsondata2, jsondata3;
        JSONArray JA2, JA3;
        String url_string2 = "";



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
            for(int i = 0 ;i <JA.length(); i++) {

                JSONObject JO = (JSONObject) JA.get(i);
                FixturesActivity.homeTeams.add(JO.get("strHomeTeam") + " ");
                FixturesActivity.awayTeams.add(JO.get("strAwayTeam") + " ");
                FixturesActivity.matchDate.add("Matchday " + JO.get("intRound") + "\n" + JO.get("strDate") + "\n" + JO.get("strTime"));
                //FixturesActivity.homePredict.add("Prediction \n 52%");
                //FixturesActivity.awayPredict.add("Prediction \n 48%");
                FixturesActivity.fixtures.add(singleParsed);

                teamHomeID = JO.get("idHomeTeam").toString();
                teamAwayID = JO.get("idAwayTeam").toString();

                if (!TextUtils.isEmpty(teamHomeID)) {
                    teamHomeID = "133604";
                }
                if (TextUtils.isEmpty(teamAwayID)) {
                    teamAwayID = "133604";
                }


                FixturesActivity.homeTeamsID.add(teamHomeID);
                FixturesActivity.awayTeamsID.add(teamAwayID);

                if (ini == 0) {

                    // HOME TEAM

                    data = "";
                    // team last 5 results
                    try {
                        url2 = new URL("https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=" + teamHomeID);
                        httpURLConnection2 = (HttpURLConnection) url2.openConnection();

                        inputStream2 = httpURLConnection2.getInputStream();

                        bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                        line2 = "";

                        while (line2 != null) {
                            line2 = bufferedReader2.readLine();
                            data = data + line2;
                        }


                        //events
                        jsondata2 = new JSONObject(data);
                        JA2 = jsondata2.getJSONArray("results");
                        //JA.length()
                        JSONObject JO2;
                        Integer homeScore = 0;
                        Integer awayScore = 0;
                        String result = "";
                        String tmpScore = "";
                        for (int j = 0; j < JA2.length(); j++) {
                            JO2 = (JSONObject) JA2.get(j);
                            tmpScore = JO2.get("intHomeScore").toString();
                            if (!TextUtils.isEmpty(tmpScore) && TextUtils.isDigitsOnly(tmpScore)) {
                                homeScore = Integer.parseInt(tmpScore);
                            } else {
                                homeScore = 0;
                            }

                            tmpScore = JO2.get("intAwayScore").toString();
                            if (!TextUtils.isEmpty(tmpScore) && TextUtils.isDigitsOnly(tmpScore)) {
                                awayScore = Integer.parseInt(tmpScore);
                            } else {
                                awayScore = 0;
                            }
                            result = "";

                            String tmpHomeID = JO2.get("idHomeTeam").toString();
                            if (teamHomeID.equals(tmpHomeID)) {
                                totalAway = totalAway + 5 * homeScore - 3 * awayScore;
                                if (homeScore > awayScore) {
                                    result = "Win H";
                                } else if (homeScore < awayScore) {
                                    result = "Lost";
                                }
                            } else {
                                totalAway = totalAway + 5 * awayScore - 3 * homeScore;
                                if (awayScore > homeScore) {
                                    result = "Win A";
                                } else if (awayScore < homeScore) {
                                    result = "Lost";
                                }
                            }
                            if (awayScore == homeScore) {
                                result = "Draw";
                            }

                            if (result == "Win A") {
                                totalHome = totalHome + 40;
                            } else if (result == "Win H") {
                                totalHome = totalHome + 30;
                            } else if (result == "Draw") {
                                totalHome = totalHome + 10;
                            }
                        }
                        //inputStream2.close();
                        httpURLConnection2.disconnect();


                    } catch (MalformedURLException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Away TEAM

                    data = "";
                    // team last 5 results
                    try {
                        url3 = new URL("https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=" + teamAwayID);
                        httpURLConnection3 = (HttpURLConnection) url3.openConnection();
                        inputStream3 = httpURLConnection3.getInputStream();
                        bufferedReader3 = new BufferedReader(new InputStreamReader(inputStream3));
                        line3 = "";
                        while (line3 != null) {
                            line3 = bufferedReader3.readLine();
                            data = data + line3;
                        }


                        //events
                        jsondata3 = new JSONObject(data);
                        JA3 = jsondata3.getJSONArray("results");
                        //JA.length()
                        String result = "";
                        String tmpScore = "";
                        Integer homeScore = 0;
                        Integer awayScore = 0;
                        JSONObject JO3;
                        for (int k = 0; i < JA3.length(); k++) {
                            //TeamActivity.lastResults.add(new MatchResult(JA.length() + "", "away", "win", 4, 0, "14-04-2019"));
                            JO3 = (JSONObject) JA3.get(k);

                            tmpScore = JO3.get("intHomeScore").toString();
                            if (!TextUtils.isEmpty(tmpScore) && TextUtils.isDigitsOnly(tmpScore)) {
                                homeScore = Integer.parseInt(tmpScore);
                            } else {
                                homeScore = 0;
                            }

                            tmpScore = JO3.get("intAwayScore").toString();
                            if (!TextUtils.isEmpty(tmpScore) && TextUtils.isDigitsOnly(tmpScore)) {
                                awayScore = Integer.parseInt(tmpScore);
                            } else {
                                awayScore = 0;
                            }
                            result = "";

                            String tmpHomeID = JO3.get("idHomeTeam").toString();
                            if (teamHomeID.equals(tmpHomeID)) {
                                totalAway = totalAway + 5 * homeScore - 3 * awayScore;
                                if (homeScore > awayScore) {
                                    result = "Win H";
                                } else if (homeScore < awayScore) {
                                    result = "Lost";
                                }
                            } else {
                                totalAway = totalAway + 5 * awayScore - 3 * homeScore;
                                if (awayScore > homeScore) {
                                    result = "Win A";
                                } else if (awayScore < homeScore) {
                                    result = "Lost";
                                }
                            }
                            if (awayScore == homeScore) {
                                result = "Draw";
                            }

                            if (result == "Win A") {
                                totalAway = totalAway + 40;
                            } else if (result == "Win H") {
                                totalAway = totalAway + 30;
                            } else if (result == "Draw") {
                                totalAway = totalAway + 10;
                            }
                        }

                        inputStream3.close();
                        httpURLConnection3.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                    Double result = 0.0;
                    if (totalAway != 0.0) {
                        result = totalHome / totalAway;
                    } else {
                        result = totalHome;
                    }

                    Double y = 100.0 / (result + 1.0);

                    Double homePerc = result * y;
                    Double awayPerc = 1.0 * y;

                    DecimalFormat oneDigit = new DecimalFormat("#,##0.0");
                    FixturesActivity.homePredict.add("Prediction \n " + oneDigit.format(homePerc) + " %");
                    FixturesActivity.awayPredict.add("Prediction \n " + oneDigit.format(awayPerc) + " %");

                    totalAway = 0.0;
                    totalHome = 0.0;


            }
            inputStream.close();
            httpURLConnection.disconnect();

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
        FixturesActivity.completed = 1;
    }
}