package com.bit2015.network1005.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerProcessThread extends Thread{
	private static final String PROTOCOL_DIVIDER = ":";

	private String nickname;
	private Socket socket;
	private List<PrintWriter> listPrintWriters;					//TODO : ???? static 이 아니고 그냥 private list 하면 thread생성될때마다 개별의 List가 생성되는거는 아닌가?????
	

	public ChatServerProcessThread (Socket socket, List<PrintWriter> listPrintWriters){
		this.socket = socket;
		this.listPrintWriters = listPrintWriters;				 
	}
	
	@Override
	public void run() {
		super.run();
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		
		try {
			// 1. 스트림 얻기
			bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
			printWriter = new PrintWriter( socket.getOutputStream() );
			
			// 2. Remote Host 정보 얻기
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();	//socketAddress -> InetSocketAddress
			String remoteHostAdress = inetSocketAddress.getHostName();
			int remoteHostPort = inetSocketAddress.getPort();
			ChatServer.log("연결됨 from "+remoteHostAdress + ":" + remoteHostPort);
			ChatServer.log("연결! (toString) : "+inetSocketAddress.toString());
			// 3. 요청 처리
			while(true){
				String request = bufferedReader.readLine();
				if( request == null){	//Client 연결 끊김
					ChatServer.log("클라이언트로부터 연결 끊김");
					doQuit(printWriter);
					break;
				}
				
				String[] tokens = request.split( PROTOCOL_DIVIDER);		// parsing 위한 토큰
				if( "join".equals( tokens[0] ) ){
					
					doJoin( printWriter, tokens[1] );
					
				}else if( "message".equals( tokens[0] ) ){
					doMessage( tokens[1] );
				
				}else if( "quit".equals( tokens[0] ) ){
					doQuit( printWriter );
					break;
				
				}else{
					ChatServer.log(	"ERRoR : 알수없는 요청명령(" + tokens[0] +") from "+inetSocketAddress.toString()	);
				}
				
				//TODO : if(연결종료 protocol) break;
				//TODO : if(protocol) do work; 		//parsing 해서.
			}
			
			// 4. 자원 정리
			bufferedReader.close();
			printWriter.close();
			if(socket.isClosed() == false){
				socket.close();
			}
			
		} catch (IOException ex) {
			ChatServer.log("ERRoR@3 : "+ex);							//static way 로 접근
			doQuit(printWriter);
		}
	}
	private void doQuit( PrintWriter printWriter ){
		// PrintWriter 제거
		removePrintWriter(printWriter);
		
		// 퇴장 메세지 브로드케스팅
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
		
	}
	public void doMessage(	String message ){
		message = nickname + ":" +message;
		broadcast(message);
	}
	
	private void doJoin( PrintWriter printWriter, String nickname){
		// 1. 닉네임 저장
		this.nickname = nickname;
		
		// 2. 메시지 Broadcasting
		// 자기자신한테 "님이 입장했습니다" 할 필요 없으니 broadcast 한 이후에 본인을 추가.
		String message = nickname + " 님이 입장했습니다.";
		broadcast(	message	);
		
		// 3. 
		//listPrintWriters.add(printWriter);
		addPrintWriter(printWriter);
		
		// 4. ACK 		// TODO
		printWriter.println("join : ok");
		printWriter.flush();
	}
	
	private void addPrintWriter( PrintWriter printWriter ){
		// 동기화 작업 ( 동시에 입장하는것 막기위해. Thread관련.)
		synchronized ( listPrintWriters) {		//listPrintWriter 라는 공유객체에 동시에 접근하는것 block 하기위해.
			listPrintWriters.add( printWriter);	//Loop 돌때도 마찬가지.add하는애들. broadcast 하는애들. 등등 
		}										//Thread 들 끼리 겹치지 않게 보장해줌
	}
	private void removePrintWriter( PrintWriter printWriter){
		synchronized( listPrintWriters){
			listPrintWriters.remove(printWriter);
		}
	}
	private void broadcast( String data	){
//		data += "\n";		//println 이니까 \n 필요없음
		
//		for( PrintWriter printWriter : listPrintWriters ){		//foreach 방식
//		System.out.println( data );
//		}
		int count = listPrintWriters.size();			//이게 더 빠르다 함
		for (int i=0 ; i<count ; i++){
			PrintWriter printWriter = listPrintWriters.get( i );
			printWriter.println(data);
			printWriter.flush();
		}
		
	}
	
}
