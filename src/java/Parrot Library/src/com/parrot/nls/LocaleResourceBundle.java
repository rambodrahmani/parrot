package com.parrot.nls;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Locale Resource Bundle Class.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 16, 2019.
 */
public class LocaleResourceBundle
{
    // Locale Resource Bundle name
    private static final String LOCALE_RESOURCE_BUNDLE_NAME = "com.parrot.nls.LocaleResourceBundle";

    // Locale Resource Bundle
    private static final ResourceBundle LOCALE_RESOURCE_BUNDLE = ResourceBundle.getBundle(LOCALE_RESOURCE_BUNDLE_NAME);

    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private LocaleResourceBundle()
    {}

    /**
     * Retrieves the string corresponding to the given key form the Locale
     * Resource Bundle.
     * 
     * @param  key  search key for the Locale Resource Bundle;
     * @return  the string retrieved from the Locale Resource Bundle for the
     *          given key or <!>key<!> in case of missing resource.
     */
    public static String getString(final String key)
    {
        try
        {
            return LOCALE_RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return "<!>" + key + "<!>";
        }
    }
}

