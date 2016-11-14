package components;

public class CacheBlock {

	private Object[] data;
	private int tag;
	private boolean valid;

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
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

	public CacheBlock(int lineSize) {
		this.data = new Object[lineSize];
		this.tag = -1;
		this.valid = false;
	}

}
