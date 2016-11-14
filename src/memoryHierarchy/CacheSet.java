package memoryHierarchy;

import java.util.ArrayList;

import exceptions.CacheMissException;

public class CacheSet {

	private CacheBlock[] blocks;

	ArrayList<Object> LRUList; //Linked list to track the LRU element in the set

	public CacheBlock[] getBlocks() {
		return blocks;
	}

	public void setBlocks(CacheBlock[] blocks) {
		this.blocks = blocks;
	}

	public CacheSet(int setLines, int lineSize) {
		this.LRUList = new ArrayList<Object>();
		this.blocks = new CacheBlock[setLines];
		for (int i = 0; i < blocks.length; i++)
			blocks[i] = new CacheBlock(lineSize);
	}

	public Byte fetch(int tag, int offset) throws RuntimeException {
		for (int i = 0; i < this.blocks.length; i++)
			if (blocks[i].getTag() == tag){
				if(LRUList.contains(blocks[i].getData(offset)))
					LRUList.remove(blocks[i].getData(offset));
				LRUList.add(blocks[i].getData(offset));
				return blocks[i].getData(offset);
			}
		throw new CacheMissException("Miss");
	}

}
