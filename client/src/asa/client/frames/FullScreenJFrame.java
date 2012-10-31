package asa.client.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FullScreenJFrame extends JFrame implements Observer{

	JPanel rootPanel;
	JButton closeButton;

	public FullScreenJFrame(String title) {
		super(title);

		rootPanel = new JPanel();
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Close button Pressed");
				dispose();
			}
		});

		setLayout();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);
	}

	private void setLayout() {
		GroupLayout rootPanelLayout = new GroupLayout(rootPanel);
		rootPanel.setLayout(rootPanelLayout);
		GroupLayout.SequentialGroup rootPanelHorLayout = rootPanelLayout.createSequentialGroup();
		rootPanelHorLayout.addContainerGap(290, Short.MAX_VALUE);
		rootPanelHorLayout.addComponent(closeButton);
		rootPanelHorLayout.addContainerGap(290, Short.MAX_VALUE);
		rootPanelLayout.setHorizontalGroup(rootPanelHorLayout);

		GroupLayout.SequentialGroup rootPanelVerLayout = rootPanelLayout.createSequentialGroup();
		rootPanelVerLayout.addContainerGap(185, Short.MAX_VALUE);
		rootPanelVerLayout.addComponent(closeButton);
		rootPanelVerLayout.addContainerGap(186, Short.MAX_VALUE);
		rootPanelLayout.setVerticalGroup(rootPanelVerLayout);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		GroupLayout.ParallelGroup horLayout = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		horLayout.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
		layout.setHorizontalGroup(horLayout);

		GroupLayout.ParallelGroup verLayout = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		verLayout.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
		layout.setVerticalGroup(verLayout);
	}

	@Override
	public void update(Observable observable, Object object) {
		String jsonString = (String) object;
		System.out.println(jsonString);
		closeButton.setText(jsonString);
	}
}