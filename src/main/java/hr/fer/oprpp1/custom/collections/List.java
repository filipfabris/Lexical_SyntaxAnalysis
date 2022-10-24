package hr.fer.oprpp1.custom.collections;

public interface List extends Collection{
	
	/**
	 * Get object from specific index
	 * @param index of element to extract
	 * @return extracted element
	 */
	abstract Object get(int index);
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * @param value of element we want to add
	 * @param position index position to which we want to add element
	 */
	abstract void insert(Object value, int position);
	
	/** index of element we want to find in storage unit
	 * @param value element which we want to find
	 * @return index of found element or -1 for not found
	 */
	abstract int indexOf(Object value);
	
	/**
	 * Remove element at specific index
	 * @param index of element to remove
	 */
	abstract void remove(int index);

}
