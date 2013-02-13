/**
 * ElementNotFoundException represents the situation in which a target element 
 * is not present in a collection
 *
 * @author Dr. Lewis
 * @author Dr. Chase
 * @version 1.0, 08/12/08
 */
package com.algdat.oblig1.exceptions;

public class ElementNotFoundException extends RuntimeException
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
    * Sets up this exception with an appropriate message.
    */
   public ElementNotFoundException (String collection)
   {
      super ("The target element is not in this " + collection);
   }
}
