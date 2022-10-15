package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {
	
	abstract Object getNextElement();
	
	abstract boolean hasNextElement();
	
	default void processRemaining(Processor p) {
		while(this.hasNextElement() == true) {
			System.out.println(this.getNextElement());
		}
	}

}
