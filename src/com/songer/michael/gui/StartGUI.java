package com.songer.michael.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.songer.michael.functions.MakeChart;
import com.songer.michael.functions.SaveQuestionnaire;

public class StartGUI extends JFrame implements ActionListener{
	
	JFrame mainFrame = new JFrame("Questionnaire GUI");
	JPanel startPanel = new JPanel();
	
	JButton newQuestionnaireBtn = new JButton("New Questionnaire");
	JButton loginBtn = new JButton("View Graphs");
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newQuestionnaireBtn))
		{
			System.out.println("Questionnaire Button Pressed");
			new QuestionnaireGUI(this, mainFrame);
		}
		else if (e.getSource().equals(loginBtn))
		{
			System.out.println("Login Button Pressed");
			new LoginGUI(this, mainFrame);
		}
	}
	public StartGUI()
	{
		System.out.println("START GUI");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(300,300);
		
		paintGUI();
	}
	private void paintGUI()
	{
		System.out.println("Painting start GUI");
		
		mainFrame.getContentPane().removeAll();
		newQuestionnaireBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		
		startPanel.setLayout(new GridLayout(2,1));
		startPanel.add(newQuestionnaireBtn);
		startPanel.add(loginBtn);
		
		mainFrame.add(startPanel);
		
		mainFrame.validate();
		mainFrame.repaint();
		
		mainFrame.setVisible(true);
	}
	public void backButton() {
		paintGUI();
	}

	public static void main(String[] args)
	{
		//new MakeChart();
		//SaveQuestionnaire saves = new SaveQuestionnaire();
		//saves.save("ss","ss","ss","ss","ss","ss");
		
		// run startup
		new StartGUI();
	}
}
