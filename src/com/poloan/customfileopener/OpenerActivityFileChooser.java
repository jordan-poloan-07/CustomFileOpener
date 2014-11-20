package com.poloan.customfileopener;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OpenerActivityFileChooser extends Activity implements
		View.OnClickListener {

	// put go back and current path
	// represented by Button and TextView

	private ListView fileList;
	private Button backButton;
	private TextView pathTextView;

	private ArrayAdapter<File> fileslistAdapter;
	private Deque<File> filePathMemento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opener_file_chooser);

		fileList = (ListView) findViewById(R.id.file_list);
		backButton = (Button) findViewById(R.id.backButton);
		pathTextView = (TextView) findViewById(R.id.pathTextView);

		filePathMemento = new ArrayDeque<File>();

		File fileExtSd = Environment.getExternalStorageDirectory();

		List<File> filesList = new ArrayList<File>();

		if (fileExtSd.isDirectory()) {
			filesList.addAll(Arrays.asList(fileExtSd.listFiles()));
			filePathMementoExec(fileExtSd);
		} else {
			Log.d("opener activity", "external file sd is something");
		}

		fileslistAdapter = new ArrayAdapter<File>(this,
				android.R.layout.simple_list_item_1, filesList);
		fileList.setAdapter(fileslistAdapter);
		fileList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> aView, View view, int index,
					long arg3) {
				File chosenFile = fileslistAdapter.getItem(index);
				execFileAction(chosenFile);
			}
		});

		backButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		File currentDir = filePathMemento.pollLast(); // the current directory
		File previousDir = filePathMemento.pollLast(); // the previous directory

		if (previousDir != null)
			execFileAction(previousDir);
		else
			execFileAction(currentDir);
	}

	private void execFileAction(File file) {

		if (file == null) {
			Log.d("opener activity", "File is null");
			return;
		}

		if (file.isDirectory()) {
			// list files
			fileslistAdapter.clear();
			fileslistAdapter.addAll(Arrays.asList(file.listFiles()));
			filePathMementoExec(file);
		} else if (file.isFile()) {
			// return result
			Intent result = new Intent();
			result.putExtra(OpenerActivity.ACTIVITY_RESULT_IDENTIFIER, file);
			setResult(RESULT_OK, result);
			finish();
		} else {
			Log.d("opener activity", "Okay file is neither a directory or file");
		}
	}

	private void filePathMementoExec(File dir) {
		// file must be dir
		filePathMemento.addLast(dir);
		displayPath();
	}

	private void displayPath() {
		StringBuffer sBuff = new StringBuffer();

		Iterator<File> filePathIterator = filePathMemento.iterator();

		while (filePathIterator.hasNext()) {
			File currFile = filePathIterator.next();
			sBuff.append(currFile.getName());
			if (filePathIterator.hasNext()) {
				sBuff.append(" > ");
			}
		}

		pathTextView.setText(sBuff.toString());
	}
}
