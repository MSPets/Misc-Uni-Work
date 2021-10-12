package com.songer.michael.functions;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MakeChart {
	public MakeChart()
	{
	}
	
	public static BufferedImage getChart(String[] ansLabels, ArrayList<String> ansNumbers)
	{
		// https://quickchart.io/documentation/
		// https://stackoverflow.com/questions/13448368/trying-to-display-url-image-in-jframe
        try
         {
        	String ansLabelsStr = "";
        	for (int i=0; i<ansLabels.length; i++)
        	{
        		ansLabelsStr += "\"" + ansLabels[i] +"\"";
        		if(i + 1 != ansLabels.length)
        		{
        			ansLabelsStr += "," ;
        		}
        	}
        	
        	String ansNumberStr = "";
        	for (int i = 0; i < ansNumbers.size(); i++)
        	{
        		ansNumberStr += String.valueOf(ansNumbers.get(i));
        		
        		if(i+1!=ansNumbers.size())
        		{
        			ansNumberStr+=",";
        		}
        	}
        	
            String path = "https://quickchart.io/chart?w=250&h=150&bkg=white&c={type:%27doughnut%27,"
            		+ "data:{labels:["+ansLabelsStr+"],"
            		+ "datasets:[{data:["+ansNumberStr+"]}]}}";

            return ImageIO.read(new URL(path));
        }
        catch (Exception e)
        {
            System.out.println("An Error occured: "+e.toString());
            e.printStackTrace();
        }
        return null;
	}
}
