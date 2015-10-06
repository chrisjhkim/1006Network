package com.bit2015.network1001.multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CopyOfEchoServerReceiveThread_Buffer extends Thread {

	private Socket socket;

	public CopyOfEchoServerReceiveThread_Buffer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
				.getRemoteSocketAddress();
		System.out.println("[서버] 연결됨 from " + inetSocketAddress.getHostName()
				+ inetSocketAddress.getPort());

		// 4-2. 데이터 읽기
		BufferedReader reader = null;		//Buffered Reader는 line 단위로 읽음
		PrintWriter printWriter = null;		//byte를 char 로 바꿔주는놈 필요
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			printWriter = new PrintWriter(socket.getOutputStream());
			
			while (true) {
				String data = reader.readLine();		//TODO : TCP 서버랑 비교해보자. 얼마나 간편해졌는지
				if(data == null){
					break;
				}
				System.out.println("[서버]["+ inetSocketAddress.getPort()+"] 데이터 수신 : " + data);

				printWriter.println(data);
				printWriter.flush();
			}
			reader.close();
			if (socket.isClosed() == false) {
				socket.close();
			}
		} catch (IOException ex) {
			System.out.println("[서버] 에러 : " + ex);
		}
	}
}
