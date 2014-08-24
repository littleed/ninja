package ninja.littleed.swing.accordian;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Simple GUI test for the JAccordian.
 * 
 * @author LittleEd
 * 
 */
final class JAccordianTest {

	public static void main(String[] args) {
		final JAccordian accordian = new JAccordian();
		accordian.addComponent(getTestTitle("1"), new JLabel(
				"This is a label 1"));
		accordian.addComponent(getTestTitle("2"), new JLabel(
				"This is a label 2"));
		accordian.addComponent(getTestTitle("3"), new JLabel(
				"This is a label 3"));
		accordian.addComponent(getTestTitle("4"), new JLabel(
				"This is a label 4"));
		accordian.addComponent(getTestTitle("5"), new JLabel(
				"This is a label 5"));
		accordian.addComponent(getTestTitle("6"), new JLabel(
				"This is a label 6"));

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
		JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 5));
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

	private static JComponent getTestTitle(String content) {
		JLabel label = new JLabel(content);
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setVerticalAlignment(SwingConstants.TOP);

		JPanel toReturn = new JPanel(new BorderLayout());
		toReturn.setOpaque(false);
		toReturn.add(new JLabel("O"), BorderLayout.NORTH);
		toReturn.add(label, BorderLayout.CENTER);
		return toReturn;
	}
}
