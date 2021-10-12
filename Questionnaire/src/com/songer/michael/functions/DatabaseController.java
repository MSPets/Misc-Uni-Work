package com.songer.michael.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseController
{
	private static String dir = "database\\";
	
	public DatabaseController()
	{
	
	}
	
	private static int makeDb()
	{
		try
		{
			File f = new File(dir);
			f.mkdir();
		}
		catch(Exception e)
		{
			return 1;
		}
		return 0;
	}
	
	public static String readFile(String fileName)
	{
		String file = new File(dir+fileName).getAbsolutePath();
		String fContent = "";
		try
		{
			BufferedReader  bF = new BufferedReader(new FileReader(file+".txt"));
			fContent = bF.readLine();
			bF.close();
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			return fContent;
		}
		return fContent;
	}

	public static int writeFile(String fileName, String data)
	{
		// Try make folder
		if (makeDb() == 1)
		{
			return 1;
		}

		String file = new File(dir+fileName).getAbsolutePath();
		try
		{
			FileWriter fW = new FileWriter (file+".txt", true);
			fW.write(data);
			fW.close();
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			return 1;
		}
		return 0;
	}
	/*
	public static void main(String[] args)
	{
		System.out.println(writeFile("test", "tttt"));
		System.out.println(readFile("tes"));
	}
	*/
}
