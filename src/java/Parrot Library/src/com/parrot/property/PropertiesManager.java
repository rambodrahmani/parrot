package com.parrot.property;

import java.util.Hashtable;
import java.util.Map;

import com.parrot.util.PathUtil;

/**
 * Properties Manager.
 *
 * This Class is a Singleton: only one instance of the class exists in the java
 * virtual machine at any given time.
 * 
 * Properties are stored using enumerations which implement the @{Property}
 * interface. Each enumeration contains a separate set of properties. Each
 * property in a set is represented by one of the constants of the enumeration.
 * The @{PropertiesManager} associates a @{PropertiesHandler} to each
 * enumeration and this subclass allows to manage the properties of the set.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 18, 2019.
 */
public class PropertiesManager
{
    /**
     * Properties Handler.
     * 
     * @author Rambod Rahmani <rambodrahmani@autistici.org>
     * 
     * Created on November 18, 2019.
     */
    private final class PropertiesHandler
    {
        // properties hashtable
        private Hashtable<String, String> properties = new Hashtable<String, String>();
        
        /**
         * Maps the specified key to the specified value in the handler
         * properties hashtable.
         * 
         * @param  key    property key;
         * @param  value  property value;
         * @return  the previous value of the specified key in the hashtable.
         */
        public Object put(String key, String value)
        {
            return properties.put(key, value);
        }

        /**
         * Returns the value to which the specified key is mapped in the handler
         * properties hashtable or the default value if the key is not set.
         * @{PathUtil.DefaultPaths} expansion is performed after retrieving the
         * value from the hashtable.
         * 
         * @param  key       the key to search the hashtable;
         * @param  defValue  the property default value;
         * @return  the value to which the specified key is mapped.
         */
        public String get(String key, String defValue)
        {
            return get(key, defValue, true);
        }

        /**
         * Returns the value to which the specified key is mapped in the handler
         * properties hashtable or the default value if the key is not set.
         * @{PathUtil.DefaultPaths} expansion will be performed if required.
         * 
         * @param  key       the key to search the hashtable;
         * @param  defValue  the property default value;
         * @param  expand    if true, @{PathUtil.DefaultPaths} will be expanded;
         * @return  the value to which the specified key is mapped.
         */
        public String get(String key, String defValue, boolean expand)
        {
            // retrieve value from the hashtable
            String ret = properties.get(key);

            // check if a value was actually retrieved
            if (ret == null)
            {
                // if not, set the default value to be returned
                ret = defValue;
            }

            // check if paths post processing is required and needed
            if (expand && PathUtil.containsDefaultPath(ret))
            {
                // do paths post processing
                ret = PathUtil.expandPath(ret);
            }

            // return retrieved value
            return ret;
        }

        /**
         * Copies all of the mappings from the specified map to this properties
         * handler hashtable.
         * These mappings will replace any mappings that this hashtable had for
         * any of the keys currently in the specified map.
         * 
         * @param  newProperties  the mappings to be copied to the hashtable.
         */
        public void putAll(Map<String, String> newProperties)
        {
            properties.putAll(newProperties);
        }

        /**
         * @return  the Properties Manager properties hashtable.
         */
        private Hashtable<String, String> getProperties()
        {
            return properties;
        }
    }

    // Properties Manager shared instance
    private static PropertiesManager sharedInstance = null;

    // properties handlers hashtable
    private Hashtable<String, PropertiesHandler> propertiesHandlers = new Hashtable<String, PropertiesHandler>();

    /**
     * @return  the Properties Manager class shared instance.
     */
    public static final synchronized PropertiesManager getSharedInstance()
    {
        // check if the shared instance has already been initialized
        if (sharedInstance == null)
        {
            // if not, initialize a new instance of the class
            sharedInstance = new PropertiesManager();
        }

        // return the shared instance to the caller
        return sharedInstance;
    }

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {}
}

