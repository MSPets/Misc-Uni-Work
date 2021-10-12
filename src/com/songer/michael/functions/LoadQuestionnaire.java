package com.songer.michael.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadQuestionnaire
{
	public static ArrayList<List> getAllResults()
	{
		// Get all
		String res = DatabaseController.readFile("Results");

		// Split results
		String[] resList = res.split("&");

		// Split between results
		ArrayList<List> resArList = new ArrayList<List>();

		for (String tList:resList)
		{
			List<String> tempList = Arrays.asList(tList.split(","));

			resArList.add(tempList);
		}
		return resArList;
	}

	public static void setup()
	{

	}

	public static ArrayList<ArrayList> getResultNumbers()
	{

		ArrayList <ArrayList> questionsAL = new ArrayList<ArrayList>();
		ArrayList <Integer> amountAL = new ArrayList<Integer>();

		// Setup
		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);

		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);

		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);

		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);

		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);

		amountAL = new ArrayList<Integer>();
		amountAL.add(0);
		amountAL.add(0);
		amountAL.add(0);
		questionsAL.add(amountAL);
		
		ArrayList<List> allRes = getAllResults();

		// cycle through questionnaires and count specific answers
		for (int i = 0; i < allRes.size(); i++)
		{
			List<String> sQuestions = allRes.get(i);
			for(int ii = 0; ii<sQuestions.size(); ii++)
			{
				List<String> tempLS = Arrays.asList(sQuestions.get(ii).split(":"));
				String realAnsStr = tempLS.get(1);

				// Q1
				if (ii == 0)
				{
					amountAL = questionsAL.get(0);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					else if(realAnsStr.equals("ans4"))
					{
						amountAL.set(3, amountAL.get(3) + 1);
					}
					else if(realAnsStr.equals("ans5"))
					{
						amountAL.set(4, amountAL.get(4) + 1);
					}
					questionsAL.set(0, amountAL);
				}
				// Q2
				else if(ii == 1)
				{
					amountAL = questionsAL.get(1);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					questionsAL.set(1, amountAL);
				}
				// Q3
				else if(ii == 2)
				{
					amountAL = questionsAL.get(2);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					questionsAL.set(2, amountAL);
				}

				// Q4
				else if(ii == 3)
				{
					amountAL = questionsAL.get(3);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					questionsAL.set(3, amountAL);
				}
				// Q5
				else if(ii == 4)
				{
					amountAL = questionsAL.get(4);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					questionsAL.set(4, amountAL);
				}
				// Q6
				else if(ii == 5)
				{
					amountAL = questionsAL.get(5);
					if(realAnsStr.equals("ans1"))
					{
						amountAL.set(0, amountAL.get(0) + 1);
					}
					else if(realAnsStr.equals("ans2"))
					{
						amountAL.set(1, amountAL.get(1) + 1);
					}
					else if(realAnsStr.equals("ans3"))
					{
						amountAL.set(2, amountAL.get(2) + 1);
					}
					questionsAL.set(5, amountAL);
				}
			}
		}
		System.out.println(questionsAL);
		return questionsAL;
	}
	/*
	public static void main(String[] args)
	{
		ArrayList<ArrayList> a = getResultNumbers();
		System.out.println(a);
	}
	*/
}
