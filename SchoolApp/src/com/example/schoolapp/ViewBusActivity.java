package com.example.schoolapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.schoolapp.ViewStudentActivity.classlistclick;
import com.example.schoolapp.ViewStudentActivity.viewstudents;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewBusActivity extends Activity implements LocationListener{
	public static String lat="0.0",lon="0.0";
	String location="" ;
	 LocationManager locationManager ;
	    String provider;
	
	ListView viewbuslist;
	ArrayList<String> buslst=null;
	
	ArrayList<DataModel> dataModels;
	 private static CustomAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_bus);
		
		viewbuslist=(ListView)findViewById(R.id.viewbuslist);
		viewbuslist.setOnItemClickListener(new busviewlistclick());
		
		

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
       
		
       new viewsBuses().execute();
		
		
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
	Log.d("Latitude", "changing location");
	//Toast.makeText(HomeActivity.this, "Hello", Toast.LENGTH_LONG).show();
	//Toast.makeText(ViewBusActivity.this, "Latitude:" + location.getLatitude() + ", Longitude:"+ location.getLongitude(),Toast.LENGTH_LONG).show();
	lat=""+location.getLatitude();
	lon=""+location.getLongitude();
	
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
	
	
	class busviewlistclick implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String str=buslst.get(arg2);
			String s[]=str.split("-");
			
			GlobalVariables.busno=s[0];
			final String dest_lat=s[1];
			final String dest_lon=s[2];
			
			new AlertDialog.Builder(ViewBusActivity.this)
			.setTitle("Menu")
			.setMessage("Select Action")
			.setPositiveButton("ViewStudents",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							
							
							Intent i=new Intent(ViewBusActivity.this,ViewBusStudentsActivity.class);
							startActivity(i);
							
							
							
						}
					})
			.setNegativeButton("Track Bus",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							
							String add="http://maps.google.com/?saddr="+lat+","+lon+"&daddr="+dest_lat+","+dest_lon;
					          
					           Intent ii=new Intent(Intent.ACTION_VIEW,Uri.parse(add));
					           startActivity(ii);
						}
					}).setIcon(android.R.drawable.ic_dialog_alert)
			.show();

			
			
			
			
			
			
			
			
		}
		
	}
////////////////////////
class viewsBuses extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(ViewBusActivity.this);
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

//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/viewbus.php", "GET", params);

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
	buslst=new ArrayList<String>();
	String s[]=recs.split("#");
	dataModels= new ArrayList<DataModel>();
	for(String row : s)
	{
		if(row.length()>1)
		{
		String ss[]=row.split("!");
		//al.add("\n busno : "+ ss[1]+"\n Seats : "+ss[2]+"\nDriverName : "+ss[3]+"\nPhoneno : "+ss[4]);
		dataModels.add(new DataModel("BusNo : "+ss[1]+"\nSeats : "+ss[2], "DriverName : "+ss[3]+"\nPhoneno :"+ss[4]));

		buslst.add(ss[1]+"-"+ss[5]+"-"+ss[6]);
		
		}
		
		
	}
	
	adapter= new CustomAdapter(dataModels,ViewBusActivity.this);
	viewbuslist.setAdapter(adapter);
	
}
else 
{
	ArrayList<String> al=new ArrayList<String>();
	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewBusActivity.this, android.R.layout.simple_list_item_1, al);
	viewbuslist.setAdapter(arrayAdapter);
	Toast.makeText(ViewBusActivity.this, "records  not found.  Try again!!!!",Toast.LENGTH_LONG).show();
}
}
} 
//////////////////////

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_bus, menu);
		return true;
	}

}
