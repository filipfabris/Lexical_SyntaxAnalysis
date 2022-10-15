package hr.fer.oprpp1.custom.collections;

public interface List extends Collection{
	
	abstract Object get(int index);
	
	abstract void insert(Object value, int position);
	
	abstract int indexOf(Object value);
	
	abstract void remove(int index);

}
