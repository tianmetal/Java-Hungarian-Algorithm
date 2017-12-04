package app;

import gui.Output;

public class Test
{
	public static void main(String args[])
	{
		double[][] data =
		{
				{50,50,Double.NaN,20},
				{70,40,20,30},
				{90,30,50,Double.NaN},
				{70,20,60,70}
		};
		new Output(data, 1);
	}
}