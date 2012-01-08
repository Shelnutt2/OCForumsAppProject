package com.ocforums.application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;


import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import android.app.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.ocforums.application.R;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;




public class OCForumsAppActivity extends Activity {
	
	
	//private MyCustomAdapter mAdapter;
	
	
		public static String md5(String input){
	        String res = "";
	        try {
	            MessageDigest algorithm = MessageDigest.getInstance("MD5");
	            algorithm.reset();
	            algorithm.update(input.getBytes());
	            byte[] md5 = algorithm.digest();
	            String tmp = "";
	            for (int i = 0; i < md5.length; i++) {
	                tmp = (Integer.toHexString(0xFF & md5[i]));
	                if (tmp.length() == 1) {
	                    res += "0" + tmp;
	                } else {
	                    res += tmp;
	                }
	            }
	        } catch (NoSuchAlgorithmException ex) {}
	        return res;
	    }


	
	public void loginfunc(String un, String pwd) {
	     try {
	    	 String pmd5sum = md5(pwd);
	         HttpClient client = new DefaultHttpClient(); 
	         //HttpGet httpget = new HttpGet("http://www.overclockers.com/forums/login.php?do=login");
	        // HttpResponse response = client.execute(httpget);
	         //HttpEntity entity = response.getEntity();
	         //List<Cookie> cookies = client.getCookieStore().getCookies();
	         String postURL = "http://www.overclockers.com/forums/login.php?do=login";
	         HttpPost post = new HttpPost(postURL);
	             /*List<NameValuePair> params = new ArrayList<NameValuePair>();
	             params.add(new BasicNameValuePair("vb_login_username", un));
	             params.add(new BasicNameValuePair("vb_login_md5sumpassword", pmd5sum));
	             params.add(new BasicNameValuePair("cookieuser","1"));
	             UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
	             post.setEntity(ent);
	             response = client.execute(post);
	             entity = response.getEntity();
	             cookies = client.getCookieStore().getCookies();
            */
	         final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        	    nameValuePairs.add(new BasicNameValuePair("vb_login_username", un));
	        	    nameValuePairs.add(new BasicNameValuePair("vb_login_password", ""));
	        	    nameValuePairs.add(new BasicNameValuePair("cookieuser","1"));
	        	    nameValuePairs.add(new BasicNameValuePair("s", ""));
	        	    nameValuePairs.add(new BasicNameValuePair("securitytoken","guest"));
	        	    nameValuePairs.add(new BasicNameValuePair("do", "login"));
	        	    nameValuePairs.add(new BasicNameValuePair("vb_login_md5password",pmd5sum));
	        	    nameValuePairs.add(new BasicNameValuePair("vb_login_md5password_utf",pmd5sum));

	        	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        	    final HttpResponse response = client.execute(post, context);
	        	    //final BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));


	             if (response.getEntity() != null) {
	            	 HtmlCleaner cleaner = new HtmlCleaner();
	            	 TagNode rootNode = cleaner.clean(response.getEntity().getContent());
	            	 List<TagNode> linkList = new ArrayList<TagNode>();

	                 TagNode linkElements[] = rootNode.getElementsByName("strong", true);
	                 for (int i = 0; linkElements != null && i < linkElements.length; i++)
	                 {
	                         linkList.add(linkElements[i]);
	                 }
	                 for (Iterator<TagNode> iterator = linkList.iterator(); iterator.hasNext();)
	                 {
	                     TagNode divElement = (TagNode) iterator.next();
	                     if(divElement.getText().toString().matches(".*"+ "Thank you for logging in" + ".*"));{
	                    	 loggedin = true;
	                    	 Log.i("logged in?","yes we are");
	                     }
	                    
	                 }
	             }
	             
	             if (response.getEntity() != null) {
	            	response.getEntity().consumeContent(); 
	             }
            	 if (loggedin = true){
                     try {
                         Thread.currentThread();
						Thread.sleep(500);
                  } catch (InterruptedException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                  } 
                     HtmlHelper.setcontext(context);
            		 pd = ProgressDialog.show(OCForumsAppActivity.this, "Working...", "request to server", true, false);
            	     new ParseForums().execute("http://www.overclockers.com/forums/?styleid=23");
            	 }
            	 client.getConnectionManager().closeIdleConnections(1, TimeUnit.MILLISECONDS);
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
		
    	 //Intent newActivity = new Intent(getBaseContext(), OCForumsAppActivity.class);     
         //startActivity(newActivity);
	}
	
	public void logoutfunc(){
		String shash1 = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HtmlHelper hh = new HtmlHelper(new String("http://www.overclockers.com/forums/"));
			List<TagNode> links = hh.getLinksByOnClick("return log_out('Are you sure you want to log out?')");
            for (Iterator<TagNode> iterator = links.iterator(); iterator.hasNext();)
            {
                TagNode divElement = (TagNode) iterator.next();
                shash1 = divElement.getText().toString().replace("amp;","");    
            }
	         HttpGet httpget = new HttpGet("http://www.overclockers.com/forums/"+shash1);
	         HttpResponse response = client.execute(httpget, context);

	         
	         if (response.getEntity() != null) {
            	 HtmlCleaner cleaner = new HtmlCleaner();
            	 TagNode rootNode = cleaner.clean(response.getEntity().getContent());
            	 List<TagNode> linkList = new ArrayList<TagNode>();

                 TagNode linkElements[] = rootNode.getElementsByName("div", true);
                 for (int i = 0; linkElements != null && i < linkElements.length; i++)
                 {
                     String classType = linkElements[i].getAttributeByName("style");
                     
                     if (classType != null && classType.equals("margin: 10px"))
                     {
                         linkList.add(linkElements[i]);
                     }
                 }
                 for (Iterator<TagNode> iterator = linkList.iterator(); iterator.hasNext();)
                 {
                     TagNode divElement = (TagNode) iterator.next();
                     if(divElement.getText().toString().matches(".*" + "All cookies cleared!" +".*"));{
                    	 loggedin = false;
                    	 Log.i("logged out?","yes we have");
                     }
                    
                 }
	         } 
	         
             if (response.getEntity() != null) {
            	response.getEntity().consumeContent(); 
             }
             client.getConnectionManager().closeIdleConnections(1, TimeUnit.MILLISECONDS);    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	
	  public static String gethurl()
	  {
	     return OCForumsAppActivity.hurl;
	  }
	  public static void  sethurl(String newh)
	  {
	      OCForumsAppActivity.hurl = newh;
	  }
	  public static void sethrefslist(List<String> newlist)
	  {
		  hrefslist = newlist;
	  }
	  public static List<String> gethrefslist()
	  {
		  return hrefslist;
	  }
/*	  public static void setnidn(List<String> newlist)
	  {
		  idn = newlist;
	  }
	  public static List<String> geidn()
	  {
		  return idn;
	  }*/	  
	  
	  
	CustomListView lv = new CustomListView();
	public static String hurl;
	private ProgressDialog pd;
	static List<String> hrefslist;
	String uname = null;
	String passwd = null;
	//ListView lv0 = new ListView(this);
	List<String> outputs;
	CustomListView.MyCustomAdapter adapter;
	boolean loggedin = false;
	//Common HttpClient
	HttpContext context = HtmlHelper.getcontext();
    List<String> idn = new ArrayList<String>();
	List<String> newlist = new ArrayList<String>();

	
	@Override
	protected void onStop() {
	    
	    findViewById(R.id.listView2).setVisibility(View.GONE);
	    super.onStop();
	}

	@Override
	protected void onRestart() {
	    super.onRestart();
	    findViewById(R.id.listView2).setVisibility(View.VISIBLE);
	    ListView listview = (ListView) findViewById(R.id.listView2);
	    adapter = new CustomListView.MyCustomAdapter(OCForumsAppActivity.this, R.layout.row , outputs);
	    listview.setAdapter(adapter);
	    //setContentView(lv0);
	    //adapter.notifyDataSetChanged();
	    
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    //ListView listview = (ListView) findViewById(R.id.listView2);
	   // listview.setAdapter(adapter);
	   // adapter = new CustomListView.MyCustomAdapter(OCForumsAppActivity.this, R.layout.row , outputs);
	    findViewById(R.id.listView2).setVisibility(View.VISIBLE);
	    //setContentView(lv0);
	   // adapter.notifyDataSetChanged();
	}

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        pd = ProgressDialog.show(OCForumsAppActivity.this, "Working...", "request to server", true, false);
        new ParseForums().execute("http://www.overclockers.com/forums/?styleid=23");
		

        ListView list = (ListView)findViewById(R.id.listView2);
        list.setOnItemClickListener(new OnItemClickListener(){
    	    public void onItemClick(AdapterView<?> parent, View view,
    	            int position, long id) {
    
    	    hurl = hrefslist.get((int) id).replace("amp;","");
    
    	     Log.i("testing",hurl);	
    
    	     if(hurl.matches("(?i).*forumdisplay.*")){
    
    	    	 CustomListView.sethurl(hurl);
    	    	 Intent newActivity = new Intent(getBaseContext(), BothActivity.class);     
                 startActivity(newActivity);

    	    	// BothActivity.bothactivity.execute(hurl);
    	     }
    	     else if(hurl.matches("(?i).*showthread.php.*")) {
    	    	 CustomListView.sethurl(hurl);
    	    	 Intent newActivity = new Intent(getBaseContext(), ThreadActivity.class);     
                 startActivity(newActivity);
    	     }
    	     else if(hrefslist == null){
    	    	 Toast.makeText(getApplicationContext(), "Confused?", Toast.LENGTH_LONG).show();
    	    	 //TODO add context menu for quote and reply
    	     }
    	     else{
    	    	 Toast.makeText(getApplicationContext(), "Very Confused", Toast.LENGTH_LONG).show();
    	     }
    		}

			
        });
        
        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        MenuItem mnuLogOut = menu.findItem(R.id.logout);
        MenuItem mnuLogIn = menu.findItem(R.id.login);
        MenuItem mnuSettings = menu.findItem(R.id.settings);
        MenuItem mnuUnansweredThreads = menu.findItem(R.id.UnansweredThreads);
        MenuItem mnuMyPost = menu.findItem(R.id.MyPosts);

        mnuSettings.setVisible(true);
        mnuUnansweredThreads.setVisible(true);
        mnuMyPost.setVisible(true);
        
        //set the menu options depending on login status
        if (loggedin == true)
        {
            //show the log out option
            mnuLogOut.setVisible(true);
            mnuLogIn.setVisible(false);

        }
        else
        {
            //show the log in option
            mnuLogOut.setVisible(false);
            mnuLogIn.setVisible(true);

        }

        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

          case R.id.login:    
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LinearLayout lila1= new LinearLayout(this);
            lila1.setOrientation(1); //1 is for vertical orientation
    		final EditText usernametextbox = new EditText(this);
    		final EditText passwdtextbox = new EditText(this);
    		lila1.addView(usernametextbox);
    		lila1.addView(passwdtextbox);
    		alert.setView(lila1);
    		alert.setTitle("Login");
    		alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    				uname = usernametextbox.getText().toString().trim();
    				passwd = passwdtextbox.getText().toString().trim();
    				loginfunc(uname,passwd);
    				if (loggedin=true){
    					new ParseForums().execute("http://www.overclockers.com/forums/");
    					Log.i("status of loggedin?",new Boolean(loggedin).toString());
    				}
    			}
    		});

    		alert.setNegativeButton("Cancel",
    				new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int whichButton) {
    						dialog.cancel();
    					}
    				});
    		alert.show();
         break;
         case R.id.logout:
        	 logoutfunc();
         break;  
         
         case R.id.settings:
        	 Toast.makeText(getApplicationContext(), "Perhaps one day...", Toast.LENGTH_SHORT).show();
         break;	  
         
         case R.id.UnansweredThreads:
        	 hurl ="http://www.overclockers.com/forums/search.php?do=process&replyless=1&replylimit=0&exclude=78,124,37,167,186,186,187,21,144,18,179,150,181,182,183,11,164,95,151,123,27,28,29,30,31,32,142,170,33,36,67,62,63,65,11,19,200&nocache=0";
	    	 Intent newActivity = new Intent(getBaseContext(), ThreadActivity.class);     
             startActivity(newActivity);
         break;	  
         
         case R.id.MyPosts:
        	 if(uname != null){
	    	 hurl = "http://www.overclockers.com/forums/search.php?do=process&searchuser="+uname+"&exactname=1&nocache=1";
        	 Intent newActivity1 = new Intent(getBaseContext(), ThreadActivity.class);     
             startActivity(newActivity1);
             }
        	 else{
        		 Toast.makeText(OCForumsAppActivity.this, "Please Login First",Toast.LENGTH_LONG);
        	 }
         break;	  
         
         
        }
        return true;
    }

    
   
    class ParseForums extends AsyncTask<String, Void, List<List<String>>> {

        protected List<List<String>> doInBackground(String... arg) {
        	List<List<String>> combined2d = new ArrayList<List<String>>();
            List<String> output = new ArrayList<String>();
            List<String> hrefs = new ArrayList<String>();



            try
            {
                HtmlHelper hh = new HtmlHelper(new String(arg[0])+"?styleid=23");
                HtmlHelper hh2 = new HtmlHelper(new String(arg[0]));
                List<TagNode> links = hh.getLinksByClass("forumtitle");
                List<TagNode> links2 = hh2.getForumStatus("oc_images/images/Statusicon/forum_new.gif");

                for (Iterator<TagNode> iterator = links.iterator(); iterator.hasNext();)
                {
                    TagNode divElement = (TagNode) iterator.next();
                    output.add(divElement.getText().toString());
                    hrefs.add(divElement.getAttributeByName("href").toString());
                }
                for (Iterator<TagNode> iterator = links2.iterator(); iterator.hasNext();)
                {
                    TagNode divElement = (TagNode) iterator.next();
                    idn.add(divElement.getAttributeByName("id").toString().replaceAll("\\D",""));                   	
                    

                }

                combined2d.add(output);
                combined2d.add(hrefs);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return combined2d;
        }

        protected void onPostExecute(List<List<String>> output2d) {
        	HashMap<String, String> hm = new HashMap<String, String>(); 
        	HashMap<String, String> hm2 = new HashMap<String, String>();

            output2d.size();
            Log.i("size0",Integer.toString(output2d.get(0).size()));
            Log.i("size1",Integer.toString(output2d.get(1).size()));
            ListView listview = (ListView) findViewById(R.id.listView2);
            /*for(int i = 0; i < newold.get(1).size(); i++){
            	String var = newold.get(1).get(i);
            	for(int k = 0; k < output2d.get(1).size(); k++){
            		Log.i("replaceall",output2d.get(1).get(k));
            		Log.i("replaceall",output2d.get(1).get(k).replaceAll("f.*f=", "").replaceAll("\\D",""));
            	    Log.i("variable",var);
            		if(output2d.get(1).get(k).replaceAll("f.*f=", "").replaceAll("\\D","").matches(var)){
            			Log.i("newlist",output2d.get(0).get(k));
            		      newlist.add(output2d.get(0).get(k));      		
            		}
            	}	
            }*/
            for(int k = 0; k < output2d.get(1).size(); k++){
            	//Log.i(output2d.get(1).get(k).replaceAll("f.*f=", "").replaceAll("\\D",""), output2d.get(0).get(k));
            	hm.put(output2d.get(1).get(k).replaceAll("f.*f=", "").replaceAll("\\D",""), output2d.get(0).get(k));
            }
            
            for(int i = 0; i < idn.size(); i++){
            	//Log.i(newold.get(1).get(i), newold.get(1).get(i));
            	hm2.put(idn.get(i), idn.get(i));
            }
            //Collection<String> newStuff = Collections.subtract(hm.getKey(), newold.get(1));
            
            Set<String> keys1 = hm.keySet();
            Set<String> keys2 = hm2.keySet();
            keys1.retainAll(keys2);
            for(Iterator<String> i = keys2.iterator(); i.hasNext();){
            	Log.i("keys2",(String) i.next());
            }
            for (Iterator<String> i = keys1.iterator(); i.hasNext();) {
                String key = (String) i.next();
                String value = (String) hm.get(key);
              //  Log.i(key,value);
                newlist.add(value);
            }
            pd.dismiss();
            //listview.setAdapter(new ArrayAdapter<String>(OCForumsAppActivity.this, android.R.layout.simple_list_item_1 , output2d.get(0)));
            adapter = new CustomListView.MyCustomAdapter(OCForumsAppActivity.this, R.layout.row , output2d.get(0));
            listview.setAdapter(adapter);
            //listview.setItemsCanFocus(true);
            //CustomListView.MyCustomAdapter adapter = 
            //lv.makelistview(output2d.get(0), OCForumsAppActivity.this);
            
            /* mAdapter = new MyCustomAdapter();
            
                mAdapter.addItem(output2d);
            
            listview.setAdapter(mAdapter);*/
        outputs = output2d.get(0);
        hrefslist = output2d.get(1);
        }
        }
    

    

    }

