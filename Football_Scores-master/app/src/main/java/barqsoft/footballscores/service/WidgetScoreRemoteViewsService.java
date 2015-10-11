package barqsoft.footballscores.service;


import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.model.WidgetFootballScoreData;
import barqsoft.footballscores.widget.ScoresWidget;

public class WidgetScoreRemoteViewsService extends RemoteViewsService {

    public final String LOG_TAG = this.getClass().getName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LOG_TAG, "onGetViewFactory");
        return new WidgetScoreRemoteViewsFactory(getApplicationContext(), intent);
    }

}

class WidgetScoreRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public final String LOG_TAG = this.getClass().getName();

    private List<WidgetFootballScoreData> scoresData;

    private ContentResolver contentResolver;
    private Context context;
    private Intent intent;

    private int appWidgetId;

    public WidgetScoreRemoteViewsFactory(Context applicationContext, Intent intent) {
        this.context = applicationContext;
        this.intent = intent;

        contentResolver = context.getContentResolver();

        scoresData = new ArrayList<WidgetFootballScoreData>();

        Cursor fotobollScoreDataCursor = loadData(getCurrentTimestamp());
        scoresData = mapScoreData(fotobollScoreDataCursor);

        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        Log.d(LOG_TAG, "WidgetScoreRemoteViewsFactory appWidgetId:" + appWidgetId);
    }

    public static final String[] selectColumns = new String[]{
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL
    };

    public static String getCurrentTimestamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("hh");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    // load all matches from today in order of beeing played
    private Cursor loadData(String dateyyMMdd) {

        Cursor cursor = contentResolver.query(DatabaseContract.scores_table.buildScoreWithDate(),
                selectColumns,
                DatabaseContract.scores_table.DATE_COL + " = ?",
                new String[]{dateyyMMdd},
                DatabaseContract.scores_table.TIME_COL + " ASC"
        );

        return cursor;
    }


    private List<WidgetFootballScoreData> mapScoreData(Cursor cursor) {
        List<WidgetFootballScoreData> footballScoreData = new ArrayList<>();
        int numberOfPosts = 0;
        if(cursor != null && cursor.getCount() > 0) {
            numberOfPosts = cursor.getCount();
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                WidgetFootballScoreData data = WidgetFootballScoreData.mapCursor(cursor);
                //TODO: possible improvement. don't add matches that has already started played data.getMatchTime()
                footballScoreData.add(data);
                cursor.moveToNext();
            }
        }
        Log.d(LOG_TAG, "Number of posts found: " + cursor.getCount());
        return footballScoreData;
    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");

    }

    @Override
    public void onDataSetChanged() {
        Log.d(LOG_TAG, "onDataSetChanged");
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount with size: "  + scoresData.size());
        return scoresData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(LOG_TAG, "getViewAt with position: "  + position);
        final RemoteViews remoteViewRowItem = new RemoteViews(context.getPackageName(), R.layout.widget_scores_item);
        WidgetFootballScoreData scoreData = scoresData.get(position);

        String scoreResult = scoreData.getAwayTeamGoals() + " - " + scoreData.getHomeTeamGoals();

        remoteViewRowItem.setTextViewText(R.id.score_textview, scoreResult);
        remoteViewRowItem.setTextViewText(R.id.data_textview, scoreData.getMatchTime());
        remoteViewRowItem.setTextViewText(R.id.away_name, scoreData.getAwayTeamName());
        remoteViewRowItem.setTextViewText(R.id.home_name, scoreData.getHomeTeamName());

        return remoteViewRowItem;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}