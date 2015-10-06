package com.bit2015.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	public static void main(String[] args) {
		
		Socket socketNew  = new Socket();

		try{
			System.out.println("[클라이언트 연결 요청]");
			socketNew.connect(new InetSocketAddress("192.168.1.106", 10001));
			System.out.println("[클라이언트 연결 성공]");
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(  socketNew != null && (socketNew.isClosed() == false)){
				try{
					socketNew.close();
				}catch (IOException ex){
					ex.printStackTrace();
				}
				
			}
		}
		
	}

}
