package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

public class Node {
	
	/**
	 * Collection in which childern nodes are stored
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Returns a number of (direct) children
	 */
	private int numberOfChildren;
	
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}
		
		collection.add(child);
		numberOfChildren++;
	}
	
	public int numberOfChildren() {
		return numberOfChildren;
	}
	
	public Node getChild(int index) {		
		if(index < 0 || index > numberOfChildren -1) {
			throw new IndexOutOfBoundsException("index is out of bounds");
		}
		
		if(this.collection == null) {
			throw new NullPointerException("collection was not initialized");
		}
		
		return (Node)collection.get(index);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		if(this.collection == null) {
			return "";
		}
		
		ElementsGetter iterator = this.collection.createElementsGetter();
		
		while(iterator.hasNextElement()) {
			
			Node currentNode = (Node) iterator.getNextElement();

				sb.append(currentNode.toString());	
		}
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(collection, numberOfChildren);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return this.toString().equals(other.toString());
	}


	
	

}
