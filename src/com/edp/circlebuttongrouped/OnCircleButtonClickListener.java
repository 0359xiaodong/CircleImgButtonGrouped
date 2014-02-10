package com.edp.circlebuttongrouped;

public interface OnCircleButtonClickListener {

	/**
	 * Evento de clique simples em um botao circular 
	 * @param cib = view do botao circular
	 * @param res = image resource do botao circular
	 */
	public void onClick(CircleImgBtn cib, int res);

	/**
	 * Evento de clique no grupo
	 * @param circleImgBtnGroup = Grupo clicado
	 * @param cibImageRes = ImageResource em cima
	 */
	public void onClickQuickAction(CircleImgBtnGroup circleImgBtnGroup, int cibImageRes);
	
}
