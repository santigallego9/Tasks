package edu.stanford.cs193a.sgalleg9.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import stanford.androidlib.*;

public class MainActivity extends SimpleActivity {

    private ArrayList<String> items;
    private EditText addText;

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
        String item = addText.getText().toString();

        items.add(item);

        SimpleList.with(this).setItems(findListView(R.id.items_list), items);

        addText = find(R.id.add_text);
        addText.setText("");
    }

    @Override
    public void onItemClick(ListView list, int index) {
        String holder = items.get(index);
        items.remove(index);
        items.add(holder);

        setList();

        toast("TAP: " + index);
    }

    @Override
    public boolean onItemLongClick(ListView list, int index) {
        items.remove(index);

        toast("LONG: " + index);

        setList();
        return true;
    }

    public void setList() {
        SimpleList.with(this).setItems(findListView(R.id.items_list), items);

        addText = find(R.id.add_text);
        addText.setText("");
    }
}
