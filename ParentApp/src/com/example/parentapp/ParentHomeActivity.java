package com.example.parentapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ParentHomeActivity extends Activity  implements LocationListener{
	TextView studmsg;
	Button parpro,back1,bus1;
	public static String lat="0.0",lon="0.0";
	String location="" ;
	 LocationManager locationManager ;
	    String provider;
int n=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_home);
		parpro=(Button)findViewById(R.id.parpro);
		back1=(Button)findViewById(R.id.back1);
		bus1=(Button)findViewById(R.id.bus1);
		parpro.setOnClickListener(new parproclick());
		back1.setOnClickListener(new back1click());
		bus1.setOnClickListener(new bus1click());
		
		studmsg=(TextView)findViewById(R.id.studmsg);
		
		n=-1;
		

		 // Getting LocationManager object
      locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

      // Creating an empty criteria object
      Criteria criteria = new Criteria();

      // Getting the name of the provider that meets the criteria
      provider = locationManager.getBestProvider(criteria, false);

      if(provider!=null && !provider.equals("")){

          // Get the location from the given provider
          Location location = locationManager.getLastKnownLocation(provider);

          locationManager.requestLocationUpdates(provider, 2000, 1, this);

          if(location!=null)
              onLocationChanged(location);
          else
              Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
          
                }else{
          Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
      }
     // Toast.makeText(getBaseContext(), "Busno="+GlobalVariables.busno+" sid="+GlobalVariables.sid, Toast.LENGTH_SHORT).show();
		new getattendance().execute();
	}
	
	/////////////////
////////////////////////
class getattendance extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(ParentHomeActivity.this);
private static final String TAG_SUCCESS = "success";
int success;
String status="";


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

params.add(new BasicNameValuePair("busno",GlobalVariables.busno));
params.add(new BasicNameValuePair("sid",GlobalVariables.sid));
Calendar c=Calendar.getInstance();
String today=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);

params.add(new BasicNameValuePair("today",today));
//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/parent/getattendance.php", "GET", params);

//check log cat fro response
Log.d("Create Response", json.toString());

//check for success tag

success = json.getInt(TAG_SUCCESS);
if(success==1)
{
status=json.getString("status");

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
	if(status.equals("P"))
		studmsg.setText("Your Child is dropped at school safely.");
	else
		studmsg.setText("Your Child is absent to school");
			
}


else 
{

Toast.makeText(ParentHomeActivity.this, "Details not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 
	
	
	///////////////
	@Override
    public void onBackPressed() {
     
          //  super.onBackPressed();
            doExit();
     }
    
    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ParentHomeActivity.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
			@Override
            public void onClick(DialogInterface dialog, int which) {
            	ParentHomeActivity.this.finishAffinity();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Alert");
        alertDialog.show();
    }
 
	
	
	
	
	@Override
	public void onLocationChanged(Location location) {
	Log.d("Latitude", "changing location");
	//Toast.makeText(HomeActivity.this, "Hello", Toast.LENGTH_LONG).show();
	//Toast.makeText(ParentHomeActivity.this, "Latitude:" + location.getLatitude() + ", Longitude:"+ location.getLongitude(),Toast.LENGTH_LONG).show();
	lat=""+location.getLatitude();
	lon=""+location.getLongitude();
	if(n==0)
	new  viewsBuses().execute();
	n=1;
	}

	@Override
	public void onProviderDisabled(String provider) {
	Log.d("Latitude", "disable");
	}

	@Override
	public void onProviderEnabled(String provider) {
	Log.d("Latitude", "enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	Log.d("Latitude", "status");
	}
	
	
	
	
	class parproclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i=new Intent(ParentHomeActivity.this,ParentProfileActivity.class);
			startActivity(i);
			
			
		}
		
	}
class back1click implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		doExit();
		
	}
	
}
class bus1click implements OnClickListener
{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		n=0;
		
	}
	
}

////////////////////////
class viewsBuses extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(ParentHomeActivity.this);
String recs="";
private static final String TAG_SUCCESS = "success";
int success;



@Override
protected void onPreExecute() {
super.onPreExecute();

pDialog.setMessage("searching bus...");
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
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/parent/getbuslocation.php", "GET", params);

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
String s[]=recs.split("#");
for(String row : s)
{
if(row.length()>1)
{
String ss[]=row.split("!");
//al.add("\n busno : "+ ss[1]+"\n Seats : "+ss[2]+"\nDriverName : "+ss[3]+"\nPhoneno : "+ss[4]);
//dataModels.add(new DataModel("BusNo : "+ss[1]+"\nSeats : "+ss[2], "DriverName : "+ss[3]+"\nPhoneno :"+ss[4]));

String dest_lat=ss[5];
String dest_lon=ss[6];
String add="http://maps.google.com/?saddr="+lat+","+lon+"&daddr="+dest_lat+","+dest_lon;

Intent ii=new Intent(Intent.ACTION_VIEW,Uri.parse(add));
startActivity(ii);


}


}


}
else 
{
Toast.makeText(ParentHomeActivity.this, "records  not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 
//////////////////////

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parent_home, menu);
		return true;
	}

}
