package main;
import java.io.IOException;

import file.FileHandler;

public class ConfigLite {
	private FileHandler fh;
	private boolean writeOnce=false; // if true, we wait til saveConfigFile() is called to write
	
	/**
	 * sets the writeOnce option, if true, we wait until saveConfigFile() to write
	 * if false, we write after every element/setting is added
	 * @param writeOnce
	 */
	public void setWriteOnce(boolean writeOnce){
		this.writeOnce = writeOnce;
	}
	/**
	 * starts config system, immediately attempts to read config file
	 * @return true if successfully opened file/read file
	 */
	public ConfigLite(){
		try {
			fh = new FileHandler();
			fh.readConfig();
		} catch (IOException e) {} // #1 error handling NA
	}
	/**
	 * starts config system, immediately attempts to read config file
	 * @param filePath the path to config file, if not using default
	 * @return true if successfully opened file/read file
	 */
	public ConfigLite(String filePath){
		try {
			fh = new FileHandler(filePath);
			fh.readConfig();
		} catch (IOException e) {}
	}
	
	/**
	 * writes all config changes to disk
	 * @return
	 */
	public boolean saveToFile(){
		try {
			fh.writeConfig();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Creates a blank config file at given location
	 * will overwrite current config if same location is used
	 * @param path
	 * @return
	 */
	public boolean newConfigFile(String path){
		try {
			return fh.newConfigFile(path);
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Creates a blank config file at default location
	 * will overwrite current config if same location is used
	 * @return
	 */
	public boolean newConfigFile(){
		try {
			return fh.newConfigFile();
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * adds element to config
	 * @param element
	 * @return
	 */
	public boolean addElement(String element){
		element = element.toUpperCase();
		
		fh.addElement('['+element+']');
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * return true if config already has element
	 * @param element
	 * @return
	 */
	public boolean hasElement(String element){
		return fh.hasElement(element);
	}
	
	/**
	 * Enters the element domain, precede addSetting(String, <T>) and
	 * getString(String), getDouble(String), etc. with this.
	 * Note: no need to precede getString(String, String), getDouble(String, String), etc
	 * with this
	 * 
	 * @param element
	 * @return
	 */
	public boolean enterElement(String element){
		element = element.toUpperCase();
		return fh.setElement('['+element+']');
	}

// THE NEXT METHODS ARE THE GETTERS, INVOKE enterElement() WHEN NECESSARY
	
	/**
	 * Returns the value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public String getString(String element, String attr){
		String s = fh.getSetting(element, attr);
		if(s.charAt(0) == '"' && s.charAt(s.length()-1) == '"')
			return s.substring(1, s.length()-1);
		else
			return null;
	}
	
	/**
	 * Returns the int value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public int getInt(String element, String attr){
		String s = fh.getSetting(element, attr);
		return Integer.parseInt(s);
	}
	
	/**
	 * Returns the short value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public short getShort(String element, String attr){
		String s = fh.getSetting(element, attr);
		return Short.parseShort(s);
	}
	
	/**
	 * Returns the byte value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public byte getByte(String element, String attr){
		String s = fh.getSetting(element, attr);
		return Byte.parseByte(s);
	}
	
	/**
	 * Returns the boolean value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public boolean getBoolean(String element, String attr){
		String s = fh.getSetting(element, attr);
		return Boolean.parseBoolean(s);
	}
	
	/**
	 * Returns the char value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param element
	 * @param attr
	 * @return
	 */
	public char getChar(String element, String attr){
		String s = fh.getSetting(element, attr);
		return s.charAt(0);
	}

// THE FOLLOWING GETTERS MUST BE PRECEDED BY enterElement()
	/**
	 * Returns the value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public String getString(String attr){
		String s = fh.getSetting(attr);
		if(s.charAt(0) == '"' && s.charAt(s.length()-1) == '"')
			return s.substring(1, s.length()-1);
		else
			return null;
	}
	
	/**
	 * Returns the int value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public int getInt(String attr){
		String s = fh.getSetting(attr);
		return Integer.parseInt(s);
	}
	
	/**
	 * Returns the short value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public short getShort(String attr){
		String s = fh.getSetting(attr);
		return Short.parseShort(s);
	}
	
	/**
	 * Returns the byte value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public byte getByte(String attr){
		String s = fh.getSetting(attr);
		return Byte.parseByte(s);
	}
	
	/**
	 * Returns the boolean value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public boolean getBoolean(String attr){
		String s = fh.getSetting(attr);
		return Boolean.parseBoolean(s);
	}
	
	/**
	 * Returns the char value of attribute attr in element element
	 * returns null if value isn't a string
	 * @param attr
	 * @return
	 */
	public char getChar(String attr){
		String s = fh.getSetting(attr);
		return s.charAt(0);
	}

// THE REMAINING METHODS ALL HANDLE ADDING THE SETTINGS OF VARIOUS DATA TYPES	
	
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, String value){
		value = '"'+value+"'";
		if(!fh.addSetting(element, attr, value)) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, int value){
		if(!fh.addSetting(element, attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, Short value){
		if(!fh.addSetting(element, attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, boolean value){
		if(!fh.addSetting(element, attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, byte value){
		if(!fh.addSetting(element, attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add setting to config
	 * @param element
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String element, String attr, char value){
		String v = "\'"+value+'\'';
		if(!fh.addSetting(element, attr, v)) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, String value){
		value = '"'+value+'"';
		if(!fh.addSetting(attr, value)) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, int value){
		if(!fh.addSetting(attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, short value){

		if(!fh.addSetting(attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, boolean value){
		if(!fh.addSetting(attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, byte value){
		if(!fh.addSetting(attr, String.valueOf(value))) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add a setting to config, must precede this with a enterElement
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean addSetting(String attr, char value){
		String v = "\'"+value+'\'';
		if(!fh.addSetting(attr, v)) return false;
		if(!writeOnce){
			try {
				fh.writeConfig();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
}
