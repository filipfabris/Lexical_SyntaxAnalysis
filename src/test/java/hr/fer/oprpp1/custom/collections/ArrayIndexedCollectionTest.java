package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	ArrayIndexedCollection arrayTwoElements;
	ArrayIndexedCollection defaultArray;
	int sum;

	@BeforeEach
	void start() {
		arrayTwoElements = new ArrayIndexedCollection();
		arrayTwoElements.add(1);
		arrayTwoElements.add(2);
		arrayTwoElements.add(3);

		defaultArray = new ArrayIndexedCollection(arrayTwoElements);
		defaultArray.add("Auto");
		defaultArray.add("Tenk");
		defaultArray.add(Integer.valueOf(199));
		defaultArray.add("Los Angeles");
		
		sum = 0;
	}

	@Test
	void ArrayConstructorTest1() {
		assertEquals(arrayTwoElements.size(), 3);
	}

	@Test
	void ArrayConstructorTest2() {
		ArrayIndexedCollection fromOther = new ArrayIndexedCollection(arrayTwoElements, 1);
		assertEquals(fromOther.size(), 3);
	}
	
	@Test
	void ArrayConstructorTest3() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexedCollection(-1);
		});
	}
	
	@Test
	void ArrayConstructorTest4() {
		assertThrows(NullPointerException.class, () -> {
			new ArrayIndexedCollection(null, 5);
		});
	}

	@Test
	void addElement() {
		defaultArray.contains(199);
	}

	@Test
	void removeElementObject() {
		assertEquals(defaultArray.remove("Tenk"), true);
		assertEquals(defaultArray.size(), 6);
	}

	@Test
	void removeElementIndex() {
		defaultArray.remove(4);
		assertEquals(defaultArray.size(), 6);
		assertEquals(defaultArray.contains("Tenk"), false);
	}

	@Test
	void getElementIndex() {
		assertEquals(defaultArray.get(3), "Auto");
	}

	@Test
	void InsertElementIndexMiddle() {
		int oldSize = defaultArray.size();
		defaultArray.insert("Novi Element", 5);
		assertEquals(defaultArray.get(5), "Novi Element");
		assertEquals(defaultArray.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexStart() {
		int oldSize = defaultArray.size();
		defaultArray.insert("Novi Element", 0);
		assertEquals(defaultArray.get(0), "Novi Element");
		assertEquals(defaultArray.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexEnd() {
		int oldSize = defaultArray.size();
		defaultArray.insert("Novi Element", defaultArray.size() - 1);
		assertEquals(defaultArray.get(oldSize - 1), "Novi Element");
		assertEquals(defaultArray.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			defaultArray.insert("Ne moze", 10);
		});
	}
	
	@Test
	void InsertElementIndexNull() {
		assertThrows(NullPointerException.class, () -> {
			defaultArray.insert(null, 2);
		});
	}
	
	@Test
	void forEach() {
		ArrayIndexedCollection test = new ArrayIndexedCollection();
		
		class TestFor implements Processor {
			public void process(Object o) {
				test.add(o);
			}
		};
		
		TestFor local = new TestFor();
		
		defaultArray.forEach(local);
		
		assertEquals(test.size(), defaultArray.size());
		
		String fromDefaultArray = Arrays.toString(defaultArray.toArray());
		String testArray = Arrays.toString(test.toArray());
		
		assertEquals(fromDefaultArray, testArray);
	}
	
	@Test
	void toArrayTest() {
		String fromDefaultArray = Arrays.toString(defaultArray.toArray());
		String testArray = Arrays.toString(new Object[]{1, 2, 3, "Auto", "Tenk", 199, "Los Angeles"});
		assertEquals(fromDefaultArray, testArray);
	}
	
	@Test
	void indexOfTestLast() {
		assertEquals(this.defaultArray.size()-1, this.defaultArray.indexOf("Los Angeles"));
	}
	
	@Test
	void indexOfTestFirst() {
		assertEquals(0, this.defaultArray.indexOf(1));
	}
	
	@Test
	void equalsMethod() {
		
		ArrayIndexedCollection array1 = new ArrayIndexedCollection();
		array1.add("ZZZ");
		array1.add(3);
		array1.add('h');
		
		ArrayIndexedCollection array2 = new ArrayIndexedCollection();
		array2.add("ZZZ");
		array2.add(Integer.valueOf(3));
		array2.add(Character.valueOf('h'));
		
		assertEquals(true, array1.equals(array2));
	}
	
	@Test
	void clearTest() {
		this.defaultArray.clear();
		assertEquals(0, this.defaultArray.size());
		assertEquals(-1, this.defaultArray.indexOf(3));
	}


}
