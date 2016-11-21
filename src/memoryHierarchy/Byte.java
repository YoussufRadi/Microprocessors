package memoryHierarchy;

public class Byte { // 3ashan 5ater Hazem :D
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Byte() {
		this.data = new Object();
	}
	
	public Byte(Object data) {
		this.data = data;
	}
}
