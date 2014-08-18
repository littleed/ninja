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
import javax.swing.text.PlainDocument;

/**
 * A spinner incremented and decremented by mouse drags. Might drop trying to
 * use the spinner model unless there's a cleaner way to do the drag evaluation.
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

	public DragSpinner(DragSpinnerModel spinnerModel) {
		changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO Validate
				// TODO Determine if we should update text field?
				try {
					String value = model.getValue().toString();
					textField.setText(value);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		this.model = spinnerModel;
		spinnerModel.addChangeListener(changeListener);

		textField = new JTextField(spinnerModel.getPreferredNumberOfColumns());
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		document = new PlainDocument();
		textFieldDraglistener = new DragSpinnerMouseMotionListener();
		textField.setDocument(document);
		textField.addMouseMotionListener(textFieldDraglistener);

		setLayout(new BorderLayout());
		add(textField);
	}

	private class DragSpinnerMouseMotionListener extends MouseAdapter {
		private Integer origin = null;

		@Override
		public void mousePressed(MouseEvent e) {
			origin = null;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			origin = null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			origin = null;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseExited(e);
		}

		public void mouseDragged(MouseEvent e) {
			if (origin == null) {
				origin = e.getX();
			} else {
				int distance = origin - e.getX();
				if (distance < -5) {
					model.setValue(model.getNextValue());
					origin = e.getX();
				} else if (distance > 5) {
					model.setValue(model.getPreviousValue());
					origin = e.getX();
				}
			}
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
