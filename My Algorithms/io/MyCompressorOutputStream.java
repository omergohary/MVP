/**
 * @file MyCompressorOutputStream.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents the compressor of a 3d maze
 * 				
 * @date    20/09/2015
 */

package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class represents a compressor of byteArray according to maze3d object
 * It extents the OutputStream and by that it is complying with the decorator pattern
 */
public class MyCompressorOutputStream extends OutputStream 
{
	/**
	 * C-Tor
	 * @param aOut - the stream out
	 */
	public MyCompressorOutputStream(OutputStream aOut) 
	{
		this.out = aOut;
	}

	/**
	 * OutputStream's function that must implement.
	 * It is responsible to handle single byte, check its state according to the last 
	 * incoming cell and eventually write to the outstream.
	 * @param b - an new byte from the byteArray in order to compress
	 */
	@Override
	public void write(int b) throws IOException 
	{
		if (first)
		{
			first = false;
			last = b;
			count=1;
		} 
		else 
		{
			if (b != last) 
			{
				out.write(last); // writes the byte (0/1)
				out.write(count); // writes how many occurrences
				last = b;
				count = 1;
			} 
			else 
			{
				count++;
			}
		}
		
		if(b==-1) 
		{
			out.write(b);
		}
	}
	
	/**
	 * This function is overloading of the previous function. 
	 * It gets a byteArray reffernce and compress it.
	 */
	@Override
	public void write(byte[] arr) throws IOException 
	{
		this.first = true;
		
		for (int i = 0; i < arr.length; i++) 
		{
			write(arr[i]);
		}
		write(-1);
	}
	
/****************** Members *********************/
	
	/** The decorator pattern - contain the outstream and add the compressor functionality **/
	OutputStream out;
	
	/** The last and counter that needed to the compressor functionality **/
	int last;
	int count;
	
	boolean first = true;
}
