package Classes.Managers;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountManager
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName() );

	private static final DatabaseManager DATABASE = new DatabaseManager();	

	private static String username = null;

	public AccountManager()
	{
		//LOGGER.setLevel(Level.OFF);
		LOGGER.setLevel(Level.ALL);
	}

	// Customer, Corporate Organisation, Administrator and Concert/Festival Organiser
	
	/* Public functions */
	public static final int login(String username, char[] passwordChar)
	{
		String userPass = null;
		
		ArrayList<String> retList = DATABASE.queryCommand("SELECT Password FROM users WHERE Username = '" + username+ "'");
		// If ret is empty username not found
		if (retList.isEmpty())
		{
			return -1;
		}
		else
		{
			// save users password
			userPass = retList.get(0);
		}
		// Check username and password
		if (userPass.equals(new String(passwordChar)))
		{
			// password is correct
			return 0;
		}
		else
		{
			// password is wrong
			return -2;
		}
	}

	// Make an account
	public static final int createAccount(String username, String title, String firstName, String lastName, Date DOB,
			String address1, String address2, String town, String postcode, String organisationName, char[] password,
			int phoneNo, String emailAddress, String webAddress, String level)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// Try to add to database
		// Check username
		ArrayList<String> retList = DATABASE.queryCommand("SELECT * FROM Users WHERE UserName LIKE '" + username + "'");
		// If account exists
		if (!retList.isEmpty())
		{
			return 1;
		}
		try
		{
			// If username does not exist
			Statement statement = DATABASE.connect().createStatement();
			statement.execute("INSERT INTO users (username, title, first_name, last_name, DOB, Address_1, Address_2, Town, Postcode, Organisation_name, Password, Phone_no, Email_address, Web_address, Level)"
					+ "VALUES"
					+ "('"+username+"','"+title+"','"+firstName+"','"+lastName+"','"+sdf.format(DOB)+"','"+address1+"','"+address2+"','"+town+"','"+postcode+"','"+organisationName+"','"+new String(password)+"','"+phoneNo+"','"+emailAddress+"','"+webAddress+"','"+level+"')");
			DATABASE.disconnect();
		}
		catch(Exception e)
		{
			// Error running command
			DATABASE.disconnect();
			LOGGER.log(Level.WARNING, e.toString());
			return 100;
		}
		return 0;
	}
	
	public static final int updatePassword(char[] oldPassword, char[] newPassword)
	{
		// if old password is valid update
		int conf = login(getUsername(), oldPassword);
		if (conf == 0)
		{
			return DATABASE.executeCommand("UPDATE users SET Password='"+new String(newPassword)+"' WHERE Username='"+getUsername()+"'");
		}
		else
		{
			return 1;
		}
	}
	
	public static final ArrayList<String> getAccountDetails()
	{
		return DATABASE.queryCommand("SELECT Title, First_name, Last_name,"
				+ "Address_1, Address_2, Town, Postcode, Organisation_name, Phone_no, Email_address,"
				+ "Web_address FROM users where username='" + getUsername() + "'");
	}
	
	// Check if correct privileges
	public static final String getVerification()
	{
		String username = getUsername();
		// Get verification level
		ArrayList<String> retList = DATABASE.queryCommand("SELECT Level FROM users WHERE Username = '" +username+ "'");
		if (retList.isEmpty())
		{
			return "";
		}
		else
		{
			return retList.get(0);
		}
	}
	
	public static final String getUsername()
	{
		return username;
	}
	
	public static final void setUsername(String name)
	{
		username = name;
	}
	
	public static final int getNumOfUsersBookings()
	{
		ArrayList<String> bookings =  DATABASE.queryCommand("SELECT Booking_id FROM user_booking_info WHERE Username = '"+AccountManager.getUsername()+"'");
		return bookings.size();
	}
	
	public static final ArrayList<String> getSomeUsersBookings(int numOfBook, int fromNum)
	{
		return DATABASE.queryCommand("SELECT * FROM user_booking_info WHERE Username = '"+AccountManager.getUsername()+"' LIMIT " + numOfBook + " OFFSET " + fromNum);
	}
	
	public static final int getNumOfUsersInvoices()
	{
		ArrayList<String> invoices =  DATABASE.queryCommand("SELECT Invoice_id FROM invoice_info WHERE Username = '"+ AccountManager.getUsername() +"'");
		return invoices.size();
	}
	
	public static final ArrayList<String> getSomeUsersInvoices(int numOfBook, int fromNum)
	{
		return DATABASE.queryCommand("SELECT * FROM invoice_info WHERE Username = '"+ AccountManager.getUsername() +"' LIMIT " + numOfBook + " OFFSET " + fromNum);
	}
	
	public static final ArrayList<String> getInvoiceInformtion(int invoiceID)
	{
		return DATABASE.queryCommand("SELECT * FROM invoice_info WHERE Invoice_id = '"+ invoiceID +"'");
	}
	
	public static final ArrayList<String> getPaymentDetails()
	{
		return DATABASE.queryCommand("SELECT Account_no, Payment_method FROM payment_info WHERE Username = '"+ AccountManager.getUsername() +"'");
	}
	
	public static final int addPaymentDetails(int accountNumber, String paymentMethod)
	{
		try
		{
			Statement statement = DATABASE.connect().createStatement();
			
			int org = 0;
			if (AccountManager.getVerification() == "Organisation")
			{
				// 1 for true
				org = 1;
			}
			DATABASE.executeCommand("INSERT INTO payment_info (Username, Account_no, Payment_method, Organisation) VALUES ('"+AccountManager.getUsername()+"', "+accountNumber+", '"+paymentMethod+"', "+org+")");
			
			return 0;
		}
		catch(Exception e)
		{
			LOGGER.log(Level.WARNING, "Unable to add payment method\n" + e.toString());
		}
		return -1;
	}
	
	public static final int removePaymentDetails(int accountNumber, String paymentMethod)
	{
		try
		{
			DATABASE.executeCommand("DELETE FROM payment_info WHERE Username='"+getUsername()+"' AND Account_no="+accountNumber+" AND Payment_method='"+paymentMethod+"';");
			
			return 0;
		}
		catch(Exception e)
		{
			LOGGER.log(Level.WARNING, e.toString());
		}
		return -1;
	}
}
