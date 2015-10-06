package com.bit2015.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private static final int PORT = 12345;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. Binding
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();
			
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("[서버] 바인딩 " + hostAddress + ":"+PORT);
			
			//serverSocket.bind(new InetSocketAddress("192.168.1.106", 12345));
			//serverSocket.bind(new InetSocketAddress("localhost", 10001));
																			//TODO listen 은 아직 안했음  
			//3. 연결 요청 대기
			System.out.println("[서버] 연결 기다림");
			Socket socket = serverSocket.accept();
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			System.out.println( 
					"[서버] 연결됨 from "+
					inetSocketAddress.getHostName() + 
					inetSocketAddress.getPort()	)
			;

			//4-2. 데이터 읽기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			while(true){
				byte[] buffer = new byte[128];
				int readByteCount = is.read(buffer);
				
				
				if( readByteCount <0 ){		// 상대방이 (정상적인 방법으로) 종료했으면 -1 return 됨
					// 스트림 닫기
					is.close();
					//데이터 소켓 닫기
					socket.close();
					System.out.println("[서버] 클라이언트로부터 연결 끊김");
					break;
				}
				String data = new String( buffer, 0, readByteCount, "UTF-8");
				System.out.print("[서버] 데이터 수신 : "+data);
				
				os.write(data.getBytes( "UTF-8"));
				os.flush();
			}
			//4-1. 데이터 쓰기
//			OutputStream os = socket.getOutputStream();
//			String data = "Hello World";
//			os.write(data.getBytes( "UTF-8"));	//영어는 그대로 해도 되는데 한글에는 encoding 을 해줘야됨
//			os.flush();			//내부 소켓에 큐가 있어서 어느정도 차면 보냄. flush 쓰면 안차도 보내고, close하면 보내기도하는 역할
			
			
			
			// 데이터 소켓 닫기
			socket.close();
			System.out.println("[서버] 연결 종료");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if( serverSocket != null && (serverSocket.isClosed() == false) ) {		//TODO : Spring framework 가면 exception 다 빼버려서 코드 깨끗해ㅂ짐
																					//TODO : try catch 문제점은 코드 가독성이 떨어짐. 지저분해짐. server network 에서는 코드가 지저분 할 수밖에 없음
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
