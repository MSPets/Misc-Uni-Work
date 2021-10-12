package Classes.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
// https://coderwall.com/p/609ppa/printing-the-result-of-resultset

public class DatabaseManager
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName() );
	
	// Database Properties
	private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/Concert";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String TIMEZONE = "UTC";
	private static final String MAX_POOL = "250";

	private Connection con = null;
	private Properties prop = null;

	// Setup logger
	public DatabaseManager()
	{
		//LOGGER.setLevel(Level.OFF);
		LOGGER.setLevel(Level.ALL);
	}

	// Initialise properties
	private Properties getProperties()
	{
		if (prop == null) {
			prop = new Properties();
			prop.setProperty("user", USERNAME);
			prop.setProperty("password", PASSWORD);
			prop.setProperty("MaxPooledStatements", MAX_POOL);
			prop.setProperty("serverTimezone", TIMEZONE);
			prop.setProperty("useLegacyDatetimeCode", "false");
			prop.setProperty("createDatabaseIfNotExist", "true");
		}
		return prop;
	}

	// Connect to database
	// Public so other classes can do custom commands
	public Connection connect()
	{
		if (con == null)
		{
			try
			{
				Class.forName(DATABASE_DRIVER);
				con = DriverManager.getConnection(DATABASE_URL , getProperties());
			}
			catch (ClassNotFoundException | SQLException e)
			{
				LOGGER.log(Level.WARNING, "Database connect error\n" + e.toString());
			}
		}
		return con;
	}
	// Disconnect database
	public void disconnect()
	{
		if (con != null)
		{
			try
			{
				con.close();
				con = null;
			}
			catch (SQLException e)
			{
				LOGGER.log(Level.WARNING, "Database disconnect error\n" + e.toString());
			}
		}
	}

	// Run sql query with arraylist as return 
	public ArrayList<String> queryCommand(String command)
	{
		DatabaseManager database = this;

		ArrayList<String> resultList= new ArrayList<String>();
		try
		{
			Statement statement = database.connect().createStatement();
			ResultSet rs = statement.executeQuery(command);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numOfColumns = rsmd.getColumnCount();

			String tempRes = null;
			// While theirs still results
			while (rs.next())
			{
				tempRes = "";
				int i = 1;
				// while theirs still columns
				while(i <= numOfColumns)
				{
					if (i + 1 > numOfColumns)
					{
						tempRes += rs.getString(i++);
					}
					else
					{
						tempRes += rs.getString(i++) + ",";
					}
				}
				resultList.add(tempRes);
			}
			rs.close();
		}
		catch (SQLException e)
		{
			database.disconnect();
			LOGGER.log(Level.WARNING, "Database query error\n" + e.toString());
		}
		finally
		{
			database.disconnect();
			return resultList;
		}    	
	}
	
	// Run mysql update command
	public int updateCommand(String command)
	{
		DatabaseManager database = this;
		
		// If ret is anything apart from 0 command probably worked
		int ret = 0;
		try
		{
			Statement statement = database.connect().createStatement();
			ret = statement.executeUpdate(command);
			database.disconnect();
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Database update error\n" + e.toString());
			database.disconnect();
			ret = 0;
		}
		return ret;
	}
	
	// Run basic mysql execute command
	public int executeCommand(String command)
	{
		DatabaseManager database = this;
		try
		{
			Statement statement = database.connect().createStatement();
			statement.execute(command);
			database.disconnect();
		}

		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Database execute error\n" + e.toString());
			database.disconnect();
			return 1;
		}
		return 0;
	}

	// Test
	/*
	public static void main(String[] args)
	{
		DatabaseManager d = new DatabaseManager();
		String sql = "SELECT * FROM a WHERE 1";
		try
		{
			Statement statement = d.connect().prepareStatement(sql);
			ResultSet res = statement.executeQuery(sql);	
			//System.out.println(res);
			while(res.next())
			{
				System.out.println(res.getString(1));
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, e.toString());
		}
		finally
		{
			d.disconnect();
		}
	}
	*/

}