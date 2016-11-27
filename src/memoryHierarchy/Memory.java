package memoryHierarchy;

public class Memory {
	private Word[] data;
	private int accessTime;

	public Word[] getData() {
		return data;
	}

	public void setData(Word[] data) {
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
		this.data = new Word[4096];
	}

	public Word[] fetch(int address, int offset, int lineSize) {
		Word[] x  = new Word[lineSize];
		for(int i = 0; i < lineSize; i++)
			x[i] = this.data[address - offset + i];
		return x;
	}

	public void write(Word[] data, int address) {
		for (int i = 0; i < data.length; i++)
			this.data[address+i] = data[i];
	}
	
	public Word[] writeByte(Word data, int address, int offset , int lineSize) {
		this.data[address] = data;
		Word[] x  = new Word[lineSize];
		for(int i = 0; i < lineSize; i++)
			x[i] = this.data[address - offset + i];
		return x;
	}
}
