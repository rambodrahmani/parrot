package com.parrot.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 16, 2019.
 */
public final class URLUtil
{
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private URLUtil()
    {}
    
    /**
     * Validates the given URL checking for wrong slashes.
     * 
     * @param  url  the URL to be validated;
     * @return  the validated URL.
     */
    public static String validateURL(String url)
    {
        if (url != null)
        {
            url = url.replace("\\", "/");
        }

        return url;
    }

    /**
     * Checks if the given string is a valid URL or not.
     * 
     * @param  str  the string to be checked;
     * @return  true if the given string is a URL, false otherwise.
     */
    public static boolean isURL(final String str)
    {
    	boolean ret = false;
    	
    	if (str != null)
    	{
    	    // correct wrong slashes
    	    String tmp = validateURL(str);

    	    // convert to lowercase
    	    tmp = tmp.toLowerCase();

    	    // check if the given string is a URL looking for protocols patterns
    	    ret = tmp.startsWith("http://") || 
    	          tmp.startsWith("https://") ||
    	          tmp.startsWith("ftp://") ||
    	          tmp.startsWith("ftps://") ||
    	          tmp.startsWith("file://");
    	}

    	return ret;
    }
    
    /**
     * Converts the given String into a URL.
     * 
     * @param  fileName  the string to be converted into URL;
     * @return the URL obtained from the given string or NULL in case of error.
     */
    public static URL toURL(final String fileName)
    {
    	// first, check if the given string is valid
        if (StringUtil.isValidString(fileName))
        {
            return null;
        }

        try
        {
            if (isURL(fileName))
            {
                return new URL(fileName);
            }
            else
            {
                return new File(fileName).toURI().toURL();
            }
        }
        catch (final MalformedURLException e)
        {
            return null;
        }
    }
    
    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {}
}

