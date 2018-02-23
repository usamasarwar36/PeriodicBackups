package com.test.periodic.backups;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Log.e("In On Receive", "Alarm has Initiated Broadcast Receiver after one minute it will do it again.");
		
		Intent backUpServiceIntent   = new Intent(context, BackUpService.class);
		context.startService(backUpServiceIntent);
	}

}
