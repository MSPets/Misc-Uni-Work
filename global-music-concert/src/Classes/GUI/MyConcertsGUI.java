package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

public class MyConcertsGUI extends JPanel
{
	private int[] padList = ConfigGUI.getPadding();

	private int curConcertNum = 0;
	private JPanel viewConcertsPanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JPanel editPanel = new JPanel();

	private int curPage = 0;
	private int numOfConcerts = ConcertManager.getMyConcertsTotal();

	Border redBorder = BorderFactory.createLineBorder(Color.RED);
	Border defaultBorder = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

	StartupGUI parent;

	public MyConcertsGUI(StartupGUI parent)
	{
		this.parent = parent;
		// Set main layout
		setLayout(new BorderLayout());

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		Font largeFont = ConfigGUI.getFont();

		// Make sub panel scrollable and add to main panel
		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);

		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);

		mainPanel.setLayout(gbl);
		viewConcertsPanel.setLayout(gbl);;
		editPanel.setLayout(gbl);

		subPanel.add(mainPanel);
		subPanel.add(editPanel);

		// Add to main panel
		// Back Button
		int yNum=0;
		gbc.fill = GridBagConstraints.BOTH;

		// Home button
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JButton homeBtn = new JButton("Back");
		//subPanel.add(homeBtn, gbc);
		mainPanel.add(homeBtn, gbc);

		homeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Account details");
			}
		});

		/* ADD TO LAYOUT */
		JLabel currentConcertsLbl = new JLabel("Your concerts", JTextField.CENTER);
		currentConcertsLbl.setFont(largeFont);
		gbc.gridx = 0 ;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		mainPanel.add(currentConcertsLbl, gbc);

		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);		
		mainPanel.add(viewConcertsPanel, gbc);

		// Load concerts to concert panel
		loadConcerts("first");
	}

	ActionListener actionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == "next")
			{
				curPage += 1;
				loadConcerts("next");
			}
			else if (e.getActionCommand() == "previous")
			{
				curPage -= 1;
				loadConcerts("previous");
			}
		}
	};

	private void loadConcerts(String page)
	{
		// Screen is 4x6
		int numOfRows = 6;
		int curRow = 0;
		int numOfCols = 4;
		int curCol = 0;

		GridBagConstraints concertGbc = new GridBagConstraints();
		ArrayList<String> concertsList = new ArrayList<String>();

		// Clear panel
		viewConcertsPanel.removeAll();

		if(numOfConcerts < 1)
		{
			concertGbc.gridx = 0;
			concertGbc.gridy = 0;
			concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			viewConcertsPanel.add(new JLabel("You have not added any concerts"), concertGbc);
		}
		else
		{
			// If first page
			if (page == "first")
			{
				concertsList = ConcertManager.getSomeMyConcerts(numOfRows*numOfCols, 0);
			}
			// If next page
			else if (page == "next")
			{
				curConcertNum = curConcertNum + (numOfRows*numOfCols);
				concertsList = ConcertManager.getSomeMyConcerts(numOfRows*numOfCols, curConcertNum);
			}
			// If last page
			else if (page == "previous")
			{
				curConcertNum = curConcertNum - (numOfCols*numOfRows);
				concertsList = ConcertManager.getSomeMyConcerts(numOfRows*numOfCols, curConcertNum);
			}
			else
			{
				return;
			}

			// While concerts
			for (int i=0; i<concertsList.size(); i++)
			{
				if (curCol >= numOfCols)
				{
					curCol = 0;
					curRow++;
				}
				concertGbc.gridx = curCol;
				concertGbc.gridy = curRow;
				concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);

				// Make new panel with concert information
				String[] concertIDStr = concertsList.get(i).split(",");

				viewConcertsPanel.add(addPanel(new JPanel(), Integer.parseInt(concertIDStr[1])), concertGbc);
				curCol += 1;
			}
			curRow++;

			// If not first page or last
			if (curPage > 0)
			{
				concertGbc.gridx=0;
				concertGbc.gridy=curRow;
				concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
				JButton previousBtn = new JButton("Previous Page");
				viewConcertsPanel.add(previousBtn, concertGbc);
				previousBtn.addActionListener(actionListener);
				previousBtn.setActionCommand("previous");
			}

			// Display page number
			concertGbc.gridx=1;
			concertGbc.gridy=curRow;
			concertGbc.gridwidth=2;
			concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			viewConcertsPanel.add(new JLabel(String.valueOf(curPage+1), JTextField.CENTER), concertGbc);
			concertGbc.gridwidth=1;

			// If not last Page
			if (curConcertNum + (numOfCols*numOfRows) < numOfConcerts)
			{
				concertGbc.gridx=3;
				concertGbc.gridy=curRow;
				concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
				JButton nextBtn = new JButton("Next Page");
				viewConcertsPanel.add(nextBtn, concertGbc);
				nextBtn.addActionListener(actionListener);
				nextBtn.setActionCommand("next");
			}
		}
		viewConcertsPanel.revalidate();
		viewConcertsPanel.repaint();
	}

	// Add info panel
	private JPanel addPanel (JPanel panel, int concertID)
	{
		panel.setLayout(new GridLayout(0,1,0,padList[0]));

		ArrayList<String> info = ConcertManager.getConcertInformation(concertID);

		ArrayList<String> infoList = splitInfo(info.get(0));

		panel.add(new JLabel("Concert ID - " + infoList.get(0),JTextField.CENTER));
		panel.add(new JLabel("Name - " + infoList.get(1),JTextField.CENTER));
		panel.add(new JLabel("Location - " + infoList.get(2),JTextField.CENTER));
		String[] artistsList = infoList.get(3).split("~");
		panel.add(new JLabel("Artists",JTextField.CENTER));
		for (String s : artistsList)
		{
			panel.add(new JLabel(s,JTextField.CENTER));
		}
		panel.add(new JLabel("Start - " + infoList.get(4),JTextField.CENTER));
		panel.add(new JLabel("End - " + infoList.get(5),JTextField.CENTER));
		panel.add(new JLabel("Ticket price a - " + infoList.get(6),JTextField.CENTER));
		panel.add(new JLabel("Ticket amount a - " + infoList.get(7),JTextField.CENTER));
		panel.add(new JLabel("Ticket price b - " + infoList.get(8),JTextField.CENTER));
		panel.add(new JLabel("Ticket amount b - " + infoList.get(9),JTextField.CENTER));
		panel.add(new JLabel("Ticket price c - " + infoList.get(10),JTextField.CENTER));
		panel.add(new JLabel("Ticket amount c - " + infoList.get(11),JTextField.CENTER));
		panel.add(new JLabel("Ticket amount organisation - " + infoList.get(12),JTextField.CENTER));

		JButton modifyBtn = new JButton("Modify Concert");
		modifyBtn.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				editConcert(infoList);
			}
		});
		panel.add(modifyBtn);

		JButton removeBtn = new JButton("Remove Concert");
		removeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				ConcertManager.dropConcert(Integer.parseInt(infoList.get(0)));
				parent.showCard("My Concerts");
			}
		});
		panel.add(removeBtn);

		return panel;
	}

	// Split information
	private ArrayList<String> splitInfo (String info)
	{
		info.replace("\n", "");
		ArrayList<String> infoList = new ArrayList<String>(Arrays.asList(info.split(",")));

		for (int i = 0; i < infoList.size(); i++)
		{
			infoList.get(i).replace("\n", "");
		}
		return infoList;
	}

	private void editConcert(ArrayList<String> infoList)
	{
		mainPanel.setVisible(false);

		GridBagConstraints gbc = new GridBagConstraints();

		// Clean panel
		editPanel.removeAll();

		int yNum = 0;
		int numOfColumns = 10;

		JButton backBtn = new JButton("Back");
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth=2;
		editPanel.add(backBtn, gbc);
		backBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				editPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});

		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Concert ID - " + infoList.get(0),JTextField.CENTER), gbc);
		gbc.gridwidth=1;

		// Name
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Concert name", SwingUtilities.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JTextField concertNameTf = new JTextField();
		concertNameTf.setColumns(numOfColumns);
		concertNameTf.setText(infoList.get(1));
		editPanel.add(concertNameTf, gbc);

		// Location
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Location", SwingUtilities.RIGHT), gbc);

		JTextField locationTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		locationTf.setColumns(numOfColumns);
		locationTf.setText(infoList.get(2));
		editPanel.add(locationTf, gbc);

		// Artists
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Artists", SwingUtilities.RIGHT), gbc);

		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JTextArea artistsTa = new JTextArea();
		artistsTa.setColumns(numOfColumns);
		artistsTa.setRows(4);
		String artistsList = infoList.get(3).replace("~", "\n");
		artistsTa.setText(artistsList);
		JScrollPane artistScroll = new JScrollPane(artistsTa);
		editPanel.add(artistScroll, gbc);

		// Start
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Start", SwingUtilities.RIGHT), gbc);

		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date minTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 1);
		Date startTime = calendar.getTime();
		try
		{
			startTime = sdf.parse(infoList.get(4));
		}
		catch (Exception e) {}

		calendar.add(Calendar.MINUTE, 1);
		Date endTime = calendar.getTime();
		try
		{
			endTime = sdf.parse(infoList.get(5));
		}
		catch (Exception e) {}

		// Ten year max
		calendar.add(Calendar.YEAR, 10);
		Date maxTime = calendar.getTime();

		// Start Spinner
		SpinnerModel model = new SpinnerDateModel(startTime, minTime, maxTime, Calendar.YEAR);
		JSpinner startSpnr = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(startSpnr, dateFormat);
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		startSpnr.setEditor(editor);
		// Add to panel
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(startSpnr, gbc);

		// End
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("End", SwingUtilities.RIGHT), gbc);
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
		editPanel.add(endSpnr, gbc);

		double curCostA = Double.parseDouble(infoList.get(6));
		int curAmountA = Integer.parseInt(infoList.get(7));
		double curCostB = Double.parseDouble(infoList.get(8));
		int curAmountB = Integer.parseInt(infoList.get(9));
		double curCostC = Double.parseDouble(infoList.get(10));
		int curAmountC = Integer.parseInt(infoList.get(11));
		int curAmountOrganisation = Integer.parseInt(infoList.get(12));

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
		editPanel.add(new JLabel("Ticket price a", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostASpnr = new JSpinner(new SpinnerNumberModel(curCostA, minCost, maxCost, stepCost));
		editPanel.add(ticketCostASpnr, gbc);

		// Num of tickets a
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Number of tickets a", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsA = new JSpinner(new SpinnerNumberModel(curAmountA, minTickets, maxTickets, stepTickets));
		editPanel.add(numOfTicketsA, gbc);

		// Ticket price b
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Ticket price b", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostBSpnr = new JSpinner(new SpinnerNumberModel(curCostB, minCost, maxCost, stepCost));
		editPanel.add(ticketCostBSpnr, gbc);

		// Num of tickets b
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Number of tickets b", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsB = new JSpinner(new SpinnerNumberModel(curAmountB, minTickets, maxTickets, stepTickets));
		editPanel.add(numOfTicketsB, gbc);

		// Ticket price c
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Ticket price c", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner ticketCostCSpnr = new JSpinner(new SpinnerNumberModel(curCostC, minCost, maxCost, stepCost));
		editPanel.add(ticketCostCSpnr, gbc);

		// Num of tickets c
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Number of tickets c", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsC = new JSpinner(new SpinnerNumberModel(curAmountC, minTickets, maxTickets, stepTickets));
		editPanel.add(numOfTicketsC, gbc);

		// Num of tickets Organisation
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		editPanel.add(new JLabel("Number of tickets organisation", SwingUtilities.RIGHT), gbc);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		JSpinner numOfTicketsOrganisation = new JSpinner(new SpinnerNumberModel(curAmountOrganisation, minTickets, maxTickets, stepTickets));
		editPanel.add(numOfTicketsOrganisation, gbc);

		// Edit concert
		JButton editBtn = new JButton("Edit Concert");
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth=2;
		editPanel.add(editBtn, gbc);		
		gbc.gridwidth=1;
		editBtn.addActionListener(new ActionListener()
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
						int retInt = ConcertManager.modifyConcert(Integer.parseInt(infoList.get(0)), concertNameTf.getText(), locationTf.getText(), artistsTa.getText(),
								startDateTime, endDateTime, (double) ticketCostASpnr.getValue(), (int) numOfTicketsA.getValue(), (Double) ticketCostBSpnr.getValue(), (int) numOfTicketsB.getValue(),
								(double) ticketCostCSpnr.getValue(), (int) numOfTicketsC.getValue(), (int) numOfTicketsOrganisation.getValue());
						if (retInt != -1)
						{
							JOptionPane.showMessageDialog(null, "Modified database\nid is " + retInt);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Error modifying database");
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

		editPanel.setVisible(true);
	}
}