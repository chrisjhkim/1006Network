package com.bit2015.network1001.multithread;

public class Alphabet {
	public void print(){
		for( char c='a'; c<='z'; c++){
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
