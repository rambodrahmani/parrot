package com.parrot.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.prefs.Preferences;

/**
 * JVM Related Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 5, 2019.
 */
public final class JVMUtil
{
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private JVMUtil()
    {}

    /**
     * @return  a string containing the JVM architecture: one of '32', '64' or
     *          'unknown'.
     */
    public static String getJVMArch()
    {
        return System.getProperty("sun.arch.data.model");
    }

    /**
     * @return  true if the running JVM is 64-bit, false otherwise: either the
     *          running JVM has 32-bit or unknown architecture.
     */
    public static boolean isJVM64Bit()
    {
        return getJVMArch().indexOf("64") != -1;
    }

    /**
     * Checks if the application is running with root/admin privileges or not.
     * This solution is platform-independent: it tries to write system
     * preferences and if that fails, the user might not have root/admin
     * privileges.
     * 
     * @return  true if the application has root/admin privileges.
     */
    public static boolean hasRootPrivileges()
    {
        // retrieve root preference node for the system
        Preferences sysPrefs = Preferences.systemRoot();
        
        // retrieve standard error output stream
        PrintStream errStream = System.err;
        
        // synchronize to avoid problems with other threads that access System.err
        synchronized(errStream)
        {
            // override standard error output stream
            System.setErr(new PrintStream(new OutputStream()
            {    
                @Override
                public void write(int b) throws IOException
                {
                    // suppress error messages caused by this method
                }
            }));

            try
            {
                // try to edit system preferences
                sysPrefs.put("foo", "bar");     // SecurityException on Windows
                sysPrefs.remove("foo");
                sysPrefs.flush();               // BackingStoreException on Linux
                
                // no security exception thrown: I am root
                return true;
            }
            catch (Exception exception)
            {
                // security exception thrown: I am not root
                return false;
            }
            finally
            {
                // reset standard error output stream
                System.setErr(System.err);
            }
        }
    }

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {
        if (isJVM64Bit())
        {
            System.out.println("You are running 64-bit JVM. Root privileges: "
                                + hasRootPrivileges());
        }
        else
        {
            System.out.println("You are running 32-bit JVM. Root privileges: "
                                + hasRootPrivileges());
        }
    }
}

