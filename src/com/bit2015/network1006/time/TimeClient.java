package com.bit2015.network1006.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;

public class TimeClient {
	private static final String SERVER_IP = "192.168.1.106";
	private static final int SERVER_PORT = 24240;

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;

		try {
			datagramSocket = new DatagramSocket();

			byte[] data = "".getBytes();

			DatagramPacket sendPacket = new DatagramPacket(data, data.length,
					new InetSocketAddress(SERVER_IP, SERVER_PORT));
			datagramSocket.send(sendPacket);
			System.out.println("보냈음");

			DatagramPacket receivePacket = new DatagramPacket(
					new byte[50], 50);
			datagramSocket.receive(receivePacket);

			String message2 = new String(receivePacket.getData(), 0,
					receivePacket.getLength(), "UTF-8");
			System.out.println("<<" + message2);
			datagramSocket.close();
		} catch (SocketException e) {
			System.out.println("Exception Error @3: :" + e);
		} catch (IOException e) {
			System.out.println("Exception Error @4: :" + e); // TODO:E@4
		}
		// 서버 아니여서 port번호 필요없음

	}
}
