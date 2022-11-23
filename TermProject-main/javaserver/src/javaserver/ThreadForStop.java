package javaserver;

import java.io.IOException;

public class ThreadForStop extends Thread{
	
	private WorkThread work;
	public ThreadForStop(WorkThread w) {
		super("Stop_Thread");
		this.work = w;
	}
	
	public void run() {
		try {
			work.disc();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
