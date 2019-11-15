package com.parrot.util;

/**
 * Strings Related Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 15, 2019.
 */
public final class StringUtil
{
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private StringUtil()
    {}

    /**
     * Checks if the given string a valid: it is not null and with a length > 0.
     *
     * @param  str  the string value to be checked;
     *
     * @return  true if the given string is valid.
     */
    public static boolean isValidString(final String str)
    {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * Checks if the given string is valid and truncates it to the given length.
     *
     * @param  str  the string value to be truncated;
     * @param  len  the length to be used when creating substring;
     *
     * @return  the truncated string.
     */
    public static String truncate(final String str, final int len)
    {
        if (isValidString(str))
        {
            str = str.substring(0, len);
        }

        return value;
    }

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {
        String test = "StringUtil";
        System.out.println(truncate(test, 6));
    }
}

