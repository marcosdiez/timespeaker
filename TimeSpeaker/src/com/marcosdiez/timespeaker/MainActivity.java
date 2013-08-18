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

	@Override
	protected void onNewIntent(Intent theIntent) {
		Log.d(TAG, "onNewIntent");
		super.onNewIntent(theIntent);
		sayTime();
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("TTS", "onCreate");
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
		Log.d(TAG, "onDestroy");
		// Don't forget to shutdown tts!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		Log.d(TAG, "onInit");
		if (status == TextToSpeech.SUCCESS) {
			sayTime();
		} else {
			Log.e(TAG, "Initilization Failed!");
		}
	}

	private void speakOut(String text) {
		tts.setLanguage(Locale.US);
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	public static class LockScreenReceiver extends DeviceAdminReceiver {
		void showToast(Context context, String msg) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			Log.d(TAG, msg);
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
