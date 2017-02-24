package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

  public static final int REQUEST_CODE = 007;
  public static final String KEY_POSITION = "item_position";
  public static final String KEY_ITEM = "item_text";

  private ArrayList<String> items;
  private ArrayAdapter<String> itemsAdapter;
  private EditText etNewItem;
  private ListView lvItems;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setupViews();
    readItems();

    itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);

    setupListViewListener();
  }

public void launchEditItemView(String text, int pos) {
    Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
    intent.putExtra(KEY_ITEM, text);
    intent.putExtra(KEY_POSITION, pos);

    startActivityForResult(intent, REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
      int position = data.getIntExtra(KEY_POSITION, 0);
      String newText = data.getStringExtra(KEY_ITEM);

      items.set(position, newText);

      itemsAdapter.notifyDataSetChanged();
      writeItems();
    }
  }

  public void onAddItem(View view) {
    String itemText = etNewItem.getText().toString();
    itemsAdapter.add(itemText);
    etNewItem.setText("");
    writeItems();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
            items.remove(pos);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            return true;
          }
     });

    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object listItem = lvItems.getItemAtPosition(position);
        String listItemText = listItem.toString();

        launchEditItemView(listItemText, position);
      }
    });
  }

  private void setupViews() {
    lvItems = (ListView)findViewById(R.id.lvItems);
    etNewItem = (EditText) findViewById(R.id.etNewItem);
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");

    try {
      items = new ArrayList<>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");

    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

}
