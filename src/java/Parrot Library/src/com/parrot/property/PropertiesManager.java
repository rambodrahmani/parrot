package com.parrot.property;

/**
 * Properties Manager.
 *
 * This Class is a Singleton: only one instance of the class exists in the java
 * virtual machine at any given time.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 18, 2019.
 */
public class PropertiesManager
{
    // Properties Manager shared instance
    private static PropertiesManager sharedInstance = null;

    //

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

