package hr.fer.oprpp1.custom.collections;


/**
 * <b>Class Collection </b> represents some general collection of objects
 * @author filip fabris
 * @version 1.0.0
 */
public interface Collection {

	/**
	 * The size of the Collection (the number of elements it contains).
	 * @return number of currently stored objects in this collections - <b>int</b>
	 */
	abstract int size();

	/**
	 * @return Returns true if collection contains no objects and false otherwise
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * @param value Adds the given object into this collection
	 */
	abstract void add(Object value);

	/**
	 * @param value object to be checked if exists in colletion
	 * @return Returns true only if the collection contains given value
	 */
	abstract boolean contains(Object value);

	/**
	 * @param value object to be removed
	 * @return Returns true only if the collection contains given value
	 */
	abstract boolean remove(Object value);

	/**
	 * @return Allocates new array with size equals to the size of this collections, fills it with collection content and returns the array. This method never returns null.
	 * @throws UnsupportedOperationException default execption
	 */
	abstract Object[] toArray();

	/**
	 * Method calls processor.process(.) for each element of this collection
	 * @param processor from Processor class
	 */
	default void forEach(Processor processor) {
		ElementsGetter local = this.createElementsGetter();
		Object value;
		
		while(local.hasNextElement()) {
			value = local.getNextElement();
			processor.process(value);
		}
	}

	/**
	 * Method adds into the current collection all elements from the given collection
	 * @param other colletion from which elements will be added
	 */
	default void addAll(Collection other) {

		class OtherProcess implements Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}

		OtherProcess process = new OtherProcess();

		other.forEach(process);

	}

	/**
	 * Removes all elements from this collection
	 */
	abstract void clear();
	
	/**
	 * Returns Iterator of the collection
	 */
	abstract ElementsGetter createElementsGetter();
	
	/**
	 * Tests if col variables elements tests ture for tester variable
	 * Adds elements of to caller collection
	 * @param col given collection on which tester is tested
	 * @param tester to test on givven collection
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		
		ElementsGetter iterator = col.createElementsGetter();
		
		while(iterator.hasNextElement()) {
			Object element = iterator.getNextElement();
			if(tester.test(element)) {
				this.add(element);
			}
		}
		
	}

}
