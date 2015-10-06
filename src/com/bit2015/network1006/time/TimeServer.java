package com.bit2015.network1006.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 24240;
	
	
	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		
		try {
			datagramSocket = new DatagramSocket(PORT);
			//수신대기
			DatagramPacket receivePacket = new DatagramPacket(new byte[50], 50);
			
			//String data = format.format(format);
			
			while(true){
				System.out.println("수신 대기");
				datagramSocket.receive( receivePacket);
				System.out.println("receive 했음");
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String data = format.format(new Date());
				DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.length(), receivePacket.getAddress(), receivePacket.getPort());
				datagramSocket.send(sendPacket);
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			datagramSocket.close();
			
		}
		
		
	}
}
