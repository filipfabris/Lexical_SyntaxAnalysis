package hr.fer.oprpp1.custom.collections;

/**
 * <b> Class Processor </b> represents an conceptual contract between
 *  clients which will have objects to be processed, and each concrete
 *  Processor which knows how to perform the selected operation
 * @author filip fabris
 * @version 1.0.0
 */

public interface Processor {


	/**
	 * Method used to perform action
	 * @param value
	 */
	public void process(Object value);

}
