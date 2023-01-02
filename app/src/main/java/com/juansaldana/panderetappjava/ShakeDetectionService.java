package com.juansaldana.panderetappjava;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;

public class ShakeDetectionService extends Service implements SensorEventListener {

	MediaPlayer mediaPlayer;
	public final int minTimeBetweenShake = 1000;
	private long lastShakeTime = 0;
	SensorManager sensorManager = null;
	Vibrator vibrator = null;
	private Float shakeThreshold = 10.0F;

	public ShakeDetectionService() {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long curTime = System.currentTimeMillis();
			if ((curTime - lastShakeTime) > minTimeBetweenShake) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];

				double acceleration = Math.sqrt(Math.pow(x, 2)) + Math.pow(y, 2) + Math.pow(z, 2) - SensorManager.GRAVITY_EARTH;

				if (acceleration > shakeThreshold) {
					lastShakeTime = curTime;
					if (mediaPlayer == null) {
						assert false;
						mediaPlayer.start();
					}
				}
			}
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		if (mediaPlayer == null)
			mediaPlayer = MediaPlayer.create(this, R.raw.pandereta_sound_1);
		if (sensorManager != null) {
			Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
