package com.example.schoolapp;



import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SchoolHomeActivity extends Activity {
	Button stud,bus,sout;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school_home);
		
		stud=(Button)findViewById(R.id.stud);
		bus=(Button)findViewById(R.id.bus);
		sout=(Button)findViewById(R.id.sout);
		
		stud.setOnClickListener(new studclick());
		bus.setOnClickListener(new busclick());
		sout.setOnClickListener(new soutclick());
		
	}
	
	@Override
    public void onBackPressed() {
     
          //  super.onBackPressed();
            doExit();
     }
    
    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SchoolHomeActivity.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
			@Override
            public void onClick(DialogInterface dialog, int which) {
            	SchoolHomeActivity.this.finishAffinity();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Alert");
        alertDialog.show();
    }
	
	class studclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(SchoolHomeActivity.this,StudentMenuActivity.class);
			startActivity(i);
			
		}
		
	}
	class busclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(SchoolHomeActivity.this,BusMainActivity.class);
			startActivity(i);
			
		}
		
	}
class soutclick implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		doExit();
	}
	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school_home, menu);
		return true;
	}

}
