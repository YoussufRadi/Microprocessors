package memoryHierarchy;

public class CacheBlock {

	private Byte[] data;
	private int tag;
	private boolean dirty;
	private boolean valid;

	public Byte getData(int offset) {
		return data[offset];
	}

	public void setData(Byte data, int offset) {
		this.data[offset] = data;
	}
	
	public Byte[] getData() {
		return data;
	}

	public void setData(Byte[] data) {
		this.data = data;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public CacheBlock(int lineSize) {
		this.data = new Byte[lineSize];
		this.tag = -1;
		this.dirty = false;
		this.valid = false;
	}

}