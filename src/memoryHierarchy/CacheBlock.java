package memoryHierarchy;

public class CacheBlock implements Cloneable {

	private Byte[] data;
	private int tag;
	private boolean dirty;
	private boolean valid;

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

	
	public CacheBlock(Byte[] data, int tag) {
		this.data = data;
		this.tag = tag;
		this.dirty = false;
		this.valid = true;
	}

	public void setData(Byte data, int offset) {
		this.data[offset] = data;
	}

}
