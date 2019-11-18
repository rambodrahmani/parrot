package com.parrot.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.parrot.nls.LocaleResourceBundle;

/**
 * Logging Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 16, 2019.
 */
public final class LoggingUtil
{
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private LoggingUtil()
    {}

    /**
     * Dumps the stack trace of the given Throwable into a String.
     *
     * @param  thr  the given Throwable;
     * @return  a String containing the stack trace dump.
     */
    public static String dumpStackTrace(final Throwable thr)
    {
        return dumpStackTrace(thr, null);
    }

    /**
     * Dumps the stack trace of the given Throwable into a String.
     *
     * @param  thr  the given Throwable;
     * @param  description  additional descriptive text;
     * @return  a String containing the stack trace dump.
     */
    public static String dumpStackTrace(final Throwable thr, final String description)
    {
        // initialize new string writer
        final StringWriter sw = new StringWriter();

        // check if additional description was provided
        if (description != null)
        {
            // if so, append it to the beginning of the dump
            sw.append(description + "\n");
        }

        // prepare the stack trace dump
        sw.append(
            LocaleResourceBundle.getString("txt_caught_exception_start") + 
            (new Date()) + 
            LocaleResourceBundle.getString("txt_caught_exception_end") +
            "\n"
        );

        // append the exception stack trace to the dump
        thr.printStackTrace(new PrintWriter(sw));

        // return the stack trace dump string
        return sw.toString();
    }

    /**
     * Prints the stack trace dump of the given Exception to the console and
     * shows a notification using the system tray.
     *
     * @param  thr  the given Throwable.
     */
    public static void logStackTrace(final Throwable thr)
    {}

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {}
}

