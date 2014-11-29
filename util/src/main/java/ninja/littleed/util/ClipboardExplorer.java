package ninja.littleed.util;

import info.clearthought.layout.TableLayout;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Demo for the transference of 
 * @author LittleEd
 *
 */
@SuppressWarnings("serial")
public class ClipboardExplorer extends JPanel implements ClipboardOwner {
	
	private JComboBox<MergeField> mergeFields;
	
	private JButton exportClipboard;
	
	private JList<MergeField> list;

	public ClipboardExplorer() {

		mergeFields = new JComboBox<MergeField>(MergeField.values());
		
		exportClipboard = new JButton("Copy to Clipboard");
		exportClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportClipboard();
			}
		});
		MergeFieldTransferHandler handler = new MergeFieldTransferHandler();
		list = new JList<MergeField>(MergeField.values());
		list.setTransferHandler(handler);
		list.setDragEnabled(true);
		
		setLayout(new TableLayout(new double[][] {
				{ TableLayout.FILL, TableLayout.PREFERRED,
						TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.FILL },
				{ TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL } }));
		add(new JLabel("Merge Field:  "), "1,1");
		add(mergeFields, "2,1");
		add(exportClipboard, "3,1");
		add(list, "2,3");
	}


	public void exportClipboard() {
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		MergeFieldTransferable wd = new MergeFieldTransferable((MergeField)mergeFields.getSelectedItem());
		c.setContents(wd, this);
	}

	public static void main(String[] args) {

		// Set up components
		JFrame testFrame = new JFrame();
		testFrame.setAlwaysOnTop(true);
		testFrame.setSize(500, 300);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.add(new ClipboardExplorer());
		testFrame.setVisible(true);

	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		System.out.println("AWWW NOOO! I've lost ownership! ... What does that mean?");
	}

}
