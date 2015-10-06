package com.bit2015.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try{
			Scanner scanner = new Scanner( System.in );				//표준 입력 : 키보드 / 표준 출력 : 화면
			
			
			while(true){
				System.out.print( ">");
				String host = scanner.nextLine();
				
				if( "exit".equals(host)==true ){				//host.equals("exit") 보다 이렇게 쓰는게 좋음. host.으로 하면host 가 null 인경우 nullPointerException 생김
					break;
				}
				InetAddress[] inetAddresses =
						InetAddress.getAllByName(host);
				for( int i=0 ; i<inetAddresses.length ; i++){
					System.out.println(
							inetAddresses[i].getHostName() +
							":"+
							inetAddresses[i].getHostAddress()	);
	//				System.out.println(inetAddresses[i].getHostName());
	//				System.out.println(inetAddresses[i].getHostAddress());
	//				System.out.println(inetAddresses.toString());
				}
			}
			
			scanner.close();
			
		}catch( UnknownHostException ex){
			System.out.println("IP를 가져올 수 없습니다.");
			ex.printStackTrace();
		}
		
		
	}
}
