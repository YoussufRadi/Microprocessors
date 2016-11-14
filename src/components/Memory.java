package components;

public class Memory {
	
	private int size;
	
	public Memory(int size){
		this.setSize(size);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
//	public String toString() {
//		if(head == null)
//			return "[ ]";
//		String res = "[ " + head.data;
//		Link current = head.next;
//		while(current != null) {
//			res += ", " + current.data;
//			current = current.next;
//		}
//		res += " ]";
//		return res;
//	}	
}

