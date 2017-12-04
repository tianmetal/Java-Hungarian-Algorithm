package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Input extends JFrame
{
	GridBagConstraints gbc = new GridBagConstraints();
	JPanel panel = new JPanel(new GridBagLayout());
	JTextField inputWidth,inputHeight;
	JTextField[][] input;
	JButton confirmInput,calculate;
	JComboBox<String> mode;
	public Input()
	{
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel("Width: "),gbc);
		
		gbc.gridx = 1;
		inputWidth = new JTextField(3);
		panel.add(inputWidth,gbc);
		
		gbc.gridx = 2;
		panel.add(new JLabel("Height: "),gbc);
		
		gbc.gridx = 3;
		inputHeight = new JTextField(3);
		panel.add(inputHeight,gbc);
		
		gbc.gridx = 4;
		confirmInput = new JButton("Confirm");
		panel.add(confirmInput, gbc);
		
		confirmInput.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				confirmInput.setEnabled(false);
				inputWidth.setEnabled(false);
				inputHeight.setEnabled(false);
				
				GridBagConstraints g = new GridBagConstraints();
				JPanel body = new JPanel(new GridBagLayout());
				int width = Integer.parseInt(inputWidth.getText());
				int height = Integer.parseInt(inputHeight.getText());
				int size = width;
				size = ((height > size) ? height : size);
				input = new JTextField[size][size];
				g.insets = new Insets(2, 2, 2, 2);
				g.gridx = 0;
				g.gridy = 0;
				
				for(int i = 0; i <= size; i++)
				{
					g.gridx = i;
					g.gridy = 0;
					if(i == 0) body.add(new JLabel("#"), g);
					else
					{
						body.add(new JLabel("#" + i),g);
						g.gridx = 0;
						g.gridy = i;
						body.add(new JLabel("#" + i),g);
					}
				}
				
				g.gridx = 1;
				g.gridy = 1;
				for(int i = 0; i < size; i++)
				{
					g.gridx = 1;
					for (int j = 0; j < size; j++)
					{
						input[i][j] = new JTextField(3);
						if(j >= width || i >= height)
						{
							input[i][j].setText("0");
							input[i][j].setEnabled(false);
						}
						body.add(input[i][j],g);
						g.gridx++;
					}
					g.gridy++;
				}
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.gridwidth = 5;
				panel.add(body,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.gridwidth = 2;
				mode = new JComboBox<String>();
				mode.addItem("Maximization");
				mode.addItem("Minimization");
				panel.add(mode,gbc);
				
				gbc.gridx = 2;
				calculate = new JButton("Calculate");
				panel.add(calculate,gbc);
				
				calculate.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						int size = input.length;
						double[][] result = new double[size][size];
						int method = mode.getSelectedIndex();
						for(int i = 0; i < size; i++)
						{
							for(int j = 0; j < size; j++)
							{
								try
								{
									result[i][j] =  Double.parseDouble(input[i][j].getText());
								}
								catch(NumberFormatException | NullPointerException e)
								{
									result[i][j] = Double.NaN;
								}
							}
						}
						new Output(result,method);
					}
				});
				
				pack();
			}
		});
		
		add(panel);
		pack();
		setTitle("Hungarian Method");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
