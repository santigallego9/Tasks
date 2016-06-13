package edu.stanford.cs193a.sgalleg9.tasks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import stanford.androidlib.*;

public class MainActivity extends SimpleActivity {

    private ArrayList<String> items;
    private EditText addText;
    private String itemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = find(R.id.items_list);

        items = new ArrayList<String>();
        list.setOnItemLongClickListener(this);
        list.setOnItemClickListener(this);
    }

    public void addButtonClicked(View view) {
        addText = find(R.id.add_text);
        itemText = addText.getText().toString();

        if(itemText.length() > 0) {
            items.add(itemText);

            SimpleList.with(this).setItems(findListView(R.id.items_list), items);

            addText = find(R.id.add_text);
            addText.setText("");
        } else {
            toast("Please enter text");
        }
    }

    @Override
    public void onItemClick(ListView list, int index) {
        String holder = items.get(index);


        toast("Moved " + items.get(index) + " to the end");

        items.remove(index);
        items.add(holder);

        setList();
    }

    @Override
    public boolean onItemLongClick(ListView list, int index) {

        final int location = index;

        new AlertDialog.Builder(this)
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete " + items.get(index) + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        toast("Deleted: " + items.get(location));

                        items.remove(location);

                        setList();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }

    public void setList() {
        SimpleList.with(this).setItems(findListView(R.id.items_list), items);

        addText = find(R.id.add_text);
        addText.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("items", items);
        outState.putString("edit_text", itemText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getStringArrayList("items");
        savedInstanceState.getString("edit_text");
    }

    @Override
    protected void onPause() {
        super.onPause();

        log("PAUSED");
    }


    @Override
    protected void onStop() {
        super.onStop();

        log("STOPPED");

        PrintStream out = new PrintStream(openFileOutput("list.txt", MODE_PRIVATE));

        out.print("");

        for(int i = 0; i < items.size(); i++) {
            out.println(items.get(i));
        }

        out.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        log("DESTROYED");
    }

    @Override
    protected void onStart() {
        super.onStart();

        log("START");

        try {
            Scanner s = new Scanner(openFileInput("list.txt"));

            while (s.hasNextLine()) {
                items.add(s.nextLine());
                SimpleList.with(this).setItems(findListView(R.id.items_list), items);
            }
        } catch (Exception e) {
            log("FILE DOES NOT EXIST");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        log("RESTARED");
    }

    @Override
    protected void onResume() {
        super.onResume();

        log("RESUMED");
    }
}
