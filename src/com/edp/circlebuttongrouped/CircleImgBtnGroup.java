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
import android.widget.TextView;
import android.widget.Toast;

public class CircleImgBtnGroup extends CircleImgBtn 
		implements OnClickListener, OnLongClickListener{

	private RelativeLayout rl;
	private int height, width, count, timeoutToCollapse;
	private TypedArray attribs;
	private boolean collapseAtClick, sendBackAtClick, inverted;
	private CircleImgBtnUtils cibUtils;
	private TextView lbGroupLabel;
	private OnCircleButtonClickListener onClickListener;
	ArrayList<CircleImgBtn> CIBs = new ArrayList<CircleImgBtn>();
	private ArrayList<RelativeLayout> othersRl = new ArrayList<RelativeLayout>();

	@SuppressLint("Recycle")
	public CircleImgBtnGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		attribs = context.obtainStyledAttributes(attrs, R.styleable.CircleImgBtnGroup);
		collapseAtClick = attribs.getBoolean(R.styleable.CircleImgBtnGroup_collapseAtClick, true);
		sendBackAtClick = attribs.getBoolean(R.styleable.CircleImgBtnGroup_sendBackAtClick, true);
		timeoutToCollapse = attribs.getInteger(R.styleable.CircleImgBtnGroup_timeoutToCollapse, 5);
		cibUtils = new CircleImgBtnUtils(this, timeoutToCollapse);
		configMainImgBtn();
		float dimHeight = attribs.getDimension(R.styleable.CircleImgBtnGroup_ibHeight, 30f);
		float dimWidth = attribs.getDimension(R.styleable.CircleImgBtnGroup_ibWidth, 30f);
		setHeight(Math.round(dimHeight));
		setWidth(Math.round(dimWidth));
		setButtomsCount(attribs.getInt(R.styleable.CircleImgBtnGroup_ibCount, 3));
		setGroupLabel(attribs.getString(R.styleable.CircleImgBtnGroup_label));
		inverted = attribs.getBoolean(R.styleable.CircleImgBtnGroup_inverted, false);
	}

	/**
	 * Configura o botao da frente que representa este componente
	 */
	private void configMainImgBtn() {
		final int imgID = getImgCIVs(0);
		setImageResource(imgID);
		setTag(imgID);
		addShadow();
		lbGroupLabel = new TextView(getContext());
		lbGroupLabel.setText("");
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
			case 5: return attribs.getResourceId(R.styleable.CircleImgBtnGroup_imgSrcCIB6, def);
		}
		return def;
	}
	
	public CircleImgBtn getButtom(int i) throws NullPointerException{
		if(i < CIBs.size())
			return CIBs.get(i);
		else 
			return null;
	}
	
	public CircleImgBtn getFrontButton(){
		return CIBs.get(0);
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

	public void setGroupLabel(String label){
		lbGroupLabel.setText(label);
	}
	/**
	 * configura a qtd de botoes e os instancia
	 * @param count
	 */
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
			final int imgID = getImgCIVs(i);
			circleImgBtn.setImageResource(imgID);
			circleImgBtn.setTag(imgID);
			circleImgBtn.setOnClickListener(this);
			CIBs.add(circleImgBtn);
		}
	}

	/**
	 * Posiciona os botoes no relativelayout informado 
	 * @param rl - RelativeLayout o qual serao pai dos botoes agrupados
	 */
	public CircleImgBtnGroup configGroup(OnCircleButtonClickListener cl, RelativeLayout rl){
		if(this.rl != null)
			return this;
		this.rl = rl;
		cibUtils.setRl(rl);
		configCollapseAfterOutsiteClick(rl);
		onClickListener = cl;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_LEFT, this.getId());
		params.addRule(RelativeLayout.ALIGN_BOTTOM, this.getId());
		final float lbGroupWidth = lbGroupLabel.getPaint().measureText(lbGroupLabel.getText().toString());
		int leftMargin = getViewWidth()/2 - (int)lbGroupWidth/3;
		params.setMargins(leftMargin, 0, 0, -15);
		lbGroupLabel.setLayoutParams(params);
		rl.addView(lbGroupLabel);
		//coloca iniciando do ultimo para q o primeiro fique no topo e no canto esquerdo
		drawCIBs(rl);
		bringToFront();
		return this;
	}

	void drawCIBs(RelativeLayout rl) {
		RelativeLayout.LayoutParams params;
		for (int i=CIBs.size()-1; i>0; i--) {
			CircleImgBtn cib = CIBs.get(i);
			params = cibUtils.getRelativeLayoutParamsCopy(
					(RelativeLayout.LayoutParams) getLayoutParams());
			if(inverted){
				params.leftMargin -= i*CircleImgBtnUtils.HORIZONTAL_BTN_DISTANCE;
				params.rightMargin += i*CircleImgBtnUtils.HORIZONTAL_BTN_DISTANCE;
			}else{
				params.leftMargin += i*CircleImgBtnUtils.HORIZONTAL_BTN_DISTANCE;
				params.rightMargin -= i*CircleImgBtnUtils.HORIZONTAL_BTN_DISTANCE;
			}
			cib.setLayoutParams(params);
			rl.addView(cib);
		}
	}

	/**
	 * Configure focus listener to be possible collapse after click outside buttom
	 * @param rl
	 */
	private void configCollapseAfterOutsiteClick(RelativeLayout rl) {
		rl.setFocusable(true);
		rl.setFocusableInTouchMode(true);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	void showInfo(View v) {
		int num = CIBs.indexOf(v);
		float top = CIBs.get(num).getTop() + CIBs.get(num).getTranslationY();
		float left = CIBs.get(num).getLeft() + CIBs.get(num).getTranslationX();
		String msg = "Clicou no botao " + num
				+ ". x,y: " + left + "," + top;
		Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
//		showInfo(v);
		CircleImgBtn cib = (CircleImgBtn)v;
		//Comprima se expandido
		if(cibUtils.isExpanded()){
			if(onClickListener != null){
				onClickListener.onClick(cib, cib.getImageResource());
			}
			if(collapseAtClick){
				cibUtils.collapse(inverted);
			}
		}else{
			onClickListener.onClickQuickAction(this, cib.getImageResource());
		}
		if(sendBackAtClick){
			bringCIBToFront(cib.getImageResource());
			sendCIBToBack();
		}
	}

	public void bringCIBToFront(int res) {
		if(CIBs.size() >= 2){
			while(CIBs.get(0).getImageResource() != res)
				swapImages();
			invalidate();
		}
	}

	private void sendCIBToBack() {
		if(CIBs.size() >= 2){
			swapImages();
			invalidate();
		}
	}

	private void swapImages() {
		int firstImg = CIBs.get(0).getImageResource();
		for (int i=0; i<CIBs.size()-1; i++){
			int proxImg = CIBs.get(i+1).getImageResource();
			CIBs.get(i).setImageResource(proxImg);
		}
		CIBs.get(CIBs.size()-1).setImageResource(firstImg);
	}

	@Override
	public boolean onLongClick(View v) {
		if(!cibUtils.isExpanded()){
			collapseAllOthers();
			cibUtils.expand(inverted);
		}else
			cibUtils.collapse(inverted);
		return true;
	}

	public void expand(){
		if(!cibUtils.isExpanded()){
			cibUtils.expand(inverted);
		}
	}
	
	public void collapse(){
		if(cibUtils.isExpanded()){
			cibUtils.collapse(inverted);
		}
	}
	
	public void addRLayoutWithOtherGroups(RelativeLayout rl){
		othersRl.add(rl);
	}
	
	private void collapseAllOthers() {
		//colapse para os CIBGs desse layout
		for(int i=0; i<rl.getChildCount(); i++)
			if(rl.getChildAt(i) instanceof CircleImgBtnGroup){
				CircleImgBtnGroup cibg = (CircleImgBtnGroup)rl.getChildAt(i);
				cibg.collapse();
			}
		//colapse para os CIBGs de outros layouts
		for (RelativeLayout oRl : othersRl)
			for(int i=0; i<oRl.getChildCount(); i++)
				if(oRl.getChildAt(i) instanceof CircleImgBtnGroup){
					CircleImgBtnGroup cibg = (CircleImgBtnGroup)oRl.getChildAt(i);
					cibg.collapse();
				}
	}

}