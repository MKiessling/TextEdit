package functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import tabbedPane.ClosableTabbedPane;

public class SaveThread extends Thread {
	@Override
	public void run() {
		String ending = "";
		if (files[0].getName().substring(files[0].getName().length() - 3,
				files[0].getName().length()) != "txt") {
			ending = ".txt";
		}
		try {
			tabList.get(tabbedPane.getSelectedIndex()).write(
					new FileWriter(
							new File(files[0].getAbsolutePath() + ending)));
			tabbedPane.setSaved(1);
			tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),
					files[0].getName());
			statusLabel.setText("Status: " + files[0].getName() + " saved");
			Thread.sleep(5000);
			statusLabel.setText("Status: ready");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(tabbedPane, "Could not save file");
			statusLabel.setText("Status: Could not save file");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(tabbedPane, "Could not save file");
			statusLabel.setText("Status: Could not save file");
		}
		super.run();
	}

	public SaveThread(JLabel label, ClosableTabbedPane pane, File[] files,
			LinkedList<JTextArea> list) {
		if (files != null) {
			this.tabbedPane = pane;
			this.files = files;
			this.statusLabel = label;
			label.setText("Status: saving ...");
			this.tabList = list;
			start();
		}
	}

	private ClosableTabbedPane tabbedPane;
	private LinkedList<JTextArea> tabList;
	private JLabel statusLabel;
	private File[] files;
}
