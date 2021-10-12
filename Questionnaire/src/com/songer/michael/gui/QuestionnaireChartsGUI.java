package com.songer.michael.gui;

import com.songer.michael.functions.MakeChart;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.songer.michael.functions.LoadQuestionnaire;
import com.songer.michael.functions.MakeChart;

public class QuestionnaireChartsGUI implements ActionListener
{
	private JPanel QChartsPanel = new JPanel();

	private JButton backBtn = new JButton("<---");

	private JFrame mainFrame;

	private StartGUI par;

	ImageIcon q1 = new ImageIcon();
	ImageIcon q2 = new ImageIcon();
	ImageIcon q3 = new ImageIcon();
	ImageIcon q4 = new ImageIcon();
	ImageIcon q5 = new ImageIcon();
	ImageIcon q6 = new ImageIcon();

	//private Border redBorder = BorderFactory.createLineBorder(Color.red, 1);
	//private Border defaultBorder = usernameTF.getBorder();

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(backBtn))
		{
			System.out.println("Back button pressed");
			par.backButton();
		}
	}

	public QuestionnaireChartsGUI(StartGUI par, JFrame mainFrame) {
		System.out.println("LOGIN GUI");
		this.par = par;
		this.mainFrame = mainFrame;

		// Remove old Panel
		mainFrame.getContentPane().removeAll();

		// Setup Panel
		paintGUI();

		backBtn.addActionListener(this);

		// Add new panel
		mainFrame.getContentPane().add(new JScrollPane(QChartsPanel));

		mainFrame.validate();
		mainFrame.repaint();
	}

	private void getCharts()
	{
		// get data
		ArrayList<ArrayList> all = LoadQuestionnaire.getResultNumbers();
		System.out.println(all.get(0));
		
		// Q1
		String[] ansLabels = {"ans1", "ans2", "ans3", "ans4", "ans5"};
		q1.setImage(MakeChart.getChart(ansLabels, all.get(0)));

		// Q2
		ansLabels = new String[] {"ans1", "ans2", "ans3"};
		q2.setImage(MakeChart.getChart(ansLabels, all.get(1)));
		// Q3
		ansLabels = new String[] {"ans1", "ans2", "ans3"};
		q3.setImage(MakeChart.getChart(ansLabels, all.get(2)));
		// Q4
		ansLabels = new String[] {"ans1", "ans2", "ans3"};
		q4.setImage(MakeChart.getChart(ansLabels, all.get(3)));
		// Q5
		ansLabels = new String[] {"ans1", "ans2", "ans3"};
		q5.setImage(MakeChart.getChart(ansLabels, all.get(4)));
		// q6
		ansLabels = new String[] {"ans1", "ans2", "ans3"};
		q6.setImage(MakeChart.getChart(ansLabels, all.get(5)));
		}

	private void paintGUI()
	{
		System.out.println("Painting login GUI");

		QChartsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 1;
		//gbc.ipadx = 20;
		//gbc.ipady = 20;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0,10,0,10);

		// Back button top left
		gbc.gridx = 0;
		gbc.gridy = 0;
		QChartsPanel.add(backBtn, gbc);

		getCharts();

		// Q1
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel q1Lbl = new JLabel("Question 1");
		QChartsPanel.add(q1Lbl, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel q1Chart = new JLabel(q1);
		QChartsPanel.add(q1Chart, gbc);

		// Q2
		gbc.gridx = 1;
		gbc.gridy = 1;
		JLabel q2Lbl = new JLabel("Question 2");
		QChartsPanel.add(q2Lbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		JLabel q2Chart = new JLabel(q2);
		QChartsPanel.add(q2Chart, gbc);

		// Q3
		gbc.gridx = 2;
		gbc.gridy = 1;
		JLabel q3Lbl = new JLabel("Question 3");
		QChartsPanel.add(q3Lbl, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		JLabel q3Chart = new JLabel(q3);
		QChartsPanel.add(q3Chart, gbc);

		// Q4
		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel q4Lbl = new JLabel("Question 4");
		QChartsPanel.add(q4Lbl, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel q4Chart = new JLabel(q4);
		QChartsPanel.add(q4Chart, gbc);

		// Q5
		gbc.gridx = 1;
		gbc.gridy = 3;
		JLabel q5Lbl = new JLabel("Question 5");
		QChartsPanel.add(q5Lbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		JLabel q5Chart = new JLabel(q5);
		QChartsPanel.add(q5Chart, gbc);

		//Q6
		gbc.gridx = 2;
		gbc.gridy = 3;
		JLabel q6Lbl = new JLabel("Question 6");
		QChartsPanel.add(q6Lbl, gbc);

		gbc.gridx = 2;
		gbc.gridy = 4;
		JLabel q6Chart = new JLabel(q6);
		QChartsPanel.add(q6Chart, gbc);
	}
}
