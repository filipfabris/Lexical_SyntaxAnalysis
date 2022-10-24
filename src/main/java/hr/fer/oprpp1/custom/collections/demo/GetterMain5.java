package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;

public class GetterMain5 {

	public static void main(String[] args) {


		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		col.add("Iva");

		
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println(getter.getNextElement());
		
		
		col.remove(1);
		
		System.out.println(getter.getNextElement());

		
	}

}
