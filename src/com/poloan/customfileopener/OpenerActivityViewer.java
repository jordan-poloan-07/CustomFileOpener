package com.poloan.customfileopener;

import java.io.FileInputStream;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OpenerActivityViewer extends Activity {

	private TextView fileContent;
	private String fileContentString;

	private static final String FILE_KEY = "LE_FILE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opener_viewer);

		fileContent = (TextView) findViewById(R.id.file_content);

		String filePath = getIntent().getData().getEncodedPath();

		// implement asynctask here
		if (savedInstanceState == null)
			fileContentString = getFileContent(filePath);
		else
			fileContentString = savedInstanceState.getString(FILE_KEY);
		
		fileContent.setText(fileContentString);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		outState.putString(FILE_KEY, fileContentString);

		super.onSaveInstanceState(outState);

	}

	private String getFileContent(String path) {

		FileInputStream fileStream = null;
		String fileString = null;

		try {
			fileStream = new FileInputStream(path);
			byte[] fileContentBytes = new byte[fileStream.available()];
			fileStream.read(fileContentBytes);
			fileString = new String(fileContentBytes);
			fileStream.close();
		} catch (Exception e) {
			fileString = "Error in reading the file...";
		}

		return fileString;
	}

}
