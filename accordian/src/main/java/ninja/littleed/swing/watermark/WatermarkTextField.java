package ninja.littleed.swing.watermark;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXTextField;

import com.kitfox.svg.SVGCache;
import com.kitfox.svg.app.beans.SVGIcon;

public class WatermarkTextField extends JXTextField {

	private SVGIcon search;

	public WatermarkTextField() {
		loadSearchIcon();
	}

	private void loadSearchIcon() {
setPrompt("Search...");
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream("/search.svg");
			
			URI searchURI = SVGCache.getSVGUniverse().loadSVG(is, "search");
			// SVGElement element =
			// SVGCache.getSVGUniverse().getElement(searchURI);
			search = new SVGIcon(); 
			search.setSvgURI(searchURI);
			search.setPreferredSize(new Dimension(getPreferredSize().height, getPreferredSize().height));
			search.setScaleToFit(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
//		int x = getWidth()-search.getIconWidth();
//		search.paintIcon(this, g, 0, 0);
	}

	public static void main(String[] args) {
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new WatermarkTextField(), BorderLayout.NORTH);
		panel.add(new JButton(),BorderLayout.WEST);
		// Set up components
		JFrame testFrame = new JFrame();
		testFrame.setSize(800, 600);
		testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		testFrame.setContentPane(panel);
		// Add to frame
		testFrame.setVisible(true);
	}

}
