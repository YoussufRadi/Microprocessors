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
			this.data[address+i] = data[i];
	}
	
	public Byte[] writeByte(Byte data, int address, int offset , int lineSize) {
		this.data[address] = data;
		Byte[] x  = new Byte[lineSize];
		for(int i = 0; i < lineSize; i++)
			x[i] = this.data[address - offset + i];
		return x;
	}
}
