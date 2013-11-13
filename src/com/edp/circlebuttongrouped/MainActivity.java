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
		setContentView(R.layout.activity_main);
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
		((CircleImgBtnGroup) findViewById(R.id.CibGroup01)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.CibGroup02)).configGroup(rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.CibGroup03)).configGroup(rlLeft);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
