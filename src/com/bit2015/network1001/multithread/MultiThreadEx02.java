package com.bit2015.network1001.multithread;

public class MultiThreadEx02 {
	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		Thread thread3 = new DigitThread();
		
		System.out.println("Main");
		thread1.start();
		thread2.start();
		thread3.start();
		System.out.println("HAHA");
	}

}
