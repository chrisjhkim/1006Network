package com.bit2015.network1006.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPEchoScanClient {
	private static final String SERVER_IP = "192.168.1.106";
	private static final int SERVER_PORT = 60000;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		
		DatagramSocket datagramSocket = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			//1. UDP 소켓 생성
			datagramSocket = new DatagramSocket();
			//2. Packet 보내기
			while(true){
				System.out.print(">>");
				String strScanMessage = scan.nextLine();	//TODO : nextLine이 맞을까? 
				if("exit".equals(strScanMessage) ){
					log("종료합니다");
					break;
				}
				
				byte[] data2 = strScanMessage.getBytes();
				
				DatagramPacket sendPacket2 = new DatagramPacket(data2,  data2.length, new InetSocketAddress(SERVER_IP, SERVER_PORT)	);
				
				datagramSocket.send( sendPacket2);
				if("serverExit".equals(strScanMessage)){
					log("serverExit Message로 서버종료시키고 Client 도 종료합니다");
					break;
				}
				//3. Data 받기
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				datagramSocket.receive( receivePacket);
				
				//4. Data 출력
				String message2 = new String( receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
	//			log("Packet 수신 : "+message2);
				System.out.println("<<"+message2);
			}
			// 자원정리.
			//datagramSocket.close();
		} catch (SocketException e) {
			log("Exception Error @3: :"+e);
		} catch (IOException e) {
			log("Exception Error @4: :"+e); //TODO:E@4
		}finally{
			datagramSocket.close();
		}
		scan.close();
		
		
	}
	public static void log(String log){
		System.out.println("[UDP-Echo-Client] Log : "+log);
		//여기에 시간도 추가해주면 쓸만함
	}
}
