package Classes.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.UIManager;

public final class ConfigGUI
{
	//https://thebadprogrammer.com/swing-uimanager-keys/
	
	private static final Font LARGEFONT = new Font("Georgia", Font.BOLD, 16);
			
	public static final void setDefaults()
	{
		Font defaulFont = new Font("Georgia", Font.PLAIN, 13);
		
		UIManager.put("Button.font", defaulFont);
		UIManager.put("Button.background", Color.BLUE);
		UIManager.put("Button.background", Color.WHITE);

		UIManager.put("Spinner.font", defaulFont);

		UIManager.put("CheckBox.font", defaulFont);
		UIManager.put("CheckBox.background", SystemColor.inactiveCaptionBorder);
		UIManager.put("CheckBox.foreground", SystemColor.desktop);

		UIManager.put("ToggleButton.font", defaulFont);
		UIManager.put("RadioButton.font", defaulFont);
		UIManager.put("Label.font", defaulFont);
		UIManager.put("OptionPane.font", defaulFont);
		UIManager.put("Panel.font", defaulFont);
		UIManager.put("ScrollPane.font", defaulFont);
		UIManager.put("Viewport.font", defaulFont);
		UIManager.put("TabbedPane.font", defaulFont);
		UIManager.put("PasswordField.font", defaulFont);
		UIManager.put("TextArea.font", defaulFont);	
		UIManager.put("TextPane.font", defaulFont);
		UIManager.put("EditorPane.font", defaulFont);
		
		UIManager.put("Panel.background", SystemColor.inactiveCaptionBorder);
		UIManager.put("Panel.foreground", SystemColor.desktop);
		
		//mainFrame.getContentPane().setForeground(SystemColor.desktop);
		//mainFrame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		//mainFrame.setForeground(Color.CYAN);
		//mainFrame.setBackground(Color.LIGHT_GRAY);
	}
	public static final Font getFont()
	{
		return LARGEFONT;
	}
	public static final int[] getPadding()
	{
		// Top Bottom Left Right
		return new int[] {2,2,5,5};
	}
}
