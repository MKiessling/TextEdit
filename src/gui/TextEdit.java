package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import functions.FileOpenSave;
import functions.OpenThread;
import functions.SaveThread;

@SuppressWarnings("serial")
public class TextEdit extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextEdit frame = new TextEdit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TextEdit() {
		initGUI();
	}

	/**
	 * build gui
	 */
	private void initGUI() {
		// *************************************
		// set frame preferences
		// *************************************
		setMinimumSize(new Dimension(640, 480));
		setTitle("TextEdit");
		setSize(new Dimension(640, 480));
		setPreferredSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		menuBar.setBackground(UIManager
				.getColor("OptionPane.questionDialog.border.background"));
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		tabbedPane.setBorder(null);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusPanel.add(statusLabel);

		// *************************************
		// add components to menu
		// *************************************
		// file-menu
		menuBar.add(mnFile);
		// new
		mnFile.add(mntmNew);
		mntmNew.setIcon(new ImageIcon(TextEdit.class
				.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		// open
		mnFile.add(mntmOpen);
		mntmOpen.setIcon(new ImageIcon(
				TextEdit.class
						.getResource("/com/sun/java/swing/plaf/windows/icons/TreeOpen.gif")));
		// save
		mnFile.add(mntmSave);
		mntmSave.setIcon(new ImageIcon(
				TextEdit.class
						.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mntmSave.setEnabled(false);
		// exit
		mnFile.add(mntmExit);
		mntmExit.setIcon(new ImageIcon(TextEdit.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));

		// *************************************
		// mnemonics (menu-shortcuts)
		// *************************************
		// file-menu
		mnFile.setMnemonic('F');
		// new
		mntmNew.setMnemonic('N');
		mntmNew.setAccelerator(KeyStroke
				.getKeyStroke('N', InputEvent.CTRL_MASK));
		// open
		mntmOpen.setMnemonic('O');
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_MASK));
		// save
		mntmSave.setMnemonic('S');
		mntmSave.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_MASK));
		// exit
		mntmExit.setMnemonic('x');

		// *************************************
		// listener
		// *************************************
		mntmNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mntmNewActionPeformed();
			}
		});

		tabbedPane.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				enableSave();
			}

			@Override
			public void componentRemoved(ContainerEvent e) {
				if (tabbedPane.getSelectedIndex() == -1) {
					tabList.remove(tabbedPane.getSelectedIndex() + 1);
				} else {
					tabList.remove(tabbedPane.getSelectedIndex());
				}
				disableSave();
			}

		});

		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmOpenActionPerformed();
			}
		});

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmSaveActionPerformed();
			}
		});
	}

	// *************************************
	// menu functions
	// *************************************
	private void disableSave() {
		if (tabbedPane.getTabCount() == 0) {
			mntmSave.setEnabled(false);
		}
	}

	private void enableSave() {
		if (!mntmSave.isEnabled()) {
			mntmSave.setEnabled(true);
		}
	}

	// *************************************
	// action-performed methods
	// *************************************
	private void mntmSaveActionPerformed() {
		new SaveThread(statusLabel, tabbedPane, new FileOpenSave(this).saveFile(), tabList);
	}

	private void mntmOpenActionPerformed() {
		new OpenThread(statusLabel, tabbedPane, new FileOpenSave(this).openFile(filterTXT, true),
				tabList);
	}

	// SwingUtilities.invokeLater(new Runnable() {
	//
	// @Override
	// public void run() {
	// progressBar.setIndeterminate(true);
	// }
	// });

	private void mntmNewActionPeformed() {
		JTextArea jta = new JTextArea();
		jta.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				tabbedPane.setSaved(0);
			}
		});
		tabbedPane.addTab("Untitled" + (++x), new JScrollPane(jta));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		tabList.add(tabbedPane.getSelectedIndex(), jta);
		jta.requestFocus();
	}

	// *************************************
	// variables, objects
	// *************************************
	// panels
	private JPanel contentPane;
	private final tabbedPane.ClosableTabbedPane tabbedPane = new tabbedPane.ClosableTabbedPane();

	// menu
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnFile = new JMenu("File");
	private final JMenuItem mntmNew = new JMenuItem("New");
	private final JMenuItem mntmOpen = new JMenuItem("Open");
	private final JMenuItem mntmSave = new JMenuItem("Save");
	private final JMenuItem mntmExit = new JMenuItem("Exit");

	// dialogs
	private FilenameFilter filterTXT = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String s) {
			return (s.toLowerCase().endsWith(".txt"));
		}
	};

	// other
	private int x = 0;
	private LinkedList<JTextArea> tabList = new LinkedList<JTextArea>();
	private final JPanel statusPanel = new JPanel();
	private final JLabel statusLabel = new JLabel("Status: ready");
}