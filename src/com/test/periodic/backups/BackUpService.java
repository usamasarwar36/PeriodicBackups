package com.test.periodic.backups;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BackUpService extends IntentService {

	public BackUpService() {

		super("BackUpService");
		// TODO Auto-generated constructor stub
		Log.e("In the BackUpService", "Constructor");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Log.e("In the BackUpService", "Handling Intent");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Log.e("In the BackUpService", "ok....I have done my task ... now Destroying ... Ta Ta ...!!!!");
	}

}
