package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.*;

public class TesterMain {
	
	public static void main(String[] args) {
		
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();

		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);

		col2.addAllSatisfying(col1, new Tester() {

			@Override
			public boolean test(Object obj) {
				if (!(obj instanceof Integer))
					return false;

				Integer i = (Integer) obj;
				return i % 2 == 0;
			}
		});

//		Object obj[] = {};
		
		System.out.println(Arrays.toString(col2.toArray()));
		
		Object obj[] = {12, 2, 4, 6};

		System.out.println(Arrays.toString(obj));
	

	}
}
