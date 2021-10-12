package Classes.Managers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcertManager
{
	private static final Logger LOGGER = Logger.getLogger(ConcertManager.class.getName());	
	private static final DatabaseManager DATABASE = new DatabaseManager();

	private static final void setLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}

	public static final int getNumOfConcerts()
	{
		ArrayList<String> retList = DATABASE.queryCommand("SELECT COUNT(*) FROM concert_info");

		int numOfConcertsInt = 0;
		try
		{
			numOfConcertsInt = Integer.parseInt(retList.get(0).toString());
		}
		catch (Exception e)
		{
			//System.out.println(e);
		}
		return numOfConcertsInt;
	}

	public static final ArrayList<String> getAllConcerts()
	{
		return DATABASE.queryCommand("SELECT * FROM concert_info");
	}

	// Get certain amount of concerts after certain point
	public static final ArrayList<String> getSomeConcerts(int numOfCon, int fromNum)
	{
		return DATABASE.queryCommand("SELECT * FROM concert_info ORDER BY Start ASC LIMIT " + numOfCon + " OFFSET " + fromNum);
	}

	public static final ArrayList<String> getConcertInformation(int concertID)
	{
		return DATABASE.queryCommand("SELECT * FROM concert_info WHERE Concert_id = '" + concertID + "'");
	}

	public static final int addNewConcert(String name, String location, String artistName, Date start, Date end,
			double tickePriceA, int numOfTicketsA, double ticketPriceB, int numOfTicketsB, double ticketPriceC, int numOfTicketsC, int numOfTicketsOrganisation)
	{
		setLogger();
		artistName = artistName.replace("\n", "~");
		int key = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startStr = sdf.format(start);
		String endStr = sdf.format(end);
		try
		{
			Statement statement = DATABASE.connect().createStatement();
			statement.executeUpdate("INSERT INTO concert_info (Name, Location, Artists_name, Start, End, Ticket_price_a, Num_of_tickets_a, Ticket_price_b, Num_of_tickets_b, Ticket_price_c, Num_of_tickets_c, Num_of_tickets_organisation)"
					+ "VALUES"
					+ "('"+name+"','"+location+"','"+artistName+"','"+startStr+"','"+endStr+"','"+
					+tickePriceA+"','"+numOfTicketsA+"','"+ticketPriceB+"','"+numOfTicketsB+"','"+ticketPriceC+"','"+numOfTicketsC+"','"+numOfTicketsOrganisation+"')", Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
			{
				key = rs.getInt(1);
			}

			statement.executeUpdate("INSERT INTO organiser_info (Organiser_username, Concert_id )"
					+ "VALUES"
					+ "('"+ AccountManager.getUsername() + "','" + key + "');"
					);

			DATABASE.disconnect();
		}
		catch (Exception e)
		{
			DATABASE.disconnect();
			LOGGER.log(Level.WARNING, "Error adding concert\n" + e.toString());
			return -1;
		}
		if (key != 0)
		{
			return key;
		}
		else
		{
			return -1;	
		}
	}

	public static final int modifyConcert(int concertID, String name, String location, String artistName, Date start, Date end,
			double tickePriceA, int numOfTicketsA, double ticketPriceB, int numOfTicketsB, double ticketPriceC, int numOfTicketsC, int numOfTicketsOrganisation)
	{
		setLogger();
		artistName = artistName.replace("\n", "~");
		int key = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String startStr = sdf.format(start);
		String endStr = sdf.format(end);
		try
		{
			Statement statement = DATABASE.connect().createStatement();
			key = statement.executeUpdate("UPDATE concert_info SET Name='"+name+"', Location='"+location+"', Artists_name='"+artistName+"', "+
					"Start='"+startStr+"', End='"+endStr+"', Ticket_price_a='"+tickePriceA+"', Num_of_tickets_a='"+numOfTicketsA+"', "+
					" Ticket_price_b='"+ticketPriceB+"', Num_of_tickets_b='"+numOfTicketsB+"', " +
					" Ticket_price_c='"+ticketPriceC+"', Num_of_tickets_c='"+numOfTicketsC+"', " +
					" Num_of_tickets_organisation='"+numOfTicketsOrganisation+"'"
					+ " WHERE Concert_id='"+concertID+"'");

			DATABASE.disconnect();
		}
		catch (Exception e)
		{
			DATABASE.disconnect();
			LOGGER.log(Level.WARNING, "Error modifying concert\n" + e.toString());
			return -1;
		}
		if (key != 0)
		{
			return key;
		}
		else
		{
			return -1;	
		}
	}
	
	public static final int dropConcert(int concertID)
	{
		int ret = DATABASE.executeCommand("DELETE FROM concert_info WHERE Concert_id='"+concertID+"'");
		if (ret == 0)
		{
			DATABASE.executeCommand("DELETE FROM organiser_info WHERE Concert_id='"+concertID+"'");
		}
		return ret;
	}
	
	public static final int bookTickets(int concertID, int numOfTicketA, int numOfTicketB, int numOfTicketC, int numOfTicketsOrganisation)
	{
		ArrayList<String> concertInfo = getConcertInformation(concertID);
		String[] concertList = concertInfo.get(0).split(",");
		int currentA = Integer.parseInt(concertList[7]);
		int currentB = Integer.parseInt(concertList[9]);
		int currentC = Integer.parseInt(concertList[11]);
		int currentOrganisation = Integer.parseInt(concertList[12]);

		int newA = currentA - numOfTicketA;
		int newB = currentB - numOfTicketB;
		int newC = currentC - numOfTicketC;
		int newOrganisation = currentOrganisation - numOfTicketsOrganisation;

		int ret = DATABASE.updateCommand("UPDATE concert_info set Num_of_tickets_a='"+newA+"',"
				+ "Num_of_tickets_b='"+newB+"',"
				+ "Num_of_tickets_c='"+newC+"',"
				+ "Num_of_tickets_Organisation='"+newOrganisation+"' "
				+ "WHERE Concert_id='"+concertID+"'");

		return ret;
	}

	/*
	public static final ArrayList<String> getArtistsInformation(String artistsName)
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		return arrayList;
	}
	 */
	
	public static final String getOrganiser(int concertID)
	{
		ArrayList<String> organiser = DATABASE.queryCommand("SELECT Organiser_username FROM organiser_info WHERE Concert_id = '" + concertID +"'");
		return organiser.get(0).replace("\n", "");
	}

	public static final int setAgentInformation(String agentName, int agentPhoneNo, String agentEmailAddress, String agentWorkAddress)
	{
		int ret = DATABASE.executeCommand("INSERT INTO Agent_info (Agent_name, Agent_Phone_no, Agent_email_address, Agent_work_address)"
				+ "VALUES"
				+ "('"+agentName+"','"+agentPhoneNo+"','"+agentEmailAddress+"','"+agentWorkAddress+"'");

		return ret;
	}

	public ArrayList<String> getAgentInformation(String agentName)
	{
		ArrayList<String> retList = DATABASE.queryCommand("SELECT * FROM Agent_info WHERE Agent_name = '" + agentName + "'");

		return retList;
	}

	public static int getMyConcertsTotal()
	{
		ArrayList<String> retList = DATABASE.queryCommand("SELECT * FROM organiser_info WHERE Organiser_username = '" + AccountManager.getUsername() + "'");

		return retList.size();
	}

	public static ArrayList<String> getSomeMyConcerts(int numOfCon, int fromNum)
	{
		ArrayList<String> retList = DATABASE.queryCommand("SELECT * FROM organiser_info WHERE Organiser_username = '" + AccountManager.getUsername() + "' LIMIT " + numOfCon + " OFFSET " + fromNum);

		return retList;
	}

	/*
	public static void main(String[] args)
	{
		ConcertManager con = new ConcertManager();

		//System.out.println(con.getConcertInformation(1));
		//System.out.println(con.addNewConcert("name", "location", "artistName", "dates", 100.1, 200.1, 300.1, 250.0));
		//System.out.println(con.getAllConcerts());
	}
	 */
}
