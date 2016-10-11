package com.neslihan.expandedlistview.Main;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid implements  OnSeekBarChangeListener{

	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer lightPosBuffer;
	private FloatBuffer lightPosBuffer1;
	private FloatBuffer ambientBuffer;
	private FloatBuffer specularBuffer;
	private FloatBuffer grbaBuffer;
	private FloatBuffer diffuseBuffer;
		
	public  float vertices[];
	public  float colors[];

    float[] lightColorAmbient = {1,1,1,1};
    float[] lightColorSpecular = {0.5f, 0.5f, 0.5f, 1};
    float[] rgba = {0.17f, 0.17f, 0.17f};
    float[] diffuse = {1f,1f,1f,1};

    float[] lightPos = {0.0f, 0.0f, 0.7f, 1.0f };
    float[] lightPos1 = {0.0f, 0.0f, 80.0f, 1.0f };

	public Pyramid() {
		
		vertices = new float[ColladaLoader.parser.vertexList.size()];
		int i = 0;		
		for (Float f : ColladaLoader.parser.vertexList) {
		    vertices[i++] = (f != null ? f : Float.NaN);
		  
		}
		if(ColladaLoader.parser.colorArrayList.size()>ColladaLoader.parser.vertexList.size()){
		colors = new float[ColladaLoader.parser.colorArrayList.size()];
		 i = 0;		
			for (Float f : ColladaLoader.parser.colorArrayList) {
				colors[i++] = (f != null ? f : Float.NaN);
			}
		}else 
			colors = new float[ColladaLoader.parser.vertexList.size()];

		
		
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		 byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		

		byteBuf = ByteBuffer.allocateDirect(lightColorAmbient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		ambientBuffer = byteBuf.asFloatBuffer();
		ambientBuffer.put(lightColorAmbient);
		ambientBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightColorSpecular.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		specularBuffer = byteBuf.asFloatBuffer();
		specularBuffer.put(lightColorSpecular);
		specularBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(lightPos.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPosBuffer = byteBuf.asFloatBuffer();
		lightPosBuffer.put(lightPos);
		lightPosBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(lightPos1.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPosBuffer1 = byteBuf.asFloatBuffer();
		lightPosBuffer1.put(lightPos1);
		lightPosBuffer1.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(rgba.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		grbaBuffer = byteBuf.asFloatBuffer();
		grbaBuffer.put(rgba);
		grbaBuffer.position(0);
		
		
		byteBuf = ByteBuffer.allocateDirect(diffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		diffuseBuffer = byteBuf.asFloatBuffer();
		diffuseBuffer.put(diffuse);
		diffuseBuffer.position(0);
	}

	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 * @param gl - The GL Context
	 */
	public void draw(GL10 gl) {

		float colorBlue[] = { 0.0f, 0.0f, 1.0f, 1.0f };
		
		gl.glFrontFace(GL10.GL_CW);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT, grbaBuffer);
        gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, grbaBuffer);
        gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, grbaBuffer);
        gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 0.5f);
        gl.glLightModelfv(GL10.GL_AMBIENT, ambientBuffer);
        gl.glLightModelfv(GL10.GL_SPECULAR, specularBuffer);
        gl.glLightModelfv(GL10.GL_DIFFUSE, diffuseBuffer);
        
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularBuffer);
        
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosBuffer1);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientBuffer);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseBuffer);
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularBuffer);
        
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);/////neccesaryforonlycolor	
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); /////neccesaryforonlycolor
		
		gl.glEnable (GL10.GL_COLOR_MATERIAL ) ;/////neccesaryforonlycolor
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);/////neccesaryforonlycolor
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);/////neccesaryforonlycolor
		
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);//vertices length/3 number of triangles/////neccesaryforonlycolor
	
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);/////neccesaryforonlycolor
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);/////neccesaryforonlycolor
	}

	 @Override
	    public void onProgressChanged(SeekBar seekBar, int progress,
	            boolean fromUser) {


	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {


	    }

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {


	    }
}

