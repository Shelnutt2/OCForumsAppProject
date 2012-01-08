package com.ocforums.application;

import java.util.ArrayList;
import java.util.List;


import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListView extends ListActivity {
	static List<String> lists = new ArrayList<String>();
	static List<String> hlists = new ArrayList<String>(); 
	static List<String> newlist = new ArrayList<String>();
	static List<String> usernames = new ArrayList<String>();
	static List<String> usernamecolors = new ArrayList<String>(); 
	static List<String> dates = new ArrayList<String>();
	static List<String> messages = new ArrayList<String>();
	
	//lists = new List<String>();
	Context dontext = this;
	static int rid;

	static Context fcontext;
	static String hurl;
	
	@Override
	protected void onStop() {
	    super.onStop();
	    getListView().setVisibility(View.GONE);
	}

	@Override
	protected void onRestart() {
	    super.onRestart();
	    getListView().setVisibility(View.VISIBLE);
	}

	
	public void makelist(List<String> list){
		lists=list;
		
		Log.i("is it empy?",lists.get(0));
		//ListView listview = (ListView) findViewById(R.id.listView1);
		//setContentView(R.layout.row);
		//setContentView(R.layout.main);
		//MyCustomAdapter adapter= new MyCustomAdapter(this, R.layout.row, lists);
		//setListAdapter(adapter);
	}
	
	  public static String gethurl()
	  {
	     return hurl;
	  }
	  public static void  sethurl(String newh)
	  {
	      hurl = newh;
	  }
	  public static void sethlist(List<String> newlist)
	  {
		  hlists = newlist;
	  }
	  public static List<String> gethlist()
	  {
		  return hlists;
	  }
	  
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        
    }
	
    static class MyCustomAdapter extends BaseAdapter {
    	private LayoutInflater mInflater;

    	public MyCustomAdapter(Context context, int textViewResourceId,List<String> objects) {
		super();
		mInflater = LayoutInflater.from(context);
		rid = textViewResourceId;
		fcontext = context;
		lists = objects;
		//newlist = onobjects;
		Log.i("is it empy?(lists)",Integer.toString(lists.size()));
		//Log.i("is it empy?(newlist)",Integer.toString(newlist.size()));
		}

    	public int getCount() {
    	return lists.size();
    	}

    	public Object getItem(int position) {
    	return position;
    	}

    	public long getItemId(int position) {
    	return position;
    	}

    	public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
    	if (convertView == null) {
    	convertView = mInflater.inflate(rid, null);
    	holder = new ViewHolder();
    	holder.text = (TextView) convertView.findViewById(R.id.forumlist);

    	convertView.setTag(holder);
    	} else {
    	holder = (ViewHolder) convertView.getTag();
    	}
    	holder.text.setText(lists.get(position));
    	
		//View row = LayoutInflater.from(fcontext).inflate(R.layout.row, null);
		//TextView label=(TextView)row.findViewById(R.id.forumlist);
		//label.setText(lists.get(position));
		ImageView icon=(ImageView)convertView.findViewById(R.id.icon);
		//Log.i("newlist size",Integer.toString(newlist.size()));
		if (newlist.size() > 0 && lists.get(position).matches(newlist.get(position))){
		icon.setImageResource(R.drawable.forum_new);
		}
		else{
		icon.setImageResource(R.drawable.forum_old);
		}

    	return convertView;
    	}

    	static class ViewHolder {
    	TextView text;
    	}
    	}
   
    
    
    
    
    
    static class MyCustomAdapter2 extends BaseAdapter {
    	private LayoutInflater mInflater;

    	public MyCustomAdapter2(Context context, int textViewResourceId,List<List<String>> objects) {
		super();
		mInflater = LayoutInflater.from(context);
		rid = textViewResourceId;
		fcontext = context;
		usernames = objects.get(0);
		usernamecolors = objects.get(1); 
		dates = objects.get(2);
		messages = objects.get(3);
		Log.i("is it empy?(messages)",Integer.toString(messages.size()));
		Log.i("is it empy?(dates)",Integer.toString(dates.size()));
		Log.i("is it empy?(usernamecolors)",Integer.toString(usernamecolors.size()));
		Log.i("is it empy?(usernames)",Integer.toString(usernames.size()));
		}

    	public int getCount() {
    	return messages.size();
    	}

    	public Object getItem(int position) {
    	return position;
    	}

    	public long getItemId(int position) {
    	return position;
    	}

    	public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
       	if (convertView == null) {
        	convertView = mInflater.inflate(rid, null);
        		//convertView	= new ListView(fcontext);
        		holder = new ViewHolder();
        		holder.text = (TextView) convertView.findViewById(R.id.postinfo);
        		//holder.text2 = (TextView) convertView.findViewById(R.id.post);
        		//holder.text3 = (TextView) convertView.findViewById(R.id.dateinfo);
        		convertView.setTag(holder);

        	} else {
        		holder = (ViewHolder) convertView.getTag();
        	}
        	holder.text.setText(usernames.get(position));
        	holder.text.setTextColor(Color.parseColor(usernamecolors.get(position)));
        	
        	ViewHolder holderd = new ViewHolder();
        	holderd.text2 = (TextView) convertView.findViewById(R.id.dateinfo);
        	holderd.text2.setText(dates.get(position));
        	
        	
        	 
        	ViewHolder holderm = new ViewHolder();
        	holderm.text3 = (TextView) convertView.findViewById(R.id.post);
        	holderm.text3.setText(messages.get(position));

    	
        	//View row = LayoutInflater.from(fcontext).inflate(R.layout.row, null);
    		//TextView label=(TextView)row.findViewById(R.id.forumlist);
    		//label.setText(lists.get(position));
    		//ImageView icon=(ImageView)convertView.findViewById(R.id.icon2);
    		//Log.i("newlist size",Integer.toString(newlist.size()));
    	
		if (newlist.size() > 0 && lists.get(position).matches(newlist.get(position))){
		//icon.setImageResource(R.drawable.forum_new);
		}
		else{
		//icon.setImageResource(R.drawable.forum_old);
		}

    	return convertView;
    	}

    	static class ViewHolder {
    	public TextView text3;
		public TextView text2;
		TextView text;
    	}
    	}
}








