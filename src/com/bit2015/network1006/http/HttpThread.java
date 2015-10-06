/***
 * 
 * image파일을 browser 에 전송해주는것만들어보기
 * 
 * 
 * 
 */

package com.bit2015.network1006.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpThread extends Thread {
	private Socket socket;
	private static final String WEB_ROOT = "C:\\bit2015\\workspace\\Network\\web-root";
	private static final String PROTOCOL_DIVIDER = " "; 

	public HttpThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		// 보조Stream 말고 Output Stream 사용함. 나중에 이유 나옴
		OutputStream outputStream = null;

		try {
			// 1. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outputStream = socket.getOutputStream();

			// 2. Remote Host Information
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
					.getRemoteSocketAddress();
			HttpServer.log("연결됨 from " + inetSocketAddress.getHostName() + ":"
					+ inetSocketAddress.getPort());

//			// 3. 요청처리
//			String request = bufferedReader.readLine();
//			if (request != null) {
//				HttpServer.log("request:" + request);
//			}

			//3-2. 요청처리
			String request = bufferedReader.readLine();
			if( request != null ) {
			  	HttpServer.log( "request:" + request );
							
				String[] tokens = request.split(PROTOCOL_DIVIDER);			
				if ("GET".equals(tokens[0]) == true) {
					sendStaticResource(outputStream, tokens[1], tokens[2]);	
					//dynamic 아닌 static. 시간 지나도 안변하는 페이지 		.. 음??
					//변하는 프로그램, 페이지를 만드는게 웹 프로그래밍. 그게 static.. 음?? 
					//html파일, 파일, 그림파일 
					// token[1] : file pass 
					// token[2] : protocol
				} else {
					HttpServer.log("error: 지원하지 않는 요청 명령(" + tokens[0] + ")");
				}
			}
			// 4. 연결 끊기
			//한번에 바로 끊는게 TCP//IP 서버 기반이랑 다른점 중 하나.
			//서버에서는 사용자가 누군지 모름. 같은 사용자가 10번 찔러도 다 다른놈이라 생각함.
			//이후에 로그인처리 어떻게 하는지 다시 배울예정
			bufferedReader.close();	
			outputStream.close();
			socket.close();

		} catch (IOException ex) {
			HttpServer.log("error:" + ex);
		}
	}
	
	private void sendStaticResource( OutputStream outputStream, String path, String protocol ) throws IOException {
		
	    if( "/".equals( path ) ) {
	        path = "/index.html"; 
	    }
			

	    String extension = path.substring( path.lastIndexOf( "." ) );
	    //볼필요x 파일요청해서없으면 not found 로 해주려고 한거.
			
	    if( ".html".equals( extension ) == false && ".htm".equals( extension ) == false ) {
	       sendError404( outputStream, protocol );		//404 는 실패. 200 성공
	       return;
	    }
			
	    File file = new File( WEB_ROOT, path );
	    if( file.exists() == false ) {
	       sendError404( outputStream, protocol );
	       return;
	    }
	    
	    /*
	     * 요청한 파일을 읽어서 브라우저에 저송
	     * 
	     * 
	     */
	    //header	
	    outputStream.write( ( protocol + " 200 OK\r\n" ).getBytes() );
	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );
	    //				워드 파일이라면 여기 text/html 이 아닌 doc을 보여줌
	  //어떤 타입인지 알아야 byte로 encoding 이 용이
	    outputStream.write( "\r\n".getBytes() );

	    // body
	    FileInputStream fis = new FileInputStream( file );
	    byte[] buffer = new byte[ 1024 ];			
			 
	    int countReadByte = fis.read( buffer, 0, 1024 );
	    while( countReadByte >= 0 ) {
	        outputStream.write( buffer, 0, countReadByte );
	        countReadByte = fis.read( buffer, 0, 1024 );
	    }

	    outputStream.flush();
	    fis.close();
	}

	    
	    
	
	private void sendError404( OutputStream outputStream, String protocol ) throws IOException {	//보낼때복잡하거나 클래스 크면 ioexception 처리가 나음)
		//이거 안보내면 브라우저가 브라우저의 404화면 띄워줌
	    // header
	    outputStream.write( ( protocol + " 404 File Not Found\r\n" ).getBytes() );
	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );

	    outputStream.write( "\r\n".getBytes() );

	    //body
	    outputStream.write( "<h1>File Not Found</h1>".getBytes() );		//브우자 맏
	    outputStream.flush();		
	}

}
