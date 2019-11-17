package com.parrot.util;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.ImageIcon;

import com.parrot.globals.Globals;
import com.parrot.nls.LocaleResourceBundle;

/**
 * System Tray Utilities Class.
 *
 * The Parrot System tray icon is used to show the number of occurred errors (in
 * the tooltip) and allow to send an email upon clicking on it.
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
	
	// queued error messages
	private final static ArrayBlockingQueue<String> queuedErrors = new ArrayBlockingQueue<>(30);
	
	// system tray error message title
	private final static String TRAY_TITLE = LocaleResourceBundle.getString("tit_sys_tray_err");
	
    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private SystemTrayUtil()
    {}

    /**
     * Updates the System Tray icon tooltip based on the number of queued error
     * messages.
     */
    private static void updateToolTip()
    {
        // check if the Parrot system tray icon has been initialized
        if (trayIcon != null)
        {
            // number of queued errors
            final int errors = queuedErrors.size();
            
            // if there are queued error messages
            if (errors > 0)
            {
                // display the number of errors and offer to send a report email
                // to the support team
                trayIcon.setToolTip(
                    LocaleResourceBundle.getString("txt_queued_errors") +
                    errors + ". " +
                    LocaleResourceBundle.getString("txt_doubleclick_to_report")
                );
            }
            else
            {
                // otherwise, just show the title
                trayIcon.setToolTip(TRAY_TITLE);
            }
        }
    }
    
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
    		final Image parrotLogo = new ImageIcon(
                Globals.class.getResource("/com/parrot/globals/parrot_logo.jpg")
            ).getImage();
    		
    		// create the tray icon from the image
    		trayIcon = new TrayIcon(parrotLogo, TRAY_TITLE);
    	}
    	
    	// otherwise, just return the previously initialized tray icon
    	return trayIcon;
    }

    public static void reportError(final Throwable thr)
    {
        // initialize the tray icon if it already isn't
        if ((trayIcon = init()) != null)
        {
            // retrieve exception stack trace dump
            final String stackTrace = LoggingUtil.dumpStackTrace(thr);

            // try to add the error to the queue
            if (!queuedErrors.offer(stackTrace))
            {
                // the queue is full, remove one item from the top
                queuedErrors.remove();

                // now add the stack trace to the queue
                queuedErrors.offer(stackTrace);
            }

            // display a notificaiton in the System Tray
            trayIcon.displayMessage(
                        thr.getLocalizedMessage(),
                        stackTrace,
                        MessageType.ERROR
            );

            // update the system tray tooltip
            updateToolTip();
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

