package components;

public class CacheBlock {
	
	Object [] data;
	int size;
	int tag;
	boolean valid;

	public CacheBlock(int size){
		this.size = size;
		this.data = new Object[size];
		this.tag = -1;
		this.valid = false;
	}

}
