package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

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
	
	@Test
	void ElementsGetterTest() {

		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		assertEquals("Ivo", getter.getNextElement());
		assertEquals("Ana", getter.getNextElement());
		assertEquals("Jasna", getter.getNextElement());
	}

	@Test
	void ElementsGetterTest2() {

		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");

		ElementsGetter getter = col.createElementsGetter();
		col.add("Jasna");

		assertEquals("Ivo", getter.getNextElement());
		assertEquals("Jasna", getter.getNextElement());

		assertThrows(NoSuchElementException.class, () -> {
			getter.getNextElement();
		});
	}

	@Test
	void ElementsGetterTest3() {

		Collection col = new ArrayIndexedCollection();

		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();

		col.add("Ivo");
		col.add("Jasna");

		// getter1!
		assertEquals("Ivo", getter1.getNextElement());
		assertEquals("Jasna", getter1.getNextElement());

		// geter2!
		assertEquals("Ivo", getter2.getNextElement());

		// getter1!
		assertThrows(NoSuchElementException.class, () -> {
			getter1.getNextElement();
		});
	}

	@Test
	void ElementsGetterTest4() {

		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		assertEquals("Ivo", getter.getNextElement());
		col.clear();

		assertThrows(ConcurrentModificationException.class, () -> {
			getter.getNextElement();
		});
	}

	@Test
	void ElementsGetterTest5() {

		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		Collection col2 = new ArrayIndexedCollection();
		getter.processRemaining(new Processor() {

			@Override
			public void process(Object value) {
				col2.add(value);

			}
		});

		assertEquals(Arrays.toString(col2.toArray()), Arrays.toString(col.toArray()));

	}

	@Test
	void ElementsGetterTest6() {

		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		col.add("Iva");

		ElementsGetter getter = col.createElementsGetter();

		assertEquals("Ivo", getter.getNextElement());

		col.remove(2);

		assertThrows(ConcurrentModificationException.class, () -> {
			getter.getNextElement();
		});
	}

	@Test
	void ElementsGetterTest7() {

		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		col.add("Iva");

		ElementsGetter getter = col.createElementsGetter();

		assertEquals("Ivo", getter.getNextElement());

		col.remove("Iva");

		assertThrows(ConcurrentModificationException.class, () -> {
			getter.getNextElement();
		});
	}
	
	@Test
	void ElementsGetterTest8() {

		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		col.add("Iva");

		ElementsGetter getter = col.createElementsGetter();

		assertEquals("Ivo", getter.getNextElement());

		col.insert("Novi element", 1);

		assertThrows(ConcurrentModificationException.class, () -> {
			getter.getNextElement();
		});
	}
	
	@Test
	void addAllSatisfyingTest() {

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

		Object obj[] = {12, 2, 4, 6};
		
		assertEquals(Arrays.toString(obj), Arrays.toString(col2.toArray()));
	}


}
