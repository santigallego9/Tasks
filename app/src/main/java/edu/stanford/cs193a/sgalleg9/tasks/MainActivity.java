package edu.stanford.cs193a.sgalleg9.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import stanford.androidlib.*;

public class MainActivity extends SimpleActivity {

    private ArrayList<String> items;

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
        EditText addText = find(R.id.add_text);
        String item = addText.getText().toString();

        items.add(item);

        SimpleList.with(this).setItems(findListView(R.id.items_list), items);

        addText.setText("");
    }

    @Override
    public void onItemClick(ListView list, int index) {
        toast("TAP: " + index);
    }

    @Override
    public boolean onItemLongClick(ListView list, int index) {
        toast("LONG: " + index);
        return true;
    }
}
