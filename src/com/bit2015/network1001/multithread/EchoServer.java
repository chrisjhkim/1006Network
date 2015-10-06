package com.bit2015.network1001.multithread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 12345;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket();

			// 2. Binding
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();

			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("[서버] 바인딩 " + hostAddress + ":" + PORT);

			while(true){
				// 3. 연결 요청 대기
				System.out.println("[서버] 연결 기다림");
				Socket socket = serverSocket.accept();
				
				Thread thread = new EchoServerReceiveThread(socket);		//TODO
				//Thread thread = new CopyOfEchoServerReceiveThread_Buffer(socket);
				thread.start();
			}
			
			
		} catch (IOException ex) {
			System.out.println("서버 ERROR : " + ex);
		} finally {
			if (serverSocket != null && (serverSocket.isClosed() == false)) { // TODO
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
