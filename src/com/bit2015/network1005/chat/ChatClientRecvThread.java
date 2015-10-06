package com.bit2015.network1005.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ChatClientRecvThread extends Thread{

	
	
	
	private BufferedReader bufferedReader;

	public ChatClientRecvThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		
		
		try {
			
			
			// 3. 요청 처리
			while(true){
				String strReadBuff = bufferedReader.readLine();
				if( strReadBuff == null){	//Client 연결 끊김
					ChatServer.log("서버로부터 연결 끊김");
					break;
				}
				System.out.println(strReadBuff);
				
				
				
			}
			// 4. 자원 정리
		} catch (IOException ex) {
			ChatServer.log("ERRor@1 : "+ex);							//static way 로 접근
		}
	}

}
