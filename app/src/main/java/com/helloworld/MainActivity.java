package com.helloworld;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editor;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "hello_prefs";
    public static final String KEY_SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = findViewById(R.id.editor);

        // 恢复上次保存的内容
        String savedText = prefs.getString(KEY_SAVED_TEXT, "");
        editor.setText(savedText);
        // 光标移到末尾
        if (!savedText.isEmpty()) {
            editor.setSelection(savedText.length());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }

    private void saveText() {
        String text = editor.getText().toString();
        prefs.edit().putString(KEY_SAVED_TEXT, text).apply();

        // 通知小组件更新
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, TextWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        if (appWidgetIds.length > 0) {
            TextWidgetProvider.updateAllWidgets(this, appWidgetManager, appWidgetIds);
        }
    }
}
