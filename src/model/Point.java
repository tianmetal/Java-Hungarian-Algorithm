package model;

public class Point
{
	private double value;
	private int x,y;
	public Point(double value, int x, int y)
	{
		this.value = value;
		this.x = x;
		this.y = y;
	}
	public double getValue()
	{
		return value;
	}
	public void setValue(double value)
	{
		this.value = value;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
}
