package hr.fer.oprpp1.custom.collections;

/**
 * Interaface which allows colletion class to iterate on its elements
 * @author filip
 */
public interface ElementsGetter {
	
	/**
	 * @return next object of given collection
	 */
	abstract Object getNextElement();
	
	/**
	 * @return true if there is more elements
	 */
	abstract boolean hasNextElement();
	
	/**
	 * Default implementaion to iterate on remaining elements on given collection
	 * @param p object of Processor class, method process used to perform action
	 */
	default void processRemaining(Processor p) {
		while(this.hasNextElement() == true) {
			p.process(this.getNextElement());
		}
	}

}
