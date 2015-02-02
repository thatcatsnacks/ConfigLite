package file.configtree;

import java.util.ArrayList;
import java.util.List;

/**
 * A tree-like data structure that contains the config elements, attributes, and values
 * @author Cat Snacks
 *
 */
public class ConfigTree {
	List<Element> elements;
	
	public ConfigTree(){
		elements = new ArrayList<Element>();
	}
	
	/**
	 * gets elements with given name
	 * @param name name of element to find
	 * @return element with given name, or null if none found
	 */
	public Element getElement(String name){
		for(int i=0; i<elements.size(); i++){
			// if the element name = specified name
			if(elements.get(i).getName().equals(name))
				return elements.get(i);
		}
		// no element found..
		return null;
	}
	
	/**
	 * return true if element specified already exists
	 * @param name
	 * @return
	 */
	public boolean hasElement(String name){
		for(int i=0;i<elements.size();i++){
			if(elements.get(i).getName().equals(name))
				return true;
		}
		return false;
	}
	/**
	 * returns element at given index in elements list
	 * @param index
	 * @return
	 */
	public Element getElement(int index){
		return elements.get(index);
	}
	/**
	 * Adds element to config
	 * @param name
	 */
	public void addElement(String name){
		elements.add(new Element(name));
	}
	
	/**
	 * Adds element to config
	 * @param e
	 */
	public void addElement(Element e){
		elements.add(e);
	}
	
	public int size(){
		return elements.size();
	}
	/**
	 * Main elements in config file, they look like: [ELEMENT].
	 * each element contains a list of sub-elements, called settings
	 * @author Cat Snacks
	 *
	 */
	public class Element{
		private String name;
		private List<Setting> settings;
		
		public Element(String name){
			this.name = name;
			settings = new ArrayList<Setting>();
		}
		
		/**
		 * Returns the name of this element
		 * @return
		 */
		public String getName(){
			return name;
		}
		/**
		 * Adds setting to current element
		 * @param attr Attribute name
		 * @param value Attribute value
		 */
		public void addSetting(String attr, String value){
			settings.add(new Setting(attr, value));
		}
		
		/**
		 * Finds setting with specified attribute name
		 * @param attr the attribute name
		 * @return the setting with given attribute name
		 */
		public Setting getSetting(String attr){
			for(int i=0; i<settings.size(); i++){
				// if the element name = specified name
				if(settings.get(i).getAttribute().equals(attr))
					return settings.get(i);
			}
			// no element found..
			return null;
		}
		
		public Setting getSetting(int index){
			return settings.get(index);
		}
		
		/**
		 * returns size of settings list
		 * @return
		 */
		public int size(){
			return settings.size();
		}
	}
	
	/**
	 * Sub-element of Element, represents one setting within a specific element
	 * @author Cat Snacks
	 *
	 */
	public class Setting{
		String attr;
		String value;
		public Setting(String attr, String value){
			this.attr = attr;
			this.value = value;
		}
		
		/**
		 * getter for attribute
		 * @return
		 */
		public String getAttribute(){
			return attr;
		}
		
		/**
		 * getter for value
		 * @return
		 */
		public String getValue(){
			return value;
		}
	}
}
