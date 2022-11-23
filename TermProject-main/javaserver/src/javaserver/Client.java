//package javaserver;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import javax.swing.border.EmptyBorder;
//
//public class Client extends JFrame{
//    // 문자열 입력창
//	private JPanel contentPane;
//    private  JTextField textField;
//
//    // 출력 UI
//    private JTextArea textArea;
//
//    //Server와 통신하기 위한 Socket
//    private Socket socket = null;
//
//    //Server로부터 데이터를 읽어들이기 위한 입력스트림
//    private BufferedReader in = null;
//
//    //서버로 내보내기 위한 출력 스트림
//    PrintWriter out = null;
//
//    InetAddress ia = null;
//
//    // 서버 아이피
//    private String serverId = "192.168.0.13";
//
//    // 서버 포트
//    private int serverPort = 30000;
//    
//    //클라이언트 번호
//    private String ClientNo;
//    
//    public Client() throws IOException {
//        
////        initClient();
//        renderUI();
////        doProcess();
//    }
//
//    /**
//     * 클라이언트 초기화
//     */
//    private void initClient() throws IOException {
//        ia = InetAddress.getByName(serverId);
//        System.out.println(ia);
//        socket = new Socket(ia, serverPort);
//        socket.setKeepAlive(true);
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
//        
//        ClientNo = receiveMessage();
//		System.out.print(ClientNo);
//    }
//
//    /**
//     * 클라이언트와 통신을 처리하는 함수
//     */
//    private void doProcess() throws IOException {
//        new Thread(() -> {
//            try {
//                while (true){
//                    if( socket.isClosed()) break;
//                    String msg = receiveMessage();
//                    if( msg != null) textArea.append ( msg + "\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//    /**
//     * 화면의 UI를 렌더링한다.
//     */
//    private void renderUI() {
//
//        this.setTitle("클라이언트");
//        
////        new WinExit();
//        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout());
////		add(contentPane);
//		
//		textField = new JTextField("입력하시오", 30);
////		textField.addActionListener(this);
//		textField.setHorizontalAlignment(SwingConstants.CENTER);
//		textField.setBounds(12, 10, 412, 180);
//		textField.setColumns(10);
//		contentPane.add(textField, BorderLayout.CENTER);
//        
//		//add(textField, BorderLayout.PAGE_END);
//		
//		JButton btnNewButton = new JButton("전송");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String message = textField.getText();
//				try {
//					sendMessage(message);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				textField.setText("");
//			}
//		});
//		btnNewButton.setBounds(162, 200, 118, 53);
//		contentPane.add(btnNewButton, BorderLayout.SOUTH);
//       
//		
////        textArea = new JTextArea(50, 50);
////        textArea.setEditable(false);  
//
////        add(textArea, BorderLayout.CENTER);
////        pack();
//    
//    }
//
//    /**
//     * 서버로 부터 메세지 수신
//     */
//    private String receiveMessage() throws IOException {
//        return in.readLine();
//    }
//
//    /**
//     * 서버로 메세지 전송
//     */
//    private void sendMessage(String message) throws IOException {
//        out.println(message);
//        out.flush();
//    }
//
//    class WinExit extends JFrame{
//    	public WinExit() {
//			System.out.print(ClientNo);
//
//    		this.addWindowListener(new WinEvent());
//    	}
//    }
//    
//    class WinEvent implements WindowListener{
//    	public void windowClosing(WindowEvent e) {
//    		try {
//    			System.out.print(ClientNo);
//    			sendMessage("MSG_DISCONN_" + ClientNo);
//    			
//				socket.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//    		System.exit(0);
//    	}
//
//		@Override
//		public void windowOpened(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void windowClosed(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void windowIconified(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void windowDeiconified(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void windowActivated(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void windowDeactivated(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//    }
//      
////    @Override
////    public void actionPerformed(ActionEvent evt) {
////    	String message = textField.getText();
////        try {
////            sendMessage(message);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        textField.setText("");
////    }
//
//    public static void main(String[] args) throws IOException {
//    	EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					Client client = new Client();
//					client.setVisible(true);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//    		
//    	});
//        
////        EventQueue.invokeLater(new Runnable() {
////			public void run() {
////				try {
////					Client frame = new Client();
////					frame.setVisible(true);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		});
//    }
//}

//일단 아래걸로 임시작업.
//
//package javaserver;
//
//import java.awt.BorderLayout;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//
//import javax.swing.JFrame;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//public class Client extends JFrame implements ActionListener{
//    // 문자열 입력창
//    private  JTextField textField;
//
//    // 출력 UI
//    private JTextArea textArea;
//
//    //Server와 통신하기 위한 Socket
//    private Socket socket = null;
//
//    //Server로부터 데이터를 읽어들이기 위한 입력스트림
//    private BufferedReader in = null;
//
//    //서버로 내보내기 위한 출력 스트림
//    PrintWriter out = null;
//
//    InetAddress ia = null;
//
//    // 서버 아이피
//    private String serverId = "localhost";
//  //private String serverId = "172.19.15.118";
//
//    // 서버 포트
//    private int serverPort = 30000;
//    
//    //클라이언트 번호
//    private String ClientNo;
//    
//    public Client() throws IOException {
//        
//        initClient();
//        renderUI();
//        doProcess();
//    }
//
//    /**
//     * 클라이언트 초기화
//     */
//    private void initClient() throws IOException {
//        ia = InetAddress.getByName(serverId);
//        System.out.println(ia);
//        socket = new Socket(ia, serverPort);
//        socket.setKeepAlive(true);
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
//        
//        ClientNo = receiveMessage();
//
//    }
//
//    /**
//     * 클라이언트와 통신을 처리하는 함수
//     */
//    private void doProcess() throws IOException {
//		Thread t = new Thread(() -> {
//            try {
//                while (true){
//                    if(socket.isClosed()) break;
//                    String msg = receiveMessage();
//                    if( msg != null) textArea.append ( msg + "\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//		t.setDaemon(true);
//		t.start();
//    }
//
//    /**
//     * 화면의 UI를 렌더링한다.
//     */
//    private void renderUI() {
//        this.setTitle("클라이언트");
//        this.addWindowListener(new WinExit(ClientNo,out,socket));
//        
//        textField = new JTextField(30);
//        textField.addActionListener(this);
//
//        textArea = new JTextArea(50, 50);
//        textArea.setEditable(false);
//
//        add(textField, BorderLayout.PAGE_END);
//        add(textArea, BorderLayout.CENTER);
//        pack();
//        setVisible(true);
//    }
//
//    /**
//     * 서버로 부터 메세지 수신
//     */
//    private String receiveMessage() throws IOException {
//        return in.readLine();
//    }
//
//    /**
//     * 서버로 메세지 전송
//     */
//    private void sendMessage(String message) throws IOException {
//    	if(message != null)
//    		out.println(message);
//        out.flush();
//    }
//
//  
//    @Override
//    public void actionPerformed(ActionEvent evt) {
//        try {
//            String message = textField.getText();
//            sendMessage(message);
//            textField.setText("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        Client client = new Client();
//    }
//}



package javaserver;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener{
    // 문자열 입력창
    private  JTextField textField;

    // 출력 UI
    private JTextArea textArea;

    //Server와 통신하기 위한 Socket
    private Socket socket = null;
    
    private boolean sck_isclose = false;

    //Server로부터 데이터를 읽어들이기 위한 입력스트림
    private BufferedReader in = null;

    //서버로 내보내기 위한 출력 스트림
    PrintWriter out = null;

    InetAddress ia = null;

    // 서버 아이피
    private String serverId = "localhost";
  //private String serverId = "172.19.15.118";

    // 서버 포트
    private int serverPort = 30000;
    
    //클라이언트 번호
    private String ClientNo;
    
    public Client() throws IOException {
        
        initClient();
        renderUI();
        doProcess();
    }

    /**
     * 클라이언트 초기화
     */
    private void initClient() throws IOException {
        ia = InetAddress.getByName(serverId);
        System.out.println(ia);
        socket = new Socket(ia, serverPort);
        socket.setKeepAlive(true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        
        ClientNo = receiveMessage();

    }

    /**
     * 클라이언트와 통신을 처리하는 함수
     */
    private void doProcess() throws IOException {
        new Thread(() -> {
            try {
                while (socket.isConnected()){
                    if(sck_isclose) {
                    	socket.close();  
                    	break;
                    }
                    System.out.println("기다리는중");
                    String msg = receiveMessage();
                    if( msg != null) textArea.append ( msg + "\n");   
                    System.out.println("----");
                }
            } 
            catch (IOException e) {
                e.printStackTrace();
                this.dispose();
                System.out.println("나갑니다유1");
            }
            System.out.println("나갑니다유2");
        }).start();
    }

    /**
     * 화면의 UI를 렌더링한다.
     */
    private void renderUI() {

        this.setTitle("클라이언트");
        
       
        this.addWindowListener(new WinExit());
        
        
        textField = new JTextField(30);
        textField.addActionListener(this);

        textArea = new JTextArea(50, 50);
        textArea.setEditable(false);

        add(textField, BorderLayout.PAGE_END);
        add(textArea, BorderLayout.CENTER);
        pack();
        setVisible(true);
        
    }
    
    class WinExit extends WindowAdapter
    {
    	@Override
        public void windowClosing(WindowEvent e)
        {
    		
    		try {
				sendMessage("MSG_DISCONN_" + ClientNo);
				sck_isclose = true;
				System.out.println("나 나가유~ ");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("나 나가유~ 533");
			}
    		System.out.println("나 나가유~ 533");
    	    System.exit(0);
        }
    }

    /**
     * 서버로 부터 메세지 수신
     */
    private String receiveMessage() throws IOException {
    	String s;
    	try {
    		s = in.readLine();
    		if(s.compareTo("EXITCLIENT") == 0)
    			throw new IOException("연결종료");
    	}catch(IOException e) {
    		throw new IOException("연결종료");
    	}
    	return s;
    }

    /**
     * 서버로 메세지 전송
     */
    private void sendMessage(String message) throws IOException {
        out.println(message);
        out.flush();
    }
  
    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            String message = textField.getText();
            sendMessage(message);
            textField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
    }
}