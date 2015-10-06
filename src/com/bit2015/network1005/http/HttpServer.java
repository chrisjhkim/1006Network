package com.bit2015.network1005.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bit2015.network1005.chat.ChatServerProcessThread;

public class HttpServer {
	private static final int PORT = 8088;
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try{
			//1. server socket 생성
			serverSocket = new ServerSocket();
			
			//2. Binding
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("연결 기다림  "+hostAddress + ":" +PORT );
			
			//3. 연결 요청 기다림
			while(true){
				Socket socket = serverSocket.accept();
				
				Thread thread = new HttpThread( socket);
				thread.start();
			}

			
			
			
			
			
		}catch( IOException ex){
			log("ERRoR@1 : " + ex);
		}finally{
			if( (serverSocket != null) && (serverSocket.isClosed() == false)){
				try{
					serverSocket.close();
					
				}catch( IOException ex){
					log("ERRoR@2 : close exception");
				}
				
			}
		}
		
		
	}
	public static void log(String log){
		System.out.println("[http server]" + log);
	}
}

