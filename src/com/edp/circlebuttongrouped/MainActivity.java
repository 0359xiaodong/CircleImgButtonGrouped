package com.edp.circlebuttongrouped;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private RelativeLayout rlLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cibg_main);
		rlLeft = (RelativeLayout) findViewById(R.id.rlLeft);
		createGroup();
	}

	private void createGroup() {
		((CircleImgBtnGroup) findViewById(R.id.cibTop3)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop4)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop5)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop6)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom3)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom4)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom5)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom6)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib3)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib4)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_1)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_2)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_3)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_1)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_2)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_3)).configGroup(rlLeft);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
