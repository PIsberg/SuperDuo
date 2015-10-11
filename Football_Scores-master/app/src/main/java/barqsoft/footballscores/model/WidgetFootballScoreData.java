package barqsoft.footballscores.model;


import android.database.Cursor;

public class WidgetFootballScoreData {
    private String matchId;
    private String matchTime;
    private String matchDay;
    private String homeTeamId;
    private String homeTeamName;
    private String homeTeamCrestUrl;
    private int homeTeamGoals;
    private String awayTeamId;
    private String awayTeamName;
    private String awayTeamCrestUrl;
    private int awayTeamGoals;

    private long leagueId;

    public long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }


    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(String matchDay) {
        this.matchDay = matchDay;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamCrestUrl() {
        return homeTeamCrestUrl;
    }

    public void setHomeTeamCrestUrl(String homeTeamCrestUrl) {
        this.homeTeamCrestUrl = homeTeamCrestUrl;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(int homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamCrestUrl() {
        return awayTeamCrestUrl;
    }

    public void setAwayTeamCrestUrl(String awayTeamCrestUrl) {
        this.awayTeamCrestUrl = awayTeamCrestUrl;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(int awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public static WidgetFootballScoreData mapCursor(Cursor cursor) {

        WidgetFootballScoreData data = new WidgetFootballScoreData();
        data.setMatchId(cursor.getString(0));

        data.setMatchId(cursor.getString(0));
        data.setLeagueId(cursor.getInt(1));
        data.setHomeTeamId(cursor.getString(2));
        data.setHomeTeamName(cursor.getString(3));
        data.setAwayTeamId(cursor.getString(4));
        data.setAwayTeamName(cursor.getString(5));
        data.setMatchDay(cursor.getString(6));
        data.setMatchTime(cursor.getString(7));

        return data;
    }
}
