package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Classes.Managers.AccountManager;

public class HomeGUI extends JPanel
{
	public HomeGUI(StartupGUI parent)
	{
		setLayout(new BorderLayout());
				
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);

		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);
		
		int[] padList = ConfigGUI.getPadding();
		
		gbc.fill = GridBagConstraints.BOTH;
		int yNum = 0;

		// My Account
		gbc.gridx=0;
		gbc.gridy=yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		JButton account = new JButton("My Account");
		subPanel.add(account, gbc);
		account.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("Account details");
			}
		});
		
		gbc.gridx=0;
		gbc.gridy=yNum++;
		JButton viewConcerts = new JButton("View Concerts");
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(viewConcerts, gbc);
		viewConcerts.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.showCard("View Concerts");
			}
		});
		
		
		// If admin
		if (AccountManager.getVerification().equals("Admin") || AccountManager.getVerification().equals("Organiser"))
		{
			gbc.gridx=0;
			gbc.gridy=yNum++;
			JButton addConcert = new JButton("Add Concert");
			gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
			subPanel.add(addConcert, gbc);
			addConcert.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					parent.showCard("Add Concert");
				}
			});
		}
	}
}
