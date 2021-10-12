package com.songer.michael.functions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveQuestionnaire {

	public SaveQuestionnaire()
	{

	}

	public static void save(String ans)
	{
		// Upload to database
		// Use current time to make file name random

		DatabaseController.writeFile("Results",ans+"&");
	}
}