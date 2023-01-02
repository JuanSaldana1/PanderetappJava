package com.juansaldana.panderetappjava;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	TextView textView;
	MediaPlayer mediaPlayer;
	SensorManager sensorManager;
	Sensor sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		textView = findViewById(R.id.textView);
		if (mediaPlayer == null)
			mediaPlayer = MediaPlayer.create(this, R.raw.pandereta_sound_1);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	public void onResume() {
		super.onResume();
		sensorManager.registerListener(gyroscopeListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onStop() {
		super.onStop();
		sensorManager.unregisterListener(gyroscopeListener);
	}

	public SensorEventListener gyroscopeListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			textView.setText(String.format("X: %s, Y: %s, Z: %s", event.values[0], event.values[1], event.values[2]));
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			Log.i("MYTAG", "Has girado el movil");
			mediaPlayer.start();
		}
	};
}