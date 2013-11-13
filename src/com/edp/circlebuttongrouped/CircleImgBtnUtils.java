package com.edp.circlebuttongrouped;

import java.util.ArrayList;

import android.widget.RelativeLayout;

public class CircleImgBtnUtils {

	public static final int EXPAND_DISTANCE = 25;
	public static final int VERTICAL_BTN_DISTANCE = 5;
	public static final int HORIZONTAL_BTN_DISTANCE = 5;
	
	private boolean expanded;
	private CircleImgBtnGroup cibg;
	private ArrayList<CircleImgBtn> CIBs;
	
	public CircleImgBtnUtils(CircleImgBtnGroup cibg) {
		this.cibg = cibg;
		CIBs = cibg.CIBs;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public boolean isCIBOutsideTop(CircleImgBtn cib) {
		final float yPos = cib.getTop() + cib.getTranslationY();
		if(yPos < 0)
			return true;
		return false;
	}

	public boolean isCIBOutsideBottom(CircleImgBtn cib) {
		final RelativeLayout parent = (RelativeLayout) cibg.getParent();
		final float yPos = cib.getTop() + cib.getTranslationY();
		if(yPos + cibg.getViewHeight() > parent.getHeight())
			return true;
		return false;
	}

	public boolean willBeCIBOutsideTop(CircleImgBtn cib, float yPos) {
		yPos += cib.getTop() + cib.getTranslationY();
		if(yPos < 0)
			return true;
		return false;
	}

	public boolean willBeCIBOutsideBottom(CircleImgBtn cib, float yPos) {
		final RelativeLayout parent = (RelativeLayout) cibg.getParent();
		yPos += cib.getTop() + cib.getTranslationY();
		if(yPos + cibg.getViewHeight() > parent.getHeight())
			return true;
		return false;
	}

	public RelativeLayout.LayoutParams getRelativeLayoutParamsCopy(RelativeLayout.LayoutParams lp) {
		RelativeLayout.LayoutParams thisParams = lp;
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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

	/**
	 * desagrupa os botões mostrando os que estão por trás
	 */
	void expand() {
		expanded = true;
		//instancias de botões
		final CircleImgBtn cib1 = CIBs.get(1);
		final CircleImgBtn cib2 = (cibg.getButtomsCount() > 2) ? CIBs.get(2) : null;
		final CircleImgBtn cib3 = (cibg.getButtomsCount() > 3) ? CIBs.get(3) : null;
		final CircleImgBtn cib4 = (cibg.getButtomsCount() > 4) ? CIBs.get(4) : null;
		final int cibWidth = cibg.getViewWidth();
		final int cibHeight = cibg.getViewHeight();
		//posicao inicial dos botões
		float cib1_x = cib1.getTranslationX(), cib2_x = 0, cib3_x = 0, cib4_x = 0;
		if (cibg.getButtomsCount() > 2)
			cib2_x = cib2.getTranslationX() - 1*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 3)
			cib3_x = cib3.getTranslationX() - 2*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 4)
			cib4_x = cib4.getTranslationX() - 3*HORIZONTAL_BTN_DISTANCE;
		//posição final dos botões e ajustes se algum saiu da tela
		float cib1_y_End = 0, cib2_y_End = 0, cib3_y_End = 0, cib4_y_End = 0;
		float TopOutAdjust = 0, BottomOutAdjust = 0;
		switch (cibg.getButtomsCount()) {
			case 2:
				cib1.animate().translationX(cib1_x + cibWidth + EXPAND_DISTANCE).withLayer();
				break;
			case 3:
				cib1_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				cib2_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? -cib1_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib2, cib2_y_End) ? -cib2_y_End : 0;
				cib1.animate().translationX(cib1_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib2.animate().translationX(cib2_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib1.invalidate();
				cib2.invalidate();
				break;
			case 4:
				cib1_y_End = -cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2;
				cib3_y_End = +cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? -cib1_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib3, cib3_y_End) ? -cib3_y_End : 0;
				cib1.animate().translationX(cib1_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib2.animate().translationX(cib2_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib3.animate().translationX(cib3_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib3_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				break;
			case 5:
				cib1_y_End = -cibHeight - cibHeight/2 - 3*VERTICAL_BTN_DISTANCE;
				cib2_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				cib3_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				cib4_y_End = +cibHeight + cibHeight/2 + 3*VERTICAL_BTN_DISTANCE;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? -cib1_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib4, cib4_y_End) ? -cib4_y_End : 0;
				cib1.animate().translationX(cib1_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib2.animate().translationX(cib2_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib3.animate().translationX(cib3_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib3_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				cib4.animate().translationX(cib4_x + cibWidth + EXPAND_DISTANCE).
					translationY(cib4_y_End + TopOutAdjust + BottomOutAdjust).withLayer();
				break;
		}
	}

	/**
	 * Agrupa os botões mostrando apenas o que esta na frente
	 */
	void collapse() {
		expanded = false;
		final CircleImgBtn cib1 = CIBs.get(1);
		final CircleImgBtn cib2 = (cibg.getButtomsCount() > 2) ? CIBs.get(2) : null;
		final CircleImgBtn cib3 = (cibg.getButtomsCount() > 3) ? CIBs.get(3) : null;
		final CircleImgBtn cib4 = (cibg.getButtomsCount() > 4) ? CIBs.get(4) : null;
		final int cibWidth = cibg.getViewWidth();
		final int cibHeight = cibg.getViewHeight();
		float cib1_x = cib1.getTranslationX(), cib1_y = cib1.getTranslationY();
		float cib2_x = 0, cib2_y = 0;
		float cib3_x = 0, cib3_y = 0;
		float cib4_x = 0, cib4_y = 0;
		if (cibg.getButtomsCount() > 2){
			cib2_x = cib2.getTranslationX() + 1*HORIZONTAL_BTN_DISTANCE;
			cib2_y = cib2.getTranslationY(); 
		}
		if (cibg.getButtomsCount() > 3){
			cib3_x = cib3.getTranslationX() + 2*HORIZONTAL_BTN_DISTANCE;
			cib3_y = cib3.getTranslationY();
		}
		if (cibg.getButtomsCount() > 4){
			cib4_x = cib4.getTranslationX() + 3*HORIZONTAL_BTN_DISTANCE;
			cib4_y = cib4.getTranslationY();
		}
		switch (cibg.getButtomsCount()) {
			case 2:
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).withLayer();
				break;
			case 3:
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1_y + cibHeight/2 + VERTICAL_BTN_DISTANCE).withLayer();
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2_y - cibHeight/2 - VERTICAL_BTN_DISTANCE).withLayer();
				break;
			case 4:
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1_y + cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2).withLayer();
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2_y).withLayer();
				cib3.animate().translationX(cib3_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib3_y - cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2).withLayer();
				break;
			case 5:
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1_y + cibHeight + cibHeight/2 + 3*VERTICAL_BTN_DISTANCE).withLayer();
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2_y + cibHeight/2 + VERTICAL_BTN_DISTANCE).withLayer();
				cib3.animate().translationX(cib3_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib3_y - cibHeight/2 - VERTICAL_BTN_DISTANCE).withLayer();
				cib4.animate().translationX(cib4_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib4_y - cibHeight - cibHeight/2 - 3*VERTICAL_BTN_DISTANCE).withLayer();
				break;
		}
	}

}
