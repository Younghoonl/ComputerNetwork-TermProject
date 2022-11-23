package javaserver;

import java.io.IOException;

import javax.swing.DefaultListModel;

public class WorkThread extends Thread{
	JavaServer server;
	Thread updateJList;
	int count=0;
	DefaultListModel<String> guiModel;
	public WorkThread(JavaServer jas, DefaultListModel<String> model) {
		this.server = jas;
		this.guiModel = model;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!server.getSvSocket().isClosed()) {
					try {
						if(server.getSvSocket().isClosed()) break;
						if(count != JavaServer.getClients().size()) {
//							Thread.sleep(1000);
//							guiModel.removeAllElements();
							guiModel.clear();
							String[] ar = JavaServer.getClients()
									.stream().map(t -> t.getClientNo())
									.map(String::valueOf).toArray(String[]::new);
							for(String e : ar) {
								System.out.println("...Name : "+e);
								guiModel.addElement("Client-"+e);
							}
							count = JavaServer.getClients().size();
						}
					}				
					catch(Exception e) {
						e.printStackTrace();
						break;
					}
				}
//						guiModel.removeAllElements();
			}
		};
		this.updateJList = new Thread(r);
	}
	
	public void conn() throws IOException, InterruptedException {
//		updateJList.start();
		server.connect();
	}
	
	public void disc() throws IOException, InterruptedException{
		server.disconnect();
	}
}
