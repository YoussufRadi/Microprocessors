package memoryHierarchy;

import exceptions.CacheMissException;

public class CacheSet {

	private CacheBlock[] blocks;

	// LinkList LRUList; //Linked list to track the LRU element in the set

	public CacheBlock[] getBlocks() {
		return blocks;
	}

	public void setBlocks(CacheBlock[] blocks) {
		this.blocks = blocks;
	}

	public CacheSet(int setLines, int lineSize) {
		// this.LRUList = new LinkList();
		this.blocks = new CacheBlock[setLines];
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = new CacheBlock(lineSize);
	}

	public Byte fetch(int tag, int offset) throws RuntimeException {
		for (int i = 0; i < this.blocks.length; i++)
			if (blocks[i].getTag() == tag)
				return blocks[i].getData(offset);
		throw new CacheMissException("Miss");
	}

}
