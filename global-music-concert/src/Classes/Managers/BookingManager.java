package Classes.Managers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingManager
{
	private static final Logger LOGGER = Logger.getLogger(BookingManager.class.getName() );

	private static final DatabaseManager DATABASE = new DatabaseManager();

	private static final void setLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}

	public static final ArrayList<String> getInvoiceInformation(int invoiceID)
	{
		return DATABASE.queryCommand("SELECT * FROM invoice_info WHERE Invoice_id='"+ invoiceID + "'");
	}
	
	//  Booking information same ad invoice
	public static final ArrayList<String> getBookingInformation(int bookingID)
	{
		//  Get invoice ID
		ArrayList<String> invoiceIDList = DATABASE.queryCommand("SELECT Invoice_id FROM user_booking_info WHERE Booking_id = '" + bookingID + "'");
		
		// Get invoice information
		if (invoiceIDList.size() != 0)
		{
			return DATABASE.queryCommand("SELECT * FROM invoice_info WHERE Invoice_id='"+ invoiceIDList.get(0) + "'");
		}
		
		return null;
	}
	
	public static final ArrayList<String> bookTickets(int concertID, int wantedTicketsA, int wantedTicketsB, int wantedTicketsC, int wantedTicketsOrganisation)
	{
		setLogger();
		
		ArrayList<String> concertInfo = ConcertManager.getConcertInformation(concertID);
		String[] concertinfoList = concertInfo.get(0).split(",");
		
		String organiserUsername = ConcertManager.getOrganiser(concertID);
		
		double ticketCostA=0;
		int numOfTicketsA=0;
		double ticketCostB=0;
		int numOfTicketsB=0;
		double ticketCostC=0;
		int numOfTicketsC=0;		
		int numOfTicketsOrganisation=0;

		double totalCost=0;
		
		try
		{
			// Get ticket price from database
			ticketCostA =  Double.parseDouble(concertinfoList[6]);
			numOfTicketsA =  Integer.parseInt(concertinfoList[7]);

			ticketCostB =  Double.parseDouble(concertinfoList[8]);
			numOfTicketsB =  Integer.parseInt(concertinfoList[9]);			

			ticketCostC =  Double.parseDouble(concertinfoList[10]);
			numOfTicketsC =  Integer.parseInt(concertinfoList[11]);

			numOfTicketsOrganisation =  Integer.parseInt(concertinfoList[12]);
		}
		catch (Exception e)
		{
			LOGGER.log(Level.INFO, "Error converting numbers\n" + e.toString());
			return null;
		}
		
		// Make sure theirs enough tickets
		if(wantedTicketsA <= numOfTicketsA)
		{
			if (wantedTicketsA > 0)
			{
				totalCost += (ticketCostA * wantedTicketsA);
			}
		}
		else {return null;}
		
		if(wantedTicketsB <= numOfTicketsB)
		{
			if (wantedTicketsB > 0)
			{
				totalCost += (ticketCostB * wantedTicketsB);
			}
		} else {return null;}
		
		if(wantedTicketsC <= numOfTicketsC)
		{
			if (wantedTicketsC > 0)
			{
				totalCost += (ticketCostC * wantedTicketsC);
			}
		}
		else {return null;}
		
		if(wantedTicketsOrganisation > numOfTicketsOrganisation)
		{
			return null;
		}

		// Generate invoice
		ArrayList<String> invoice = new ArrayList<String>();

		invoice.add(AccountManager.getUsername());
		invoice.add(String.valueOf(concertID));

		invoice.add(String.valueOf(wantedTicketsA));
		invoice.add(String.valueOf(ticketCostA));

		invoice.add(String.valueOf(wantedTicketsB));
		invoice.add(String.valueOf(ticketCostB));

		invoice.add(String.valueOf(wantedTicketsC));
		invoice.add(String.valueOf(ticketCostC));

		invoice.add(String.valueOf(wantedTicketsOrganisation));
		
		invoice.add(organiserUsername);
		
		// Add invoice to database
		int invoiceID = -1;
		int bookingID = -1;

		// Connect to database
		Statement statement;
		try
		{
			statement = DATABASE.connect().createStatement();
		}
		catch(Exception e)
		{
			return null;
		}
		// Upload required things to database
		try
		{
			// Upload invoice
			statement.executeUpdate("INSERT INTO invoice_info (Concert_id, Num_of_tickets_a, Ticket_cost_a, Num_of_tickets_b, Ticket_cost_b, Num_of_tickets_c, Ticket_cost_c, Num_of_tickets_organisation, Organiser_username)"
					+ "VALUES"
					+ "('"+invoice.get(1)+"','"+invoice.get(2)+"','"+invoice.get(3)+"','"+invoice.get(4)+"','"+invoice.get(5)+ "','"+invoice.get(6)+ "','"+invoice.get(7)+"','"+invoice.get(8)+"','"+invoice.get(9)+"')", Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
			{
				invoiceID = rs.getInt(1);
			}
			
			// Link to booking ID
			statement.executeUpdate("INSERT INTO user_booking_info (Username, Invoice_id)"
					+ "VALUES"
					+ "('"+invoice.get(0)+"','"+invoiceID+"')", Statement.RETURN_GENERATED_KEYS);

			rs = statement.getGeneratedKeys();
			if (rs.next())
			{
				bookingID = rs.getInt(1);
			}

			// Remove tickets from concert
			int ret = ConcertManager.bookTickets(Integer.parseInt(invoice.get(1)), Integer.parseInt(invoice.get(2)), Integer.parseInt(invoice.get(4)), Integer.parseInt(invoice.get(6)), Integer.parseInt(invoice.get(8)));
			if (ret == 0)
			{
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.INFO, "Error Inserting booking invoice_info or user_booking_info\n" + e.toString());

			// Error removing tickets
			if (invoiceID != -1)
			{
				try
				{
					// Remove invoiceID
					statement.execute("DELETE FROM invoice_info WHERE Invoice_id='" + invoiceID + "'");
				}
				catch(Exception e2)
				{
					LOGGER.log(Level.SEVERE, "Error removing invoice " + invoiceID + "\nError was " + e2.toString());
				}
			}
			// Error updating booking id
			if (bookingID != -1)
			{
				// Remove bookingID
				try
				{
					// Remove booking id
					statement.execute("DELETE FROM user_booking_info WHERE Booking_id='" + bookingID + "'");
				}
				catch(Exception e2)
				{
					LOGGER.log(Level.SEVERE, "Error removing booking info " + bookingID + "\nError was " + e2.toString());
				}
			}
		}

		DATABASE.disconnect();

		if (bookingID == -1 | invoiceID == -1)
		{
			return null;
		}
		else
		{
			// Add information to invoice array
			invoice.add(0, String.valueOf(bookingID));
			invoice.add(1, String.valueOf(invoiceID));
			
			invoice.add(String.valueOf(totalCost));
			// Return invoice
			return invoice;
		}
	}
}
