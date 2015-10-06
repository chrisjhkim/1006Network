package com.bit2015.network1001.multithread;

public class MultiThreadEx03 {
	public static void main(String[] args) {
		Thread thread1 = new DigitThread();
		Thread thread2 = new Thread( new AlphabetRunnableImpl() );
		Alphabet thread3 = new Alphabet();
		thread1.start();
		thread2.start();
		thread3.print();
		
	}

}
