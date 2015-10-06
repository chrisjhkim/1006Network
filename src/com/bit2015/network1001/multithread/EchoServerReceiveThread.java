package com.bit2015.network1001.multithread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;

	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
				.getRemoteSocketAddress();
		System.out.println("[서버] 연결됨 from " + inetSocketAddress.getHostName()
				+ inetSocketAddress.getPort());

		// 4-2. 데이터 읽기
		InputStream is = null;
		OutputStream os = null;

		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			while (true) {
				byte[] buffer = new byte[128];
				int readByteCount = is.read(buffer); // 여기서 Blocking 됨.2
				if (readByteCount < 0) { // 상대방이 (정상적인 방법으로) 종료했으면 -1 return 됨
					// 스트림 닫기
					is.close();
					// 데이터 소켓 닫기
					socket.close();
					System.out.println("[서버] 클라이언트로부터 연결 끊김");
					break;
				}
				String data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.print("[서버]["+ inetSocketAddress.getPort()+"] 데이터 수신 : " + data);

				os.write(data.getBytes("UTF-8"));
				os.flush();
			}
			is.close();
			os.close();
			if (socket.isClosed() == false) {
				socket.close();
			}
		} catch (IOException ex) {
			System.out.println("[서버] 에러 : " + ex);
		}
	}
}
