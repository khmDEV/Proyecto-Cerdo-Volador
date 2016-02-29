/**
 * @author cristian
 */
package es.pcv;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static void main(String[] args) {		
		JFrame frame = new JFrame("PCV");
		frame.setSize(300, 300);
		frame.add(new JLabel("Hello World!"));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
