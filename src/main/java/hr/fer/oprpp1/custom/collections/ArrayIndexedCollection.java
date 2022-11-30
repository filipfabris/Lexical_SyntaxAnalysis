package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <b>Class ArrayIndexedCollection </b> extends <b>class Collection</b> <br>
 * Resizable-array of Objects<br>
 * Duplicate elements are allowed, storage of null references is not allowed
 * @author filip fabris
 * @version 1.0.0
 */
public class ArrayIndexedCollection implements List{
	
	/**
	 * <b>GetterClass </b> implements  <b> ElementsGetter </b>
	 * It Gives ArrayIndexedCollection class functionality of iteration element by element
	 * @author filip fabris
	 */
	private static class GetterClass implements ElementsGetter{
		
		/**
		 * index of element to return on next call of funtion <b>getNextElement()</b>
		 */
		int currentIndex;
		/**
		 * variable to check whether there was modification during iteration on given array
		 */
		long savedModificationCount;
		/**
		 * given collection on which we iterate
		 */
		ArrayIndexedCollection collection;
		
		/**
		 * Default constructor 
		 * @param savedModificationCount variable to check if there was modification on given collection
		 * @param collection on which we iterate
		 * @throws NullPointerException if given collection variable is null
		 */
		GetterClass(long savedModificationCount, ArrayIndexedCollection collection){
			if(collection == null) {
				throw new NullPointerException();
			}
			this.currentIndex = 0;
			this.savedModificationCount = savedModificationCount;
			this.collection = collection;
		}
		
		/**
		 * Function return next element from given collection on which we iterate
		 * @throws NoSuchElementException if there is no more elements
		 * @throwsConcurrentModificationException if elements in given collection was modified
		 */
		@Override
		public Object getNextElement() {
			
			if(this.savedModificationCount != this.collection.modificationCount) {
				throw new ConcurrentModificationException("List has been modified");
			}
			
			if(this.hasNextElement() == false) {
				throw new NoSuchElementException("No more elements");
			}
			
			
			return this.collection.elements[currentIndex++];
		}
		
		/**
		 * Returns if there is more elements
		 */
		@Override
		public boolean hasNextElement() {
			return currentIndex < this.collection.size();
		}
		
	}
	

	/**
	 * Default size of storage unit used if it is not specified
	 */
	final static int DEFAULT_SIZE = 16;

	/**
	 * Current populated size of an array of objects variable <b>elements</b>
	 */
	private int size;


	/**
	 * Internal storage unit
	 */
	private Object[] elements;
	
	/**
	 * Tracks modifications made on internal storage unit
	 */
	private long modificationCount;

	/**
	 * Default <b>ArrayIndexedCollection</b>A constructor <br>
	 * Initializes storage unit <b>elements</b> to default size of 16 elements <br>
	 * Initializes <b>size</b> variable to 0
	 */
	//Constructor
	public ArrayIndexedCollection(){
		this(DEFAULT_SIZE);
	}

	/**
	 * <b>ArrayIndexedCollection</b>A constructor <br>
	 * Initializes storage unit <b>elements</b> to size of initialCapacity <br>
	 * Initializes <b>size</b> variable to 0
	 * @param initialCapacity used to set capacity of internal storage unit
	 * @throws IllegalArgumentException when given initialCapacity is smaller than 1
	 */
	//Constructor
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("given initialCapacity is smaller than 1");
		}
		
		this.elements = new Object[initialCapacity];
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * <b>ArrayIndexedCollection</b>A constructor <br>
	 * Initializes storage unit <b>elements</b> to size of initialCapacity <br>
	 * Initializes <b>size</b> variable to the size of other collection <br>
	 * Length of storage unit is determinated by max( initialCapacity, other.size() )
	 * @param other collection from which elements will be added
	 * @param initialCapacity used to set capacity of internal storage unit
	 * @throws IllegalArgumentException when given initialCapacity is smaller than 1
	 * @throws NullPointerException if other collection is null
	 */
	//Constructor
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) {
			throw new NullPointerException("other collection is null");
		}
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("initialCapacity is smaller than 1");
		}
		
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		
		this.elements = new Object[initialCapacity];
		this.addAll(other);
		this.size = other.size();
		this.modificationCount = 0;
	}

	/**
	 * <b>ArrayIndexedCollection</b>A constructor <br>
	 * Initializes storage unit <b>elements</b> to size of initialCapacity <br>
	 * Initializes <b>size</b> variable to the size of other collection <br>
	 * Length of storage unit is determinated by other.size()
	 * @param other collection from which elements will be added
	 * @throws IllegalArgumentException when given initialCapacity is smaller than 1
	 * @throws NullPointerException if other collection is null
	 */
	//Constructor
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_SIZE);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * @param value Adds the given object into this collection
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("value is null");
		}
		
		checkCapacity();
		
		elements[size] = value;
		size++;
//		modificationCount++;
	}

	/**
	 * @param value object to be checked if exists in colletion
	 * @return Returns true only if the collection contains given value
	 * @throws NullPointerException if value is null
	 */
	@Override
	public boolean contains(Object value) {
		if(value == null) {
			throw new NullPointerException("value is null");
		}
		
		for(int i = 0; i<size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param value object to be removed
	 * @return Returns true only if the collection contains given value
	 * @throws NullPointerException if value is null
	 */
	@Override
	public boolean remove(Object value) {
		if(value == null) {
			throw new NullPointerException("value is null");
		}
		
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				postRemove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOfRange(elements, 0, this.size);
	}



	@Override
	public void clear() {	
//		this.forEach(new Processor() {
//
//			@Override
//			public void process(Object value) {
//				value = null;
//			}
//		});
		
		for(int i = 0; i<size; i++) {
			elements[i] = null;
		}
		
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * Get object from specific index
	 * @param index of element to extract
	 * @return extracted element
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public Object get(int index) {
		if(index < 0 || index > size -1) {
			throw new IndexOutOfBoundsException("index is out of bounds");
		}
		
		return elements[index];
	}

	/** index of element we want to find in storage unit
	 * @param value element which we want to find
	 * @return index of found element or -1 for not found
	 * @throws NullPointerException if value is null
	 */
	public int indexOf(Object value) {
		if(value == null) {
			throw new NullPointerException("value is null");
		}

		for(int i = 0; i<size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Remove element at specific index
	 * @param index of element to remove
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public void remove(int index) {
		if(index < 0 || index > size -1) {
			throw new IndexOutOfBoundsException("index is out of bounds");
		}
		postRemove(index);
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * @param value of element we want to add
	 * @param position index position to which we want to add element
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 * @throws NullPointerException if value is null
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size -1) {
			throw new IndexOutOfBoundsException("index is out of bounds");
		}
		
		if(value == null) {
			throw new NullPointerException("value is null");
		}
		
		checkCapacity();
		
		for(int i = size; i>position; i--) {
			elements[i] = elements[i-1]; 
		}
		this.elements[position] = value;	
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Used as internal method to double size of storage unit if it is full
	 */
	private void doubleSize() {
		Object[] doubleElements = new Object[size*2];
		
		for(int i = 0; i<size; i++) {
			doubleElements[i] = elements[i];
		}
		
		elements = doubleElements;
	}

	/**
	 * Used as internal method to safely remove element as specific index
	 * @param index of element to remove
	 */
	private void postRemove(int index) {
		for(int i = index; i<size-1; i++) {
			elements[i] = elements[i+1];
		}
		this.size--;
		this.elements[size] = null;
		this.modificationCount++;
	}

	/**
	 * Checks if there is more space
	 */
	private void checkCapacity() {
		if(size >= elements.length) {
			doubleSize();
		}
	}
	
	/**
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + Objects.hash(size);
		return result;
	}

	/**
	 * @param obj element to be compared
	 * @return true if elements are equal, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayIndexedCollection other = (ArrayIndexedCollection) obj;
		return Arrays.deepEquals(elements, other.elements) && size == other.size;
	}
	
	/**
	 * Creates ElementsGetter object which can be used as iterator
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new GetterClass(this.modificationCount, this);
	}
	

}
