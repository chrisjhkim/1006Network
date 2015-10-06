package com.bit2015.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			System.out.println( inetAddress.getHostName());
			System.out.println( inetAddress.getHostAddress());
			
			byte[] addresses = inetAddress.getAddress();

			String aaa ="";
			for( int i = 0 ; i <addresses.length ; i++){
				//int address = (int)addresses[i];
				int address = addresses[i] & 0xff;
				aaa += addresses[i];
				System.out.println(	addresses[i]	);
				System.out.println(	address	);
				
			}
			//System.out.println(	((int)aaa) );	//java에서는 int로 형변환이 안되네???
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		
		
	}

}
