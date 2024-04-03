package com.example.schoolapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudentActivity extends Activity {
EditText rn1,class2,section1,studname1,fname1,phno1,busno1,seatno1;
String rn2,class3,section2,studname2,fname2,phno2,busno2,seatno2;
Button addstud1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_student);
		rn1=(EditText)findViewById(R.id.rn1);
		class2=(EditText)findViewById(R.id.class2);
		section1=(EditText)findViewById(R.id.section1);
		studname1=(EditText)findViewById(R.id.studname1);
		fname1=(EditText)findViewById(R.id.fname1);
		phno1=(EditText)findViewById(R.id.phno1);
		busno1=(EditText)findViewById(R.id.busno1);
		seatno1=(EditText)findViewById(R.id.seatno1);
		
		
		addstud1=(Button)findViewById(R.id.addstud1);
		
		addstud1.setOnClickListener(new addstud1click());
		
		
	}
	class addstud1click implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			rn2=rn1.getText().toString();
			class3=class2.getText().toString();
			
			 section2=section1.getText().toString();
			 studname2=studname1.getText().toString();
			 fname2=fname1.getText().toString();
			 phno2=phno1.getText().toString();
			 busno2=busno1.getText().toString();
			 seatno2=seatno1.getText().toString();
			 
			 if(rn2.length()==0 ){
				 Toast.makeText(AddStudentActivity.this, "Enter  Roll Number",Toast.LENGTH_LONG).show();
			 }
			 else if(class3.length()==0 ){
				 Toast.makeText(AddStudentActivity.this, "Enter  class",Toast.LENGTH_LONG).show();
			 }
			
			 else if( section2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter section",Toast.LENGTH_LONG).show();
			 }
			 else if( studname2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter Name",Toast.LENGTH_LONG).show();
			 }
			 else if( fname2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter fathername",Toast.LENGTH_LONG).show();
			 }
			 else if( phno2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter phone number",Toast.LENGTH_LONG).show();
			 }
			 else if( busno2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter Bus Number",Toast.LENGTH_LONG).show();
			 }
			 else if( seatno2.length()==0 ) {
				 Toast.makeText(AddStudentActivity.this, "Enter seat number",Toast.LENGTH_LONG).show();
			 }
			 else
			 {
				 new AddStud().execute();
			 }
		
			
		}
	}

	////////////////////////
	class AddStud extends AsyncTask<String, String, String>
	{

	/**
	* Before starting background thread Show Progress Dialog
	* */
	private ProgressDialog pDialog = new ProgressDialog(AddStudentActivity.this);
	private static final String TAG_SUCCESS = "success";
	int success;



	@Override
	protected void onPreExecute() {
	super.onPreExecute();

	pDialog.setMessage("Add Student...");
	pDialog.setIndeterminate(false);
	pDialog.setCancelable(true);
	pDialog.show();
	}

	/**
	* Storing Data
	* */
	protected String doInBackground(String... args) {

	try { 
	//Building Parameters


	List<NameValuePair> params = new ArrayList<NameValuePair>();

	params.add(new BasicNameValuePair("rn",rn2));
	params.add(new BasicNameValuePair("class",class3));
	
	params.add(new BasicNameValuePair("section",section2));
	params.add(new BasicNameValuePair("name",studname2));
	params.add(new BasicNameValuePair("fname",fname2));
	params.add(new BasicNameValuePair("phoneno",phno2));
	
	params.add(new BasicNameValuePair("busno",busno2));
	params.add(new BasicNameValuePair("seatno",seatno2));




	//getting JSON Object
	//Note that create product url accepts POST method
	//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
	JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/addstudent.php", "GET", params);

	//check log cat fro response
	Log.d("Create Response", json.toString());

	//check for success tag

	try {
	    // Checking for SUCCESS TAG
	   success = json.getInt(TAG_SUCCESS);
	    
	} catch (JSONException e) {
	    e.printStackTrace();
	}



	} catch (Exception e) {
	//e.printStackTrace();
	//Toast.makeText(AddempActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
	}

	return null;
	}

	/**
	* After completing background task Dismiss the progress dialog
	* **/
	protected void onPostExecute(String file_url) {
	//dismiss the dialog once done
	pDialog.dismiss();
	if(success==1)
	{
		
		Intent i=new Intent(AddStudentActivity.this,StudentMenuActivity.class);
		startActivity(i);
	}
	else
	{
		Toast.makeText(AddStudentActivity.this, "Record not added. phoneno or password already exist.",Toast.LENGTH_LONG).show();
	}


	} 
	}
	//////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_student, menu);
		return true;
	}

}
