package model;

public class Matrix
{
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	private int size;
	private Point[][] points;
	public Matrix(int size)
	{
		this.size = size;
		points = new Point[size][size];
	}
	public Matrix(Point[][] points)
	{
		this.size = points.length;
		this.points = points;
	}
	public void setData(double value[][])
	{
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				if(Double.isNaN(value[i][j])) continue;
				points[i][j] = new Point(value[i][j], i, j);
			}
		}
	}
	public Point[][] getAllData()
	{
		return points;
	}
	public Point[] getData(int col, int mode)
	{
		Point[] result = new Point[size];
		if(mode == HORIZONTAL)
		{
			for (int i = 0; i < size; i++)
			{
				result[i] = points[col][i];
			}
		}
		else if(mode == VERTICAL)
		{
			for (int i = 0; i < size; i++)
			{
				result[i] = points[i][col];
			}
		}
		return result;
	}
	public int getSize()
	{
		return size;
	}
	public String printMatrix()
	{
		String out = "";
		
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				out += points[i][j].getValue() + " ";
			}
			out += "\n";
		}
		
		return out;
	}
	public Matrix clone()
	{
		Matrix copy = new Matrix(size);
		double[][] a = new double[size][size];
		for(int i = 0; i < a.length; i++)
		{
			for (int j = 0; j < a[i].length; j++)
			{
				if(points[i][j] != null)
				{
					a[i][j] = points[i][j].getValue();
				}
				else
				{
					a[i][j] = Double.NaN;
				}
			}
		}
		copy.setData(a);
		return copy;
	}
}
