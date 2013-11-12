package com.edp.circlebuttongrouped;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CircleImgBtnGroup extends CircleImgBtn implements OnClickListener, OnLongClickListener{

	private static final int EXPAND_DISTANCE = 20;
	private static final int VERTICAL_BTN_DISTANCE = 5;
	private static final int HORIZONTAL_BTN_DISTANCE = 5;
	
	private int height, width, count;
	private TypedArray attribs;
	private boolean expanded, collapseAtClick;
	private ArrayList<CircleImgBtn> CIBs = new ArrayList<CircleImgBtn>();

	@SuppressLint("Recycle")
	public CircleImgBtnGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		attribs = context.obtainStyledAttributes(attrs, R.styleable.CircleImgBtnGroup);
		collapseAtClick = attribs.getBoolean(R.styleable.CircleImgBtnGroup_collapseAtClick, true);
		configMainImgBtn();
		setHeight(attribs.getInt(R.styleable.CircleImgBtnGroup_ibHeight, 100));
		setWidth(attribs.getInt(R.styleable.CircleImgBtnGroup_ibWidth, 100));
		setButtomsCount(attribs.getInt(R.styleable.CircleImgBtnGroup_ibCount, 3));
	}

	private void configMainImgBtn() {
		setImageResource(getImgCIVs(0));
		addShadow();
		setOnClickListener(this);
		setOnLongClickListener(this);
	}

	/**
	 * @param i = número do botão
	 * @return - id do drawable associado a ele
	 */
	public int getImgCIVs(int i) {
		final int def = R.drawable.ic_launcher;
		switch (i) {
			case 0: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB1, def);
			case 1: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB2, def);
			case 2: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB3, def);
			case 3: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB4, def);
			case 4: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB5, def);
		}
		return def;
	}
	
	public CircleImgBtn getButtom(int i) throws NullPointerException{
		if(i < CIBs.size())
			return CIBs.get(i);
		else 
			return null;
	}
	
	public void setHeight(int height) {
		this.height = height;
		super.setHeight(height);
	}

	public void setWidth(int width) {
		this.width = width;
		super.setWidth(width);
	}

	public int getButtomsCount() {
		return count;
	}

	public void setButtomsCount(int count) {
		this.count = count;
		if(count > 5)
			count = 5;
		CIBs.clear();
		CIBs.add(this);
		for(int i=1; i<count; i++){
			CircleImgBtn circleImgBtn = new CircleImgBtn(getContext());
			circleImgBtn.setHeight(height);
			circleImgBtn.setWidth(width);
			circleImgBtn.addShadow();
			circleImgBtn.setImageResource(getImgCIVs(i));
			circleImgBtn.setOnClickListener(this);
			circleImgBtn.setOnLongClickListener(this);
			CIBs.add(circleImgBtn);
		}
	}

	public void configGroup(RelativeLayout rl){
		//coloca iniciando do ultimo para q o primeiro fique no topo e no canto esquerdo
		for (int i=CIBs.size()-1; i>0; i--) {
			CircleImgBtn cib = CIBs.get(i);
			RelativeLayout.LayoutParams params = getRelativeLayoutParamsCopy();
			params.leftMargin += i*HORIZONTAL_BTN_DISTANCE;
			cib.setLayoutParams(params);
			rl.addView(cib);
		}
		bringToFront();
	}

	private RelativeLayout.LayoutParams getRelativeLayoutParamsCopy() {
		RelativeLayout.LayoutParams thisParams = (RelativeLayout.LayoutParams)getLayoutParams();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		final int[] rules = thisParams.getRules();
		if(rules[RelativeLayout.ALIGN_TOP] != 0)
			params.addRule(RelativeLayout.ALIGN_TOP, rules[RelativeLayout.ALIGN_TOP]);
		if(rules[RelativeLayout.ALIGN_LEFT] != 0)
			params.addRule(RelativeLayout.ALIGN_LEFT, rules[RelativeLayout.ALIGN_LEFT]);
		if(rules[RelativeLayout.ALIGN_RIGHT] != 0)
			params.addRule(RelativeLayout.ALIGN_RIGHT, rules[RelativeLayout.ALIGN_RIGHT]);
		if(rules[RelativeLayout.ALIGN_BOTTOM] != 0)
			params.addRule(RelativeLayout.ALIGN_BOTTOM, rules[RelativeLayout.ALIGN_BOTTOM]);
		if(rules[RelativeLayout.ALIGN_PARENT_TOP] != 0)
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, rules[RelativeLayout.ALIGN_PARENT_TOP]);
		if(rules[RelativeLayout.ALIGN_PARENT_LEFT] != 0)
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, rules[RelativeLayout.ALIGN_PARENT_LEFT]);
		if(rules[RelativeLayout.ALIGN_PARENT_RIGHT] != 0)
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, rules[RelativeLayout.ALIGN_PARENT_RIGHT]);
		if(rules[RelativeLayout.ALIGN_PARENT_BOTTOM] != 0)
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, rules[RelativeLayout.ALIGN_PARENT_BOTTOM]);
		if(rules[RelativeLayout.ABOVE] != 0)
			params.addRule(RelativeLayout.ABOVE, rules[RelativeLayout.ABOVE]);
		if(rules[RelativeLayout.BELOW] != 0)
			params.addRule(RelativeLayout.BELOW, rules[RelativeLayout.BELOW]);
		if(rules[RelativeLayout.LEFT_OF] != 0)
			params.addRule(RelativeLayout.LEFT_OF, rules[RelativeLayout.LEFT_OF]);
		if(rules[RelativeLayout.RIGHT_OF] != 0)
			params.addRule(RelativeLayout.RIGHT_OF, rules[RelativeLayout.RIGHT_OF]);
		params.topMargin = thisParams.topMargin;
		params.leftMargin = thisParams.leftMargin;
		params.rightMargin = thisParams.rightMargin;
		params.bottomMargin = thisParams.bottomMargin;
		return params;
	}
	
	@Override
	public void onClick(View v) {
		if(expanded && collapseAtClick & v.equals(this))
			collapse();
		int num = CIBs.indexOf(v);
		int[] pos = new int[2];
		CIBs.get(num).getLocationInWindow(pos);
		String msg = "Clicou no botão " + num + ". x,y: " + pos[0] + "," + pos[1];
		Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onLongClick(View v) {
		if(!expanded)
			expand();
		else
			collapse();
		return true;
	}

	/**
	 * desagrupa os botões mostrando os que estão por trás
	 */
	private void expand() {
		expanded = true;
		float cib1_x = CIBs.get(1).getTranslationX(), cib1_y = CIBs.get(1).getTranslationY();
		float cib2_x = 0, cib2_y = 0;
		float cib3_x = 0, cib3_y = 0;
		float cib4_x = 0, cib4_y = 0;
		if (count > 2){
			cib2_x = CIBs.get(2).getTranslationX() - 1*HORIZONTAL_BTN_DISTANCE;
			cib2_y = CIBs.get(2).getTranslationY(); 
		}
		if (count > 3){
			cib3_x = CIBs.get(3).getTranslationX() - 2*HORIZONTAL_BTN_DISTANCE;
			cib3_y = CIBs.get(3).getTranslationY();
		}
		if (count > 4){
			cib4_x = CIBs.get(4).getTranslationX() - 3*HORIZONTAL_BTN_DISTANCE;
			cib4_y = CIBs.get(4).getTranslationY();
		}
		switch (count) {
			case 2:
				CIBs.get(1).animate().translationX(cib1_x + width + EXPAND_DISTANCE).withLayer();
				break;
			case 3:
				CIBs.get(1).animate().translationX(cib1_x + width + EXPAND_DISTANCE).
					translationY(cib1_y - height/2 - VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(2).animate().translationX(cib2_x + width + EXPAND_DISTANCE).
					translationY(cib2_y + height/2 + VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(1).invalidate();
				CIBs.get(2).invalidate();
				break;
			case 4:
				CIBs.get(1).animate().translationX(cib1_x + width + EXPAND_DISTANCE).
					translationY(cib1_y - height - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2).withLayer();
				CIBs.get(2).animate().translationX(cib2_x + width + EXPAND_DISTANCE).
					translationY(cib2_y).withLayer();
				CIBs.get(3).animate().translationX(cib3_x + width + EXPAND_DISTANCE).
					translationY(cib3_y + height + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2).withLayer();
				break;
			case 5:
				CIBs.get(1).animate().translationX(cib1_x + width + EXPAND_DISTANCE).
					translationY(cib1_y - height - height/2 - 3*VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(2).animate().translationX(cib2_x + width + EXPAND_DISTANCE).
					translationY(cib2_y - height/2 - VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(3).animate().translationX(cib3_x + width + EXPAND_DISTANCE).
					translationY(cib3_y + height/2 + VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(4).animate().translationX(cib4_x + width + EXPAND_DISTANCE).
					translationY(cib4_y + height + height/2 + 3*VERTICAL_BTN_DISTANCE).withLayer();
				break;
		}
	}

	/**
	 * Agrupa os botões mostrando apenas o que esta na frente
	 */
	private void collapse() {
		expanded = false;
		float cib1_x = CIBs.get(1).getTranslationX(), cib1_y = CIBs.get(1).getTranslationY();
		float cib2_x = 0, cib2_y = 0;
		float cib3_x = 0, cib3_y = 0;
		float cib4_x = 0, cib4_y = 0;
		if (count > 2){
			cib2_x = CIBs.get(2).getTranslationX() + 1*HORIZONTAL_BTN_DISTANCE;
			cib2_y = CIBs.get(2).getTranslationY(); 
		}
		if (count > 3){
			cib3_x = CIBs.get(3).getTranslationX() + 2*HORIZONTAL_BTN_DISTANCE;
			cib3_y = CIBs.get(3).getTranslationY();
		}
		if (count > 4){
			cib4_x = CIBs.get(4).getTranslationX() + 3*HORIZONTAL_BTN_DISTANCE;
			cib4_y = CIBs.get(4).getTranslationY();
		}
		switch (count) {
			case 2:
				CIBs.get(1).animate().translationX(cib1_x - width - EXPAND_DISTANCE).withLayer();
				break;
			case 3:
				CIBs.get(1).animate().translationX(cib1_x - width - EXPAND_DISTANCE).
					translationY(cib1_y + height/2 + VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(2).animate().translationX(cib2_x - width - EXPAND_DISTANCE).
					translationY(cib2_y - height/2 - VERTICAL_BTN_DISTANCE).withLayer();
				break;
			case 4:
				CIBs.get(1).animate().translationX(cib1_x - width - EXPAND_DISTANCE).
					translationY(cib1_y + height + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2).withLayer();
				CIBs.get(2).animate().translationX(cib2_x - width - EXPAND_DISTANCE).
					translationY(cib2_y).withLayer();
				CIBs.get(3).animate().translationX(cib3_x - width - EXPAND_DISTANCE).
					translationY(cib3_y - height - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2).withLayer();
				break;
			case 5:
				CIBs.get(1).animate().translationX(cib1_x - width - EXPAND_DISTANCE).
					translationY(cib1_y + height + height/2 + 3*VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(2).animate().translationX(cib2_x - width - EXPAND_DISTANCE).
					translationY(cib2_y + height/2 + VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(3).animate().translationX(cib3_x - width - EXPAND_DISTANCE).
					translationY(cib3_y - height/2 - VERTICAL_BTN_DISTANCE).withLayer();
				CIBs.get(4).animate().translationX(cib4_x - width - EXPAND_DISTANCE).
					translationY(cib4_y - height - height/2 - 3*VERTICAL_BTN_DISTANCE).withLayer();
				break;
		}
	}

}
