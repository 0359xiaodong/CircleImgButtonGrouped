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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class CircleImgBtnGroup extends CircleImgBtn implements OnClickListener, OnLongClickListener{

	private RelativeLayout rl;
	private int height, width, count;
	private TypedArray attribs;
	private boolean collapseAtClick;
	private CircleImgBtnUtils cibUtils;
	ArrayList<CircleImgBtn> CIBs = new ArrayList<CircleImgBtn>();

	@SuppressLint("Recycle")
	public CircleImgBtnGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		cibUtils = new CircleImgBtnUtils(this);
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
	 * @param i = numero do botao
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
			count = 6;
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
		this.rl = rl;
		//coloca iniciando do ultimo para q o primeiro fique no topo e no canto esquerdo
		for (int i=CIBs.size()-1; i>0; i--) {
			CircleImgBtn cib = CIBs.get(i);
			RelativeLayout.LayoutParams params = cibUtils.
					getRelativeLayoutParamsCopy((LayoutParams) getLayoutParams());
			params.leftMargin += i*CircleImgBtnUtils.HORIZONTAL_BTN_DISTANCE;
			cib.setLayoutParams(params);
			rl.addView(cib);
		}
		bringToFront();
	}

	@Override
	public void onClick(View v) {
		if(cibUtils.isExpanded() && collapseAtClick & v.equals(this)){
			cibUtils.collapse();
		}
		int num = CIBs.indexOf(v);
		float top = CIBs.get(num).getTop() + CIBs.get(num).getTranslationY();
		float left = CIBs.get(num).getLeft() + CIBs.get(num).getTranslationX();
		String msg = "Clicou no botão " + num
				+ ". x,y: " + left + "," + top;
		Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onLongClick(View v) {
		if(v != this){
			RelativeLayout.LayoutParams params = (LayoutParams) CIBs.get(1).getLayoutParams();
			params.topMargin -= 5;
			CIBs.get(1).setLayoutParams(params);
			return true;
		}
		if(!cibUtils.isExpanded()){
			collapseAllOthers();
			cibUtils.expand();
		}else
			cibUtils.collapse();
		return true;
	}

	public void expand(){
		if(!cibUtils.isExpanded())
			cibUtils.expand();
	}
	
	public void collapse(){
		if(cibUtils.isExpanded())
			cibUtils.collapse();
	}
	
	private void collapseAllOthers() {
		for(int i=0; i<rl.getChildCount(); i++)
			if(rl.getChildAt(i) instanceof CircleImgBtnGroup){
				CircleImgBtnGroup cibg = (CircleImgBtnGroup)rl.getChildAt(i);
				cibg.collapse();
			}
	}

}