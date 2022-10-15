package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <b>Class LinkedListIndexedCollection </b> extends <b>class Collection</b> <br>
 * Linked List of Objects<br>
 * Duplicate elements are allowed, storage of null references is not allowed
 * @author filip fabris
 * @version 1.0.0
 */

public class LinkedListIndexedCollection implements List {
	
	private static class GetterClass implements ElementsGetter{
		
		int currentIndex;
		ListNode current;
		long savedModificationCount;
		LinkedListIndexedCollection collection;
		
		
		GetterClass(ListNode current, long savedModificationCount, LinkedListIndexedCollection collection){
			this.currentIndex = 0;
			this.current = current;
			this.savedModificationCount = savedModificationCount;
			this.collection = collection;
		}

		@Override
		public Object getNextElement() {
			if(this.hasNextElement() == false) {
				throw new NoSuchElementException("No more elements");
			}
			if(this.savedModificationCount != this.collection.modificationCount) {
				throw new ConcurrentModificationException("List has been modified");
			}
			
			Object value = current.value;
			current = current.next;
			currentIndex++;
			return value;
		}

		@Override
		public boolean hasNextElement() {
			return currentIndex < this.collection.size();
		}
		
	}

	/**
	 * Local class used for creating nodes of LinkedList
	 */
	private static class ListNode {

		/**
		 * Reference to previous node
		 */
		ListNode previous;
		/**
		 * Reference to next node
		 */
		ListNode next;
		/**
		 * Value of current node
		 */
		Object value;

		/**
		 * next and previous node are set to null
		 * @param value to be assigned to node
		 */
		public ListNode(Object value) {
			this.value = value;
			this.previous = null;
			this.next = null;
		}

		/**
		 * Prepare node for garbage collector
		 * @param node to be deleted
		 */
		public static void resetNode(ListNode node){
			node.previous = null;
			node.next = null;
			node.value = null;
		}
	}

	/**
	 * Current size of LinkedList
	 */
	private int size;
	/**
	 * Reference to first Node element
	 */
	private ListNode first;
	/**
	 * Reference to last Node element
	 */
	private ListNode last;
	
	/**
	 * Tracks modifications made on internal storage unit
	 */
	private long modificationCount;

	/**
	 * Default <b>LinkedListIndexedCollection</b> constructor
	 * first and last Node references are set to null
	 */
	//Constructor
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
		this.modificationCount = 0;
	}

	/**
	 * Adds all elements from <b>other</b> Collection to <b>LinkedListIndexedCollection</b>
	 * @param other collection from which elements will be added to current <b>LinkedListIndexedCollection</b>
	 */
	//Constructor
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		this.addAll(other);
		this.modificationCount = 0;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection at the end of collection
	 * @param value Adds the given object into this collection
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
//		this.modificationCount++;
		this.size++;

		if (first == null) {
			this.first = this.last = new ListNode(value);
			return;
		}

		ListNode node = new ListNode(value);

		//Zadnji sada pokazuje na novi node
		this.last.next = node;

		//Novi zadnji pokazuje na prijašnji zadnji i na null za next
		node.previous = last;
		node.next = null;

		//Zadnji je sada updatan
		this.last = node;

		return;

	}

	/**
	 * @param value object to be checked if exists in colletion
	 * @return true if elements exists in List
	 * @throws NullPointerException if value is null
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode current = first;

		while (current.next != null) {
			if (current.value.equals(value)) {
				return true;
			}
			current = current.next;
		}

		return false;
	}

	/**
	 * @param value object to be removed
	 * @return true if elements exists in List
	 * @throws NullPointerException if value is null
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode current = first;
		while(current != null) {
			if(current.value.equals(value)) {
				
				current.previous.next = current.next;
				current.next.previous = current.previous;
				ListNode.resetNode(current);
				this.size--;
				this.modificationCount++;
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] elements = new Object[size];
		int index = 0;

		ListNode current = first;

		while (current != null) {
			elements[index] = current.value;
			index++;
			current = current.next;
		}

		return elements;
	}


	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * @param index to extract
	 * @return the object that is stored in linked list at position index
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public Object get(int index) {

		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		int counter;
		ListNode current;
		
		if(index > size/2) {
			current = last;
			counter = size-1;
			while(current != null && counter != index) {
				current = current.previous;
				counter--;
			}
		}
		else {
		current = first;
		counter = 0;
		while(current != null && counter != index) {
			current = current.next;
			counter++;
			}
		}
		return current.value;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in linked-list. Elements starting from this position are shifted one position
	 * @param value to be added to node
	 * @param position to add element
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 * @throws NullPointerException if value is null
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (value == null) {
			throw new NullPointerException();
		}

		if(this.first == null && position == 0) {
			first = last = new ListNode(value);
		}
		
		if(position == 0) {
			ListNode newElement = new ListNode( value );
			
			newElement.next = first;
			first.previous = newElement;
			
			first = newElement;
		}

		else if(size == position) {
			this.add(value);
		}

		else {
			int counter = 0;
			ListNode current = first;
			ListNode newElement = new ListNode( value );

			while (current != null && counter != position) {
				counter++;
				current = current.next;
			}

			ListNode previous = current.previous;
			previous.next = newElement;

			newElement.next = current;
			newElement.previous = previous;

			current.previous = newElement;
		}
		this.modificationCount++;
		this.size++;
		return;

	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the given value or -1 if the value is not found <br>
	 * null is valid argument
	 * @param value to be searched
	 * @return index of found object or -1
	 */
	public int indexOf(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		int index = 0;

		ListNode current = first;

		while (current != null) {
			if (current.value.equals(value)) {
				return index;
			}
			current = current.next;
			index++;
		}
		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was previously at location index+1 after this operation is on location index <br>
	 * @param index to be removed
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		// Prvi i jedini
		if (index == 0 && size == 1) {
			this.first = null;
			this.last = null;

		}

		// Prvi
		else if (index == 0) {
			ListNode temp = first.next;
			this.first = temp;
			this.first.previous = null;

		}

		// Zadnji
		else if (index == size - 1) {
			ListNode temp = last.previous;
			temp.next = null;
			
			ListNode.resetNode(this.last);
			this.last = temp;

		}

		// Middle
		else {
			ListNode current = first;
			int counter = 0;
			while (current.next != null && counter != index) {
				current = current.next;
				counter++;
			}

			ListNode remove = current;

			ListNode previousTemp = remove.previous;
			previousTemp.next = remove.next;
			
			ListNode.resetNode(remove);
		}

		this.modificationCount++;
		this.size--;
		return;
	}

	/**
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(first, last, size);
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
		LinkedListIndexedCollection other = (LinkedListIndexedCollection) obj;
		if(this.size() != other.size()) {
			return false;
		}
		for(int i = 0; i<this.size; i++) {
			if(this.get(i) != other.get(i)) {
				return false;
			}
		}
		return true;
		
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new GetterClass(this.first, this.modificationCount, this);
	}

}
