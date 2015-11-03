/**
 * @file Maze3dDisplayer.java
 * 
 * @author Colman & Omer Gohary
 * 
 * @description This file represents a class that responsible to display the 3D-Maze
 * 				
 * @date    28/10/2015
 */

package gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

public class Maze3DDisplayer extends MazeDisplayer 
{

	public int characterCol;
	public int characterRow;

	@SuppressWarnings("unused")
	public Maze3DDisplayer(Composite parent, int style) 
	{
		super(parent, style);

		final Color white = new Color(null, 255, 255, 255);
		final Color black = new Color(null, 150, 150, 150);
		setBackground(white);
		addPaintListener(new PaintListener() 
		{

			@Override
			public void paintControl(PaintEvent e) 
			{
				if (mazeData != null) 
				{
					e.gc.setForeground(new Color(null, 0, 0, 0));
					e.gc.setBackground(new Color(null, 0, 0, 0));

					int canvasWidth = getSize().x;
					int canvasHeight = getSize().y;

					int mx = canvasWidth / 2;

					int width = mazeData[0].length;
					int height = mazeData.length;

					double cellWidth = (double) canvasWidth / width;
					double cellHeight = (double) canvasHeight / height;

					for (int i = 0; i < height; i++) 
					{
						double w0 = 0.7 * cellWidth + 0.3 * cellWidth * i / mazeData.length;
						double w1 = 0.7 * cellWidth + 0.3 * cellWidth * (i + 1) / mazeData.length;
						double start = mx - w0 * mazeData[i].length / 2;
						double start1 = mx - w1 * mazeData[i].length / 2;
						for (int j = 0; j < width; j++) 
						{
							double[] dpoints = { start + j * w0, i * cellHeight, start + j * w0 + w0, i * cellHeight,
									start1 + j * w1 + w1, i * cellHeight + cellHeight, start1 + j * w1,
									i * cellHeight + cellHeight };
							double cheight = cellHeight / 2;
							if (mazeData[i][j] != 0)
								paintCube(dpoints, cheight, e);

							if (i == characterRow && j == characterCol) 
							{
								e.gc.setBackground(new Color(null, 200, 0, 0));
								e.gc.fillOval((int) Math.round(dpoints[0]), (int) Math.round(dpoints[1] - cheight / 2),
										(int) Math.round((w0 + w1) / 2), (int) Math.round(cellHeight));
								e.gc.setBackground(new Color(null, 255, 0, 0));
								e.gc.fillOval((int) Math.round(dpoints[0] + 2),
										(int) Math.round(dpoints[1] - cheight / 2 + 2),
										(int) Math.round((w0 + w1) / 2 / 1.5), (int) Math.round(cellHeight / 1.5));
								e.gc.setBackground(new Color(null, 0, 0, 0));
							}
						}
					}
				}
			}
		});
	}

	private void paintCube(double[] p, double h, PaintEvent e) 
	{
		int[] f = new int[p.length];
		for (int k = 0; k < f.length; f[k] = (int) Math.round(p[k]), k++)
			;

		e.gc.drawPolygon(f);

		int[] r = f.clone();
		for (int k = 1; k < r.length; r[k] = f[k] - (int) (h), k += 2)
			;

		int[] b = { r[0], r[1], r[2], r[3], f[2], f[3], f[0], f[1] };
		e.gc.drawPolygon(b);
		int[] fr = { r[6], r[7], r[4], r[5], f[4], f[5], f[6], f[7] };
		e.gc.drawPolygon(fr);

		e.gc.fillPolygon(r);
	}

	@Override
	public void setCharacterPosition(int row, int col) 
	{
		characterRow = row;
		characterCol = col;
		getDisplay().syncExec(new Runnable() 
		{

			@Override
			public void run() 
			{
				redraw();
			}
		});
	}
}
