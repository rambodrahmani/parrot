package com.parrot.util;

import java.io.File;

/**
 * Paths Utilities Class.
 *
 * Declared public for all other modules to use, final to prevent sub-classing
 * and improve efficiency at runtime, with a private constructor to prevent
 * instantiation.
 * 
 * @{DefaultPaths} can be used used to obtain platform independent paths.
 * 
 * @author Rambod Rahmani <rambodrahmani@autistici.org>
 * 
 * Created on November 18, 2019.
 */
public final class PathUtil
{
    /**
     * Default Paths.
     */
    public enum DefaultPaths
    {
        /**
         * User working directory path.
         */
        USER_DIR("{user.dir}"),

        /**
         * User working directory parent path.
         */
        BASE_DIR("{base.dir}"),

        /**
         * Shared Directory path.
         */
        SHARED_DIR("{shared.dir}"),

        /**
         * JRE Directory path.
         */
        JRE_DIR("{jre.dir}"),

        /**
         * Absolute Directory path.
         */
        ABSOLUTE_DIR("{absolute.dir}");

        // path value
        private String value = "";

        /**
         * Default constructor.
         * 
         * @param  val  the path value.
         */
        private DefaultPaths(final String val)
        {
            value = val;
        }

        /**
         * @return  the path value.
         */
        public String getValue()
        {
            return value;
        }
    }

    // runtime default paths values
    private static String user_dir   = "";
    private static String base_dir   = "";
    private static String shared_dir = "";
    private static String jre_dir    = "";

    /**
     * The private constructor will prevent the instantiation of this class
     * directly.
     */
    private PathUtil()
    {}

    /**
     * Checks if the given path contains a default path.
     *
     * @param  path  the path to be checked.
     * @return  true if the given path contains a default path, false otherwise.
     */
    public final static boolean containsDefaultPath(final String path)
    {
        return (StringUtil.isValidString(path) &&
                path.startsWith("{") &&
                (path.indexOf("}") != -1));
    }

    /**
     * Updates default paths values to runtime values.
     */
    private static final void updateDefaultPaths()
    {
        // user_dir = user.dir system property
        user_dir = System.getProperty("user.dir");

        // base_dir = user_dir parent directory
        base_dir = new File(user_dir).getParent();

        // shared_dir = base_dir/shared
        shared_dir = base_dir + File.separator + "shared";

        // jre_dir = base_dir/jre
        jre_dir = base_dir + File.separator + "jre";
    }
    
    /**
     * Utility method used to do default paths replacements.
     * Replaces the string <code>seq</code> with the string <code>rep</code> in
     * the start of the source string <code>src</code>.
     *
     * @param  src  the source string;
     * @param  seq  the sequence to be replaced in the source string;
     * @param  rep  the replacement string.
     * @return  a new string with the required replacement.
     */
    private static final String replaceStartingSeq(final String src,
                                                   final String seq,
                                                   final String rep)
    {
        // new string to be returned
        String ret = src;

        // check if the sequence string is valid (not null and > 0) and if the
        // source string starts with the given sequence
        if (StringUtil.isValidString(seq) && src.startsWith(seq))
        {
            // if so, replace the starting string of src with rep
            ret = rep + src.substring(seq.length());
        }

        // return the new string with the required replacement
        return ret;
    }

    /**
     * Shortens the given path to remove platform dependent path strings.
     *
     * @param  path  the path string to be compressed;
     * @return  the resulting platform independent string path.
     */
    public static final String shortenPath(String path)
    {
        // check if the given string path is not null and > 0
        if (StringUtil.isValidString(path))
        {
            // check if the given string path is a URL
            if (URLUtil.isURL(path))
            {
                // if so, this is an absolute path
                path = DefaultPaths.ABSOLUTE_DIR.getValue() + path;
            }
            else
            {
                // otherwise, first update runtime default paths values
                updateDefaultPaths();

                // replace "/" with system-dependent default name-separator character
                path = path.replace("/", File.separator);

                // longest default paths must be replaced first
                path = replaceStartingSeq(path, shared_dir, DefaultPaths.SHARED_DIR.getValue());
                path = replaceStartingSeq(path, user_dir, DefaultPaths.USER_DIR.getValue());
                path = replaceStartingSeq(path, jre_dir, DefaultPaths.JRE_DIR.getValue());
                path = replaceStartingSeq(path, base_dir, DefaultPaths.BASE_DIR.getValue());

                // check if any default path was replaced in the given string
                if (!containsDefaultPath(path))
                {
                    // if not, it must be an absolute path
                    path = DefaultPaths.ABSOLUTE_DIR.getValue() + path;
                }
            }
        }

        return path;
    }

    /**
     * Expands the given path string to use runtime system-dependent paths.
     *
     * @param  path  the path to be processed (may be null)
     * @return  the processed string by macro-substitution
     */
    public static final String expandPath(String path)
    {
        if (StringUtil.isValidString(path))
        {
            // first update runtime default paths values
            updateDefaultPaths();

            // remove any ABSOLUTE_DIR tag
            path = replaceStartingSeq(path, DefaultPaths.ABSOLUTE_DIR.getValue(), "");

            // check if the given path is a URL
            if (!URLUtil.isURL(path))
            {
                // replace "/" with system-dependent default name-separator character
                path = path.replace("/", File.separator);

                // replace remaining default paths: no particular order needed here
                path = replaceStartingSeq(path, DefaultPaths.USER_DIR.getValue(), user_dir);
                path = replaceStartingSeq(path, DefaultPaths.BASE_DIR.getValue(), base_dir);
                path = replaceStartingSeq(path, DefaultPaths.SHARED_DIR.getValue(), shared_dir);
                path = replaceStartingSeq(path, DefaultPaths.JRE_DIR.getValue(), jre_dir);
            }
        }

        // return, resulting system-dependent path
        return path;
    }

    /**
     * Utility method used to expand a @{DefaultPaths} to a full system-
     * dependent path string.
     * 
     * @param  dp  the @{DefaultPaths} to be expanded;
     * @return  the expanded version for the given @{DefaultPaths}.
     */
    public static final String getPathValue(final DefaultPaths dp)
    {
        return expandPath(dp.value);
    }
    
    /**
     * @return  the USER_DIR default path with an ending file separator.
     */
    public static final String USER_DIR_SLASH()
    {
        return DefaultPaths.USER_DIR.getValue() + File.separator;
    }

    /**
     * @return  the BASE_DIR default path with an ending file separator.
     */
    public static final String BASE_DIR_SLASH()
    {
        return DefaultPaths.BASE_DIR.getValue() + File.separator;
    }
    
    /**
     * @return  the SHARED_DIR default path with an ending file separator.
     */
    public static final String SHARED_DIR_SLASH()
    {
        return DefaultPaths.SHARED_DIR.getValue() + File.separator;
    }

    /**
     * @return  the SHARED_DIR/plugin default path with an ending file separator.
     */
    public static final String SHARED_PLUGIN_DIR_SLASH()
    {
        return SHARED_DIR_SLASH() + "plugin" + File.separator;
    }
    
    /**
     * @return  the JRE_DIR default path with an ending file separator.
     */
    public static final String JRE_DIR_SLASH()
    {
        return DefaultPaths.JRE_DIR.getValue() + File.separator;
    }
    
    /**
     * Developer harness test.
     * 
     * @param  args  command line arguments.
     */
    public static void main(final String[] args)
    {
        // USER_DIR
        System.out.println("USER_DIR_SLASH = " + USER_DIR_SLASH());
        System.out.println("USER_DIR_SLASH = " + expandPath(USER_DIR_SLASH()));
        
        // BASE_DIR
        System.out.println("BASE_DIR_SLASH = " + BASE_DIR_SLASH());
        System.out.println("BASE_DIR_SLASH = " + expandPath(BASE_DIR_SLASH()));
        
        // SHARED_DIR
        System.out.println("SHARED_DIR_SLASH = " + SHARED_DIR_SLASH());
        System.out.println("SHARED_DIR_SLASH = " + expandPath(SHARED_DIR_SLASH()));
        
        // SHARED_PLUGIN_DIR
        System.out.println("SHARED_PLUGIN_DIR_SLASH = " + SHARED_PLUGIN_DIR_SLASH());
        System.out.println("SHARED_PLUGIN_DIR_SLASH = " + expandPath(SHARED_PLUGIN_DIR_SLASH()));
        
        // JRE_DIR
        System.out.println("SHARED_PLUGIN_DIR_SLASH = " + JRE_DIR_SLASH());
        System.out.println("SHARED_PLUGIN_DIR_SLASH = " + expandPath(JRE_DIR_SLASH()));
    }
}

