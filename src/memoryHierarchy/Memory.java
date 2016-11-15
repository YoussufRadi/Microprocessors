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

	public Byte fetch(int address) {
		return data[address];
	}

	public void write(Byte[] data, int address) {
		for (int i = 0; i < data.length; i++)
			data[address+i] = data[i];
	}
}
