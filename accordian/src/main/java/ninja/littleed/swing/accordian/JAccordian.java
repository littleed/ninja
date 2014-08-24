package ninja.littleed.swing.accordian;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;

/**
 * Accordion component in Swing
 * 
 */
@SuppressWarnings("serial")
public class JAccordian extends JComponent {
	
	private TableLayout tableLayout;

	public JAccordian() {
		tableLayout = new TableLayout(new double[][] { {TableLayout.FILL},
				{ TableLayout.FILL } });
		setLayout(tableLayout);
	}

	public void addComponent(JComponent title, JComponent component) {
		tableLayout.insertColumn(tableLayout.getNumColumn()-1,
				TableLayout.PREFERRED);
		add(new Bellow(title, component), (tableLayout.getNumColumn()-2) + ",0");
		tableLayout.layoutContainer(this);
	}
	
	public void expandAll(){
		for(Component c : getComponents()){
			if(c instanceof Bellow){
				((Bellow)c).expand();
			}
		}
	}
	
	public boolean isAllExpanded(){
		for(Component c : getComponents()){
			if(c instanceof Bellow){
				if(((Bellow)c).isCollapsed()){
					return false;
				}
			}
		}
		return true;
	}
	
	public void collapseAll(){
		for(Component c : getComponents()){
			if(c instanceof Bellow){
				((Bellow)c).collapse();
			}
		}
	}
	
	public boolean isAllCollapsed(){
		for(Component c : getComponents()){
			if(c instanceof Bellow){
				if(!((Bellow)c).isCollapsed()){
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		final JAccordian accordian = new JAccordian();
		accordian.addComponent(getTestTitle("1"), new JLabel("This is a label 1"));
		accordian.addComponent(getTestTitle("2"), new JLabel("This is a label 2"));
		accordian.addComponent(getTestTitle("3"), new JLabel("This is a label 3"));
		accordian.addComponent(getTestTitle("4"), new JLabel("This is a label 4"));
		accordian.addComponent(getTestTitle("5"), new JLabel("This is a label 5"));
		accordian.addComponent(getTestTitle("6"), new JLabel("This is a label 6"));
		
		JButton expand = new JButton("Expand");
		expand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accordian.expandAll();
			}
		});
		JButton collapse = new JButton("Collapse");
		collapse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accordian.collapseAll();
			}
		});
		JPanel buttonPanel = new JPanel(new GridLayout(1,0,5,5));
		buttonPanel.setOpaque(false);
		buttonPanel.add(expand);
		buttonPanel.add(collapse);

		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setTitle("Accordion Test");
		testFrame.setLayout(new BorderLayout());
		testFrame.add(new JScrollPane(accordian), BorderLayout.CENTER);
		testFrame.add(buttonPanel, BorderLayout.SOUTH);
		testFrame.setVisible(true);
	}
	
	private static JComponent getTestTitle(String content){
		JLabel label = new JLabel(content);
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel toReturn = new JPanel(new BorderLayout());
		toReturn.setOpaque(false);
		toReturn.add(new JLabel("O"), BorderLayout.NORTH);
		toReturn.add(label, BorderLayout.CENTER);
		return toReturn;
	}

	private class Bellow extends JComponent {
		private JToggleButton trigger;
		//TODO Switch for SCollapsiblePane to resolve duration issues
		private JXCollapsiblePane collapsiblePane;

		Bellow(JComponent title, JComponent component) {
			trigger = new JToggleButton();
			trigger.setLayout(new BorderLayout());
			trigger.add(title);
			trigger.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					collapsiblePane.setCollapsed(!trigger.isSelected());
				}
			});
			collapsiblePane = new JXCollapsiblePane(Direction.RIGHT);
			collapsiblePane.setContentPane(component);
			setLayout(new BorderLayout());
			add(trigger, BorderLayout.WEST);
			add(collapsiblePane, BorderLayout.EAST);
			collapsiblePane.setCollapsed(true);
		}
		
		void expand(){
			if(!trigger.isSelected()){
				System.out.println("Not expanded, so expanding?");
				trigger.doClick();
			}
		}
		
		boolean isCollapsed(){
			return collapsiblePane.isCollapsed();
		}
		
		void collapse(){
			if(trigger.isSelected()){
				System.out.println("Not collapsed, so collapsing?");
				trigger.doClick();
			}
		}
	}
}
