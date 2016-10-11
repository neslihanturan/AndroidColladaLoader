package com.neslihan.expandedlistview.Main;

import java.util.ArrayList;


public class Diffuse {
	 ArrayList <Float> floatArray = new ArrayList<Float>();

	public static class LibraryEffect {
        ArrayList<Effects> effects = new ArrayList<Effects>();
    }

	public static class Materials {
        String id;
        InstanceEffect instanceEffect;
    }

	public static class Effects {
        String id;
        Diffuse diffuse;
    }
}
