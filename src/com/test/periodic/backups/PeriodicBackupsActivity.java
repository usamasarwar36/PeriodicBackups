package com.test.periodic.backups;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class PeriodicBackupsActivity extends Activity implements OnClickListener {

	Button scheduleButton;
	Button cancelScheduleButton;
	CharSequence[] items = { "1 Minute", "5 Minutes", "10 Minutes" };
	PendingIntent sender;
	AlarmManager am;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		scheduleButton = (Button) findViewById(R.id.schedule);
		scheduleButton.setOnClickListener(this);

		cancelScheduleButton = (Button) findViewById(R.id.cancel);
		cancelScheduleButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == scheduleButton) {

			AlertDialog.Builder dialog = new Builder(PeriodicBackupsActivity.this);
			dialog.setTitle("Set Interval");
			dialog.setSingleChoiceItems(items, 0, null);
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					Log.v("Selcted Item Position", "" + ((AlertDialog) dialog).getListView().getCheckedItemPosition());
					addAlram((((AlertDialog) dialog).getListView().getAdapter().getItem(((AlertDialog) dialog).getListView().getCheckedItemPosition()).toString()));

				}
			});

			dialog.create().show();

		}

		else if (v == cancelScheduleButton) {
			Toast.makeText(PeriodicBackupsActivity.this, "Cancel Button Clicked", Toast.LENGTH_SHORT).show();
			cancelPeriodicSchedule();
		}

	}

	private void addAlram(String interval) {

		// Activate Broadcast Receiver to receive broadcasts 
		activateBroadcastReceiver();
		// get a Calendar object with current time
		interval = interval.substring(0, 1);
		Calendar cal = Calendar.getInstance();
		// add 5 minutes to the calendar object
		cal.add(Calendar.MINUTE, Integer.parseInt(interval));
		Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
		intent.putExtra("alarm_message", "O'Doyle Rules!");
		// In reality, you would want to have a static variable for the request
		// code instead of 192837
		sender = PendingIntent.getBroadcast(this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the AlarmManager service
		am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), Integer.parseInt(interval) * 60 * 1000, sender);
	}

	private void cancelPeriodicSchedule() {
		if (am != null) {
			if (sender != null) {
				am.cancel(sender);
				sender.cancel();
			}
		}

		// Deactivate Broadcast Receiver to stop receiving broadcasts 
		deactivateBroadcastreceiver();
	}

	private void activateBroadcastReceiver() {
		PackageManager pm = PeriodicBackupsActivity.this.getPackageManager();
		ComponentName componentName = new ComponentName(PeriodicBackupsActivity.this, AlarmReceiver.class);
		pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		Toast.makeText(getApplicationContext(), "activated", Toast.LENGTH_LONG).show();
	}

	private void deactivateBroadcastreceiver() {
		// TODO Auto-generated method stub

		PackageManager pm = PeriodicBackupsActivity.this.getPackageManager();
		ComponentName componentName = new ComponentName(PeriodicBackupsActivity.this, AlarmReceiver.class);
		pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		Toast.makeText(PeriodicBackupsActivity.this, "cancelled", Toast.LENGTH_LONG).show();

	}
}