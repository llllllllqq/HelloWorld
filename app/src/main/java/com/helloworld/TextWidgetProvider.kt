package com.helloworld

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.helloworld.MainActivity.Companion.KEY_SAVED_TEXT
import com.helloworld.MainActivity.Companion.prefs

class TextWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        updateWidgets(context, appWidgetIds)
    }

    companion object {

        /**
         * 刷新所有已添加的桌面小组件。
         * 可从 [MainActivity.saveText] 和 [onUpdate] 中调用。
         */
        fun updateWidgets(context: Context, appWidgetIds: IntArray? = null) {
            val ids = appWidgetIds
                ?: let {
                    val manager = AppWidgetManager.getInstance(context)
                    manager.getAppWidgetIds(
                        android.content.ComponentName(context, TextWidgetProvider::class.java)
                    )
                }

            val savedText = context.prefs.getString(KEY_SAVED_TEXT, "") ?: ""

            ids.forEach { id ->
                val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                    // 设置文本——空时显示提示语
                    setTextViewText(
                        R.id.widget_text,
                        savedText.ifEmpty { context.getString(R.string.widget_empty_hint) }
                    )

                    // 点击小组件打开主界面
                    val intent = Intent(context, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                PendingIntent.FLAG_IMMUTABLE
                            } else {
                                0
                            }
                    val pendingIntent = PendingIntent.getActivity(
                        context, 0, intent, flags
                    )
                    setOnClickPendingIntent(R.id.widget_container, pendingIntent)
                }
                AppWidgetManager.getInstance(context).updateAppWidget(id, views)
            }
        }
    }
}
