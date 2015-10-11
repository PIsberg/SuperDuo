package barqsoft.footballscores.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;
import barqsoft.footballscores.service.WidgetScoreRemoteViewsService;

public class ScoresWidget extends AppWidgetProvider {
    public final String LOG_TAG = this.getClass().getName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(LOG_TAG, "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            Log.d(LOG_TAG, "appWidgetId " + appWidgetId);

            Intent intent = new Intent(context, WidgetScoreRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_scores_layout);

            remoteView.setRemoteAdapter(R.id.scores_list, intent);
            remoteView.setEmptyView(R.id.scores_list, R.id.no_scores);

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    // called when data has changed from myfetchService
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG, "onReceive");

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            Log.v(LOG_TAG, "ACTION_APPWIDGET_UPDATE");

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, getClass());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.scores_list);
        }
        else {
            super.onReceive(context, intent);}
        }
}
