package com.example.schoolapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.schoolapp.AddStudentActivity.AddStud;

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

public class AddBusActivity extends Activity {
	EditText busno22,nseats2,pass22,drivername,phoneno;
	Button addbuses;
	
     String busno12,nseats3,pass33,drivername1,phoneno1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bus);
		busno22=(EditText)findViewById(R.id.busno22);
		nseats2=(EditText)findViewById(R.id.nseats2);
		pass22=(EditText)findViewById(R.id.pass22);
		
		
		drivername=(EditText)findViewById(R.id.drivername);
		phoneno=(EditText)findViewById(R.id.phoneno);
		
		
		addbuses=(Button)findViewById(R.id.addbuses);
		
		addbuses.setOnClickListener(new addbusesclick());
		
		
	}
	class addbusesclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			busno12=busno22.getText().toString();
			nseats3=nseats2.getText().toString();
			pass33=pass22.getText().toString();
			drivername1=drivername.getText().toString();
			phoneno1=phoneno.getText().toString();
			
			 if(busno12.length()==0 ){
				 Toast.makeText(AddBusActivity.this, "Enter  bus number",Toast.LENGTH_LONG).show();
			 }
			 else if(nseats3.length()==0 ){
				 Toast.makeText(AddBusActivity.this, "Enter  number of seats",Toast.LENGTH_LONG).show();
			 }
			 else if(pass33.length()==0 ){
				 Toast.makeText(AddBusActivity.this, "Enter  Password",Toast.LENGTH_LONG).show();
			 }
			 else if(drivername1.length()==0 || phoneno1.length()==0){
				 Toast.makeText(AddBusActivity.this, "Enter  drivername or phoneno",Toast.LENGTH_LONG).show();
			 }
			 else
			 {
				 new Addbus().execute();
			 }
		}
		
	}
////////////////////////
class Addbus extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(AddBusActivity.this);
private static final String TAG_SUCCESS = "success";
int success;



@Override
protected void onPreExecute() {
super.onPreExecute();

pDialog.setMessage("Add Bus...");
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

params.add(new BasicNameValuePair("busno",busno12));
params.add(new BasicNameValuePair("nseats",nseats3));
params.add(new BasicNameValuePair("password",pass33));
params.add(new BasicNameValuePair("drivername",drivername1));
params.add(new BasicNameValuePair("phoneno",phoneno1));






//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/addbus.php", "GET", params);

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

Intent i=new Intent(AddBusActivity.this,BusMainActivity.class);
startActivity(i);
}
else
{
	 Toast.makeText(AddBusActivity.this, "Record not added. busno or passwords already exist.",Toast.LENGTH_LONG).show();
}


} 
}
//////////////////////

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_bus, menu);
		return true;
	}

}
