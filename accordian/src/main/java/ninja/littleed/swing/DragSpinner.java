package ninja.littleed.swing;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * An integer spinner with a slide.
 * 
 * @author LittleEd
 * 
 */
public class DragSpinner extends JComponent {

	private DragSpinnerModel model;
	private ChangeListener changeListener;
	private JTextField textField;
	private MouseMotionListener textFieldDraglistener;
	private PlainDocument document;

	// Buttons show, but we don't like buttons
	public DragSpinner() {
		this(new DefaultSpinnerModel());
	}

	public DragSpinner(DragSpinnerModel model) {
		changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO Validate
				// TODO Determine if we should update text field?
			}
		};
		this.model = model;
		model.addChangeListener(changeListener);
		
		textField = new JTextField(model.getPreferredNumberOfColumns());
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		document = new PlainDocument();
		document.setDocumentFilter(new DragSpinnerDocumentFilter());
		textFieldDraglistener = new DragSpinnerMouseMotionListener();
		textField.setDocument(document);
		textField.addMouseMotionListener(textFieldDraglistener);

		setLayout(new BorderLayout());
		add(textField);
	}
	
	private class DragSpinnerMouseMotionListener extends MouseAdapter {
		private int lastPosition=0;
		
		@Override
		public void mousePressed(MouseEvent e) {
			lastPosition=0;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			lastPosition=0;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			lastPosition=0;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
		}

		public void mouseDragged(MouseEvent e) {
			//Increment last position
		}
	}

	private class DragSpinnerDocumentFilter extends DocumentFilter {

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException {
			// Need to determine total value
			super.remove(fb, offset, length);
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException {
			super.insertString(fb, offset, string, attr);
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length,
				String text, AttributeSet attrs) throws BadLocationException {
			super.replace(fb, offset, length, text, attrs);
		}

	}

	public static void main(String[] args) {
		// Set up components
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Add to frame
		JPanel panel = new JPanel(
				new TableLayout(new double[][] {
						{ TableLayout.FILL, TableLayout.PREFERRED,
								TableLayout.FILL },
						{ TableLayout.FILL, TableLayout.PREFERRED,
								TableLayout.FILL } }));
		panel.add(new DragSpinner(), "1,1");
		testFrame.setContentPane(panel);
		testFrame.setVisible(true);
	}
}
