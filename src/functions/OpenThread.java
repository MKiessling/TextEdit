package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tabbedPane.ClosableTabbedPane;

public class OpenThread extends Thread {
	@Override
	public void run() {
		BufferedReader br;
		try {
			for (int i = 0; i < files.length; i++) {
				br = new BufferedReader(new FileReader(files[i]));
				String tmp = "";
				JTextArea jta = new JTextArea();
				while ((tmp = br.readLine()) != null) {
					jta.append(tmp + "\n");
				}
				tabbedPane.addTab(files[i].getName(), new JScrollPane(jta));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				tabList.add(tabbedPane.getSelectedIndex(), jta);
			}
			statusLabel.setText("Status: ready");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(tabbedPane, "Could not open file");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(tabbedPane, "Could not open file");
		}
		super.run();
	}
	
	public OpenThread(JLabel label, ClosableTabbedPane pane, File[] files, LinkedList<JTextArea> list){
		if (files != null){
		this.tabbedPane = pane;
		this.files = files;
		this.statusLabel = label;
		label.setText("Status: opening ...");
		this.tabList = list;
			start();
		}
	}
	private ClosableTabbedPane tabbedPane;
	private LinkedList<JTextArea> tabList;
	private JLabel statusLabel;
	private File[] files;
}
