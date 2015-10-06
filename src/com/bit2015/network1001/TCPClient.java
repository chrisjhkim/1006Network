/**
 * Project : Network 
 * Package : com.bit2015.network1001
 * Class : TCPClient
 * 
 * 
 * 
 * 
 */
package com.bit2015.network1001;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_ADDRESS = "192.168.1.106";
	private static final int SERVER_PORT = 12345;
	
	public static void main(String[] args) {
	
		Socket socket = null;
		try {
			socket = new Socket();
			
			System.out.println("[클리아언트] 연결요청 ");
			socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
			System.out.println("[클리아언트] 연결성공 ");
			
			//쓰고 받기
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			String data = "Hello . Mr.Server\n";
			os.write(data.getBytes("UTF-8"));
			os.flush();
			
			byte[] buffer = new byte[128];
			int readByteCount = is.read(buffer);
			data = new String( buffer, 0, readByteCount, "UTF-8");
			System.out.println("[클라이언트] 데이터 수신 : " +data);
			
			if( socket.isClosed() == false	){			
				socket.close();
			}
			
			
		} catch (IOException ex) {
			System.out.println("[클라이언트] 에러 : "+ex);
		}
		
		
	}

}
