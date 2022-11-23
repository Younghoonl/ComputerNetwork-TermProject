package javaserver;

import java.io.IOException;

public class ThreadForStart extends Thread{

	private WorkThread work;
	public ThreadForStart(WorkThread w) {
		this.work = w;
	}
	
	public void run() {
		try {
			work.conn();
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
