package functions;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FileOpenSave extends FileDialog {

	public FileOpenSave(JFrame frame) {
		super(frame);
	}

	// open file dialog
	public File[] openFile(FilenameFilter filter, boolean multipleSelection) {
		this.setMode(FileDialog.LOAD);
		this.setFilenameFilter(filter);
		this.setMultipleMode(multipleSelection);
		this.setVisible(true);
		if (this.getFile() != null) {
			files = this.getFiles();
		}
		return files;
	}

	// save file dialog
	public File[] saveFile() {
		this.setMode(FileDialog.SAVE);
		this.setVisible(true);
		if (this.getFile() != null) {
			files = this.getFiles();
		}
		return files;
	}

	private File[] files;
}