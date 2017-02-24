package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

  private int itemPosition;
  private String itemText;
  private EditText textField;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    populateEditTextContent();
  }

  private void populateEditTextContent() {
    itemText = getIntent().getStringExtra(MainActivity.KEY_ITEM);
    itemPosition = getIntent().getIntExtra(MainActivity.KEY_POSITION, 0);

    textField = (EditText) findViewById(R.id.editItem_text_field);
    textField.append(itemText);
  }

  public void saveEdits(View view) {
    Intent intent = new Intent(EditItemActivity.this, MainActivity.class);

    itemText = textField.getText().toString();

    intent.putExtra(MainActivity.KEY_ITEM, itemText);
    intent.putExtra(MainActivity.KEY_POSITION, itemPosition);
    setResult(RESULT_OK, intent);
    finish();
  }
}
