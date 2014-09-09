package ninja.littleed.swing.accordian;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.MatteBorderExt;
import org.jdesktop.swingx.painter.AbstractLayoutPainter.VerticalAlignment;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;


/**
 * A single bellow in the JAccordian. Contains a component and a title
 * component.
 * 
 * @author LittleEd
 * 
 */
@SuppressWarnings("serial")
class JAccordianBellow extends JPanel {
	
	private Color spineColour;
	private MattePainter mouseOver;
	private MattePainter mouseOut;
	
	private JXPanel triggerPanel;
	private JToggleButton trigger;
	// TODO Switch for SCollapsiblePane to resolve duration issues
	private JXCollapsiblePane collapsiblePane;

	/**
	 * Create a new bellow with the title component and main component provided.
	 * 
	 * @param title
	 * @param component
	 */
	JAccordianBellow(JComponent title, JComponent component, Color spineColour) {
		this.spineColour = spineColour;
		mouseOver = new MattePainter(new GradientPaint(0f,0f, new Color(255,255,255,0), 0.5f,0f, Color.lightGray));
		mouseOut = new MattePainter(new GradientPaint(0f,0f, Color.lightGray, 0.2f,0f, new Color(255,255,255,0)));
		trigger = new JToggleButton();
		trigger.setOpaque(false);
		trigger.setContentAreaFilled(false);
		trigger.setLayout(new BorderLayout());
		trigger.add(title);
		trigger.setBorder(BorderFactory.createEmptyBorder());
		trigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				collapsiblePane.setCollapsed(!trigger.isSelected());
			}
		});
		triggerPanel = new JXPanel(new BorderLayout());
		triggerPanel.addComponentListener(new ComponentListener() {
			
			public void componentShown(ComponentEvent e) {
				recalcPaint();
			}
			
			public void componentResized(ComponentEvent e) {
				recalcPaint();	
			}
			
			public void componentMoved(ComponentEvent e) {
				recalcPaint();
			}
			
			public void componentHidden(ComponentEvent e) {
				recalcPaint();
			}
		});
		triggerPanel.setOpaque(false);
		triggerPanel.setBackgroundPainter(mouseOut);
		triggerPanel.add(trigger);
		triggerPanel.add(Box.createHorizontalStrut(35), BorderLayout.SOUTH);
		collapsiblePane = new JXCollapsiblePane(Direction.RIGHT);
		collapsiblePane.setContentPane(component);
		
		collapsiblePane.setBorder(new BellowContentBorder(spineColour, 15, true));
		setLayout(new BorderLayout());
		add(triggerPanel, BorderLayout.WEST);
		add(collapsiblePane, BorderLayout.EAST);
		collapsiblePane.setCollapsed(true);
		trigger.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent e) {
				triggerPanel.setBackgroundPainter(mouseOver);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				triggerPanel.setBackgroundPainter(mouseOut);
			}
			
		});
		setOpaque(false);
	}
	
	private void recalcPaint(){
		int w = triggerPanel.getWidth();
		int h = triggerPanel.getHeight();
		GradientPaint gp1 = new GradientPaint((float)(0.2*w), 0f, Color.white, (float)(0.8*w), 0f, spineColour, true);
		GradientPaint gp2 = new GradientPaint((float)(0.4*w), 0f, Color.white, (float)(1.3*w), 0f, spineColour, true);
		mouseOut.setFillPaint(gp1);
		mouseOver.setFillPaint(gp2);
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