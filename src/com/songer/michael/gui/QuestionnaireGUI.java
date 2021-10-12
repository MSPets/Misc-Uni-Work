package com.songer.michael.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import com.songer.michael.functions.DatabaseController;
import com.songer.michael.functions.SaveQuestionnaire;

public class QuestionnaireGUI implements ActionListener
{
	private StartGUI par;
	private JFrame mainFrame;
	private JPanel qPanel = new JPanel();

	private JButton backBtn = new JButton ("<--");
	private JButton submitBtn = new JButton("Submit");

	// Questions
	private ArrayList<JLabel> questionsAL = new ArrayList<JLabel>();
	private ArrayList<ArrayList<JRadioButton>> ansAL = new ArrayList<ArrayList<JRadioButton>>();
	private ArrayList<ButtonGroup> bGroupAL = new ArrayList<ButtonGroup>();

	public void actionPerformed (ActionEvent e) {
		if (e.getSource().equals(backBtn))
		{
			System.out.println("Back button pressed");
			par.backButton();
		}
		else if (e.getSource().equals(submitBtn))
		{
			// Temp variables
			boolean ansBool = false;
			boolean allAns = true;
			ArrayList<JRadioButton> tempAns;
			JRadioButton tempRB;
			JLabel tempL;
			String userAnsStr = "";
			// Check for answers
			// Cycle questions
			for(int q=0; q<questionsAL.size(); q++)
			{
				ansBool = false;
				tempAns = ansAL.get(q);
				// Cycle answers
				for (int ans=0; ans<tempAns.size(); ans++)
				{
					tempRB = tempAns.get(ans);
					if(tempRB.isSelected())
					{
						ansBool = true;
						// Match questions ans answers
						tempL = questionsAL.get(q);
						userAnsStr += tempL.getText() + ":" + tempRB.getText();
						if (!(q+1==questionsAL.size()))
						{
							userAnsStr += ",";
						}
					}
				}
				if (ansBool == false)
				{
					allAns = false;
					tempL = questionsAL.get(q);
					tempL.setBorder(BorderFactory.createLineBorder(Color.red, 1));
				}
			}
			System.out.println("All answered " + allAns);
			System.out.println("UserAnsStr " + userAnsStr);
			if (allAns)
			{
				SaveQuestionnaire.save(userAnsStr);
				
				par.backButton();
			}
			else
			{
				// Refresh frame
				mainFrame.validate();
				mainFrame.repaint();
			}
		}
	}

	public QuestionnaireGUI(StartGUI par, JFrame mainFrame)
	{
		System.out.println("QUESTIONNAIRE GUI");
		this.par = par;
		this.mainFrame = mainFrame;

		// Remove old Panel
		mainFrame.getContentPane().removeAll();

		setupQuestions();
		// Setup Panel
		paintGUI();

		// login button control
		backBtn.addActionListener(this);
		submitBtn.addActionListener(this);

		// Add new panel
		mainFrame.getContentPane().add(new JScrollPane(qPanel));
		mainFrame.validate();
		mainFrame.repaint();
	}

	private void setupQuestions()
	{
		ArrayList<JRadioButton> tempAnsAL = new ArrayList<JRadioButton>();
		ButtonGroup tempButtonGroup = new ButtonGroup();

		// Q1
		questionsAL.add(new JLabel("Question 1"));
		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans4"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans5"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);

		// Q2
		tempAnsAL = new ArrayList<JRadioButton>();
		tempButtonGroup = new ButtonGroup();

		questionsAL.add(new JLabel("Question 2"));

		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);

		// Q3
		tempAnsAL = new ArrayList<JRadioButton>();
		tempButtonGroup = new ButtonGroup();

		questionsAL.add(new JLabel("Question 3"));

		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);

		// Q4
		tempAnsAL = new ArrayList<JRadioButton>();
		tempButtonGroup = new ButtonGroup();

		questionsAL.add(new JLabel("Question 4"));

		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);

		// Q5
		tempAnsAL = new ArrayList<JRadioButton>();
		tempButtonGroup = new ButtonGroup();

		questionsAL.add(new JLabel("Question 5"));

		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);

		// Q6
		tempAnsAL = new ArrayList<JRadioButton>();
		tempButtonGroup = new ButtonGroup();

		questionsAL.add(new JLabel("Question 6"));

		tempAnsAL.add(new JRadioButton("ans1"));
		tempButtonGroup.add(tempAnsAL.get(0));
		tempAnsAL.add(new JRadioButton("ans2"));
		tempButtonGroup.add(tempAnsAL.get(1));
		tempAnsAL.add(new JRadioButton("ans3"));
		tempButtonGroup.add(tempAnsAL.get(2));

		ansAL.add(tempAnsAL);
		bGroupAL.add(tempButtonGroup);
	}
	private void paintGUI()
	{
		System.out.println("Painting login GUI");
		qPanel.setLayout(new GridBagLayout());
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
		gbc.gridx = 0;
		gbc.gridy = gridy;
		qPanel.add(backBtn, gbc);

		// Temp radio button array list
		ArrayList<JRadioButton> tempRbAl;
		// For all questions
		for (int q = 0; q < questionsAL.size(); q++)
		{
			gbc.gridx = 0;
			gbc.gridy = ++gridy;
			// Show question
			qPanel.add(questionsAL.get(q), gbc);
			++gridy;
			gridx = 0;
			// Get possible ans
			tempRbAl = ansAL.get(q);		
			for (int i = 0; i< tempRbAl.size(); i++)
			{
				gbc.gridx = gridx++;
				gbc.gridy = gridy;
				qPanel.add(tempRbAl.get(i), gbc);
			}
		}

		// Submit Button
		gbc.gridx = 1;
		gbc.gridy = ++gridy;
		qPanel.add(submitBtn, gbc);
	}
}
