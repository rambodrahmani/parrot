package com.parrot.util;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.ImageIcon;

import com.parrot.globals.Globals;
import com.parrot.nls.LocaleResourceBundle;

/**
 * System Tray Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 16, 2019.
 */
public final class SystemTrayUtil
{
	// parrot library system tray icon
	private static TrayIcon trayIcon = null;
	
	// system tray error messages queue
	private final static ArrayBlockingQueue<String> trayErrorsQueue = new ArrayBlockingQueue<>(30);
	
	// system tray error message title
	private final static String TRAY_TITLE = LocaleResourceBundle.getString("sys_tray_err_title");
	
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private SystemTrayUtil()
    {}
    
    /**
     * Initializes the Parrot System Tray Icon if not already initialized.
     * 
     * @return  the initialized Parrot System Tray Icon.
     */
    private static TrayIcon init()
    {
    	// check if system tray is supported and Parrot system tray icon has
    	// not already been initialized
    	if (SystemTray.isSupported() && trayIcon == null)
    	{
    		// get the system tray reference
    		final SystemTray systemTray = SystemTray.getSystemTray();
    		
    		// load Parrot logo image instance
    		final Image parrotLogo = new ImageIcon(Globals.class.getResource("/com/parrot/globals/parrot_logo.jpg")).getImage();
    		
    		// create the tray icon from the image
    		trayIcon = new TrayIcon(parrotLogo, TRAY_TITLE);
    	}
    	
    	// otherwise, just return the previously initialized tray icon
    	return trayIcon;
    }

    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(String[] args)
    {}
}

