package com.neslihan.expandedlistview.Main;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;


import com.neslihan.expandedlistview.R;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

   ExpandableListAdapter listAdapter;
   ExpandableListView expListView;
   List<String> listDataHeader;
   HashMap<String, List<String>> listDataChild;
   String exampleName=" ";
   static String browsePath=" ";
   static boolean comingFromBrowse = false;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
      
       // get the listview
       expListView = (ExpandableListView) findViewById(R.id.lvExp);
       // preparing list data
       prepareListData();
       listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
       // setting list adapter
       expListView.setAdapter(listAdapter);
       if(comingFromBrowse){
    	   Intent i = new Intent(getApplicationContext(), ColladaLoader.class);
    	   i.putExtra("new_variable_name2",browsePath);
    	   startActivity(i); 
       }


       // Listview on child click listener
       expListView.setOnChildClickListener(new OnChildClickListener() {

           @Override
           public boolean onChildClick(ExpandableListView parent, View v,
                   int groupPosition, int childPosition, long id) {
               // TODO Auto-generated method stub
            
        	   if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).equals("Browse")){
                   Intent intent = new Intent(MainActivity.this,LoadActivity.class);
                   startActivity(intent);//creating Browse Activity
            	   return false;
        	   }
        	  exampleName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
        			  
        	   Intent i = new Intent(getApplicationContext(), ColladaLoader.class);
        	   i.putExtra("new_variable_name",exampleName);
        	   startActivity(i);
        	  
               return false;
           }
       });
   }

   /*
    * Preparing the list data
    */
   private void prepareListData() {
       listDataHeader = new ArrayList<String>();
       listDataChild = new HashMap<String, List<String>>();
   
       // Adding child data
       listDataHeader.add("Show an Example");
       listDataHeader.add("Select From Files");
       

       // Adding child data

       List<String> nowShowing = new ArrayList<String>();
       nowShowing.add("winter.dae");
       nowShowing.add("beach.dae");
       nowShowing.add("assets.dae");

       
       List<String> top = new ArrayList<String>();
       top.add("Browse");

       listDataChild.put(listDataHeader.get(1), top); // Header, Child data
       listDataChild.put(listDataHeader.get(0), nowShowing);
      
   }

}