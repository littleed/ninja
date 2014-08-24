package ninja.littleed.swing.accordian;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;

/**
 * A single bellow in the JAccordian. Contains a component and a title
 * component.
 * 
 * @author LittleEd
 * 
 */
@SuppressWarnings("serial")
class JAccordianBellow extends JComponent {
	private JToggleButton trigger;
	// TODO Switch for SCollapsiblePane to resolve duration issues
	private JXCollapsiblePane collapsiblePane;

	/**
	 * Create a new bellow with the title component and main component provided.
	 * 
	 * @param title
	 * @param component
	 */
	JAccordianBellow(JComponent title, JComponent component) {
		trigger = new JToggleButton();
		trigger.setLayout(new BorderLayout());
		trigger.add(title);
		trigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
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

	/**
	 * Expands the component if not already.
	 */
	void expand() {
		if (!trigger.isSelected()) {
			trigger.doClick();
		}
	}

	/**
	 * Returns true if currently collapsed.
	 * 
	 * @return
	 */
	boolean isCollapsed() {
		return collapsiblePane.isCollapsed();
	}

	/**
	 * Collapse the component if not collapsed already.
	 */
	void collapse() {
		if (trigger.isSelected()) {
			trigger.doClick();
		}
	}
}
