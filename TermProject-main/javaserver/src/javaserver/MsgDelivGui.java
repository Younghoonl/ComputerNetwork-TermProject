package javaserver;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.StyleSheet;

public class MsgDelivGui {
	private ServerGui gui;
	private JavaServer from;
	private JTextPane textPane;
	StyledDocument doc;
	public MsgDelivGui(JTextPane pane) {
		this.textPane = pane;
	}
	
	public void setGui(ServerGui g) {
		this.gui = g;
//		this.from = g.server;
	}
	public void setFrom(JavaServer f) {
		this.from = f;
	}
	protected void printMsg(String[] args) {
		StyledDocument doc = gui.getTextPane().getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleSheet.DEFAULT_STYLE);
		Style style = doc.addStyle("red", def);
		StyleConstants.setForeground(style, Color.red);
		style = doc.addStyle("blue", def);
		StyleConstants.setForeground(style, Color.blue);
		style= doc.addStyle("black", def);
		StyleConstants.setForeground(style, Color.black);
		String str = "";
		int type = (args[0].compareTo("ROOT") == 0) ? 0 : 1; 
			//0 == root , 1 == client
		if(args[1].compareTo("종료요청") == 0)
			type = -1;
		str += args[0];
		try {
			if(type == 0) {
				doc.insertString(doc.getLength(), str , doc.getStyle("red"));
				doc.insertString(doc.getLength(), " > "+args[1]+"입장\n", doc.getStyle("black"));
			}
			else if(type == 1){
				doc.insertString(doc.getLength(), str , doc.getStyle("blue"));
				doc.insertString(doc.getLength(), " : "+args[1]+"을 입력\n", doc.getStyle("black"));
			}else {
				doc.insertString(doc.getLength(), "ROOT > ", doc.getStyle("red"));
				doc.insertString(doc.getLength(), args[0], doc.getStyle("blue"));
				doc.insertString(doc.getLength(), "가 종료하였습니다.\n", doc.getStyle("black"));
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void printInit() {
		StyledDocument doc = gui.getTextPane().getStyledDocument();
		Style style = doc.addStyle("default", null);
		StyleConstants.setBold(style, true);
		try {
			doc.insertString(0, "서버를 시작합니다\n", style);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
