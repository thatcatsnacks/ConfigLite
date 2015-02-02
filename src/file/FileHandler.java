package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import file.configtree.ConfigTree;
import file.configtree.ConfigTree.Element;
import file.configtree.ConfigTree.Setting;

/**
 * Handles file i/o
 * @author Cat Snacks
 *
 */
public class FileHandler {
	private final String DEFAULT_FILE_LOCATION = "config.cfg";
	private final char COMMENT = '#';
	
	private File file;
	private BufferedReader in;
	private BufferedWriter out;
	private ConfigTree config;
	private Element currentElement=null;

	
	/**
	 * Constructor
	 * @throws IOException
	 */
	public FileHandler() throws IOException{
		file = new File(DEFAULT_FILE_LOCATION);
		// if file doesn't exist, create it
		if(!file.exists()) file.createNewFile();
		
		in = new BufferedReader(new FileReader(file));
		config = new ConfigTree();
	}
	
	/**
	 * Constructor
	 * @param filePath Path to config file
	 * @throws IOException 
	 */
	public FileHandler(String filePath) throws IOException{
		file = new File(filePath);
		
		// if file doesn't exist, create it
		if(!file.exists()) file.createNewFile();
		
		in = new BufferedReader(new FileReader(file));
		config = new ConfigTree();
	}
	
	/**
	 * creates new config file at default location
	 * @return
	 * @throws IOException
	 */
	public boolean newConfigFile() throws IOException{
		file = new File(DEFAULT_FILE_LOCATION);
		if(file.exists())
			file.delete();
		file.createNewFile();
		in = new BufferedReader(new FileReader(file));
		return true;
	}
	
	/**
	 * creates new config file at given location
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean newConfigFile(String path) throws IOException{
		file = new File(path);
		if(file.exists())
			file.delete();
		file.createNewFile();
		in = new BufferedReader(new FileReader(file));
		return true;
	}
	/**
	 * Sets the current element to work under
	 * @param element
	 * @return
	 */
	public boolean setElement(String element){
		currentElement = config.getElement(element);
		if(currentElement != null) return true;
		return false;
	}
	
	/**
	 * adds element to config
	 * @param element
	 * @return
	 */
	public boolean addElement(String element){
		if(config.hasElement(element))
			return false;
		
		config.addElement(element);
		return true;
	}
	
	/**
	 * returns true if element already exists in config
	 * @param element
	 * @return
	 */
	public boolean hasElement(String element){
		return config.hasElement(element);
	}
	
	
	/**
	 * returns value of specified attribute within the given element
	 * @param element
	 * @param attr
	 * @return
	 */
	public String getSetting(String element, String attr){
		Element e = config.getElement('['+element+']');
		if(e != null){
			Setting s = e.getSetting(attr);
			return s.getValue();
		}
		
		return null;
	}
	
	/**
	 * returns value of specified attribute, this must be preceded with 
	 * a setElement() call
	 * @param attr
	 * @return
	 */
	public String getSetting(String attr){
		if(currentElement!=null){
			Setting s = currentElement.getSetting(attr);
			return s.getValue();
		}
		
		return null;
	}
	/**
	 * adds element to config, must precede this with setElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, String value){
		if(currentElement == null) return false;
		
		currentElement.addSetting(attr, value);
		return true;
	}
	/**
	 * Add setting attr=value to specified element
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, String value){
		Element e = config.getElement(element);
		e.addSetting(attr, value);
		return true;
	}
	/**
	 * Reads config file into ConfigTree data structure
	 * @throws IOException
	 */
	public void readConfig() throws IOException{
		String stringRead;
		Element currentElement=null;
		int linesRead=0;
		while((stringRead = in.readLine())!=null){
			linesRead++;
			// if the string isn't just whitespace..
			if(stringRead.trim().length() > 0){
				// if the string starts with a '[', it is a setting header
				if(stringRead.charAt(0) == '['){
					// add to root
					config.addElement(stringRead);
					currentElement = config.getElement(stringRead);
				// if string starts with '#', it is comment line
				}else if(stringRead.charAt(0)==COMMENT){
					currentElement.addSetting(String.valueOf(COMMENT), stringRead);
				// Otherwise string is id/value pair
				}else{
					if(stringRead.indexOf(';') > -1){
						// add to the setting we're currently under
						String attr = stringRead.substring(0, stringRead.indexOf('='));
						String value = stringRead.substring(stringRead.indexOf('=')+1, stringRead.indexOf(';'));
						currentElement.addSetting(attr, value);
					}else{
						System.out.println("Error: Malformed setting on line "+linesRead);
					}
				}
			}
		}
		// close stream
		in.close();
		
		// if file exists but is whitespace/empty, recreate file 
		if(config.size() == 0 && file.exists()){
			file.delete();
			file.createNewFile();
		}
	}
	
	/**
	 * Writes ConfigTree data structure to file, this should be done after each change
	 * @throws IOException 
	 */
	public void writeConfig() throws IOException{
		out = new BufferedWriter(new FileWriter(file, false));
		// for each element in the config file..
		for(int i=0;i<config.size();i++){
			Element e = config.getElement(i);
			// write the element header i.e. [ELEMENT]
			out.write(e.getName()+'\n');
			// then, for each setting within that element...
			for(int j=0;j<e.size();j++){
				Setting s = e.getSetting(j);
				if(s.getAttribute().equals(String.valueOf(COMMENT))){
					out.write(s.getValue() + '\n');
				}else{
					// write attr=value
					StringBuilder setting = new StringBuilder();
					setting.append(s.getAttribute());
					setting.append("=");
					setting.append(s.getValue());
					setting.append(";");
					out.write(setting.toString() + '\n');
				}
			}
		}
		
		out.close();
	}
}
