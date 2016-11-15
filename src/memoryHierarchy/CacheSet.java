package memoryHierarchy;

import java.util.ArrayList;

import exceptions.CacheMissException;

public class CacheSet {

	private ArrayList<CacheBlock> blocks;
	private int lineSize;
	ArrayList<CacheBlock> LRUList; // Linked list to track the LRU element in
									// the
									// set

	public ArrayList<CacheBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<CacheBlock> blocks) {
		this.blocks = blocks;
	}

	public int getLineSize() {
		return lineSize;
	}

	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}

	public CacheSet(int setLines, int lineSize) {
		this.LRUList = new ArrayList<CacheBlock>();
		this.blocks = new ArrayList<CacheBlock>();
		this.setLineSize(lineSize);
	}

	public Byte fetch(int tag, int offset) throws RuntimeException {
		for (int i = 0; i < this.blocks.size(); i++)
			if (blocks.get(i).getTag() == tag) {
				if (LRUList.contains(blocks.get(i)))
					LRUList.remove(blocks.get(i));
				LRUList.add(blocks.get(i));
				return blocks.get(i).getData(offset);
			}
		throw new CacheMissException("Miss");
	}

	public CacheBlock write(Byte[] data, int tag) {
		CacheBlock x = new CacheBlock(data, tag);
		CacheBlock y = null;
		for (int i = 0; i < LRUList.size(); i++) {
			if (LRUList.get(i).getTag() == tag)
				LRUList.remove(i);
		}
		LRUList.add(x);
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getTag() == tag) {
				blocks.get(i).setData(data);
				blocks.get(i).setDirty(true);
				return y;
			}
		}
		if (blocks.size() > lineSize) {
			y = LRUList.remove(0);
			blocks.remove(y);
		}
		blocks.add(x);
		return y;
	}
}
