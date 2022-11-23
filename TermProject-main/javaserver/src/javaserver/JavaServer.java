
 package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 실험결과 서버가 내 집 컴같은 Lan선과 연결된 컴에서 서버열면 클라이언트를 노트북으로 접속할 수있음.
 * 
 * 
 * N-1기능 완료 : 여러개 차일드가 들어갈 수 있음.
 * 
 * 필요한 추가기능:
 * 자식이 X키로 강제로 종료 시 안정적인 TCP 연결종료를 위한 이벤트 필요.
 * 서버가 강제종료 시 모든 자식Thread와 안전한 연결종료 를 위한 함수나 이벤트 필요.
 * 자식 풀 설정해야함. ThreadPool 4~6개로 해서 총 6명 인원만 제한하기. (변할 수 있음.)
 * 게임이 끝나고 4~5초 있다가 자동으로 게임 끄기. 
 * 		텍스트에 5..4..3..2..1.. 초단위로 출력해서 0이 되면 이벤트 발생해서 Platform.exit()때리면 될듯.
 * 
 * GUI :
 * 	서버에 들어온 사람이 어떤 사람들이 들어왔는지 , 그 사람이 입력한 게 뭔지 보여주는 UI 필요.
 * BorderPane. 모든 vgap: 3 hgap 3 
 * 	<left>에 
 * 		StackList로 	Guest 1	강퇴	
 * 				  	Guest 2	강퇴
 * 					Guest 3	강퇴
 *	<center> 의 textarea는 maxwidth 끝까지 해놓기.
 *	<bottom>에는 걍 종료버튼 깔던가 재시작 버튼 추가.
 *
 *	OK입장, 삭제시 JList에 추가.
 *	버튼 강퇴시 접속종료 삭제.
 *	클라이언트와 연결 중인데, 서버가 먼저 강제종료 할 경우 서버 스레드 살아있는거 죽이기.
 *
 * @author 이종범
 *
 */



public class JavaServer implements RootServerInterface{
	int clientNo;
	private int portNumber;
	private ServerSocket svSocket = null; //클라이언트와 통신 하기 위한 소켓 
	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	private static List<ChildThread> clients = new ArrayList<ChildThread>();
	private MsgDelivGui guiPrintText;
	
	public JavaServer() {}
	public JavaServer(int port) throws IOException {
		this.portNumber = port;
		initServer();
	}
	

	
	private void initServer() {
		clientNo = 0;
		try { 
			svSocket = new ServerSocket(portNumber);
		} 
		catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
	protected void connect() { 
		guiPrintText.printInit();
//		System.out.println("서버 실행합니다.");
		while(true) {
			try { 
				System.out.println("wait...");
				Socket socket = svSocket.accept();
				ChildThread ct = new ChildThread(socket, ++clientNo, guiPrintText);
				guiPrintText.printMsg(toGui("ROOT", String.valueOf(clientNo)));
				ct.setName("Client-Thread-"+clientNo);
				ct.start();
				clients.add(ct);
				System.out.println(clientNo +" Enter. clients number ="+clients.size());
			} catch (IOException e) { 
				System.out.println("Oh my gosh!");
				e.printStackTrace();
				break;
//				try {
//					disconnect();
//				} catch (IOException | InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		}

	}
	
	protected void disconnect() {
		System.out.println("disconnect() called");

		if(!svSocket.isClosed()) {
			System.out.println("SvSocket is Not Null");
			if(!clients.isEmpty()) {
				System.out.println("들어와있는 클라이언트 수 : "+clients.size());
				while(!clients.isEmpty()) {
					ChildThread e = clients.remove(0);
					try {
						e.infoDisconnect();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("[JavaServer] A critical Remove error occurs.");
						e1.printStackTrace();
					}
				}

			}else {
				System.out.println("클라이언트 0명입니다.");
				
			}
			try {
				
				svSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	public static List<ChildThread> getClients(){
		return Objects.requireNonNull(clients);
	}
	
	
	protected static boolean isNumber(String sno) {
		if(sno == null)
			return false;
		return pattern.matcher(sno).matches();
	}
	
	protected ServerSocket getSvSocket() {
		return this.svSocket;
	}
	
	protected void setMsgDelivGui(MsgDelivGui g) {
		this.guiPrintText = g;
	}
//	public static void main(String[] args) throws IOException { 
//		new JavaServer(30000); 
//		return;
//	}
	@Override
	public void toClient(String msg) {
		// TODO Auto-generated method stub
		
	}

}
 
