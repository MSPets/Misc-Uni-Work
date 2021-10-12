package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Classes.Managers.ConcertManager;

public class AddConcertGUI extends JPanel
{
	public AddConcertGUI(StartupGUI parent)
	{
		setLayout(new BorderLayout());

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		Font largeFont = ConfigGUI.getFont();
		int numOfColumns = 10;
		int[] padList = ConfigGUI.getPadding();

		Border redBorder = BorderFactory.createLineBorder(Color.RED);
		Border defaultBorder = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);

		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);

		/* Add Concert */	
		int yNum=0;
		gbc.fill = GridBagConstraints.BOTH;

		// Home button
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JButton homeBtn = new JButton("Home");
		subPanel.add(homeBtn, gbc);
		homeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Home");
			}
		});


		JLabel loginLbl = new JLabel("New Concert", JTextField.CENTER);
		loginLbl.setFont(largeFont);
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth=2;
		subPanel.add(loginLbl, gbc);
		gbc.gridwidth=1;

		// Name
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Concert name", SwingUtilities.RIGHT), gbc);

		JTextField concertNameTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(concertNameTf, gbc);
		concertNameTf.setColumns(numOfColumns);

		// Location
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Location", SwingUtilities.RIGHT), gbc);

		JTextField locationTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(locationTf, gbc);
		locationTf.setColumns(numOfColumns);

		// Artists
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Artists", SwingUtilities.RIGHT), gbc);

		JTextArea artistsTa = new JTextArea();
		artistsTa.setColumns(numOfColumns);
		artistsTa.setRows(4);
		JScrollPane artistScroll = new JScrollPane(artistsTa);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(artistScroll, gbc);


		// Start
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Start", SwingUtilities.RIGHT), gbc);


		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date minTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 1);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 1);
		Date endTime = calendar.getTime();
		// Ten year max
		calendar.add(Calendar.YEAR, 10);
		Date maxTime = calendar.getTime();

		// Start Spinner
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SpinnerModel model = new SpinnerDateModel(startTime, minTime, maxTime, Calendar.YEAR);
		JSpinner startSpnr = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(startSpnr, dateFormat);
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		startSpnr.setEditor(editor);
		// Add to panel
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(startSpnr, gbc);

		// End
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("End", SwingUtilities.RIGHT), gbc);
		// End spinner
		SpinnerModel modelEnd = new SpinnerDateModel(endTime, minTime, maxTime, Calendar.YEAR);
		JSpinner endSpnr = new JSpinner(modelEnd);
		JSpinner.DateEditor editorEnd = new JSpinner.DateEditor(endSpnr, "yyyy-MM-dd HH:mm:ss");
		editorEnd.getTextField().setHorizontalAlignment(JTextField.CENTER);
		endSpnr.setEditor(editorEnd);
		// Add to panel
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(endSpnr, gbc);

		double minCost = 0.00;
		double maxCost = 1000000.00;
		double stepCost = 0.01;

		int minTickets = 0;
		int maxTickets = 1000000;
		int stepTickets = 1;

		// Ticket price a
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Ticket price a", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostASpnr = new JSpinner(new SpinnerNumberModel(minCost, minCost, maxCost, stepCost));
		subPanel.add(ticketCostASpnr, gbc);

		// Num of tickets a
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Number of tickets a", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsA = new JSpinner(new SpinnerNumberModel(minTickets, minTickets, maxTickets, stepTickets));
		subPanel.add(numOfTicketsA, gbc);

		// Ticket price b
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Ticket price b", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostBSpnr = new JSpinner(new SpinnerNumberModel(minCost, minCost, maxCost, stepCost));
		subPanel.add(ticketCostBSpnr, gbc);

		// Num of tickets b
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Number of tickets b", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsB = new JSpinner(new SpinnerNumberModel(minTickets, minTickets, maxTickets, stepTickets));
		subPanel.add(numOfTicketsB, gbc);

		// Ticket price c
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Ticket price c", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostCSpnr = new JSpinner(new SpinnerNumberModel(minCost, minCost, maxCost, stepCost));
		subPanel.add(ticketCostCSpnr, gbc);

		// Num of tickets c
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Number of tickets c", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsC = new JSpinner(new SpinnerNumberModel(minTickets, minTickets, maxTickets, stepTickets));
		subPanel.add(numOfTicketsC, gbc);

		// Num of tickets Organisation
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Number of tickets organisation", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsOrganisation = new JSpinner(new SpinnerNumberModel(minTickets, minTickets, maxTickets, stepTickets));
		subPanel.add(numOfTicketsOrganisation, gbc);

		JButton submitBtn = new JButton("Submit concert");
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(submitBtn, gbc);
		submitBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Date startDateTime = (Date) startSpnr.getValue();
				Date endDateTime = (Date) endSpnr.getValue();

				if(startDateTime.compareTo(endDateTime) >= 0)
				{
					JOptionPane.showMessageDialog(null, "Concert cannot end before it starts");
				}
				else
				{
					if (!concertNameTf.getText().isEmpty() &&
							!locationTf.getText().isEmpty() &&
							!artistsTa.getText().isEmpty() &&
							(
									(Double) ticketCostASpnr.getValue() > 0 ||
									(Double) ticketCostBSpnr.getValue() > 0 ||
									(Double) ticketCostCSpnr.getValue() > 0)
							)
					{
						// Add to database
						int retInt = ConcertManager.addNewConcert(concertNameTf.getText(), locationTf.getText(), artistsTa.getText(),
								startDateTime, endDateTime, (Double) ticketCostASpnr.getValue(), (int) numOfTicketsA.getValue(), (Double) ticketCostBSpnr.getValue(), (int) numOfTicketsB.getValue(),
								(Double)ticketCostCSpnr.getValue(), (int) numOfTicketsC.getValue(), (int) numOfTicketsOrganisation.getValue());
						if (retInt != -1)
						{
							JOptionPane.showMessageDialog(null, "Added to database\nid is " + retInt);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error adding concert to database");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "One or more fields are not filled");
						// Set red border to unfilled boxes
						if (concertNameTf.getText().isEmpty()) {concertNameTf.setBorder(redBorder);}
						else{concertNameTf.setBorder(defaultBorder);}
						if (locationTf.getText().isEmpty()) {locationTf.setBorder(redBorder);}
						else{locationTf.setBorder(defaultBorder);}
						if (artistsTa.getText().isEmpty()) {artistsTa.setBorder(redBorder);}
						else{artistsTa.setBorder(defaultBorder);}
						if ((Double) ticketCostASpnr.getValue() == 0 &&
								(Double) ticketCostBSpnr.getValue() == 0 &&
								(Double) ticketCostCSpnr.getValue() == 0)
						{
							ticketCostASpnr.setBorder(redBorder);
						}
						else
						{
							ticketCostASpnr.setBorder(defaultBorder);
						}
					}
				}
			}
		});
	}
}
