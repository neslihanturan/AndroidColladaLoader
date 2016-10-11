package com.neslihan.expandedlistview.Main;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;



public class Render extends GLSurfaceView implements Renderer {
	
	/** Pyramid instance */
	private  Pyramid pyramid;
	private float xrot; 	
	/** Angle For The Cube */
	private float yrot;
	private float oldX;
    private float oldY;
    
	private final float TOUCH_SCALE = 0.2f;
	private boolean light = false;
	private float z = -5.0f;			//Depth Into The Screen ( NEW )

	public Render(Context context, AttributeSet attrs){
       super(context,attrs);
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
	
	}
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		gl.glShadeModel(GL10.GL_FLAT);
		
		gl.glClearColor(0f, 0f, 0f, 0.0f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
	
	}

	public void onDrawFrame(GL10 gl) {
		//Clear Screen And Depth Buffer
		pyramid = new Pyramid();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		gl.glLoadIdentity();					//Reset The Current Modelview Matrix
		
		//Drawing
		gl.glTranslatef(0.0f,-10+ ColladaLoader.zoomRate/10, -2*(100-ColladaLoader.zoomRate));
		gl.glScalef(0.8f, 0.8f, 0.8f);
		
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);	//X
		gl.glRotatef(yrot, 0.0f, 0.0f, 1.0f);	//Y

		pyramid.draw(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		GLU.gluPerspective(gl, 1500.0f, (float)width / (float)height, 0.1f, 4000.0f);
		Log.d("hei",""+height);
		Log.d("we",""+width);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
		
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//
		
		float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	//Calculate the change
        	float dx = x - oldX;
	        float dy = y - oldY;
			xrot += dy * TOUCH_SCALE;
			yrot += dx * TOUCH_SCALE;

        //A press on the screen
        }else if(event.getAction() == MotionEvent.ACTION_UP) {
        	//Define an upper area of 10% to define a lower area
        	int upperArea = this.getHeight() / 10;
        	int lowerArea = this.getHeight() - upperArea;
        	
        	//Change the light setting if the lower area has been pressed 
        	if(y > lowerArea) {
        		if(light) {
        			light = false;
        		} else {
        			light = true;
        		}
        	}
        }
        
        //Remember the values
        oldX = x;
        oldY = y;
        
        //We handled the event
		return true;
	}
	
}