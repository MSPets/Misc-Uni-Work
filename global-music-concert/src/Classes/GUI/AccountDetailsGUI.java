package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Classes.Managers.AccountManager;

public class AccountDetailsGUI extends JPanel
{	
	public AccountDetailsGUI(StartupGUI parent)
	{		
		setLayout(new BorderLayout());

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);

		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);

		Font largeFont = ConfigGUI.getFont();
		int numOfColumns = 10;
		int[] padList = ConfigGUI.getPadding();

		gbc.fill = GridBagConstraints.BOTH;
		int yNum = 0;

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

		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.gridwidth=2;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JLabel accountDetailsLbl = new JLabel("Account Details", JTextField.CENTER);
		accountDetailsLbl.setFont(largeFont);
		subPanel.add(accountDetailsLbl, gbc);

		/* Show details */
		ArrayList<String> retList = AccountManager.getAccountDetails();
		List<String> details = Arrays.asList(retList.get(0).split(","));
		/*
		 * 0 Title  -  1 First_name  -  2 Last_name          -    3 Address_1  -  4 Address_2
		 * 5 Town   -  6 Postcode    -  7 Organisation_name  -    8 Phone_no   -  9 Email_address
		 * 10 Web_address
		 */
		// Name		
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(0) + " " + details.get(1) + " " + details.get(2), JTextField.CENTER), gbc);
		gbc.gridwidth=1;

		// username
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Username -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(AccountManager.getUsername(), SwingConstants.LEFT), gbc);

		// Address 1
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Address 1 -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(3), SwingConstants.LEFT), gbc);

		// Address 2
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Address 2 -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(4), SwingConstants.LEFT), gbc);

		// Town
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Town -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(5), SwingConstants.LEFT), gbc);

		// Post code
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Postcode -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(6), SwingConstants.LEFT), gbc);

		// Organisation name
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Organisation name -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(7), SwingConstants.LEFT), gbc);

		// Phone number
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Phone number -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel( details.get(8), SwingConstants.LEFT), gbc);

		// Email address
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Email address -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(9), SwingConstants.LEFT), gbc);

		// Web address
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Web address -", SwingConstants.RIGHT), gbc);
		gbc.gridx=1;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel(details.get(10), SwingConstants.LEFT), gbc);

		// Change password
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth=2;
		JLabel changePasswordLbl = new JLabel ("Change Password", JTextField.CENTER);
		changePasswordLbl.setFont(largeFont);
		subPanel.add(changePasswordLbl, gbc);
		gbc.gridwidth=1;

		// Old password
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel ("Old password", SwingConstants.RIGHT), gbc);

		gbc.gridx=1;
		gbc.gridy=yNum++;
		JPasswordField oldPassword = new JPasswordField("");
		oldPassword.setColumns(numOfColumns);
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(oldPassword, gbc);

		// New password
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("New Password", SwingConstants.RIGHT), gbc);

		gbc.gridx=1;
		gbc.gridy=yNum++;
		JPasswordField newPassword = new JPasswordField("");
		newPassword.setColumns(numOfColumns);
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(newPassword, gbc);

		// New password retyped
		gbc.gridx=0;
		gbc.gridy=yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Retype new Password", SwingConstants.RIGHT), gbc);

		gbc.gridx=1;
		gbc.gridy=yNum++;
		JPasswordField retypeNewPassword = new JPasswordField("");
		retypeNewPassword.setColumns(numOfColumns);
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(retypeNewPassword, gbc);

		// Change password button
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.gridwidth=2;
		JButton changePassword = new JButton("Change Password");
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(changePassword, gbc);

		changePassword.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// If passwords match try to update system
				if (Arrays.equals(newPassword.getPassword(), retypeNewPassword.getPassword()) && newPassword.getPassword().length > 0)
				{
					// If password is correct
					// Not sure about security
					int ret = AccountManager.updatePassword(oldPassword.getPassword(), newPassword.getPassword());
					if (ret == 0)
					{
						// If works
						JOptionPane.showMessageDialog(null, "Password changed");
					}
					else
					{
						// If something goes wrong
						JOptionPane.showMessageDialog(null, "Error changing password");
					}
					oldPassword.setText("");
					newPassword.setText("");
					retypeNewPassword.setText("");
				}
				else
				{
					oldPassword.setText("");
					newPassword.setText("");
					retypeNewPassword.setText("");
				}
			}
		});

		// Change password button
		gbc.gridx=0;
		gbc.gridy=yNum++;
  		JButton viewBookings = new JButton("View Bookings");
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(viewBookings, gbc);
		viewBookings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Bookings");
			}
		});
		
		gbc.gridx=0;
		gbc.gridy=yNum++;
		JButton viewInvoices = new JButton("View Invoices");
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(viewInvoices, gbc);
		viewInvoices.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Invoices");
			}
		});
		if (AccountManager.getVerification().equals("Organiser"))
		{
			// Change password button
			gbc.gridx=0;
			gbc.gridy=yNum++;
	  		JButton myConcerts = new JButton("My Concerts");
			gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			subPanel.add(myConcerts, gbc);
			myConcerts.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					parent.showCard("My Concerts");
				}
			});
		}
	}
}