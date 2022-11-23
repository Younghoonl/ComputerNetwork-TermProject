package javaserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServerGui extends JFrame implements ListSelectionListener{
	private JList<String> list;
	protected JavaServer server = new JavaServer(30000);
	private JPanel contentPane;
	private DefaultListModel<String> model;
	private JButton kick;
	private JButton start, stop;
	private JTextPane textArea;
	private Thread listUpdateThread;
	private Thread th;
	private WorkThread work;
	private static JFrame frame;
	private MsgDelivGui printGuiTextField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					ServerGui frame = new ServerGui();
					frame = new ServerGui();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ServerGui() throws IOException {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE|WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(2, 2));
		listUpdateThread = new Thread(()->{
			while(!server.getSvSocket().isClosed()) {
				try {
					if(server.getSvSocket().isClosed()) break;
					
					if(model.getSize() != JavaServer.getClients().size()) {
						model.clear();
						String[] ar = JavaServer.getClients()
								.stream().map(ChildThread::getClientNo)
								.map(String::valueOf).toArray(String[]::new);
						System.out.println("COUNT : "+ar.length);
						for(String e : ar) {
							model.addElement("Client-"+e);
						}
						list.setModel(model);
						
//						list.updateUI();
					}
				}				
				catch(Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
		addToolbar();
		listSet();
	}
	private void addToolbar() {
		JPanel flowPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		start = new JButton("Start");
		stop = new JButton("End");
		start.addActionListener(new InitStart());
		stop.addActionListener(new InitStop());
		flowPane.add(start);
		flowPane.add(stop);
		contentPane.add(flowPane, BorderLayout.NORTH);
	}

	
	class InitStart implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			work = new WorkThread(server, model);
			th = new ThreadForStart(work);
			th.start();
			listUpdateThread.setDaemon(true);
			listUpdateThread.start();
			
		}
		
	}
	
	class InitStop implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("종료 버튼이 눌렸습니다.");
			th = new ThreadForStop(work);
			th.setDaemon(true);
			th.start();
			frame.dispose();
		}

	}
	
	
	private void listSet() {
		model = new DefaultListModel<>();
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setFont(new Font("Arial", Font.BOLD, 16));
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(100, 0));
		kick = new JButton("강퇴");
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		printGuiTextField = new MsgDelivGui(textArea);
		printGuiTextField.setGui(this);
		
		server.setMsgDelivGui(printGuiTextField);
		
		kick.addActionListener(new KickListener());
		contentPane.add(scrollPane, BorderLayout.WEST);
		contentPane.add(kick, BorderLayout.SOUTH);
		contentPane.add(textArea, BorderLayout.CENTER);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	class KickListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int idx = list.getSelectedIndex();
			int size = model.getSize();
			System.out.println("Current user: "+size);
			if(size <= 0) {
				kick.setEnabled(false);
			}else {
				kick.setEnabled(true);
				String s = (String) model.getElementAt(idx);
				for(ChildThread c : JavaServer.getClients()) {
					if(("Client-"+c.getClientNo()).compareTo(s) == 0)
						try {
							c.infoDisconnect();
							break;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				System.out.println(s+" 님께서 추방되셨습니다!");
				model.remove(idx);
				if(idx == model.getSize())
					idx--;
			}
			list.setSelectedIndex(idx);
			list.ensureIndexIsVisible(idx);
		}
		
	}


	public JTextPane getTextPane() {
		return this.textArea;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
		
	}

}
