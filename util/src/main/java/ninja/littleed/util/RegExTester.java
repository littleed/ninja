package ninja.littleed.util;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;

public class RegExTester extends JPanel {

	private JXTextField inputField;
	private JXTable table;
	private DefaultTableModel tableModel;
	private List<RowFilter<Object, Object>> searchTerms;
	private RowFilter<Object,Object> orFilter;
	private TableRowSorter<DefaultTableModel> sorter;

	public RegExTester() {
		
		
		tableModel = new DefaultTableModel(new Object[][] { { "One", "Two" },{"Tom", "Jump"} }, new Object[] {
				"ColA", "ColB" });
		
		table = new JXTable(tableModel);
		
		searchTerms = new ArrayList<RowFilter<Object,Object>>();
		sorter = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(sorter);
		
		inputField = new JXTextField();
		inputField.setPrompt("Sample Input");
		inputField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				match();
			}
			
		});
		TableLayout tableLayout = new TableLayout(new double[][] {
				{ TableLayout.FILL, 250, TableLayout.FILL },
				{ TableLayout.PREFERRED, TableLayout.FILL } });
		tableLayout.setVGap(5);
		setLayout(tableLayout);
		add(inputField, "1,0");
		add(new JScrollPane(table), "1,1");
		match();
	}

	private int[] getRows() {
		int[] rows = new int[table.getRowCount()];
		for (int i = 0; i < rows.length; ++i) {
			rows[i] = i;
		}
		return rows;
	}

	private void match() {
		int[] rows = getRows();
		searchTerms.clear();
		for (String term : inputField.getText().split(" ")) {
			searchTerms.add(RowFilter.regexFilter(term, rows));
		}
		if(searchTerms.size()<1){
			searchTerms.add(RowFilter.regexFilter("*", rows));
		}
		
		sorter.setRowFilter(RowFilter.orFilter(searchTerms));
		tableModel.fireTableDataChanged();
	}

	public static void main(String[] args) {
		// Set up components
		JPanel p = new JPanel(new BorderLayout());
		p.add(new RegExTester(), BorderLayout.CENTER);
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setContentPane(p);
		// Add to frame
		testFrame.setVisible(true);
	}
}
