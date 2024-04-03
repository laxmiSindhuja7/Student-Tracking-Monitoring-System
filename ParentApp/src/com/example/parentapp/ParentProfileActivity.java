package com.example.parentapp;

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
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class ParentProfileActivity extends Activity {
	EditText name,fname,seatno,busno,section,class1,phone,rn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_profile);
		name=(EditText)findViewById(R.id.name);
		fname=(EditText)findViewById(R.id.fname);
		seatno=(EditText)findViewById(R.id.seatno);
		busno=(EditText)findViewById(R.id.busno);
		section=(EditText)findViewById(R.id.section);
		class1=(EditText)findViewById(R.id.class1);
		phone=(EditText)findViewById(R.id.phone);
		rn=(EditText)findViewById(R.id.rn);
		new getprofile().execute();
		
	}

	
//////
		class getprofile extends AsyncTask<String, String, String>
		{

		/**
		* Before starting background thread Show Progress Dialog
		* */
		private ProgressDialog pDialog = new ProgressDialog(ParentProfileActivity.this);
		private static final String TAG_SUCCESS = "success";
		private int success;

		String name1,fname1,seatno1,busno1,section1,class11,phone1,rn1;

		@Override
		protected void onPreExecute() {
		super.onPreExecute();

		pDialog.setMessage("wait...");
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
		params.add(new BasicNameValuePair("phoneno",GlobalVariables.phoneno));
		
		//getting JSON Object
		//Note that create product url accepts POST method
		//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
		JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/parent/getprofile.php", "GET", params);

		//check log cat fro response
		Log.d("Create Response", json.toString());

		//check for success tag

		success = json.getInt(TAG_SUCCESS);
		if(success==1)
		{

			name1=json.getString("name");
			fname1=json.getString("fname");
			seatno1=json.getString("seatno");
			busno1=json.getString("busno");
			section1=json.getString("section");
			
			class11=json.getString("class");
			phone1=json.getString("phoneno");
			rn1=json.getString("rn");
			
		}


		} catch (JSONException e) {
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

		if (success == 1) {
		//successfully created product
			
			
			name.setText(name1);
			fname.setText(fname1);
			seatno.setText(seatno1);
			busno.setText(busno1);
			section.setText(section1);
			class1.setText(class11);
			phone.setText(phone1);
			rn.setText(rn1);
			
			

		} else 
		{
		//failed to create product
		Toast.makeText(ParentProfileActivity.this, "Details not found",Toast.LENGTH_LONG).show();
		}
		}
		} 
		
	////////////////////////
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parent_profile, menu);
		return true;
	}

}
