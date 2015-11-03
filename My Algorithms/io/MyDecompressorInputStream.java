/**
 * @file MyDecompressorInputStream.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the decompressor of a 3d maze
 * 				
 * @date    21/09/2015
 */

package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents a decompressor of byteArray according to maze3d object
 * It extents the InputStream and by that it is complying with the decorator pattern
 */
public class MyDecompressorInputStream extends InputStream 
{
	/**
	 * C-Tor
	 * @param aIn - the stream in (to read from)
	 */
	public MyDecompressorInputStream(InputStream aIn) 
	{
		in = aIn;
	}

	/**
	 * InputStream function that must implement.
	 * It is responsible on taking the compressed bytes and padding them to the originally maze.
	 * @return 0 for success -1 for failed
	 */
	@Override
	public int read() throws IOException 
	{
		// if not end of file
		if (currByte >= 0)
		{
			if (repeat <= 0) 
			{
				// if no more times to repeat, get next byte and next amout to repeat
				currByte = in.read();
				repeat = in.read();
			}
		}
		
		repeat--;
		return currByte;
	}
	
	/********** Members *********/
	InputStream in;
	int currByte = 0;
	int repeat   = 0;
	
}
