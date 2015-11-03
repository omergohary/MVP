/**
 * @file clientHandler.java
 * 
 * @author Omer Gohary 
 * 
 * @description This file represents the interface for handler that responsible on the client action
 * 				
 * @date    20/10/2015
 */

package serverSide;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler 
{
	void handleClient(InputStream inFromClient, OutputStream outToClient);
}
