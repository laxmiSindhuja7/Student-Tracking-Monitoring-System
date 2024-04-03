package com.example.busapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusHomeActivity extends Activity implements LocationListener {

	String lat="0.0",lon="0.0";
	String phno="",etype="",location="" ;
	
	
	 LocationManager locationManager ;
	    String provider;
	    
	    ////////////
	    ListView lv;
		 Model[] modelItems;
		 Button post;
		 
		  // Progress Dialog
	private ProgressDialog pDialog;

	//Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	String recs="",att_res="",studids="";
	int n=0;

	TextView t[];
	CheckBox c[];
	
	ArrayList<String> sids=null;
	//url to get all products list
	private static String url_doc_search = "http://www.helplinecontacts.com/schoolbt/bus/getstudents.php";

	//JSON Node names
	private static final String TAG_SUCCESS = "success";
	//private static final String TAG_PRODUCTS = "products";
	//private static final String TAG_PID = "slno";
	//private static final String TAG_NAME = "phno"; 

	//products JSONArray
	JSONArray products = null;
	LinearLayout l1;
	    
	    //////////

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_home);
		
		
		
		 // Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
        // Creating an empty criteria object
        Criteria criteria = new Criteria();
 
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
 
        if(provider!=null && !provider.equals("")){
 
            // Get the location from the given provider
            Location location = locationManager.getLastKnownLocation(provider);
 
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
 
            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            
                  }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
        
        
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());                 
		 try {
		     List<Address> listAddresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
		     if(null!=listAddresses&&listAddresses.size()>0){
		          location = listAddresses.get(0).getAddressLine(0);
		          Toast.makeText(BusHomeActivity.this, "Location="+location , Toast.LENGTH_LONG).show();
		     }
		 } catch (IOException e) {
			 Toast.makeText(BusHomeActivity.this, "Location can't retrive" , Toast.LENGTH_LONG).show();
		 }
		 
		 
		 ///////////
		  new GetStudDetials().execute();
	       l1=(LinearLayout)findViewById(R.id.l1);
	       
	       
		  
		  
		  
		  post=(Button)findViewById(R.id.postatt);
		  post.setOnClickListener(new postclick());
		 /////////
		 
		
		
	}
	
	@Override
    public void onBackPressed() {
     
          //  super.onBackPressed();
            doExit();
     }
    
    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BusHomeActivity.this);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @SuppressLint("NewApi")
			@Override
            public void onClick(DialogInterface dialog, int which) {
            	BusHomeActivity.this.finishAffinity();
            }
        });

        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Alert");
        alertDialog.show();
    }
 
	////////////
	class postclick implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			att_res="";
			
			for(int i=0;i<t.length;i++)
			{
			
			String s=t[i].getText().toString();
			//String ss[]=s.split("-");
					String sid=sids.get(i);
			if(c[i].isChecked())
			 att_res+=s+"-P-"+sid+",";
			else
				att_res+=s+"-A-"+sid+",";	
			
			
			//studids=studids+sids.get(i)+",";
			}
			
			
			//Toast.makeText(BusHomeActivity.this, att_res, Toast.LENGTH_LONG).show();
			
			new PostAttendance().execute();
			
			
		}
		
	}
	
	/**
	  * Background Async Task to Load all product by making HTTP Request
	  * */
	 class GetStudDetials extends AsyncTask<String, String, String> {

	     /**
	      * Before starting background thread Show Progress Dialog
	      * */
	     @Override
	     protected void onPreExecute() {
	         super.onPreExecute();
	         pDialog = new ProgressDialog(BusHomeActivity.this);
	         pDialog.setMessage("Loading..  Please wait...");
	         pDialog.setIndeterminate(false);
	         pDialog.setCancelable(false);
	         pDialog.show();
	     }

	     /**
	      * getting All products from url
	      * */
	     protected String doInBackground(String... args) {
	         // Building Parameters
	    	
	         List<NameValuePair> params = new ArrayList<NameValuePair>();
	         params.add(new BasicNameValuePair("busno",GlobalVariables.busno));
	         // getting JSON string from URL
	         JSONObject json = jParser.makeHttpRequest(url_doc_search, "GET", params);

	         // Check your log cat for JSON reponse
	         Log.d("All Products: ", json.toString());

	         try {
	             // Checking for SUCCESS TAG
	             int success = json.getInt(TAG_SUCCESS);
	           
	             if (success == 1) 
	             {
	            	 
	            	 recs=json.getString("recs");
	            	 
	             } else {
	            	 recs=null;

	             }
	         } catch (JSONException e) {
	             e.printStackTrace();
	         }

	         return null;
	     }

	     /**
	      * After completing background task Dismiss the progress dialog
	      * **/
	     protected void onPostExecute(String file_url) {
	         // dismiss the dialog after getting all products
	         pDialog.dismiss();
	         
	         TextView t1;
	         CheckBox c1;

	         if(recs!=null)
	         {
	        	 String rows[]=recs.split("#");
	         
	        	t=new TextView[rows.length];
	        	c=new CheckBox[rows.length];
	        	int i=0;
	        	sids=new ArrayList<String>();
	        	 for(String row : rows)
	        	 {
	        		 String ss[]=row.split("!");
	        		t1=new TextView(BusHomeActivity.this);
	        		t1.setText(ss[0]);
	        		c1=new CheckBox(BusHomeActivity.this);
	        		c1.setChecked(true);
	        		t[i]=t1;
	        		c[i++]=c1;
	        		
	        		LinearLayout hl=new LinearLayout(BusHomeActivity.this);
	        		hl.setOrientation(LinearLayout.HORIZONTAL);
	        		
	        		hl.addView(c1);
	        		hl.addView(t1);
	        		l1.addView(hl);
	        		
	        		sids.add(ss[1]);
	        		
	        		
	        	 }
	    	  
	        	
	         }
	         
	         
	         
	             }
	       

	     }
	 
	 
	 
////////////////
class PostAttendance extends AsyncTask<String, String, String>
{
private ProgressDialog progressDialog = new ProgressDialog(BusHomeActivity.this);
InputStream is = null ;
String result = "";
protected void onPreExecute() {
progressDialog.setMessage("Fetching data...");
progressDialog.show();
}
@Override
protected String doInBackground(String... args) {



Calendar c=Calendar.getInstance();
String today=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);


String url_select = "http://www.helplinecontacts.com/schoolbt/bus/postattendance.php";


ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

param.add(new BasicNameValuePair("busno",GlobalVariables.busno));
param.add(new BasicNameValuePair("today",today));
param.add(new BasicNameValuePair("att_res",att_res));

JSONObject json = jParser.makeHttpRequest(url_select, "GET", param);
//Check your log cat for JSON reponse
Log.d("Student details: ", json.toString());





return null;

}
protected void onPostExecute(String file_url) {
//dismiss the dialog after getting all products
progressDialog.dismiss();
Toast.makeText(BusHomeActivity.this, "attendance posted", Toast.LENGTH_LONG).show();

}
}

/////////////////
	
	/////////////
	

	@Override
	public void onLocationChanged(Location location) {
	Log.d("Latitude", "changing location");
	//Toast.makeText(HomeActivity.this, "Hello", Toast.LENGTH_LONG).show();
	Toast.makeText(BusHomeActivity.this, "Latitude:" + location.getLatitude() + ", Longitude:"+ location.getLongitude(),Toast.LENGTH_LONG).show();
	lat=""+location.getLatitude();
	lon=""+location.getLongitude();
	new updatebuslocation().execute();
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
	
////////////////////////
class updatebuslocation extends AsyncTask<String, String, String>
{

/**
* Before starting background thread Show Progress Dialog
* */
private ProgressDialog pDialog = new ProgressDialog(BusHomeActivity.this);
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

params.add(new BasicNameValuePair("busno",GlobalVariables.busno));
params.add(new BasicNameValuePair("latitude",lat));

params.add(new BasicNameValuePair("longitude",lon));

//getting JSON Object
//Note that create product url accepts POST method
//JSONObject json = new JSonParser().makeHttpRequest("http://www.navyugelectronics.com/ems/addemp.php", "POST", params);
JSONObject json = new JSONParser().makeHttpRequest("http://www.helplinecontacts.com/schoolbt/bus/addbuslocation.php", "GET", params);

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
//if(success==1)
//{

//Intent i=new Intent(AddStudentActivity.this,StudentMenuActivity.class);
//startActivity(i);
//}


} 
}
//////////////////////
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_home, menu);
		return true;
	}

}
