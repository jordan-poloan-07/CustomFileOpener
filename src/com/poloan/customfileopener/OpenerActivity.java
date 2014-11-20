package com.poloan.customfileopener;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OpenerActivity extends Activity {

	public static final int ACTIVITY_RESULT_CODE = 90791;
	public static final String ACTIVITY_RESULT_IDENTIFIER = "res_ident";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_opener_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.open_file:
			chooseFile();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTIVITY_RESULT_CODE && resultCode == RESULT_OK) {
			// Make sure the request was successful
			Intent openerViewerActivityIntent = new Intent(this,
					OpenerActivityViewer.class);
			File chosenFile = (File) data.getExtras().get(ACTIVITY_RESULT_IDENTIFIER);
			openerViewerActivityIntent.setData(Uri.fromFile(chosenFile));
			startActivity(openerViewerActivityIntent);
		} else {
			Log.d("opener activity", "Invalid resultcode");
		}
	}

	private void chooseFile() {
		Intent openerActivityFileChooserIntent = new Intent(this,
				OpenerActivityFileChooser.class);
		startActivityForResult(openerActivityFileChooserIntent, ACTIVITY_RESULT_CODE);
	}

}
