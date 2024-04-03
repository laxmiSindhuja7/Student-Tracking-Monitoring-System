package com.example.schoolapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StudentMenuActivity extends Activity {
   Button addstud,viewstud,back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_menu);
		addstud=(Button)findViewById(R.id.addstud1);
		viewstud=(Button)findViewById(R.id.viewstud);
		back=(Button)findViewById(R.id.back);
		
		addstud.setOnClickListener(new addstudclick());
		viewstud.setOnClickListener(new viewstudclick());
		back.setOnClickListener(new backclick());
		
		
	}
	class addstudclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(StudentMenuActivity.this,AddStudentActivity.class);
			startActivity(i);
			
			
		}
		
	}
	class viewstudclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(StudentMenuActivity.this,ViewStudentActivity.class);
			startActivity(i);
			
		}
		
	}
	class backclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(StudentMenuActivity.this,SchoolHomeActivity.class);
			startActivity(i);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_menu, menu);
		return true;
	}

}
