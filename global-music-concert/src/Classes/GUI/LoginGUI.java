package Classes.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Classes.Managers.AccountManager;

public class LoginGUI extends JPanel
{
	public LoginGUI(StartupGUI parent)
	{
		setLayout(new BorderLayout());

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		Font largeFont = ConfigGUI.getFont();
		int numOfColumns = 10;
		int[] padList = ConfigGUI.getPadding();

		Border redBorder = BorderFactory.createLineBorder(Color.RED);
		Border defaultBorder = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

		JPanel subPanel = new JPanel();
		subPanel.setLayout(gbl);
		
		JScrollPane scrollPane = new JScrollPane(subPanel);
		scrollPane.setSize(this.getWidth(), this.getHeight());
		add(scrollPane);
		
		/* LOGIN */	
		int yNum=0;
		gbc.fill = GridBagConstraints.BOTH;
		
		JLabel loginLbl = new JLabel("Login", JTextField.CENTER);
		loginLbl.setFont(largeFont);
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth = 2;
		subPanel.add(loginLbl, gbc);
		gbc.gridwidth = 1;
		
		// Username
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Username", SwingUtilities.RIGHT), gbc);

		JTextField loginUsernameTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(loginUsernameTf, gbc);
		loginUsernameTf.setColumns(numOfColumns);

		// Password
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Password", SwingUtilities.RIGHT), gbc);

		JPasswordField loginPasswordPf = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(loginPasswordPf, gbc);
		loginPasswordPf.setColumns(numOfColumns);

		// Login button
		JButton loginBtn = new JButton("Login");
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(loginBtn, gbc);

		/* CREATE ACCOUNT */
		JLabel createAccountLbl = new JLabel("Create Account", JTextField.CENTER);
		createAccountLbl.setFont(largeFont);
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth = 2;
		subPanel.add(createAccountLbl, gbc);
		gbc.gridwidth = 1;
		
		// Username
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Username", SwingUtilities.RIGHT), gbc);

		JTextField newUsernameTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(newUsernameTf, gbc);
		newUsernameTf.setColumns(numOfColumns);

		// Title
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Title", SwingUtilities.RIGHT), gbc);

		JTextField titleTf = new JTextField();
		titleTf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(titleTf, gbc);
		titleTf.setColumns(numOfColumns);

		// First name
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("First Name", SwingUtilities.RIGHT), gbc);

		JTextField firstNameTf = new JTextField();
		firstNameTf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(firstNameTf, gbc);
		firstNameTf.setColumns(numOfColumns);

		// Surname
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Surname", SwingUtilities.RIGHT), gbc);

		JTextField surnameTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(surnameTf, gbc);
		surnameTf.setColumns(numOfColumns);

		// Street address 1
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add( new JLabel("Street Address", SwingUtilities.RIGHT), gbc);

		JTextField streetAddress1Tf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(streetAddress1Tf, gbc);
		streetAddress1Tf.setColumns(numOfColumns);

		// Street address 2
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Street address 2", SwingUtilities.RIGHT), gbc);

		JTextField streetAddress2Tf = new JTextField();
		streetAddress2Tf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(streetAddress2Tf, gbc);
		streetAddress2Tf.setColumns(numOfColumns);

		// Town
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add( new JLabel("Town", SwingUtilities.RIGHT), gbc);

		JTextField townTf = new JTextField();
		townTf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(townTf, gbc);
		townTf.setColumns(numOfColumns);

		// PostCode
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Postcode", SwingUtilities.RIGHT), gbc);

		JTextField postCodeTf = new JTextField();
		postCodeTf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(postCodeTf, gbc);
		postCodeTf.setColumns(numOfColumns);

		// Organisation
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Organisation", SwingUtilities.RIGHT), gbc);

		JTextField organisationTf = new JTextField();
		organisationTf.setBackground(SystemColor.window);
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(organisationTf, gbc);
		organisationTf.setColumns(numOfColumns);

		// Email address
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Email address", SwingUtilities.RIGHT), gbc);

		JTextField emailAddressTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(emailAddressTf, gbc);
		emailAddressTf.setColumns(numOfColumns);

		// Web address
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Web address", SwingUtilities.RIGHT), gbc);

		JTextField webAddressTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(webAddressTf, gbc);
		webAddressTf.setColumns(numOfColumns);

		// Phone number
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Phone number", SwingUtilities.RIGHT), gbc);

		JTextField phoneNumberTf = new JTextField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(phoneNumberTf, gbc);
		phoneNumberTf.setColumns(numOfColumns);

		// DOB
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Date  of birth", SwingUtilities.RIGHT), gbc);

		// DOB Spinner
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date now = calendar.getTime();
		calendar.add(Calendar.YEAR, - 120);
		Date min = calendar.getTime();
		SpinnerModel model = new SpinnerDateModel(now, min, now, Calendar.YEAR);
		JSpinner dobSpnr = new JSpinner(model);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(dobSpnr, "yyyy-MM-dd");
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		dobSpnr.setEditor(editor);
		// Add to panel
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(dobSpnr, gbc);
		
		// Organisation
		gbc.gridx = 0;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		gbc.gridwidth=2;
		JCheckBox organisationCb = new JCheckBox("Are you an organisation?");
		organisationCb.setHorizontalAlignment(JLabel.CENTER);
		subPanel.add(organisationCb, gbc);
		gbc.gridwidth=1;

		// Set password
		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Set Password", SwingUtilities.RIGHT), gbc);

		JPasswordField newPasswordPf = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(newPasswordPf, gbc);
		newPasswordPf.setColumns(numOfColumns);

		gbc.gridx = 0;
		gbc.gridy = yNum;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(new JLabel("Confirm password", SwingUtilities.RIGHT), gbc);

		JPasswordField confirmPasswordPf = new JPasswordField();
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(confirmPasswordPf, gbc);
		confirmPasswordPf.setColumns(numOfColumns);

		JButton signUpBtn = new JButton("Sign Up");
		gbc.gridx = 1;
		gbc.gridy = yNum++;
		gbc.insets = new Insets(padList[0],padList[2],padList[1],padList[3]);
		subPanel.add(signUpBtn, gbc);
		
		// Login button pressed
		loginBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountManager ac = new AccountManager();

				// If text fields are not empty
				if (!loginUsernameTf.getText().isEmpty() && loginPasswordPf.getPassword().length != 0)
				{
					int ret = ac.login(loginUsernameTf.getText(), loginPasswordPf.getPassword());
					if (ret == 0)
					{
						AccountManager.setUsername(loginUsernameTf.getText());
						parent.showCard("Home");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Username or password are not correct");
						loginUsernameTf.setText("");
						loginPasswordPf.setText("");
					}
				}
				else
				{
					// Fields not filled
					JOptionPane.showMessageDialog(null, "Username or password not entered");
					if  (loginUsernameTf.getText().isEmpty()){loginUsernameTf.setBorder(redBorder);}
					else {loginUsernameTf.setBorder(defaultBorder);}
					if  (loginPasswordPf.getPassword().length == 0){loginPasswordPf.setBorder(redBorder);}
					else {loginPasswordPf.setBorder(defaultBorder);}
				}
			}
		});
		// Sign up button pressed
		signUpBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Check all fields are not empty
				if (!newUsernameTf.getText().isEmpty() &&
						!titleTf.getText().isEmpty() &&
						!firstNameTf.getText().isEmpty() &&
						!surnameTf.getText().isEmpty() &&
						!streetAddress1Tf.getText().isEmpty() &&
						!townTf.getText().isEmpty() &&
						!postCodeTf.getText().isEmpty() &&
						!emailAddressTf.getText().isEmpty() &&
						!phoneNumberTf.getText().isEmpty() &&
						newPasswordPf.getPassword().length != 0 &&
						confirmPasswordPf.getPassword().length != 0)
				{
					int phoneNumber = 0;
					// Check phone number
					try
					{
						phoneNumber = Integer.parseInt(phoneNumberTf.getText());
					}
					catch(Exception er)
					{
						JOptionPane.showMessageDialog(null, "Phone number is not a number");
						phoneNumberTf.setText("");
						phoneNumberTf.setBorder(redBorder);
						return;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					// Make sure user is old enough
					String userDobStr = null;
					String limitStr = null;
					Date userDobDate = null;
					Date limitDate = null;
					try
					{
						userDobStr = sdf.format(dobSpnr.getValue());
						userDobDate = sdf.parse(userDobStr);
						Calendar ageLimit = calendar.getInstance();
						ageLimit.add(Calendar.YEAR, -16);
						limitStr = sdf.format(ageLimit.getTime());
						limitDate = sdf.parse(limitStr);
					}
					catch(Exception ee)
					{
						// Error
						ee.printStackTrace();
						return;
					}
					if (userDobDate.getTime() < limitDate.getTime())
					{
						// Passwords are equal
						if (Arrays.equals(newPasswordPf.getPassword(), confirmPasswordPf.getPassword()))
						{
							AccountManager ac = new AccountManager();
							// Convert password to string to add to database
							String user = "User";
							if (organisationCb.isSelected())
							{
								user = "Organisation";
							}
							int ret = ac.createAccount(newUsernameTf.getText(), titleTf.getText(), firstNameTf.getText(), surnameTf.getText(), userDobDate,
									streetAddress1Tf.getText(), streetAddress2Tf.getText(), townTf.getText(), postCodeTf.getText(), organisationTf.getText(), newPasswordPf.getPassword(),

									phoneNumber, emailAddressTf.getText(), webAddressTf.getText(), user);
							// userDobStr
							// Worked well
							if(ret == 0)
							{
								AccountManager.setUsername(newUsernameTf.getText());
								parent.showCard("Home");
							}
							// username invalid
							else if (ret == 1)
							{
								newUsernameTf.setText("");
							}
							// Different error
							else
							{
							}
						}
						else
						{
							newPasswordPf.setText("");
							confirmPasswordPf.setText("");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "You are not old enough");
					}
				}
				else
				{
					// Display error
					JOptionPane.showMessageDialog(null, "One or more fields are not filled");
					// Set red border or remove possible red border
					if  (newUsernameTf.getText().isEmpty()) {newUsernameTf.setBorder(redBorder);}
					else {newUsernameTf.setBorder(defaultBorder);}
					if (titleTf.getText().isEmpty()){titleTf.setBorder(redBorder);}
					else {titleTf.setBorder(defaultBorder);}
					if( firstNameTf.getText().isEmpty()){firstNameTf.setBorder(redBorder);}
					else {firstNameTf.setBorder(defaultBorder);}
					if (surnameTf.getText().isEmpty()){surnameTf.setBorder(redBorder);}
					else {surnameTf.setBorder(defaultBorder);}
					if (streetAddress1Tf.getText().isEmpty()){streetAddress1Tf.setBorder(redBorder);}
					else {streetAddress1Tf.setBorder(defaultBorder);}
					if (townTf.getText().isEmpty()){townTf.setBorder(redBorder);}
					else {townTf.setBorder(defaultBorder);}
					if (postCodeTf.getText().isEmpty()){postCodeTf.setBorder(redBorder);}
					else {postCodeTf.setBorder(defaultBorder);}
					if (emailAddressTf.getText().isEmpty()){emailAddressTf.setBorder(redBorder);}
					else {emailAddressTf.setBorder(defaultBorder);}
					if (phoneNumberTf.getText().isEmpty()){phoneNumberTf.setBorder(redBorder);}
					else {phoneNumberTf.setBorder(defaultBorder);}
					if (newPasswordPf.getPassword().length == 0){newPasswordPf.setBorder(redBorder);}
					else {newPasswordPf.setBorder(defaultBorder);}
					if (confirmPasswordPf.getPassword().length == 0){confirmPasswordPf.setBorder(redBorder);}
					else {confirmPasswordPf.setBorder(defaultBorder);}
				}
			}
		});
	}
}
