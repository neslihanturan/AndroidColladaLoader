package com.neslihan.expandedlistview.Main;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.neslihan.expandedlistview.R;


@SuppressLint("NewApi")
public class LoadActivity extends ListActivity{

    private enum DISPLAYMODE{ ABSOLUTE, RELATIVE; }
    private final DISPLAYMODE displayMode = DISPLAYMODE.ABSOLUTE;
    private List<String> directoryEntries = new ArrayList<String>();
    private File currentDirectory = new File("/");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Browse(Environment.getExternalStorageDirectory());
    }

    private void upOneLevel(){
        if(this.currentDirectory.getParent() != null)
            this.Browse(this.currentDirectory.getParentFile());
    }

    private void Browse(final File aDirectory){
        if (aDirectory.isDirectory()){
            this.currentDirectory = aDirectory;
            fill(aDirectory.listFiles());

        }
    }

    private void fill(File[] files) {
        this.directoryEntries.clear();
        if(this.currentDirectory.getParent() != null)
            this.directoryEntries.add("..");

        switch(this.displayMode){
            case ABSOLUTE:
                for (File file : files){
                    this.directoryEntries.add(file.getPath());
                }
                break;
            case RELATIVE: // On relative Mode, we have to add the current-path to the beginning
                int currentPathStringLenght = this.currentDirectory.getAbsolutePath().length();
                for (File file : files){
                    this.directoryEntries.add(file.getAbsolutePath().substring(currentPathStringLenght));
                }
                break;
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, R.layout.load, this.directoryEntries);
        this.setListAdapter(directoryList);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        int selectionRowID = position;
        String selectedFileString = this.directoryEntries.get(selectionRowID);
        if(selectedFileString.equals("..")){
            this.upOneLevel();
        }else if(selectedFileString.contains(".dae")){
            Intent i = new Intent(this, ColladaLoader.class);
            i.putExtra("new_from_browse",this.directoryEntries.get(selectionRowID));
            startActivity(i);
            this.readFile();
        }else if(selectedFileString.contains("storage/emulated/0/Download")){
            File n = new File(this.directoryEntries.get(selectionRowID));
            this.Browse(n);
            this.readFile();
        }
        else {
            Log.d(selectedFileString,"secilen");
            File clickedFile = null;
            switch(this.displayMode){
                case RELATIVE:
                    clickedFile = new File(this.currentDirectory.getAbsolutePath()
                            + this.directoryEntries.get(selectionRowID));
                    break;
                case ABSOLUTE:
                    clickedFile = new File(this.directoryEntries.get(selectionRowID));
                    break;
            }
            if(clickedFile.isFile())
                this.Browse(clickedFile);
        }
    }

    private void readFile() {

    }

}