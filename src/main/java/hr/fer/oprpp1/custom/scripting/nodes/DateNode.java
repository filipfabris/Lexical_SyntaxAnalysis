package hr.fer.oprpp1.custom.scripting.nodes;

public class DateNode extends Node{
	
	String date;
	
	public DateNode(){
		
	}
	
	public DateNode(String date){
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$");
		
		sb.append("DATE" );
		
		sb.append(date);
		
		sb.append("$}");
		
		return sb.toString();
	}
	
	
}
