import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Mon Oct 02 23:54:30 CEST 2023
 */



/**
 * @author bened
 */
public class test extends JFrame {
	public test() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Kincses Benedek
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button6 = new JButton();
		button7 = new JButton();
		button8 = new JButton();
		button5 = new JButton();
		button9 = new JButton();
		layeredPane1 = new JLayeredPane();
		panel1 = new JPanel();
		button10 = new JButton();
		button11 = new JButton();
		label2 = new JLabel();

		//======== this ========
		Container contentPane = getContentPane();

		//---- button1 ----
		button1.setText("INVENTORY");

		//---- button2 ----
		button2.setText("TRAVEL");

		//---- button3 ----
		button3.setText("text");

		//---- button4 ----
		button4.setText("text");

		//---- button6 ----
		button6.setText("text");

		//---- button7 ----
		button7.setText("text");

		//---- button8 ----
		button8.setText("text");

		//---- button5 ----
		button5.setText("text");

		//---- button9 ----
		button9.setText("text");

		//======== layeredPane1 ========
		{

			//======== panel1 ========
			{
				panel1.setBackground(new Color(0x003c3f41, true));
				panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
				( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border
				. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
				. Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
				propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( )
				; }} );
				panel1.setLayout(new GridLayout(12, 16));

				//---- button10 ----
				button10.setText("text");
				button10.setBackground(Color.white);
				panel1.add(button10);

				//---- button11 ----
				button11.setText("text");
				button11.setBackground(Color.white);
				panel1.add(button11);
			}
			layeredPane1.add(panel1, JLayeredPane.DEFAULT_LAYER);
			panel1.setBounds(0, 0, 990, 725);

			//---- label2 ----
			label2.setText("text");
			label2.setIcon(new ImageIcon(getClass().getResource("/src/resources/img/maps/1.png")));
			layeredPane1.add(label2, JLayeredPane.DEFAULT_LAYER);
			label2.setBounds(0, 0, 990, 725);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button1, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(button2, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(button3, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
								.addComponent(button4, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
							.addGap(18, 18, 18)
							.addComponent(layeredPane1, GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE))
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addGap(0, 670, Short.MAX_VALUE)
							.addComponent(button5, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button6, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button7, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button8, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button9, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
					.addGap(39, 39, 39))
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(contentPaneLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
							.addComponent(button1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(28, 28, 28)
							.addComponent(button3, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(18, 18, 18)
							.addComponent(button4, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addComponent(layeredPane1, GroupLayout.PREFERRED_SIZE, 725, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button6, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(button7, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(button8, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(button5, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(button9, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Evaluation license - Kincses Benedek
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button5;
	private JButton button9;
	private JLayeredPane layeredPane1;
	private JPanel panel1;
	private JButton button10;
	private JButton button11;
	private JLabel label2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
