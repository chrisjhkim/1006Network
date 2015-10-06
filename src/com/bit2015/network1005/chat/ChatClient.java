package com.bit2015.network1005.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_ADDRESS = "192.168.1.106";
	private static final int SERVER_PORT = 12345;
	
	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		
		try {
			//1 키보드 연결
			scanner = new Scanner(System.in);
			
			//2. socket 생성
			socket = new Socket();
			
			//3. 연결
			System.out.println("[클리아언트] 연결요청 ");
			//socket.connect(new InetSocketAddress(SERVER_ADRTEMP,PORT_TEMP));
			socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
			//socket.connect(new InetSocketAddress(SERVER_ADDRESS_OTHER, SERVER_PORT));
			System.out.println("[클라이언트] connected to "+SERVER_ADDRESS + SERVER_PORT);
			
			//4. reader / writer 생성 
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			PrintWriter printWriter = new PrintWriter( socket.getOutputStream() );
			
			// 5. join protocol
			System.out.print("아이디를 입력하세요 : ");
			String id = scanner.nextLine();
			printWriter.println("join:"+id);
			printWriter.flush();
			System.out.println("채팅방에 입장하였습니다.");
			//bufferedReader.readLine();	//TODO : Server가 ACK 보내면 이것도 하는게 맞을듯
			
			// 6. ChatClientRecvThread 시작
			//Thread thread = new ChatClientRecvThread());
			//Thread thread_maybe = new ChatClientRecvThread(socket);
			//thread_maybe.start();
			Thread thread = new ChatClientRecvThread(bufferedReader);
			thread.start();
			
			// 7. 키보드 입력 처리
			while (true){
				System.out.println(">>");	// TODO : 이거 지워야 될지도
				String strBuff = scanner.nextLine();
				
				if( "exit".equals( strBuff ) ){
					//8. quit 프로토콜 처리
					printWriter.println("quit:");
					printWriter.flush();
					
					break;
				}
				else{
					//9. message 처리
					printWriter.println("message:"+strBuff);
					printWriter.flush();
				}
			}
			//10. 자원정리
			scanner.close();
			bufferedReader.close();
			printWriter.close();
			if(socket.isClosed() == false){
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error@2 : "+e);
		}
		
	}
}
