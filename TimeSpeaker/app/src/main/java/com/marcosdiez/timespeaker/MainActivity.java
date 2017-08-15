package com.marcosdiez.timespeaker;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements
        TextToSpeech.OnInitListener {

    private static String TAG = "TTS";
    private TextToSpeech tts;
    private static final boolean debug_mode = false;

    static void debug(String string){
        if(debug_mode){
            Log.d(TAG,string);
        }
    }

    @Override
    protected void onNewIntent(Intent theIntent) {
        debug("onNewIntent");
        super.onNewIntent(theIntent);
        sayTime();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        debug("onCreate");
        super.onCreate(savedInstanceState);
        tts = new TextToSpeech(this, this);
        sayTime();
        setContentView(R.layout.activity_main);
        populateReadme();
    }

    void populateReadme(){
        TextView main_text_view = (TextView)findViewById(R.id.main_text_view);
        main_text_view.setMovementMethod(new ScrollingMovementMethod());
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.readme);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            main_text_view.setText(new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            main_text_view.setText("Error: can't show help.");
        }
    }

    private void sayTime() {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        if(minutes >= 10) {
            String output = "The time is " + hours + " " + minutes;
            speakOut(output);
            return;
        }
        if(minutes > 0 ){
            String output = "The time is " + hours + " o " + minutes;
            speakOut(output);
            return;
        }
        String output = "The time is " + hours + " o'clock";
        speakOut(output);
    }

    @Override
    public void onDestroy() {
        debug("onDestroy");
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        debug("onInit");
        if (status == TextToSpeech.SUCCESS) {
            sayTime();
        } else {
            debug("Initialization Failed!");
        }
    }

    private void speakOut(String text) {
        tts.setLanguage(Locale.US);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public static class LockScreenReceiver extends DeviceAdminReceiver {
        void showToast(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            debug(msg);
        }

        @Override
        public void onPasswordFailed(Context context, Intent intent) {
			showToast(context, context.getString(R.string.time_toast));
            Intent localIntent = new Intent(context, MainActivity.class);
            localIntent.addFlags(PendingIntent.FLAG_CANCEL_CURRENT);
            context.startActivity(localIntent);
        }
    }
}


/*package com.marcosdiez.timespeaker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
*/
