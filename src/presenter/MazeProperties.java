/**
 * @file Properties.java
 * 
 * @author Omer Gohary
 * 
 * @description This file represents a class that responsible on the external configuration file
 * 				
 * @date    28/10/2015
 */

package presenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * This singleton class handles the configuration file
 */
public class MazeProperties implements Serializable
{
	private MazeProperties()
	{

	}
	
    public static MazeProperties getInstance() 
    {
	     return m_properties;
	}
    
    public int GetNumOfThreads()
    {
    	return m_numOfThreads;
    }
    
    public void loadConfigFile() throws IOException
    {
        Properties prop = new Properties();
        
        FileInputStream fis = new FileInputStream(ConfigFileName);
        
        prop.loadFromXML(fis);

        fis.close();
        
        m_numOfThreads 			= Integer.valueOf(prop.getProperty("NumOfThreads"));
        m_gererationAlgorithm   = prop.getProperty("GenerationAlgorithm");
        m_searchAlgorithm       = prop.getProperty("SearchAlgorithm");
    }
    
    /**
     * Overloading of the next function, set default params
     * @throws IOException
     */
    public void setConfigToFile() throws IOException
    {
    	setConfigToFile(20, "BFS", "SimpleGeneration");
    }

    /**
     * Write to XML file the configurations
     * 
     * @param numOfThreads        - max threads allow in program
     * @param searchAlgorithm     - the default search algorithm to use
     * @param generationAlgorithm - the default generation algorithm to use
     * @throws IOException
     */
    public void setConfigToFile(int numOfThreads, String searchAlgorithm, String generationAlgorithm) throws IOException
    {
    	m_numOfThreads			= numOfThreads;
    	m_searchAlgorithm 		= searchAlgorithm;
    	m_gererationAlgorithm 	= generationAlgorithm;
    	
        Properties prop = new Properties();
        prop.setProperty("NumOfThreads", 		String.valueOf(m_numOfThreads));
        prop.setProperty("SearchAlgorithm", 	m_searchAlgorithm);
        prop.setProperty("GenerationAlgorithm", m_gererationAlgorithm);

        FileOutputStream fos = new FileOutputStream(ConfigFileName);
        
        prop.storeToXML(fos, "MazeConfiguration");
        
        fos.close();
    }
	
	/***** Properties instance *****/
	
	private static MazeProperties m_properties = new MazeProperties();

	/******** Config Params ********/
	
	public int m_numOfThreads;
	public String m_searchAlgorithm;
	String m_gererationAlgorithm;
	
	/*********** Consts ************/
    private static final long serialVersionUID = 1L;
    public static final String ConfigFileName = "properties.xml";

}
