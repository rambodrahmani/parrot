package com.parrot.property;

/**
 * Property Interface.
 *
 * All enumerations containing properties must implement this interface.
 * The {@PropertiesManager} can be used to get and set the current value for
 * each property.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 16, 2019.
 */
public interface Property
{
    /**
     * @return  the property name;
     */
    public String getName();

    /**
     * @return  the default value for the property.
     */
    public String getDefaultValue();

    /**
     * @return  the short description for the property.
     */
    public String getShortDescription();

    /**
     * @return  the long description for the property.
     */
    public String getLongDescription();
    
    /**
     * @return  true if the property is editable, false otherwise.
     */
    public boolean isEditable();
}

