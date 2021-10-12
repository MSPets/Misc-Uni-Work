package Classes.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;

import Classes.Managers.AccountManager;
import Classes.Managers.BookingManager;
import Classes.Managers.ConcertManager;
import Classes.Managers.DatabaseManager;

public class PopulateDatabase
{
	private DatabaseManager database = new DatabaseManager();
	private AccountManager ac = new AccountManager();

	private void addUsersTable()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS users ("
				+ "Username VARCHAR(60) PRIMARY KEY,"
				+ "Title VARCHAR(10) NOT NULL,"
				+ "First_name VARCHAR(60) NOT NULL,"
				+ "Last_name VARCHAR(60) NOT NULL,"
				+ "DOB DATE NOT NULL,"
				+ "Address_1 VARCHAR(60) NOT NULL,"
				+ "Address_2 VARCHAR(60),"
				+ "Town VARCHAR(60) NOT NULL,"
				+ "Postcode VARCHAR(60) NOT NULL,"
				+ "Organisation_name VARCHAR(60),"
				+ "Password VARCHAR(60) NOT NULL,"
				+ "Phone_no INT NOT NULL,"
				+ "Email_address VARCHAR(60) NOT NULL,"
				+ "Web_address VARCHAR(60),"
				+ "Level VARCHAR(60) NOT NULL);"
				);
		if (ret != 0)
		{
			System.out.println("Error making users table");
		}
		else
		{
			System.out.println("Made users table");
		}
	}

	private void populateUsersTable() throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date DOB = sdf.parse("1970-01-01");
		
		// Add admin
		int ret = ac.createAccount("Admin", "Title", "FirstName", "LastName", DOB,
				"Address1", "Address2", "Town", "Postcode", "OrganisationName", ("Admin").toCharArray(),
				123456789, "EmailAddress", "WebAddress", "Admin");			
		if (ret != 0)
		{
			System.out.println("Error adding admin to users table");
		}

		ret = ac.createAccount("Organiser", "Title", "FirstName", "LastName ", DOB,
				"Address1", "Address2", "Town", "Postcode", "OrganisationName", ("Organiser").toCharArray(),
				123456789, "EmailAddress", "WebAddress", "Organiser");
		if (ret != 0)
		{
			System.out.println("Error adding admin to users table");
		}

		ret = ac.createAccount("Username", "title", "firstName", "lastName", DOB,
				"address1", "address2", "town", "postcode", "organisationName", ("Username").toCharArray(),
				123456789, "emailAddress", "webAddress", "User");
		if (ret != 0)
		{
			System.out.println("Error adding to users table");
		}

		//ArrayList<String> user = database.queryCommand("SELECT * FROM users WHERE 1");
		//for (String s : user)
		//{
		//System.out.print(s);
		//}
	}

	private void addOrganiserInfoTable()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS organiser_info ("
				+ "Organiser_username VARCHAR(60) NOT NULL,"
				+ "Concert_id INT NOT NULL,"
				+ "PRIMARY KEY(Organiser_username, Concert_id));"
				);
		if (ret != 0)
		{
			System.out.println("Error making organiser_info table");
		}
		else
		{
			System.out.println("Made organiser_info table");
		}
	}

	private void addConcertInfoTable()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS concert_info ("
				+ "Concert_id INT AUTO_INCREMENT PRIMARY KEY,"
				+ "Name VARCHAR(60) NOT NULL,"
				+ "Location VARCHAR(60) NOT NULL,"
				+ "Artists_name TEXT NOT NULL,"
				+ "Start DATETIME NOT NULL,"
				+ "End DATETIME NOT NULL,"
				+ "Ticket_price_a FLOAT(8,2) NOT NULL,"
				+ "Num_of_tickets_a INT NOT NULL,"
				+ "Ticket_price_b FLOAT(8,2),"
				+ "Num_of_tickets_b INT,"
				+ "Ticket_price_c FLOAT(8,2),"
				+ "Num_of_tickets_c INT,"
				+ "Num_of_tickets_organisation INT);"
				);
		if (ret != 0)
		{
			System.out.println("Error making concert_info table");
		}
		else
		{
			System.out.println("Made concert_info table");
		}
	}

	private void populateConcertInfoTable()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 1; i < 1000; i++)
		{
			calendar.add(Calendar.DATE, i);
			Date startDate = calendar.getTime();
			calendar.add(Calendar.DATE, i);
			Date endDate = calendar.getTime();

			int key = 0;
			try
			{
				Statement statement = database.connect().createStatement();
				statement.executeUpdate("INSERT INTO concert_info (Name, Location, Artists_name, Start, End, Ticket_price_a, Num_of_tickets_a, Ticket_price_b, Num_of_tickets_b, Ticket_price_c, Num_of_tickets_c, Num_of_tickets_organisation)"
						+ "VALUES"
						+ "('name', 'location', 'artist 1~artist 2~artist 3~artist 4~artist 5~artist 6~artist 7','"+
						sdf.format(startDate)+"','"+sdf.format(endDate)+"','"+i+"','"+100+"','"+(i+i)+"','"+200+"','"+(i+i+i)+"','"+300+"','"+30+"')", Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next())
				{
					key = rs.getInt(1);
				}

				statement.executeUpdate("INSERT INTO organiser_info (Organiser_username, Concert_id )"
						+ "VALUES"
						+ "('Organiser','" + key + "');"
						);
				database.disconnect();
			}
			catch (Exception e)
			{
				database.disconnect();
				System.out.println("Error adding concert");
				System.out.println(e.toString());
			}
		}
	}

	private void addInvoiceInfoTable()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS invoice_info ("
				+ "Invoice_id INT AUTO_INCREMENT PRIMARY KEY,"
				+ "Concert_id INT NOT NULL,"
				+ "Num_of_tickets_a INT NOT NULL,"
				+ "Ticket_cost_a FLOAT(8,2) NOT NULL,"
				+ "Num_of_tickets_b INT NOT NULL,"
				+ "Ticket_cost_b FLOAT(8,2) NOT NULL,"
				+ "Num_of_tickets_c INT NOT NULL,"
				+ "Ticket_cost_c FLOAT(8,2) NOT NULL,"
				+ "Num_of_tickets_organisation INT NOT NULL,"
				+ "Organiser_username VARCHAR(60) NOT NULL"
				+ "/*FOREIGN KEY (Organiser_username) REFERENCES organiser_info(Organiser_username)*/);"
				);
		if (ret != 0)
		{
			System.out.println("Error making concert_info table");
		}
		else
		{
			System.out.println("Made concert_info table");
		}
	}

	private void addUserBookingInfoTable()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS user_booking_info ("
				+ "Booking_id INT NOT NULL AUTO_INCREMENT,"
				+ "Username VARCHAR(60) NOT NULL,"
				+ "Invoice_id INT NOT NULL,"
				+ "PRIMARY KEY (Booking_id, Username, Invoice_id));"
				);
		if (ret != 0)
		{
			System.out.println("Error making user_booking_info table");
		}
		else
		{
			System.out.println("Made user_booking_info table");
		}
	}

	private void populateUserBookingInfoTable()
	{
		int invoiceID = 0;
		// Upload invoice
		try
		{
			Statement statement = database.connect().createStatement();
			statement.executeUpdate("INSERT INTO invoice_info ("
					+ "Concert_id, Num_of_tickets_a, Ticket_cost_a,"
					+ "Num_of_tickets_b, Ticket_cost_b,"
					+ "Num_of_tickets_c, Ticket_cost_c,"
					+ "Num_of_tickets_organisation,"
					+ "Organiser_username)"
					+ "VALUES"
					+ "('100', '20', '100',"
					+ "'21', '100',"
					+ "'22', '100',"
					+ "'23',"
					+ "'Organiser')", Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
			{
				invoiceID = rs.getInt(1);
			}

			// Link to booking ID
			statement.executeUpdate("INSERT INTO user_booking_info (Username, Invoice_id)"
					+ "VALUES"
					+ "('Username','"+invoiceID+"')");

			System.out.println("Added booking and invoice to database");
		}
		catch (Exception e)
		{
			System.out.println("Error adding booking and invoice to database");
			System.out.println(e.toString());
		}
	}
	
	private void addPaymentInfo()
	{
		int ret = database.executeCommand("CREATE TABLE IF NOT EXISTS payment_info ("
				+ "Username VARCHAR(60) NOT NULL,"
				+ "Account_no INT NOT NULL,"
				+ "Payment_method VARCHAR(40) NOT NULL,"
				+ "Organisation BOOLEAN NOT NULL);"
				);
		if (ret != 0)
		{
			System.out.println("Error making payment_info table");
		}
		else
		{
			System.out.println("Made payment_info table");
		}
	}
	private void populatePaymentInfo()
	{
		try
		{
			Statement statement = database.connect().createStatement();
			statement.executeUpdate("INSERT INTO payment_info  (Username, Account_no, Payment_method, Organisation)"
					+ "VALUES"
					+ "('Username', '1', 'Debit Card 1', '0')");

			statement.executeUpdate("INSERT INTO payment_info  (Username, Account_no, Payment_method, Organisation)"
					+ "VALUES"
					+ "('Username', '12', 'Debit Card 2', '0')");
			
			statement.executeUpdate("INSERT INTO payment_info  (Username, Account_no, Payment_method, Organisation)"
					+ "VALUES"
					+ "('Username', '123', 'Debit Card 3', '0')");
			
			statement.executeUpdate("INSERT INTO payment_info  (Username, Account_no, Payment_method, Organisation)"
					+ "VALUES"
					+ "('Username', '1234', 'Debit Card 4', '0')");
			
			statement.executeUpdate("INSERT INTO payment_info  (Username, Account_no, Payment_method, Organisation)"
					+ "VALUES"
					+ "('Username', '12345', 'Debit Card 5', '0')");
			
			System.out.println("Added payment methods");
			database.disconnect();
		}
		catch (Exception e)
		{
			database.disconnect();
			System.out.println("Error debit card");
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) throws ParseException
	{
		PopulateDatabase d = new PopulateDatabase();

		//d.addUsersTable();
		//d.addOrganiserInfoTable();
		//d.addConcertInfoTable();
		//d.addInvoiceInfoTable();
		//d.addUserBookingInfoTable();
		//d.addPaymentInfo();

		//d.populateUsersTable();
		//d.populateConcertInfoTable();
		//d.populateUserBookingInfoTable();
		d.populatePaymentInfo();
	}
}
