package Classes.GUI;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

// https://programming.guide/java/create-a-custom-event.html

public class StartupGUI
{
	private JFrame mainFrame = null;
	private JPanel cards = null;
	
	private JPanel loginPanel;
	private JPanel viewConcertsPanel;
	private JPanel accountPanel;
	private JPanel viewBookingsPanel;
	private JPanel viewInvoicesPanel;
	private JPanel myConcertsPanel;

	private static boolean needsRefresh = true;
	
	// Switch to a different card
	public void showCard(String key)
	{
		mainFrame.setTitle(key);
		// Remove cards to be refreshed later
		try{cards.remove(loginPanel);}
		catch (Exception e) {}
		
		try{cards.remove(viewConcertsPanel);}
		catch (Exception e) {}
		
		try{cards.remove(viewBookingsPanel);}
		catch (Exception e) {}
		
		try{cards.remove(viewInvoicesPanel);}
		catch (Exception e) {}
		
		// Update cards
		if (needsRefresh == true)
		{
			JPanel homePanel = new HomeGUI(this);
			JPanel newConcertPanel = new AddConcertGUI(this);
			
			cards.add(homePanel, "Home");
			cards.add(newConcertPanel, "Add Concert");
			
			needsRefresh = false;
		}
		// Reload concerts
		if (key == "View Concerts")
		{
			viewConcertsPanel = new ViewConcertsGUI(this);
			cards.add(viewConcertsPanel, "View Concerts");
		}
		// Reload details
		else if (key == "Account details")
		{
			accountPanel = new AccountDetailsGUI(this);
			cards.add(accountPanel, "Account details");
		}
		else if (key == "Bookings")
		{
			viewBookingsPanel = new ViewBookingsGUI(this);
			cards.add(viewBookingsPanel, "Bookings");
		}
		else if (key == "Invoices")
		{
			viewInvoicesPanel = new ViewInvoicesGUI(this);
			cards.add(viewInvoicesPanel , "Invoices");
		}
		else if (key == "My Concerts")
		{
			myConcertsPanel = new MyConcertsGUI(this);
			cards.add(myConcertsPanel , "My Concerts");
		}
		
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, key);
	}
	// Setup frame
	private void setup()
	{
		ConfigGUI.setDefaults();
		
		mainFrame = new JFrame("Login");
		mainFrame.setSize(1080, 720);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		cards = new JPanel(new CardLayout());

		loginPanel = new LoginGUI(this);
		cards.add(loginPanel, "");

		mainFrame.add(cards);
		mainFrame.setVisible(true);
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new StartupGUI().setup();
				}
				catch (Exception e)
				{
					// log error
					e.printStackTrace();
				}
			}
		});
	}
}
