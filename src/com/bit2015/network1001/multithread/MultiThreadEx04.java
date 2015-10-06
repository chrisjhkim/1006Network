package com.bit2015.network1001.multithread;

public class MultiThreadEx04 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();		//Thread 돌려야 되는 상황 생ㄱ기면 이렇게 하지말고 
		Thread thread2 = new Thread( new AlphabetRunnableImpl2());	// 이렇게 하는게 나음
		
		thread1.start();
		thread2.start();
	}
}
