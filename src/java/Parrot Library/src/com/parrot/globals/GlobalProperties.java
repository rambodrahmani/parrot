package com.parrot.globals;

import com.parrot.property.Property;

/**
 * Global Properties Enumeration.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 16, 2019.
 */
public enum GlobalProperties implements Property
{
    // Sample property used for testing
    TEST("Test", "Test Property", "Sample Property for Testing Purposes", true);

    // property values: use the enumeration name() for the property name
    private String  defaultValue     = null;
    private String  shortDescription = null;
    private String  longDescription  = null;
    private boolean isEditable       = false;

    /**
     * Default constructor.
     * 
     * @param defValue   default value to be used for the property;
     * @param shortDesc  short description to be used for the property;
     * @param longDesc   long description to be used for the property;
     * @param isEdit     editable flag to be used for the property.
     */
    GlobalProperties(final String defValue,
                     final String shortDesc,
                     final String longDesc,
                     final boolean isEdit)
    {
        // set property values
        defaultValue = defValue;
        shortDescription = shortDesc;
        longDescription = longDesc;
        isEditable = isEdit;
    }
    
    @Override
    public String getName()
    {
        // return the enumeration name
        return name();
    }

    @Override
    public String getDefaultValue()
    {
        return defaultValue;
    }

    @Override
    public String getShortDescription()
    {
        return shortDescription;
    }

    @Override
    public String getLongDescription()
    {
        return longDescription;
    }

    @Override
    public boolean isEditable()
    {
        return isEditable;
    }
}

