package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {
	
	LinkedListIndexedCollection listTwoElements;
	LinkedListIndexedCollection defaultList;
	int sum;

	@BeforeEach
	void start() {
		listTwoElements = new LinkedListIndexedCollection();
		listTwoElements.add(1);
		listTwoElements.add(2);
		listTwoElements.add(3);

		defaultList = new LinkedListIndexedCollection(listTwoElements);
		defaultList.add("Auto");
		defaultList.add("Tenk");
		defaultList.add(Integer.valueOf(199));
		defaultList.add("Los Angeles");
		
		sum = 0;
	}

	@Test
	void ArrayConstructorTest1() {
		assertEquals(listTwoElements.size(), 3);
	}

	@Test
	void ArrayConstructorTest2() {
		LinkedListIndexedCollection fromOther = new LinkedListIndexedCollection(listTwoElements);
		assertEquals(fromOther.size(), 3);
	}
	
	@Test
	void ArrayConstructorTest3() {
		assertThrows(NullPointerException.class, () -> {
			new LinkedListIndexedCollection(null);
		});
	}
	

	@Test
	void addElement() {
		defaultList.contains(199);
	}

	@Test
	void removeElementObject() {
		assertEquals(defaultList.remove("Tenk"), true);
		assertEquals(defaultList.size(), 6);
	}

	@Test
	void removeElementIndex() {
		defaultList.remove(4);
		assertEquals(defaultList.size(), 6);
		assertEquals(defaultList.contains("Tenk"), false);
	}

	@Test
	void getElementIndex() {
		assertEquals(defaultList.get(3), "Auto");
	}

	@Test
	void InsertElementIndexMiddle() {
		int oldSize = defaultList.size();
		defaultList.insert("Novi Element", 5);
		assertEquals(defaultList.get(5), "Novi Element");
		assertEquals(defaultList.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexStart() {
		int oldSize = defaultList.size();
		defaultList.insert("Novi Element", 0);
		assertEquals(defaultList.get(0), "Novi Element");
		assertEquals(defaultList.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexEnd() {
		int oldSize = defaultList.size();
		defaultList.insert("Novi Element", defaultList.size() - 1);
		assertEquals(defaultList.get(oldSize - 1), "Novi Element");
		assertEquals(defaultList.size(), oldSize + 1);
	}

	@Test
	void InsertElementIndexOutOfBounds() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			defaultList.insert("Ne moze", 10);
		});
	}
	
	@Test
	void InsertElementIndexNull() {
		assertThrows(NullPointerException.class, () -> {
			defaultList.insert(null, 2);
		});
	}
	
	@Test
	void forEach() {
		LinkedListIndexedCollection test = new LinkedListIndexedCollection();
		
		class TestFor implements Processor {
			public void process(Object o) {
				test.add(o);
			}
		};
		
		TestFor local = new TestFor();
		
		defaultList.forEach(local);
		
		assertEquals(test.size(), defaultList.size());
		
		String fromDefaultArray = Arrays.toString(defaultList.toArray());
		String testArray = Arrays.toString(test.toArray());
		
		assertEquals(fromDefaultArray, testArray);
	}
	
	@Test
	void toArrayTest() {
		String fromDefaultArray = Arrays.toString(defaultList.toArray());
		String testArray = Arrays.toString(new Object[]{1, 2, 3, "Auto", "Tenk", 199, "Los Angeles"});
		assertEquals(fromDefaultArray, testArray);
	}
	
	@Test
	void indexOfTestLast() {
		assertEquals(this.defaultList.size()-1, this.defaultList.indexOf("Los Angeles"));
	}
	
	@Test
	void indexOfTestFirst() {
		assertEquals(0, this.defaultList.indexOf(1));
	}
	
	@Test
	void equalsMethod() {
		LinkedListIndexedCollection array1 = new LinkedListIndexedCollection();
		array1.add("ZZZ");
		array1.add(3);
		array1.add('h');
		
		LinkedListIndexedCollection array2 = new LinkedListIndexedCollection();
		array2.add("ZZZ");
		array2.add(Integer.valueOf(3));
		array2.add(Character.valueOf('h'));
		
		assertEquals(true, array1.equals(array2));
	}
	
	@Test
	void clearTest() {
		this.defaultList.clear();
		assertEquals(0, this.defaultList.size());
		assertEquals(-1, this.defaultList.indexOf(3));
	}


}
