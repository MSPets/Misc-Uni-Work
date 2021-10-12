package com.songer.michael.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.songer.michael.functions.DatabaseController;
import com.songer.michael.functions.MakeChart;

public class LoginGUI implements ActionListener{
	private JPanel loginPanel = new JPanel();

	private JButton backBtn = new JButton("<---");
	private JTextField usernameTF = new JTextField();
	private JPasswordField passwordTF = new JPasswordField();
	private JButton loginBtn = new JButton("Login");
	
	private JFrame mainFrame;
	
	private StartGUI par;
	
	private Border redBorder = BorderFactory.createLineBorder(Color.red, 1);
	private Border defaultBorder = usernameTF.getBorder();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(backBtn))
		{
			System.out.println("Back button pressed");
			par.backButton();
		}
		else if(e.getSource().equals(loginBtn))
		{
			// Attempt Login
			System.out.println("Login button pressed");
			if(usernameTF.getText().isBlank())
			{
				usernameTF.setBorder(redBorder);
			}
			else if(passwordTF.getPassword().length == 0)
			{
				passwordTF.setBorder(redBorder);
			}
			else
			{
				String pass = DatabaseController.readFile(usernameTF.getText());
				if(pass.isBlank())
				{
					System.out.println("No user");
					usernameTF.setText("");
					usernameTF.setBorder(defaultBorder);
					passwordTF.setText("");
					passwordTF.setBorder(defaultBorder);
				}
				else
				{
					if (Arrays.equals(pass.toCharArray(), passwordTF.getPassword()))
					{
						new QuestionnaireChartsGUI(this.par, mainFrame);
						System.out.println("Correct");
					}
				}
			}
		}
	}
	
	public LoginGUI(StartGUI par, JFrame mainFrame) {
		System.out.println("LOGIN GUI");
		this.par = par;
		this.mainFrame = mainFrame;
		
		// Remove old Panel
		mainFrame.getContentPane().removeAll();
		
		// Setup Panel
		paintGUI();
		
		// login button control
		backBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		
		// Add new panel
		mainFrame.getContentPane().add(new JScrollPane(loginPanel));
		
		mainFrame.validate();
		mainFrame.repaint();
	}
	
	private void paintGUI()
	{
		System.out.println("Painting login GUI");
		
		loginPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 1;
		//gbc.ipadx = 20;
		//gbc.ipady = 20;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0,10,0,10);
		
		int gridx = 0;
		int gridy = 0;
		
		// Back button top left
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		loginPanel.add(backBtn, gbc);
		
		// Username
		gbc.gridx = gridx;
		gbc.gridy = ++gridy;
		loginPanel.add(new JLabel("Username", JLabel.CENTER), gbc);
		gbc.gridx = ++gridx;
		gbc.gridy = gridy;
		loginPanel.add(usernameTF, gbc);
		
		// Password
		gbc.gridx = --gridx;
		gbc.gridy = ++gridy;
		loginPanel.add(new JLabel("Password", JLabel.CENTER), gbc);
		gbc.gridx = ++gridx;
		gbc.gridy = gridy;
		loginPanel.add(passwordTF, gbc);
		
		// Login Button
		gbc.gridx = gridx;
		gbc.gridy = ++gridy;
		loginPanel.add(loginBtn, gbc);
		
	}
}
