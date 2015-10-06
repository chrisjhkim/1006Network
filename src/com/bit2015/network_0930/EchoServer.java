package com.bit2015.network_0930;

import java.util.concurrent.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("unused")
public class EchoServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
//		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 10001);
		
		
		
		
		
		
		try {
			serverSocket = new ServerSocket();
			
//			serverSocket.bind(new InetSocketAddress("localhost", 10001));
			serverSocket.bind(new InetSocketAddress("192.168.1.106", 10001));
			
			System.out.println("accept()");
			Socket socket = serverSocket.accept();
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			
			System.out.println("[서버] 연결됨 from "+
								"\n HostName : " +inetSocketAddress.getHostName()+
								"\n Address  : " +inetSocketAddress.getAddress()+
								"\n Port     : " +inetSocketAddress.getPort()+
								"\n toString : " +inetSocketAddress.toString() );
			
			OutputStream os = socket.getOutputStream();
			
			String strData = "HELLO JAVA NETWORK!!";
			
			os.write(strData.getBytes("UTF-8"));
			
			socket.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally{
			if(serverSocket != null && (serverSocket.isClosed() == false) ){
				try{
					serverSocket.close();
				}catch( IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
