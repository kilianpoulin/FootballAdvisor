package i.myapplication3;

public class MatchResult {
    private String homeTeam;
    private String awayTeam;
    private String result;
    private Integer homeScore;
    private Integer awayScore;
    private String date;

    public MatchResult(String homeTeam, String awayTeam, String result, Integer homeScore, Integer awayScore, String date){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.result = result;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
