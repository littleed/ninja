package ninja.littleed.swing.dragspinner;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ninja.littleed.swing.dragspinner.integer.IntegerSpinnerModel;
import ninja.littleed.swing.dragspinner.integer.IntegerStringConverter;

class TestDragSpinner {

	private DragSpinner<Integer> dragSpinner;
	private String currentControlLocation;

	public TestDragSpinner() {

		IntegerSpinnerModel model = new IntegerSpinnerModel();
		model.setMin(1900);
		model.setMax(3000);
		model.setValue(Calendar.getInstance().get(Calendar.YEAR));
		currentControlLocation=BorderLayout.SOUTH;

		dragSpinner = new DragSpinner<Integer>();
		dragSpinner.setModel(model);
		dragSpinner.setColumns(4);
		dragSpinner.setControlLocation(currentControlLocation);
		dragSpinner.setRenderer(new IntegerStringConverter());

		JButton orientation = new JButton("Switch Orientation");
		orientation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchOrientation();
			}
		});

		// Main panel
		JPanel panel = new JPanel(new TableLayout(new double[][] {
				{ TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL },
				{ TableLayout.FILL, TableLayout.PREFERRED,
						TableLayout.PREFERRED, TableLayout.FILL } }));
		panel.add(dragSpinner, "1,1");
		panel.add(orientation, "1,2");

		// Containing frame
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setContentPane(panel);
		testFrame.setVisible(true);
	}
	
	private void switchOrientation(){
		if(currentControlLocation==BorderLayout.NORTH){
			currentControlLocation=BorderLayout.EAST;
		}else if(currentControlLocation==BorderLayout.EAST){
			currentControlLocation=BorderLayout.SOUTH;
		}else if(currentControlLocation==BorderLayout.SOUTH){
			currentControlLocation=BorderLayout.WEST;
		}else if(currentControlLocation==BorderLayout.WEST){
			currentControlLocation=BorderLayout.NORTH;
		}
		dragSpinner.setControlLocation(currentControlLocation);
	}

	public static void main(String[] args) {
		new TestDragSpinner();
	}

}
