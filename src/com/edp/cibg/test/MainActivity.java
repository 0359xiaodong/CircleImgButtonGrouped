package com.edp.cibg.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edp.circlebuttongrouped.CircleImgBtn;
import com.edp.circlebuttongrouped.CircleImgBtnGroup;
import com.edp.circlebuttongrouped.OnCircleButtonClickListener;
import com.edp.circlebuttongrouped.R;

public class MainActivity extends Activity implements OnCircleButtonClickListener {

	private RelativeLayout rlLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cibg_main);
		rlLeft = (RelativeLayout) findViewById(R.id.rlLeft);
		createGroup();
	}

	private void createGroup() {
		((CircleImgBtnGroup) findViewById(R.id.cibTop3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop4)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop5)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibTop6)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom4)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom5)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibBottom6)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib4)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_1)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_2)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib5_3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_1)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_2)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cib6_3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR1)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR2)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR3)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR4)).configGroup(this, rlLeft);
		((CircleImgBtnGroup) findViewById(R.id.cibR5)).configGroup(this, rlLeft);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCIBClick(CircleImgBtn cib, int res) {
		if (res == R.drawable.cry) {
			Toast.makeText(this, "cry", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.open_book) {
			Toast.makeText(this, "book", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.close_book) {
			Toast.makeText(this, "book", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_launcher) {
			Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
		} else {
		}
	}

	@Override
	public void onClickQuickAction(CircleImgBtnGroup cibg, int cibImageRes) {
		Toast.makeText(this, "cibg: " + getResources().getResourceEntryName(cibg.getId()) + 
				" - " + getResources().getResourceEntryName(cibImageRes), Toast.LENGTH_SHORT).show();
	}

}
