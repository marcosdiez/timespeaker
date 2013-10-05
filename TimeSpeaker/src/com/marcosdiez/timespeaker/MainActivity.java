package com.marcosdiez.timespeaker;

import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

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
	}

	private void sayTime() {
		Calendar c = Calendar.getInstance();
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);

		String output = "The time is " + hours + " " + minutes;
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
            debug("Initilization Failed!");
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
//			showToast(context,
//					context.getString(R.string.admin_receiver_status_pw_failed));
			Intent localIntent = new Intent(context, MainActivity.class);

			localIntent.addFlags(PendingIntent.FLAG_CANCEL_CURRENT);
			context.startActivity(localIntent);
		}

	}

}
