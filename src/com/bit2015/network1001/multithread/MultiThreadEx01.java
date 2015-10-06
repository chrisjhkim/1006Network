/**
 * thread.start()는 일반적인 thread의 목적인 동시실행을 가능하게함.
 * thread.start 는 한번밖에 사용못함. 
 * 다시 사용하려면 Thread thread2 = new DigitThread(); 같이 다른거 만들어서 해야하는듯
 * DigitThread내부에서 thread.run()의 부분이 main문의 thread.start 와 같음
 * 
 * main문에서 thread.run()을 하면 그냥 블락걸고 method실행하는것과 같이 됨.
 * 
 * thread.start 돌아가는중에
 * main문에서 thread.run()돌리는것은 가능
 * (쓸모없긴함)
 * 
 * 
 * 
 * 
 */

package com.bit2015.network1001.multithread;

public class MultiThreadEx01 {

	public static void main(String[] args) {
		
		Thread thread = new DigitThread();
		thread.start();
		
		
//		for (int i=0 ; i<10 ; i++){
//			System.out.print(i);
//		}
		for (char c = 'A';  c<='Z'  ; c++){
			System.out.print(c);
			try {
				Thread.sleep(500);
				if(c == 'F'){
					thread.run();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread.run();
	}
}
