package edu.stanford.cs193a.sgalleg9.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
        toast("Deleted: " + items.get(index));

        items.remove(index);

        setList();
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
