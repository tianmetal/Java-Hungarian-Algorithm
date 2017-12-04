package model;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HungarianMethod
{
	private int crossed[][];
	private Matrix original,matrix;
	public HungarianMethod(Matrix matrix)
	{
		this.original = matrix;
		this.matrix = matrix.clone();
		crossed = new int[matrix.getSize()][matrix.getSize()];
	}
	public void invert()
	{
		Point[][] points = matrix.getAllData();
		double highest = Double.MIN_VALUE;
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				if(points[i][j] == null || highest > points[i][j].getValue()) continue;
				highest = points[i][j].getValue();
			}
		}
		
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				if(points[i][j] == null) continue;
				points[i][j].setValue(Math.abs(points[i][j].getValue()-highest));
			}
		}
	}
	public void subtract(int mode)
	{
		for (int i = 0; i < matrix.getSize(); i++)
		{
			double lowest = Double.MAX_VALUE;
			Point[] points = matrix.getData(i, mode);
			for(int j = 0; j < points.length; j++)
			{
				if(points[j] == null) continue;
				if(points[j].getValue() < lowest)
				{
					lowest = points[j].getValue();
				}
			}
			for(int j = 0; j < points.length; j++)
			{
				if(points[j] == null) continue;
				points[j].setValue(points[j].getValue()-lowest);
			}
		}
	}
	public void strip()
	{
		Point points[][] = matrix.getAllData();
		double lowest = Double.MAX_VALUE;
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				if(points[i][j] == null || crossed[i][j] != 0) continue;
				if(lowest < points[i][j].getValue()) continue;
				lowest = points[i][j].getValue();
			}
		}
		
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				if(points[i][j] == null || crossed[i][j] == 1) continue;
				else if(crossed[i][j] == 2) points[i][j].setValue(points[i][j].getValue()+lowest);
				else points[i][j].setValue(points[i][j].getValue()-lowest);
			}
		}
	}
	public int cross()
	{
		return cross(0);
	}
	public int cross(int mx)
	{
		int count = 0,
			maxcross = 0;
		Point[] points;
		int hCount[] = new int[matrix.getSize()];
		int vCount[] = new int[matrix.getSize()];
		
		for (int i = 0; i < matrix.getSize(); i++)
		{
			for (int j = 0; j < matrix.getSize(); j++)
			{
				crossed[i][j] = 0;
			}
		}
		for (int i = 0; i < matrix.getSize(); i++)
		{
			points = matrix.getData(i,Matrix.HORIZONTAL);
			for (int j = 0; j < points.length; j++)
			{
				if(points[j] == null || points[j].getValue() != 0) continue;
				hCount[i]++;
				if(hCount[i] > maxcross) maxcross = hCount[i];
			}
		}
		
		for (int i = 0; i < matrix.getSize(); i++)
		{
			points = matrix.getData(i,Matrix.VERTICAL);
			for (int j = 0; j < points.length; j++)
			{
				if(points[j] == null || points[j].getValue() != 0) continue;
				vCount[i]++;
				if(vCount[i] > maxcross) maxcross = vCount[i];
			}
		}
		
		maxcross = ((mx == 0) ? maxcross : mx);
		
		int cross = maxcross;
		while(cross > 0)
		{
			for(int i = 0; i < hCount.length; i++)
			{
				if(hCount[i] != cross) continue;
				if(vCount[i] > hCount[i]) continue;
				points = matrix.getData(i, Matrix.HORIZONTAL);
				for (int j = 0; j < points.length; j++)
				{
					if(points[j] == null) continue;
					crossed[points[j].getX()][points[j].getY()]++;
					if(points[j].getValue() == 0) vCount[j]--;
				}
				hCount[i] = 0;
				count++;
			}
			for(int i = 0; i < vCount.length; i++)
			{
				if(vCount[i] != cross) continue;
				if(hCount[i] > vCount[i]) continue;
				points = matrix.getData(i, Matrix.VERTICAL);
				for (int j = 0; j < points.length; j++)
				{
					if(points[j] == null) continue;
					crossed[points[j].getX()][points[j].getY()]++;
					if(points[j].getValue() == 0) hCount[j]--;
				}
				vCount[i] = 0;
				count++;
			}
			cross--;
			
		}
		
		if(count > matrix.getSize())
		{
			return cross(maxcross-1);
		}
		return count;
	}
	public Point[][] getResult()
	{
		Point[][] realPoints = original.getAllData();
		List<Point[]> possible = new ArrayList<Point[]>();
		List<Point[]> valid = new ArrayList<Point[]>();
		int[] input = new int[realPoints.length];
		for(int i = 0; i < input.length; i++)
		{
			input[i] = i;
		}
		permute(input, 0, possible);
		
		for(Point[] points : possible)
		{
			boolean isValid = true;
			for(Point point : points)
			{
				if(point == null || point.getValue() != 0.0)
				{
					isValid = false;
				}
			}
			if(isValid) valid.add(points);
		}
		
		Point[][] result = new Point[valid.size()][realPoints.length];
		for(int i = 0; i < result.length; i++)
		{
			Point[] points = valid.get(i);
			for(int j = 0; j < result[i].length; j++)
			{
				result[i][j] = realPoints[points[j].getX()][points[j].getY()];
			}
		}
		
		return result;
	}
	private void permute(int[] input, int startindex, List<Point[]> result)
	{
	    if(input.length == startindex)
	    {
	    	Point[][] matrix = this.matrix.getAllData();
	    	Point[] points = new Point[input.length];
	    	for(int i = 0; i < points.length; i++)
	    	{
	    		points[i] = matrix[i][input[i]];
	    	}
	    	result.add(points);
	    }
	    else
	    {
	        for (int i = startindex; i < input.length; i++)
	        {
	        	int[] input2 = input.clone();
	        	int temp = input2[i];
	            input2[i] = input2[startindex];
	            input2[startindex] = temp;
	            permute(input2, startindex + 1, result);
	        }
	    }
	}
	public JPanel printMatrix()
	{
		GridBagConstraints g = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		
		g.gridx = 0;
		g.gridy = 0;
		g.insets = new Insets(3, 3, 3, 3);
		
		Point[][] points = matrix.getAllData();
		for (int i = 0; i < points.length; i++)
		{
			for (int j = 0; j < points[i].length; j++)
			{
				g.gridx = j;
				g.gridy = i;
				JLabel label = new JLabel();
				if(points[i][j] == null)
				{
					label.setText("<html><div style='background-color:#FF0000;'>-</div></html>");
				}
				else if(crossed[i][j] == 2)
				{
					label.setText("<html><div style='background-color:#FFFF00;'>" + points[i][j].getValue() + "</div></html>");
				}
				else if(crossed[i][j] == 1)
				{
					label.setText("<html><div style='background-color:#00FF00;'>" + points[i][j].getValue() + "</div></html>");
				}
				else
				{
					label.setText("" + points[i][j].getValue());
				}
				panel.add(label,g);
			}
		}
		
		return panel;
	}
	
	public JPanel printResult(int index)
	{
		double total = 0;
		Point[][] points = getResult();
		if(index >= points.length) return null;
		GridBagConstraints g = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		
		g.insets = new Insets(3, 3, 3, 3);
		
		for (int i = 0; i < points[index].length; i++)
		{
			g.gridy = i;
			JLabel label = new JLabel("#" + (i+1));
			JLabel result = new JLabel("" + points[index][i].getValue());
			total += points[index][i].getValue();
			g.gridx = 0;
			panel.add(label,g);
			g.gridx = 1;
			panel.add(result,g);
		}
		
		JLabel label = new JLabel("Result");
		JLabel result = new JLabel("" + total);
		
		g.gridy = points[index].length;
		g.gridx = 0;
		panel.add(label,g);
		g.gridx = 1;
		panel.add(result,g);
		
		return panel;
	}
}
