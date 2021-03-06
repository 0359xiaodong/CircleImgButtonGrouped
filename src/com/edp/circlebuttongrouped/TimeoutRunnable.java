package com.edp.circlebuttongrouped;

import android.os.Handler;

public class TimeoutRunnable extends Thread {

	private int timeout;
	private boolean running;
	private CircleImgBtnGroup cibg;
	private Handler handler;
	
	public TimeoutRunnable(int timeout, CircleImgBtnGroup cibg, Handler handler) {
		super();
		this.timeout = timeout;
		this.cibg = cibg;
		this.handler = handler;
	}

	@Override
	public void run() {
		running = true;
		int count = 0;
		while(count < timeout){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
			if(running){
				count++;	
			}else{
				return;	
			}
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				cibg.collapse();
			}
		});
	}
	
	public void stopTimeout(){
		running = false;
	}

	public int getTimeout() {
		return timeout;
	}
	
}
