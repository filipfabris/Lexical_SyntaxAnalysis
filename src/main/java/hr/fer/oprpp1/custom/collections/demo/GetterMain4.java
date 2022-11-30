package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.*;

public class GetterMain4 {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		System.out.println(col.toArray().toString());
		
	
		System.out.println(Arrays.toString(col.toArray()));
		ElementsGetter getter = col.createElementsGetter();
		
		
		Collection col2 = new ArrayIndexedCollection();
		getter.processRemaining(new Processor() {
			
			@Override
			public void process(Object value) {
				col2.add(value);
				
			}
		});
		
		System.out.println(Arrays.toString(col2.toArray()));
	}
}
