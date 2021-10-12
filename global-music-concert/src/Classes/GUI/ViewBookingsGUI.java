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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Classes.Managers.AccountManager;
import Classes.Managers.BookingManager;
import Classes.Managers.ConcertManager;
import Classes.Managers.DatabaseManager;

public class ViewBookingsGUI extends JPanel
{
	private int[] padList = ConfigGUI.getPadding();

	private int curBookingNum = 0;
	private JPanel viewBookingsPanel = new JPanel();
	private JPanel mainPanel = new JPanel();

	private int curPage = 0;
	private int numOfBookings = AccountManager.getNumOfUsersBookings();

	public ViewBookingsGUI(StartupGUI parent)
	{
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
		viewBookingsPanel.setLayout(gbl);;

		// Add to main panel
		// Back Button
		int yNum=1;
		gbc.fill = GridBagConstraints.BOTH;

		// Home button
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JButton homeBtn = new JButton("Back");
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
		JLabel currentConcertsLbl = new JLabel("Your bookings", JTextField.CENTER);
		currentConcertsLbl.setFont(largeFont);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		mainPanel.add(currentConcertsLbl, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(mainPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(viewBookingsPanel, gbc);

		// Load concerts to concert panel
		loadBookings("first");
	}

	ActionListener actionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == "next")
			{
				curPage += 1;
				loadBookings("next");
			}
			else if (e.getActionCommand() == "previous")
			{
				curPage -= 1;
				loadBookings("previous");
			}
		}
	};

	private void loadBookings(String page)
	{
		// Screen is 4x6
		int numOfRows = 6;
		int curRow = 0;
		int numOfCols = 4;
		int curCol = 0;

		GridBagConstraints bookingGbc = new GridBagConstraints();
		ArrayList<String> bookingList = new ArrayList<String>();

		// Clear panel
		viewBookingsPanel.removeAll();

		if(numOfBookings < 1)
		{
			bookingGbc.gridx = 0;
			bookingGbc.gridy = 0;
			bookingGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			viewBookingsPanel.add(new JLabel("You have not booked any concerts"), bookingGbc);
		}
		else
		{
			// If first page
			if (page == "first")
			{
				bookingList = AccountManager.getSomeUsersBookings(numOfRows*numOfCols, 0);
			}
			// If next page
			else if (page == "next")
			{
				curBookingNum = curBookingNum + (numOfRows*numOfCols);
				bookingList = AccountManager.getSomeUsersBookings(numOfRows*numOfCols, curBookingNum);
			}
			// If last page
			else if (page == "previous")
			{
				curBookingNum = curBookingNum - (numOfCols*numOfRows);
				bookingList = AccountManager.getSomeUsersBookings(numOfRows*numOfCols, curBookingNum);
			}
			else
			{
				return;
			}

			// While concerts
			for (int i=0; i<bookingList.size(); i++)
			{
				if (curCol >= numOfCols)
				{
					curCol = 0;
					curRow++;
				}
				bookingGbc.gridx = curCol;
				bookingGbc.gridy = curRow;
				bookingGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);

				// Make new panel with concert information
				String[] concertIDStr = splitInfo(bookingList.get(i));
				viewBookingsPanel.add(addPanel(new JPanel(), Integer.parseInt(concertIDStr[0]), Integer.parseInt(concertIDStr[2])), bookingGbc);
				curCol += 1;
			}
			curRow++;

			// If not first page or last
			if (curPage > 0)
			{
				bookingGbc.gridx=0;
				bookingGbc.gridy=curRow;
				bookingGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
				JButton previousBtn = new JButton("Previous Page");
				viewBookingsPanel.add(previousBtn, bookingGbc);
				previousBtn.addActionListener(actionListener);
				previousBtn.setActionCommand("previous");
			}

			// Display page number
			bookingGbc.gridx=1;
			bookingGbc.gridy=curRow;
			bookingGbc.gridwidth=2;
			bookingGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			viewBookingsPanel.add(new JLabel("Page "+String.valueOf(curPage+1), JTextField.CENTER), bookingGbc);
			bookingGbc.gridwidth=1;

			// If not last Page
			if (curBookingNum + (numOfCols*numOfRows) < numOfBookings)
			{
				bookingGbc.gridx=3;
				bookingGbc.gridy=curRow;
				bookingGbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
				JButton nextBtn = new JButton("Next Page");
				viewBookingsPanel.add(nextBtn, bookingGbc);
				nextBtn.addActionListener(actionListener);
				nextBtn.setActionCommand("next");
			}
		}
		viewBookingsPanel.revalidate();
		viewBookingsPanel.repaint();
	}

	// Add info panel
	private JPanel addPanel (JPanel panel, int bookingID, int invoiceID)
	{
		panel.setLayout(new GridLayout(0,1,0,padList[0]));

		ArrayList<String> bookingArr = AccountManager.getInvoiceInformtion(invoiceID);

		String[] infoList = splitInfo(bookingArr.get(0));
		for (String s: infoList)
		{
			System.out.println(s);
		}
		/* bookingInfoList
		 * Invoice_id
		 * Concert_id
		 * Num_of_tickets_a
		 * Ticket_cost_a
		 * Num_of_tickets_b
		 * Ticket_cost_b
		 * Num_of_tickets_c
		 * Ticket_cost_c
		 * Num_of_tickets_organisation
		 * Ticket_cost_organisation
		 */

		panel.add(new JLabel("Booking ID - " + bookingID,JTextField.CENTER));
		panel.add(new JLabel("Concert ID - " + infoList[1],JTextField.CENTER));
		panel.add(new JLabel("Number of Tickets a - " + infoList[2],JTextField.CENTER));
		panel.add(new JLabel("Cost of Tickets a - " + infoList[3],JTextField.CENTER));
		panel.add(new JLabel("Number of Tickets b - " + infoList[4],JTextField.CENTER));
		panel.add(new JLabel("Cost of Tickets b - " + infoList[5],JTextField.CENTER));
		panel.add(new JLabel("Number of Tickets c - " + infoList[6],JTextField.CENTER));
		panel.add(new JLabel("Cost of Tickets c - " + infoList[7],JTextField.CENTER));
		panel.add(new JLabel("Number of Tickets organisations - " + infoList[8],JTextField.CENTER));
		panel.add(new JLabel("Total cost - " + String.valueOf(
				(Double.parseDouble(infoList[2]) * Double.parseDouble(infoList[3])) +
				(Double.parseDouble(infoList[4]) * Double.parseDouble(infoList[5])) +
				(Double.parseDouble(infoList[6]) * Double.parseDouble(infoList[7]))) , JTextField.CENTER)
				);
		
		return panel;
	}

	// Split information
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
}
