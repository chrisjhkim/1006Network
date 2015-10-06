/**
 * Project : Network 
 * Package : com.bit2015.network1001.echo
 * Class : TCPClient
 * 
 * Echo Client. 
 * exit 입력할때까지 계속 보냄
 * 
 * 
 */
package com.bit2015.network1001.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
	private static final String SERVER_ADDRESS = "192.168.1.106";
	private static final String SERVER_ADDRESS_OTHER = "192.168.1.172";
	private static final int SERVER_PORT = 15002;
	private static final String SERVER_ADRTEMP = "222.106.22.80";
	private static final int PORT_TEMP = 10002;
	public static void main(String[] args) {
	
		Socket socket = null;
		Scanner scanner = null;
		
		
		try {
			scanner = new Scanner(System.in); 
			socket = new Socket();
			
			System.out.println("[클리아언트] 연결요청 ");
			
//			socket.connect(new InetSocketAddress(SERVER_ADRTEMP,PORT_TEMP));
			socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
			//socket.connect(new InetSocketAddress(SERVER_ADDRESS_OTHER, SERVER_PORT));
			System.out.println("[클라이언트] connecting to "+SERVER_ADDRESS + SERVER_PORT);
			System.out.println("[클리아언트] 연결성공 ");
			
			//쓰고 받기
			OutputStream os = null;
			InputStream is = null;

			while(true){
				os = socket.getOutputStream();
				is = socket.getInputStream();
				System.out.print(">>");
				String data = scanner.nextLine();
				if("exit".equals(data)){		//if( "exit".equals(data) == true )
					break;
				}
				data += "\n";					//으으으으음>???????????TODO 
				os.write(data.getBytes("UTF-8"));
				os.flush();
				
				byte[] buffer = new byte[128];
				int readByteCount = is.read(buffer);
				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.print("<<"+data);
			}
			
			os.close();
			is.close();
			
			if( socket.isClosed() == false	){			
				socket.close();
			}
			
			
		} catch (IOException ex) {
			System.out.println("<< 에러 : "+ex);
		}
		
		
	}

}
