package com.edp.circlebuttongrouped;

public interface OnCircleButtonClickListener {

	/**
	 * Evento de clique simples em um bot�o circular 
	 * @param cib = view do bot�o circular
	 * @param res = image resource do bot�o circular
	 */
	public void onClick(CircleImgBtn cib, int res);

	/**
	 * Evento de clique no grupo
	 * @param circleImgBtnGroup = Grupo clicado
	 * @param cibImageRes = ImageResource em cima
	 */
	public void onClickQuickAction(CircleImgBtnGroup circleImgBtnGroup, int cibImageRes);
	
}
