package com.edp.circlebuttongrouped;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CircleImgBtnUtils implements OnClickListener{

	public static final int EXPAND_DISTANCE = 25;
	public static final int VERTICAL_BTN_DISTANCE = 5;
	public static final int HORIZONTAL_BTN_DISTANCE = 5;
	private static final int EXPANDED_COLOR = Color.parseColor("#33B5E5");
	
	private boolean expanded;
	private CircleImgBtnGroup cibg;
	private ArrayList<CircleImgBtn> CIBs;
	private float TopOutAdjust = 0, BottomOutAdjust = 0;
	private TimeoutRunnable timeoutThread;
	private int timeoutToCollapse;
	private RelativeLayout rl;
	private ImageView ivOverlay;
	
	public CircleImgBtnUtils(CircleImgBtnGroup cibg, int timeoutToCollapse) {
		this.cibg = cibg;
		CIBs = cibg.CIBs;
		this.timeoutToCollapse = timeoutToCollapse;
	}

	public void setRl(RelativeLayout rl) {
		this.rl = rl;
	}

	public boolean isExpanded() {
		return expanded;
	}

	/**
	 * @param cib
	 * @return true se o botao informado esta fora da tela saindo por cima
	 */
	public boolean isCIBOutsideTop(CircleImgBtn cib) {
		final float yPos = cib.getTop() + cib.getTranslationY();
		if(yPos < 0)
			return true;
		return false;
	}

	/**
	 * @param cib
	 * @return true se o botao informado esta fora da tela saindo por baixo
	 */
	public boolean isCIBOutsideBottom(CircleImgBtn cib) {
		final RelativeLayout parent = (RelativeLayout) cibg.getParent();
		final float yPos = cib.getTop() + cib.getTranslationY();
		if(yPos + cibg.getViewHeight() > parent.getHeight())
			return true;
		return false;
	}

	/**
	 * @param cib
	 * @param futureYPos
	 * @return true se o botao informado estara fora da tela por cima
	 */
	public boolean willBeCIBOutsideTop(CircleImgBtn cib, float futureYPos) {
		futureYPos += cib.getTop() + cib.getTranslationY();
		if(futureYPos < 0)
			return true;
		return false;
	}

	/**
	 * @param cib
	 * @param futureYPos
	 * @return true se o botao informado estara fora da tela por baixo
	 */
	public boolean willBeCIBOutsideBottom(CircleImgBtn cib, float futureYPos) {
		final RelativeLayout parent = (RelativeLayout) cibg.getParent();
		futureYPos += cib.getTop() + cib.getTranslationY();
		Log.i("out", futureYPos + " " + parent.getHeight());
		if(futureYPos + cibg.getViewHeight() > parent.getHeight())
			return true;
		return false;
	}

	/**
	 * Cria uma copia do Relative.LayoutParams
	 * @param lp
	 * @return Copy do LayoutParams
	 */
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
		if(rules[RelativeLayout.CENTER_HORIZONTAL] != 0)
			params.addRule(RelativeLayout.CENTER_HORIZONTAL, rules[RelativeLayout.CENTER_HORIZONTAL]);
		if(rules[RelativeLayout.CENTER_VERTICAL] != 0)
			params.addRule(RelativeLayout.CENTER_VERTICAL, rules[RelativeLayout.CENTER_VERTICAL]);
		params.topMargin = thisParams.topMargin;
		params.leftMargin = thisParams.leftMargin;
		params.rightMargin = thisParams.rightMargin;
		params.bottomMargin = thisParams.bottomMargin;
		return params;
	}

	/**
	 * desagrupa os botoes mostrando os que estao por tras
	 */
	void expand(boolean inverted) {
		if(cibg.getButtomsCount() < 2)
			return;
		expanded = true;
		showOverlay(true);
		if(timeoutToCollapse > 0){
			timeoutThread = new TimeoutRunnable(timeoutToCollapse, cibg, new Handler());
			timeoutThread.start();
		}
		cibg.setBorderColor(EXPANDED_COLOR);
		TopOutAdjust = BottomOutAdjust = 0;
		//instancias de botoes
		final CircleImgBtn cib1 = CIBs.get(1);
		final CircleImgBtn cib2 = (cibg.getButtomsCount() > 2) ? CIBs.get(2) : null;
		final CircleImgBtn cib3 = (cibg.getButtomsCount() > 3) ? CIBs.get(3) : null;
		final CircleImgBtn cib4 = (cibg.getButtomsCount() > 4) ? CIBs.get(4) : null;
		final CircleImgBtn cib5 = (cibg.getButtomsCount() > 5) ? CIBs.get(5) : null;
		final int cibWidth = cibg.getViewWidth();
		final int cibHeight = cibg.getViewHeight();
		//posicao inicial dos botoes
		int inv = (inverted)?-1:1;
		float cib1_x = cib1.getTranslationX(), cib2_x = 0, cib3_x = 0, cib4_x = 0, cib5_x = 0;
		if (cibg.getButtomsCount() > 2)
			cib2_x = cib2.getTranslationX() - 1*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 3)
			cib3_x = cib3.getTranslationX() - 2*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 4)
			cib4_x = cib4.getTranslationX() - 3*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 5)
			cib5_x = cib5.getTranslationX() - 4*HORIZONTAL_BTN_DISTANCE;
		//posicao final dos botoes e ajustes se algum saiu da tela
		float cib1_y_End = 0, cib2_y_End = 0, cib3_y_End = 0, cib4_y_End = 0, cib5_y_End = 0;
		switch (cibg.getButtomsCount()) {
			case 2:
				cib1.animate().translationX(inv*(cib1_x + cibWidth + EXPAND_DISTANCE))
					;
				break;
			case 3:
				cib1_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				cib2_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? -cib1_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib2, cib2_y_End) ? -cib2_y_End : 0;
				cib1.animate().translationX(inv*(cib1_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust);
				cib2.animate().translationX(inv*(cib2_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust);
				cib1.invalidate();
				cib2.invalidate();
				break;
			case 4:
				cib1_y_End = -cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2;
				cib2_y_End = 0;
				cib3_y_End = +cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? -cib1_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib3, cib3_y_End) ? -cib3_y_End : 0;
				cib1.animate().translationX(inv*(cib1_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust);
				cib2.animate().translationX(inv*(cib2_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust);
				cib3.animate().translationX(inv*(cib3_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib3_y_End + TopOutAdjust + BottomOutAdjust);
				break;
			case 5:
				cib1_y_End = -cibHeight - cibHeight/2 - 3*VERTICAL_BTN_DISTANCE;
				cib2_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				cib3_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				cib4_y_End = +cibHeight + cibHeight/2 + 3*VERTICAL_BTN_DISTANCE;
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? 
						willBeCIBOutsideTop(cib2, cib2_y_End) ? -cib1_y_End : -cib2_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib4, cib4_y_End) ? 
						willBeCIBOutsideBottom(cib3, cib3_y_End) ? -cib4_y_End : -cib4_y_End : 0;
				cib1.animate().translationX(inv*(cib1_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust);
				cib2.animate().translationX(inv*(cib2_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust);
				cib3.animate().translationX(inv*(cib3_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib3_y_End + TopOutAdjust + BottomOutAdjust);
				cib4.animate().translationX(inv*(cib4_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib4_y_End + TopOutAdjust + BottomOutAdjust);
				break;
			case 6:
				cib1_y_End = 2*(-cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2); 
				cib2_y_End = -cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2;
				cib3_y_End = 0;
				cib4_y_End = +cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2;
				cib5_y_End = 2*(+cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2);
				TopOutAdjust = willBeCIBOutsideTop(cib1, cib1_y_End) ? 
						willBeCIBOutsideTop(cib2, cib2_y_End) ? -cib1_y_End : -cib2_y_End : 0;
				BottomOutAdjust = willBeCIBOutsideBottom(cib5, cib5_y_End) ? 
						willBeCIBOutsideBottom(cib4, cib4_y_End) ? -cib5_y_End : -cib4_y_End : 0;
				cib1.animate().translationX(inv*(cib1_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib1_y_End + TopOutAdjust + BottomOutAdjust);
				cib2.animate().translationX(inv*(cib2_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib2_y_End + TopOutAdjust + BottomOutAdjust);
				cib3.animate().translationX(inv*(cib3_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib3_y_End + TopOutAdjust + BottomOutAdjust);
				cib4.animate().translationX(inv*(cib4_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib4_y_End + TopOutAdjust + BottomOutAdjust);
				cib5.animate().translationX(inv*(cib5_x + cibWidth + EXPAND_DISTANCE)).
					translationY(cib5_y_End + TopOutAdjust + BottomOutAdjust);
				break;
		}
	}

	/**
	 * Agrupa os botoes mostrando apenas o que esta na frente
	 */
	void collapse(boolean inverted) {
		if(cibg.getButtomsCount() < 2)
			return;
		expanded = false;
		showOverlay(false);
		if(timeoutThread != null)
			timeoutThread.stopTimeout();
		cibg.resetBorderColor();
		//instancias de botoes
		final CircleImgBtn cib1 = CIBs.get(1);
		final CircleImgBtn cib2 = (cibg.getButtomsCount() > 2) ? CIBs.get(2) : null;
		final CircleImgBtn cib3 = (cibg.getButtomsCount() > 3) ? CIBs.get(3) : null;
		final CircleImgBtn cib4 = (cibg.getButtomsCount() > 4) ? CIBs.get(4) : null;
		final CircleImgBtn cib5 = (cibg.getButtomsCount() > 5) ? CIBs.get(5) : null;
		final int cibWidth = cibg.getViewWidth();
		final int cibHeight = cibg.getViewHeight();
		//posicao inicial dos botoes
		int inv = (inverted)?-1:1;
		float cib1_x = inv*(cib1.getTranslationX()), cib2_x = 0, cib3_x = 0, cib4_x = 0, cib5_x = 0;
		if (cibg.getButtomsCount() > 2)
			cib2_x = inv*cib2.getTranslationX() + 1*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 3)
			cib3_x = inv*cib3.getTranslationX() + 2*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 4)
			cib4_x = inv*cib4.getTranslationX() + 3*HORIZONTAL_BTN_DISTANCE;
		if (cibg.getButtomsCount() > 5)
			cib5_x = inv*cib5.getTranslationX() + 4*HORIZONTAL_BTN_DISTANCE;
		//posicao final dos botoes e ajustes se algum saiu da tela
		float cib1_y_End = 0, cib2_y_End = 0, cib3_y_End = 0, cib4_y_End = 0, cib5_y_End = 0;
		switch (cibg.getButtomsCount()) {
			case 2:
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE);
				break;
			case 3:
				cib1_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				cib2_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				if(TopOutAdjust != 0){ 
					cib1_y_End -= TopOutAdjust;
					cib2_y_End -= TopOutAdjust;
				}
				if(BottomOutAdjust != 0){
					cib1_y_End -= BottomOutAdjust;
					cib2_y_End -= BottomOutAdjust;
				}
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1.getTranslationY() + cib1_y_End);
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2.getTranslationY() + cib2_y_End);
				break;
			case 4:
				cib1_y_End = +cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2;
				cib2_y_End = 0;
				cib3_y_End = -cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2;
				if(TopOutAdjust != 0){ 
					cib1_y_End -= TopOutAdjust;
					cib2_y_End -= TopOutAdjust;
					cib3_y_End -= TopOutAdjust;
				}
				if(BottomOutAdjust != 0){
					cib1_y_End -= BottomOutAdjust;
					cib2_y_End -= BottomOutAdjust;
					cib3_y_End -= BottomOutAdjust;
				}
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1.getTranslationY() + cib1_y_End);
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2.getTranslationY() + cib2_y_End);
				cib3.animate().translationX(cib3_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib3.getTranslationY() + cib3_y_End);
				break;
			case 5:
				cib1_y_End = +cibHeight + cibHeight/2 + 3*VERTICAL_BTN_DISTANCE;
				cib2_y_End = +cibHeight/2 + VERTICAL_BTN_DISTANCE;
				cib3_y_End = -cibHeight/2 - VERTICAL_BTN_DISTANCE;
				cib4_y_End = -cibHeight - cibHeight/2 - 3*VERTICAL_BTN_DISTANCE;
				if(TopOutAdjust != 0){ 
					cib1_y_End -= TopOutAdjust;
					cib2_y_End -= TopOutAdjust;
					cib3_y_End -= TopOutAdjust;
					cib4_y_End -= TopOutAdjust;
				}
				if(BottomOutAdjust != 0){
					cib1_y_End -= BottomOutAdjust;
					cib2_y_End -= BottomOutAdjust;
					cib3_y_End -= BottomOutAdjust;
					cib4_y_End -= BottomOutAdjust;
				}
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1.getTranslationY() + cib1_y_End);
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2.getTranslationY() + cib2_y_End);
				cib3.animate().translationX(cib3_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib3.getTranslationY() + cib3_y_End);
				cib4.animate().translationX(cib4_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib4.getTranslationY() + cib4_y_End);
				break;
			case 6:
				cib1_y_End = 2*(+cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2);
				cib2_y_End = +cibHeight + VERTICAL_BTN_DISTANCE + VERTICAL_BTN_DISTANCE/2;
				cib3_y_End = 0;
				cib4_y_End = -cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2;
				cib5_y_End = 2*(-cibHeight - VERTICAL_BTN_DISTANCE - VERTICAL_BTN_DISTANCE/2);
				if(TopOutAdjust != 0){ 
					cib1_y_End -= TopOutAdjust;
					cib2_y_End -= TopOutAdjust;
					cib3_y_End -= TopOutAdjust;
					cib4_y_End -= TopOutAdjust;
					cib5_y_End -= TopOutAdjust;
				}
				if(BottomOutAdjust != 0){
					cib1_y_End -= BottomOutAdjust;
					cib2_y_End -= BottomOutAdjust;
					cib3_y_End -= BottomOutAdjust;
					cib4_y_End -= BottomOutAdjust;
					cib5_y_End -= BottomOutAdjust;
				}
				cib1.animate().translationX(cib1_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib1.getTranslationY() + cib1_y_End);
				cib2.animate().translationX(cib2_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib2.getTranslationY() + cib2_y_End);
				cib3.animate().translationX(cib3_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib3.getTranslationY() + cib3_y_End);
				cib4.animate().translationX(cib4_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib4.getTranslationY() + cib4_y_End);
				cib5.animate().translationX(cib5_x - cibWidth - EXPAND_DISTANCE).
					translationY(cib5.getTranslationY() + cib5_y_End);
				break;
		}
	}

	public ImageView createOverlay() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		ImageView ivOverlay = new ImageView(rl.getContext());
		ivOverlay.setLayoutParams(params);
		ivOverlay.setOnClickListener(this);
		ivOverlay.setBackgroundColor(Color.TRANSPARENT);
		return ivOverlay;
	}

	public void showOverlay(boolean show){
		if(ivOverlay == null)
			ivOverlay = createOverlay();
		if(show){
			rl.addView(ivOverlay);
			reDrawCIBs();
		}else
			rl.removeView(ivOverlay);
	}

	private void reDrawCIBs() {
		rl.removeView(cibg);
		for (int i=CIBs.size()-1; i>0; i--) {
			CircleImgBtn cib = CIBs.get(i);
			rl.removeView(cib);
		}
		cibg.drawCIBs(rl);
		rl.addView(cibg);
	}

	@Override
	public void onClick(View v) {
		if(v == ivOverlay)
			cibg.collapse();
	}
	
}
