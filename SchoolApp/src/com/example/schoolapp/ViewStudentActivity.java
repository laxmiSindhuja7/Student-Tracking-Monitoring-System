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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewStudentActivity extends Activity {
	Spinner classlist;
	
	ListView viewstudlist;
	
	ArrayList<DataModel> dataModels;
	 private static CustomAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_student);
		classlist=(Spinner)findViewById(R.id.classlist);
		
		viewstudlist=(ListView)findViewById(R.id.viewstudlist);
		
		classlist.setOnItemSelectedListener(new classlistclick());	
		
	}
	class classlistclick implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			new viewstudents().execute();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

////////////////////////
class viewstudents extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(ViewStudentActivity.this);
String recs="";
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

String class1=classlist.getSelectedItem().toString();




List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("class",class1));




//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/viewstudent.php", "GET", params);

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
	String s[]=recs.split("-");
	dataModels= new ArrayList<DataModel>();
	for(String row : s)
	{
		String ss[]=row.split(",");
		//al.add("sid :"+ss[0]+"\n rn : "+ ss[1]+"\n class :"+ss[2]+"\n section : "+ss[3]+"\n name : "+ss[4]+"\n fname :"+ss[5]+"\n phoneno : "+ss[6]+"\n busno : "+ss[7]+"\n seatno : "+ss[8]);
		dataModels.add(new DataModel("SName : "+ss[4]+"\nClass : "+ss[2]+"\nSection : "+ss[3]+"\nRollNo : "+ss[1], "FName : "+ss[5]+"\nPhoneno :"+ss[6]+"\n busno : "+ss[7]+"\nSeatNo :"+ss[8]));

		
	}
	
	adapter= new CustomAdapter(dataModels,ViewStudentActivity.this);
	viewstudlist.setAdapter(adapter);
	//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewStudentActivity.this, android.R.layout.simple_list_item_1, al);
	//viewstudlist.setAdapter(arrayAdapter);
}
else 
{
	ArrayList<String> al=new ArrayList<String>();
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewStudentActivity.this, android.R.layout.simple_list_item_1, al);
	viewstudlist.setAdapter(arrayAdapter);
Toast.makeText(ViewStudentActivity.this, "records  not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 
//////////////////////
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_student, menu);
		return true;
	}

}
