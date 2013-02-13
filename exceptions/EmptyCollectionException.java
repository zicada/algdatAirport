/**
 * EmptyCollectionException represents the situation in which a collection 
 * is empty.
 *
 * @author Dr. Lewis
 * @author Dr. Chase
 * @version 1.0, 08/12/08
 */

package com.algdat.oblig1.exceptions;
public class EmptyCollectionException extends RuntimeException
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
    * Sets up this exception with an appropriate message.
    */
   public EmptyCollectionException (String collection)
   {
      super ("The " + collection + " is empty.");
   }
}
