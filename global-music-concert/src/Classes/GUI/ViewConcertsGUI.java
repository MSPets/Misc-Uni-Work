package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.sun.javafx.binding.SelectBinding;
import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;

import Classes.Managers.AccountManager;
import Classes.Managers.BookingManager;
import Classes.Managers.ConcertManager;

public class ViewConcertsGUI extends JPanel
{	
	private int[] padList = ConfigGUI.getPadding();

	private int curConcertNum = 0;
	private JPanel concertPanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JPanel bookingPanel = new JPanel();
	private JPanel paymentPanel = new JPanel();

	private int curPage = 0;
	private int numOfAvaliableConcerts = ConcertManager.getNumOfConcerts();

	private int wantedConcertID = -1;

	private JSpinner wantedTicketsASpnr;
	private JSpinner wantedTicketsBSpnr;
	private JSpinner wantedTicketsCSpnr;
	private JSpinner wantedTicketsOrganisationSpnr;

	public ViewConcertsGUI(StartupGUI parent)
	{
		// Set main layout
		setLayout(new BorderLayout());

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		mainPanel.setLayout(gbl);
		bookingPanel.setLayout(gbl);
		concertPanel.setLayout(gbl);;
		paymentPanel.setLayout(gbl);

		Font largeFont = ConfigGUI.getFont();

		// Make sub panel scrollable and add to main panel
		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);
		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);

		// Add other panels to scrollable pannel
		subPanel.add(mainPanel);
		subPanel.add(bookingPanel);
		subPanel.add(paymentPanel);

		// Add to main panel
		// Home Button
		int yNum=0;
		gbc.fill = GridBagConstraints.BOTH;
		// Home button
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JButton homeBtn = new JButton("Home");
		mainPanel.add(homeBtn, gbc);

		homeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Home");
			}
		});

		/* ADD TO LAYOUT */
		JLabel currentConcertsLbl = new JLabel("Current concerts", JTextField.CENTER);
		currentConcertsLbl.setFont(largeFont);
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		mainPanel.add(currentConcertsLbl, gbc);

		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);		
		mainPanel.add(concertPanel, gbc);

		// load concerts
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
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
			else
			{
				bookConcert(e.getActionCommand());
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

		concertPanel.removeAll();

		// If first page
		if (page == "first")
		{
			concertsList = ConcertManager.getSomeConcerts(numOfRows*numOfCols, 0);
		}
		// If next page
		else if (page == "next")
		{
			curConcertNum = curConcertNum + (numOfRows*numOfCols);
			concertsList = ConcertManager.getSomeConcerts(numOfRows*numOfCols, curConcertNum);
		}
		// If last page
		else if (page == "previous")
		{
			curConcertNum = curConcertNum - (numOfCols*numOfRows);
			concertsList = ConcertManager.getSomeConcerts(numOfRows*numOfCols, curConcertNum);
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
			concertPanel.add(addPanel(new JPanel(), concertsList.get(i), "short"), concertGbc);
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
			concertPanel.add(previousBtn, concertGbc);
			previousBtn.addActionListener(actionListener);
			previousBtn.setActionCommand("previous");
		}
		// Display page number
		concertGbc.gridx=1;
		concertGbc.gridy=curRow;
		concertGbc.gridwidth=2;
		concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		concertPanel.add(new JLabel(String.valueOf(curPage+1), JTextField.CENTER), concertGbc);
		concertGbc.gridwidth=1;
		// If not last Page
		if (curConcertNum + (numOfCols*numOfRows) < numOfAvaliableConcerts)
		{
			concertGbc.gridx=3;
			concertGbc.gridy=curRow;
			concertGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			JButton nextBtn = new JButton("Next Page");
			concertPanel.add(nextBtn, concertGbc);
			nextBtn.addActionListener(actionListener);
			nextBtn.setActionCommand("next");
		}
		concertPanel.revalidate();
		concertPanel.repaint();
	}

	// Add concert
	private JPanel addPanel (JPanel panel, String info, String length)
	{
		panel.setLayout(new GridLayout(0,1,0,padList[0]));

		String[]infoList = splitInfo(info);

		// if length is short show less info
		// if length is long show more info

		panel.add(new JLabel("Name - " + infoList[1],JTextField.CENTER));
		panel.add(new JLabel("Location - " + infoList[2],JTextField.CENTER));

		if (length == "short")
		{
			// Limit length of artists 
			String[] artistsList = infoList[3].split("~");
			// Try getting top artists
			try
			{
				panel.add(new JLabel("Artists includes",JTextField.CENTER));
				panel.add(new JLabel(artistsList[0],JTextField.CENTER));
				panel.add(new JLabel(artistsList[1],JTextField.CENTER));
				panel.add(new JLabel(artistsList[2],JTextField.CENTER));
			}
			catch (Exception e) {}
		}
		else
		{
			String[] artistsList = infoList[3].split("~");
			panel.add(new JLabel("Artists",JTextField.CENTER));
			for (String s : artistsList)
			{
				panel.add(new JLabel(s,JTextField.CENTER));
			}
		}

		panel.add(new JLabel("Start - " + infoList[4],JTextField.CENTER));
		panel.add(new JLabel("End - " + infoList[5],JTextField.CENTER));
		panel.add(new JLabel("Ticket price a - " + infoList[6],JTextField.CENTER));
		panel.add(new JLabel("Ticket amount a - " + infoList[7],JTextField.CENTER));
		panel.add(new JLabel("Ticket price b - " + infoList[8],JTextField.CENTER));
		panel.add(new JLabel("Ticket amount b - " + infoList[9],JTextField.CENTER));
		panel.add(new JLabel("Ticket price c - " + infoList[10],JTextField.CENTER));
		panel.add(new JLabel("Ticket amount c - " + infoList[11],JTextField.CENTER));

		if(AccountManager.getVerification().equals("Organisation") || AccountManager.getVerification().equals("Admin"))
		{
			panel.add(new JLabel("Ticket amount organisation - " + infoList[12],JTextField.CENTER));
		}

		if (length == "short")
		{
			JButton detailsButton = new JButton("More details");
			detailsButton.setActionCommand(info);
			detailsButton.addActionListener(actionListener);
			panel.add(detailsButton);	
		}

		return panel;
	}

	private String[] splitInfo (String info)
	{
		info.replace("\n", "");
		String[] infoList = info.split(",");
		for (int i = 0; i < infoList.length; i++)
		{
			infoList[i] = infoList[i].replace("\n", "");
		}
		return infoList;
	}

	private void bookConcert(String info)
	{
		mainPanel.setVisible(false);

		GridBagConstraints gbc = new GridBagConstraints();

		// Clean panel
		bookingPanel.removeAll();

		int yNum = 0;

		JButton backBtn = new JButton("Back");
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		bookingPanel.add(backBtn, gbc);
		backBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				bookingPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
		});

		// Get list of information
		String[] infoList = splitInfo(info);
		wantedConcertID = Integer.parseInt(infoList[0]);

		// Add information panel
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JPanel infoPanel = new JPanel();
		infoPanel = addPanel(infoPanel, info, "long");
		bookingPanel.add(infoPanel, gbc);

		// Book tickets
		// Ticket A
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		bookingPanel.add(new JLabel("Ticket A"), gbc);
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		wantedTicketsASpnr = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(infoList[7]), 1));
		bookingPanel.add(wantedTicketsASpnr, gbc);

		// Ticket B
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		bookingPanel.add(new JLabel("Ticket B"), gbc);
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		wantedTicketsBSpnr = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(infoList[9]), 1));
		bookingPanel.add(wantedTicketsBSpnr, gbc);

		// Ticket C
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		bookingPanel.add(new JLabel("Ticket C"), gbc);
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		wantedTicketsCSpnr = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(infoList[11]), 1));
		bookingPanel.add(wantedTicketsCSpnr, gbc);

		// Organisation
		wantedTicketsOrganisationSpnr = new JSpinner(new SpinnerNumberModel(0, 0, Integer.parseInt(infoList[12]), 1));
		if(AccountManager.getVerification().equals("Organisation") || AccountManager.getVerification().equals("Admin"))
		{
			gbc.gridx=0;
			gbc.gridy=yNum++;
			gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			bookingPanel.add(new JLabel("Ticket Organisation"), gbc);
			gbc.gridx=0;
			gbc.gridy=yNum++;
			gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			bookingPanel.add(wantedTicketsOrganisationSpnr, gbc);
		}

		// Book tickets
		JButton bookBtn = new JButton("Book Tickets");
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		bookingPanel.add(bookBtn, gbc);		
		bookBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(Integer.parseInt(wantedTicketsASpnr.getValue().toString()) == 0 &&
						Integer.parseInt(wantedTicketsBSpnr.getValue().toString()) == 0  &&
						Integer.parseInt(wantedTicketsCSpnr.getValue().toString()) == 0  &&
						Integer.parseInt(wantedTicketsOrganisationSpnr.getValue().toString()) == 0 )
				{
					return;
				}
				else
				{
					bookingPanel.setVisible(false);
					paymentPanel.removeAll();

					// Select payment method
					// Get all payment methods
					ArrayList<String> paymentMethods = AccountManager.getPaymentDetails();
					int maxCols = 4;
					int curCol = 0;
					int curRow = 0;

					// Back Button
					JButton backBtn = new JButton("Back");
					gbc.gridx = curCol;
					gbc.gridy = curRow++;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					paymentPanel.add(backBtn);
					backBtn.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							paymentPanel.setVisible(false);
							bookingPanel.setVisible(true);
							return;
						}
					});
					// If any current payment methods
					if (paymentMethods.size() > 0)
					{
						GridBagConstraints gbc = new GridBagConstraints();

						for (String s : paymentMethods)
						{
							gbc.gridx = curCol++;
							gbc.gridy = curRow;
							gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
							paymentPanel.add(addPaymentDetails(s), gbc);
							if (curCol >= maxCols)
							{
								curCol = 0;
								curRow += 1;
							}
						}
					}
					JPanel addCardPanel = new JPanel();
					addCardPanel.setLayout(new GridBagLayout());
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					addCardPanel.add(new JLabel("Payment method"), gbc);

					JTextField paymentMethodTf = new JTextField();
					paymentMethodTf.setColumns(10);
					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					addCardPanel.add(paymentMethodTf, gbc);

					gbc.gridx = 0;
					gbc.gridy = 2;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					addCardPanel.add(new JLabel("Account number"), gbc);

					JTextField accountNumberTf = new JTextField();
					accountNumberTf.setColumns(10);
					gbc.gridx = 0;
					gbc.gridy = 3;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					addCardPanel.add(accountNumberTf, gbc);

					JButton submitBtn = new JButton("Submit");
					gbc.gridx = 0;
					gbc.gridy = 4;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					addCardPanel.add(submitBtn, gbc);
					submitBtn.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (!accountNumberTf.getText().isEmpty() && !paymentMethodTf.getText().isEmpty())
							{
								int accountNum = 0;
								try
								{
									accountNum = Integer.parseInt(accountNumberTf.getText().toString());
								}
								catch(Exception ee)
								{
									System.out.println(ee);
									JOptionPane.showMessageDialog(null, "Account number should be a number");
									accountNumberTf.setText("");
									return;
								}
								
								// Add payment method proceed with booking
								AccountManager.addPaymentDetails(accountNum, paymentMethodTf.getText().toString());
								processBooking();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Please enter both an account number and payment details");
							}
						}
					});
					gbc.gridx=curCol;
					gbc.gridy=curRow;
					gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
					paymentPanel.add(addCardPanel, gbc);
				}
				// Hide booking panel
				paymentPanel.revalidate();
				paymentPanel.repaint();
				paymentPanel.setVisible(true);
			}
		});
		bookingPanel.setVisible(true);
	}
	private JPanel addPaymentDetails(String info)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1,0,padList[0]));

		String[] infoList = splitInfo(info);
		panel.add(new JLabel("Account number", JTextField.CENTER));
		panel.add(new JLabel(infoList[0], JTextField.CENTER));
		panel.add(new JLabel("Payment Method", JTextField.CENTER));
		panel.add(new JLabel(infoList[1], JTextField.CENTER));

		JButton removeBtn = new JButton("Remove");
		panel.add(removeBtn);
		removeBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int ret = AccountManager.removePaymentDetails(Integer.parseInt(infoList[0]), infoList[1]);
				if (ret != 0)
				{
					JOptionPane.showMessageDialog(null, "Error removing payment details");
				}
				else
				{
					paymentPanel.setVisible(false);
					bookingPanel.setVisible(true);
				}
			}
		});

		JButton selectBtn = new JButton("Book");
		panel.add(selectBtn);
		selectBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				processBooking();
			}
		});
		return panel;
	}

	private void processBooking()
	{
		// Process
		ArrayList<String> retList = BookingManager.bookTickets(wantedConcertID,
				Integer.parseInt(wantedTicketsASpnr.getValue().toString()),
				Integer.parseInt(wantedTicketsBSpnr.getValue().toString()),
				Integer.parseInt(wantedTicketsCSpnr.getValue().toString()),
				Integer.parseInt(wantedTicketsOrganisationSpnr.getValue().toString())
				);

		/*  retList
		 *  0 - bookingID
		 *  1 - invoiceID
		 *  2 - username
		 *  3 - concertID
		 *  4 - wantedTicketsA
		 *  5 - ticketCostA
		 *  6 - wantedTicketsB
		 *  7 - ticketCostB
		 *  8 - wantedTicketsC
		 *  9 - ticketCostC
		 *  10 - wantedTicketsOrganisation
		 */

		if (retList == null)
		{
			JOptionPane.showMessageDialog(null, "Error booking tickets");
		}
		else
		{
			// Display information
			JOptionPane.showMessageDialog(null,
					"Booking ID - " + retList.get(0) +
					"\nInvoice ID - " + retList.get(1) +
					"\nUsername - " + retList.get(2) +
					"\nConcert ID - " + retList.get(3) +
					"\nTicket Amount A - " + retList.get(4) +
					"\nTicket Cost A - " + retList.get(5) +
					"\nTicket Amount B - " + retList.get(6) +
					"\nTicket Cost B - " + retList.get(7) +
					"\nTicket Amount C - " + retList.get(8) +
					"\nTicket cost C - " + retList.get(9) +
					"\nTicket Amount Organisation - " + retList.get(10) +
					"\nTotal cost - " +  retList.get(12)
					);
		}
		paymentPanel.setVisible(false);
		bookingPanel.setVisible(false);
		mainPanel.setVisible(true);
	}
}
