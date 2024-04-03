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

public class MainActivity extends Activity {
	EditText user1,pass1;
	Button signin;
	
	String userid="",password="",usertype="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		user1=(EditText)findViewById(R.id.user1);
		pass1=(EditText)findViewById(R.id.pass1);
		signin=(Button)findViewById(R.id.signin);
		signin.setOnClickListener(new signinclick());
		
		
			
	}
	class signinclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			 userid=user1.getText().toString();
			 password=pass1.getText().toString();
			 
			
			
	if(userid.length()==0 ){
		Toast.makeText(MainActivity.this, "Enter Userid",Toast.LENGTH_LONG).show();
	}
	else if( password.length()==0 ) {
		Toast.makeText(MainActivity.this, "Enter password",Toast.LENGTH_LONG).show();
	}
	else
	{

		new UserLogin().execute();
	}
		
			
		}
		
	}
////////////////////////
class UserLogin extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
private static final String TAG_SUCCESS = "success";
int success;



@Override
protected void onPreExecute() {
super.onPreExecute();

pDialog.setMessage("searching user...");
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

String userid=user1.getText().toString();
String password=pass1.getText().toString();



List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("userid",userid));
params.add(new BasicNameValuePair("password",password));



//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/login.php", "GET", params);

//check log cat fro response
Log.d("Create Response", json.toString());

//check for success tag

success = json.getInt(TAG_SUCCESS);


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
Intent i=null;
GlobalVariables.userid=userid;
if (success == 1) 
{
GlobalVariables.usertype="school";
i=new Intent(MainActivity.this,SchoolHomeActivity.class);
startActivity(i);
finish();
}


else 
{

Toast.makeText(MainActivity.this, "user not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
