package ninja.littleed.dndtest;

import info.clearthought.layout.TableLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class DropComponent extends JComponent {
	
	public DropComponent(Object[] data){
		TableLayout layout = new TableLayout(new double[][]{{TableLayout.PREFERRED, TableLayout.PREFERRED},{}});
		layout.setHGap(5);
		layout.setVGap(5);
		int i=0;
		for(Object o : data){
			layout.insertRow(i, TableLayout.PREFERRED);
			++i;
		}
		setLayout(layout);
		i=0;
		for(Object o : data){
			add(new JLabel(i + ")"), "0," + i);
			add(new JLabel(""+o), "1," + i);
		}
	}
}
