package com.parrot.util;

/**
 * Utilities Class.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on Nov 4, 2019.
 */
public class Util
{
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
	 * Developer harness test.
	 * 
	 * @param  args  command line arguments.
	 */
	public static void main(String[] args)
	{
		if (isJVM64Bit())
		{
			System.out.println("You are running 64-bit JVM.");
		}
		else
		{
			System.out.println("You are running 32-bit JVM.");
		}
	}
}

