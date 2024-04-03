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
import android.widget.ListView;
import android.widget.Toast;

public class ViewBusStudentsActivity extends Activity {
	ListView plist;
	ArrayList<DataModel> dataModels;
	 private static CustomAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_bus_students);
		plist=(ListView)findViewById(R.id.buslist1);
		new viewbusstudents().execute();
		
	}

	
////////////////////////////
class viewbusstudents extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(ViewBusStudentsActivity.this);
String recs="";
private static final String TAG_SUCCESS = "success";
int success;



@Override
protected void onPreExecute() {
super.onPreExecute();

pDialog.setMessage("searching ...");
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
params.add(new BasicNameValuePair("busno",GlobalVariables.busno));
//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/getbusstudents.php", "GET", params);

//check log cat fro response
Log.d("Create Response", json.toString());

//check for success tag

success = json.getInt(TAG_SUCCESS);
if (success == 1) 
{
recs=json.getString("recs");
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
Intent i=null;

if (success == 1) 
{
ArrayList<String> al=new ArrayList<String>();
dataModels= new ArrayList<DataModel>();

String s[]=recs.split("#");
for(String row : s)
{
if(row.length()>1)
{
String ss[]=row.split("!");

dataModels.add(new DataModel("SName : "+ss[3]+"\nClass : "+ss[1]+"\nSection : "+ss[2]+"\nRollNo : "+ss[0], "FName : "+ss[4]+"\nPhoneno :"+ss[5]+"\nSeatNo :"+ss[7]));
//al.add(" Date :"+ss[1]+"\n StoryTitle : "+ss[2]+"\n Description : "+ss[3]);

}
}

adapter= new CustomAdapter(dataModels,ViewBusStudentsActivity.this);
plist.setAdapter(adapter);
}
else 
{

Toast.makeText(ViewBusStudentsActivity.this, "records  not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 
//////////////////////
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_bus_students, menu);
		return true;
	}

}
