package com.bit2015.network1005.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.*;

public class ChatServer {
	private final static int PORT = 12345;
	
	
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<PrintWriter> listPrintWriters = new ArrayList<PrintWriter>();	//awt 에 있는 list 아닌 util 에 있는 list
		
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
				
				Thread thread = new ChatServerProcessThread( socket, listPrintWriters);
				thread.start();
				
				
				
			}

			
			
			
			
			
		}catch( IOException ex){
			log("ERRoR : " + ex);
		}finally{
			if( (serverSocket != null) && (serverSocket.isClosed() == false)){
				try{
					serverSocket.close();
					
				}catch( IOException ex){
					log("ERRoR : close exception");
				}
				
			}
		}
		
		
		
	}

	public static void log(String log){
		System.out.println("[chat-server]" + log);
	}
}
