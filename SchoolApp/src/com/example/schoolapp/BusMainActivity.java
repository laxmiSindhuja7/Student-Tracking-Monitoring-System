package com.example.schoolapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BusMainActivity extends Activity {
	Button addbus1,viewbus1,back1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_main);
		
		
		addbus1=(Button)findViewById(R.id.addbus1);
		viewbus1=(Button)findViewById(R.id.viewbus1);
		back1=(Button)findViewById(R.id.back1);
		
		
		addbus1.setOnClickListener(new addbus1click());
		viewbus1.setOnClickListener(new viewbus1click());
		back1.setOnClickListener(new back1click());
		
		
	}
class addbus1click implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(BusMainActivity.this,AddBusActivity.class);
		startActivity(i);
		
	}
	
}
class viewbus1click implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(BusMainActivity.this,ViewBusActivity.class);
		startActivity(i);
		
	}
	
}

class back1click implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		Intent i=new Intent(BusMainActivity.this,SchoolHomeActivity.class);
		startActivity(i);
		
		
	}
	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_main, menu);
		return true;
	}

}
