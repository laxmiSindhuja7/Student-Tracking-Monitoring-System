package com.example.parentapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BusTrackActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_track);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_track, menu);
		return true;
	}

}
