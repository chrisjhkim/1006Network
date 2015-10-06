package com.bit2015.network1006.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPEchoClient {
	private static final String SERVER_IP = "192.168.1.106";
	private static final int SERVER_PORT = 60000;
	private static final int BUFFER_SSIZE = 1024;

	public static void main(String[] args) {
		
		DatagramSocket datagramSocket = null;
		
		try {
			//1. UDP 소켓 생성
			datagramSocket = new DatagramSocket();
			
			//2. Packet 보내기
			String message = "HELLO WORLD!!";
			//보내야되서 byte로 바꾸기
			byte[] data = message.getBytes();
		
			DatagramPacket sendPacket = new DatagramPacket(data,  data.length, new InetSocketAddress(SERVER_IP, SERVER_PORT)	);
			datagramSocket.send( sendPacket);
			
			//3. Data 받기
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SSIZE], BUFFER_SSIZE);
			datagramSocket.receive( receivePacket);
			
			//4. Data 출력
			String message2 = new String( receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
//			log("Packet 수신 : "+message2);
			System.out.println("<<"+message2);
			// 자원정리.
			datagramSocket.close();
		} catch (SocketException e) {
			log("Exception Error @3: :"+e);
		} catch (IOException e) {
			log("Exception Error @4: :"+e); //TODO:E@4
		}
		//서버 아니여서 port번호 필요없음
		
		
	}
	public static void log(String log){
		System.out.println("[UDP-Echo-Client] Log : "+log);
		//여기에 시간도 추가해주면 쓸만함
	}
}//byte[] data = "".getBytes();
