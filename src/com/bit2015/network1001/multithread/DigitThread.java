package com.bit2015.network1001.multithread;

public class DigitThread extends Thread{
	

	public void run(){
		for(int ont =0 ; ont<10 ; ont++){
			System.out.print(ont);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	@Override
//	public synchronized void start() {
//		for (int i=0 ; i<10 ; i++){
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.print(i);
//		}
//	}
	
}
