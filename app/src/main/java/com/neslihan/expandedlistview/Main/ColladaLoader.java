package com.neslihan.expandedlistview.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.neslihan.expandedlistview.R;


public class ColladaLoader extends Activity implements OnSeekBarChangeListener{


	public static XMLParser parser;
	public static int zoomRate;

    private String value = "";
	private Render render;
	private boolean comingFromBrowse = false;
	private boolean comingFromExample = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if(extras.getString("new_variable_name")!=null){
				 value = extras.getString("new_variable_name");
				 comingFromExample = true; //set a flag
			}
			if(extras.getString("new_from_browse")!=null){
				 value = extras.getString("new_from_browse");
				 comingFromBrowse = true; //set a flag
			}
	        
		}
		   parser = new XMLParser();
		 
          try {
        	  if(comingFromBrowse){
        		  FileInputStream f = new FileInputStream(value);
             	  InputStream is = f;
             	  parser.parse(is); 
        	  }else if(comingFromExample){
        			parser.parse(getAssets().open(value)); 
        	  }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
          setContentView(R.layout.seekbar);
          render = (Render) findViewById(R.id.vistaGLSuperficie);
          
          SeekBar bar = (SeekBar)findViewById(R.id.seekBar1); // make seekbar object
          bar.setOnSeekBarChangeListener(this); // set seekbar listener.
          bar.setProgress(50);

	}

	@Override
	protected void onResume() {
		super.onResume();
		render.onResume();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		render.onPause();
		
	}
	@Override
	protected void onStop() {
	    super.onStop();
	    System.exit(0); //kill the app

	}

	@Override
	public void onBackPressed() {
	    super.onBackPressed(); 
	    onStop(); //call onStop
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		 Log.d(String.valueOf(progress),"seekbar");
		 zoomRate=progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}
}