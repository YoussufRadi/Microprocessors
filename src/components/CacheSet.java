package components;

public class CacheSet {
	
	CacheBlock [] blocks;
	int size;
	LinkList LRUList;	//Linked list to track the LRU element in the set

	public CacheSet(int size, int lineSize){
		this.size = size;
		this.LRUList = new LinkList();
		this.blocks = new CacheBlock[size];
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = new CacheBlock(lineSize);
	}

}
