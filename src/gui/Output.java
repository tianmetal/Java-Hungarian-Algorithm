package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.HungarianMethod;
import model.Matrix;

@SuppressWarnings("serial")
public class Output extends JFrame
{
	public Output(double[][] input, int mode)
	{
		int size = input.length;
		JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT);
		Matrix matrix = new Matrix(size);
		matrix.setData(input);
		
		HungarianMethod hm = new HungarianMethod(matrix);
		
		pane.add("Initial",hm.printMatrix());
		if(mode == 0)
		{
			hm.invert();
			pane.add("Inverted",hm.printMatrix());
		}
		
		hm.subtract(Matrix.HORIZONTAL);
		pane.add("H-Subtract",hm.printMatrix());
		
		hm.subtract(Matrix.VERTICAL);
		pane.add("V-Subtract",hm.printMatrix());
		
		int idx = 0;
		while(hm.cross() != size)
		{
			idx++;
			pane.add("#" + idx + " Iteration",hm.printMatrix());
			hm.strip();
			if(idx == 5) break;
		}
		pane.add("Final",hm.printMatrix());
		
		int index = 0;
		JPanel result;
		while((result = hm.printResult(index)) != null)
		{
			index++;
			pane.add("Result #" + index,result);
		}
		
		pane.addChangeListener(new ChangeListener()
		{	
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				pack();
			}
		});
		
		add(pane);
		pack();
		setTitle("Result: " + ((mode == 1) ? "Minimization" : "Maximization"));
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
