package memoryHierarchy;

public class Memory {
	private Byte[] data;
	private int accessTime;

	public Byte[] getData() {
		return data;
	}

	public void setData(Byte[] data) {
		this.data = data;
	}

	public int getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(int accessTime) {
		this.accessTime = accessTime;
	}

	public Memory(int accessTime) {
		this.accessTime = accessTime;
		this.data = new Byte[65536];
	}

}
