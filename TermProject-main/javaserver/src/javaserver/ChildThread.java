package javaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChildThread extends Thread implements ServerInterface{
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;
	private int rNumber;
	private int clientNo;
	private int life;
	String clientNumber;
	private MsgDelivGui guiPrint;
	public ChildThread(Socket socket, int cno, MsgDelivGui g) {
		this.socket = socket;
		this.clientNo = cno;
		reader = null;
		out = null;
		life = 10;
		this.guiPrint = g;
		this.rNumber = (int)(Math.random() *5000 + 1);
	}
	
	private void childDisconnect() throws IOException {
		toClient("EXITCLIENT");
		if(JavaServer.getClients().contains(this)) {
			JavaServer.getClients().remove(this);
		}
		System.out.println("ChildThread... End"+clientNo);
		reader.close();
		out.close();
		socket.close();
	}
	
	protected void infoDisconnect() throws IOException {
		childDisconnect();
	}
	
	public void run(){
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			toClient(String.valueOf(clientNo));
			toClient("환영합니다 Client-"+clientNo+"님.");
			toClient("남은 목숨은 "+life+"개 입니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(true) {
				int userNumber;
				while(true) {
					clientNumber = reader.readLine().trim();
					
					System.out.println("입력된 숫자: "+clientNumber);
					
					if(clientNumber.compareTo("MSG_DISCONN_"+clientNo)==0 || isNumber(clientNumber))
						break;
					else {
						toClient("숫자를 입력해주세요.");
					}
				};
				
				if(clientNumber.compareTo("MSG_DISCONN_"+clientNo)==0) {
//					System.out.println("종료 요청> "+clientNumber );
					guiPrint.printMsg(toGui(String.valueOf(clientNo), "종료요청"));
					childDisconnect();
					break;
				}
				
				userNumber = Integer.parseInt(clientNumber);
				guiPrint.printMsg(toGui(String.valueOf(clientNo), String.valueOf(userNumber)));
				
				--life; 
				if(life > 0) {
					//클라이언트가 입력한 숫자와 서버가 입력한 숫자와 비교합니다.
					if(userNumber > rNumber ){
						//서버 넘버 > 클라이언트 넘버
						toClient("좀 더 작은 수를 입력해보세요."); 
						toClient(life+"회 남았습니다."); 
					}else if(userNumber < rNumber) {
						//서버 넘버 < 클라이언트 넘버
						toClient("좀 더 큰 수를 입력해보세요."); 
						toClient(life+"회 남았습니다."); }
					else {//클라이언트가 맞춘경우
						toClient("What a genius you are!");
						infoDisconnect();
					} 
				} 
				else {
					toClient("지셨네요 하하.");
					infoDisconnect();
				} 
			}
		} catch(IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public int getClientNo() {
		return this.clientNo;
	}
	
	@Override
	public void toClient(String msg) {
		// TODO Auto-generated method stub
//		toGui(msg);
		out.println(msg);
	}

	@Override
	public boolean isNumber(String msg) {
		// TODO Auto-generated method stub
		return JavaServer.isNumber(msg);
	}

}
