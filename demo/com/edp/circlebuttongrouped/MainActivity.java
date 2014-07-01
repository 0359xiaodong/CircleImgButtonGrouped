package com.edp.circlebuttongrouped;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);
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
		if (res == R.drawable.ic_bug) {
			Toast.makeText(this, "bug", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_cat) {
			Toast.makeText(this, "cat", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_dog) {
			Toast.makeText(this, "dog", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_elephant) {
			Toast.makeText(this, "elephant", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_fish) {
			Toast.makeText(this, "fish", Toast.LENGTH_SHORT).show();
		} else if (res == R.drawable.ic_turtle) {
			Toast.makeText(this, "turtle", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClickQuickAction(CircleImgBtnGroup cibg, int cibImageRes) {
		Toast.makeText(this, "cibg: " + getResources().getResourceEntryName(cibImageRes), 
				Toast.LENGTH_SHORT).show();
	}

}
