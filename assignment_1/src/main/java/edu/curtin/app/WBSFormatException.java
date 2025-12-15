package edu.curtin.app;

/*
 * Represents the exception thrown when the file containing sample task data 
 * has an incorrect format.
 */
public class WBSFormatException extends Exception
{
    public WBSFormatException(String msg)
    {
        super(msg);
    }

    public WBSFormatException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
