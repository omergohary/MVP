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
    public String getSearchAlgorithm() 
    {
		return m_searchAlgorithm;
	}

	public String getGererationAlgorithm() 
	{
		return m_gererationAlgorithm;
	}

	public String getCLIorGUI() 
	{
		return m_CLIorGUI;
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
        m_CLIorGUI              = prop.getProperty("CLIorGUI");
    }
    
    /**
     * Overloading of the next function, set default params
     * @throws IOException
     */
    public void setConfigToFile() throws IOException
    {
    	setConfigToFile(20, "BFS", "SimpleGeneration", "GUI");
    }

    /**
     * Write to XML file the configurations
     * 
     * @param numOfThreads        - max threads allow in program
     * @param searchAlgorithm     - the default search algorithm to use
     * @param generationAlgorithm - the default generation algorithm to use
     * @throws IOException
     */
    public void setConfigToFile(int numOfThreads, String searchAlgorithm, String generationAlgorithm, String CLIorGui) throws IOException
    {
    	m_numOfThreads			= numOfThreads;
    	m_searchAlgorithm 		= searchAlgorithm;
    	m_gererationAlgorithm 	= generationAlgorithm;
    	m_CLIorGUI              = CLIorGui;
    	
        Properties prop = new Properties();
        prop.setProperty("NumOfThreads", 		String.valueOf(m_numOfThreads));
        prop.setProperty("SearchAlgorithm", 	m_searchAlgorithm);
        prop.setProperty("GenerationAlgorithm", m_gererationAlgorithm);
        prop.setProperty("CLIorGUI", m_CLIorGUI);

        FileOutputStream fos = new FileOutputStream(ConfigFileName);
        
        prop.storeToXML(fos, "MazeConfiguration");
        
        fos.close();
    }
	
	/***** Properties instance *****/
	
	private static MazeProperties m_properties = new MazeProperties();

	/******** Config Params ********/
	
	private int m_numOfThreads;
	private String m_searchAlgorithm;
	private String m_gererationAlgorithm;
	private String m_CLIorGUI;
	
	/*********** Consts ************/
    private static final long serialVersionUID = 1L;
    
    private static final String ConfigFileName = "properties.xml";

}
