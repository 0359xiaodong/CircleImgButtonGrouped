package com.edp.circlebuttongrouped;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout rlLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rlLeft = (RelativeLayout) findViewById(R.id.rlLeft);
		createGroup();
	}

	private void createGroup() {
		final CircleImgBtnGroup cibGroup = (CircleImgBtnGroup) findViewById(R.id.cibGroup1);
		cibGroup.configGroup(rlLeft);
		CircleImgBtnGroup cibGroup1 = (CircleImgBtnGroup) findViewById(R.id.CibGroup01);
		cibGroup1.configGroup(rlLeft);
		CircleImgBtnGroup cibGroup2 = (CircleImgBtnGroup) findViewById(R.id.CibGroup02);
		cibGroup2.configGroup(rlLeft);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
