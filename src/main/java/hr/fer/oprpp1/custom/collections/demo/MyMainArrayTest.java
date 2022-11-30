package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class MyMainArrayTest {

	public static void main(String[] args) {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		
		array.add("*");
		
		char temp = '*';
		
		boolean value = array.contains(temp);
		
		System.out.println(value);
	}

}
