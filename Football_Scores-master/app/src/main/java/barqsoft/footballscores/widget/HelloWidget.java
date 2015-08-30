package barqsoft.footballscores.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import barqsoft.footballscores.R;

public class HelloWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CurrentTime(context, appWidgetManager), 1, 1000);
    }

    private class CurrentTime extends TimerTask {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName componentName;
        DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());

        public CurrentTime(Context context, AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            componentName = new ComponentName(context, HelloWidget.class);
        }

        @Override
        public void run() {
            remoteViews.setTextViewText(R.id.widget_textview, format.format(new Date()));
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }

    }
}
