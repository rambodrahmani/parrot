package com.parrot.util;

/**
 * Logging Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 16, 2019.
 */
public final class LoggingUtil
{
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private LoggingUtil()
    {}
    
    public static void logStackTrace(final Throwable thr)
    {
    	
    }

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {}
}

