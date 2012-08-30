package functions;

import java.util.TimerTask;

import javax.swing.JLabel;

public class Task extends TimerTask {
	public Task(JLabel label){
		this.label = label;
	}
	public void run(){
		label.setText("Status: ready");
	}

	private JLabel label;
}