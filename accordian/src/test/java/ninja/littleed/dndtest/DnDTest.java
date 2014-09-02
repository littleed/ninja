package ninja.littleed.dndtest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;

public class DnDTest extends JComponent {

	private DefaultTableModel tableModel;
	private JXTable table;
	private JPanel dropTarget;

	public DnDTest() {
		tableModel = new DefaultTableModel(new Object[][] {
				{ "One", "Romeo", "A" }, { "Two", "Juliet", "B" } },
				new String[] { "Number", "Name", "Letter" });
		table = new JXTable(tableModel);
		table.setDragEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DnDObjectHandler<String[]> tableObjectHandler = new DnDObjectHandler<String[]>() {

			public void addObject(String[] object) {
				tableModel.addRow(object);
				tableModel.fireTableDataChanged();
			}

			public String[] getObject() {
				int row = table.getSelectedRow();
				if(row>-1){
					String[] toReturn = new String[tableModel.getColumnCount()];
					for(int i=0; i<toReturn.length; ++i){
						toReturn [i]=tableModel.getValueAt(row, i).toString();
					}
					return toReturn;
				}else{
					return null;
				}
			}
			
		};
		LocalJVMTransferHandler<String[]> tableTransferHandler = new LocalJVMTransferHandler<String[]>(String[].class, tableObjectHandler);
		tableTransferHandler.setReceiver(false);
		table.setTransferHandler(tableTransferHandler);

		dropTarget = new JPanel(new GridLayout(0, 1, 5, 5));
		dropTarget.setBackground(Color.RED);
		
		DnDObjectHandler<String[]> panelObjectHandler = new DnDObjectHandler<String[]>(){

			public void addObject(String[] object) {
				JPanel panel = new JPanel(new GridLayout(0,1,5,5));
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panel.setBackground(Color.WHITE);
				for(String s : object){
					panel.add(new JLabel(s));
				}
				dropTarget.add(panel);
				
			}

			public String[] getObject() {
				return null;
			}
			
		};
		LocalJVMTransferHandler<String[]> panelTransferHandler = new LocalJVMTransferHandler<String[]>(String[].class, panelObjectHandler);
		panelTransferHandler.setGiver(false);
		
		table.setDropTarget(dt);
		table.setTransferHandler(panelTransferHandler);

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.EAST);
		add(dropTarget, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// Set up components
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setContentPane(new DnDTest());
		// Add to frame
		testFrame.setVisible(true);
	}

}
