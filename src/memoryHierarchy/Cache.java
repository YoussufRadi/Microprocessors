package memoryHierarchy;

import exceptions.StructuralException;

public class Cache {

	private int cacheSize;
	private int lineSize;
	private int numberOfLines;
	private int mWays;
	private WritingPolicy policy;
	private int accessTime;
	private CacheSet[] sets;

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public int getLineSize() {
		return lineSize;
	}

	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public int getmWays() {
		return mWays;
	}

	public void setmWays(int mWays) {
		this.mWays = mWays;
	}

	public WritingPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(WritingPolicy policy) {
		this.policy = policy;
	}

	public int getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(int accessTime) {
		this.accessTime = accessTime;
	}

	public CacheSet[] getSets() {
		return sets;
	}

	public void setSets(CacheSet[] sets) {
		this.sets = sets;
	}

	public Cache(int cacheSize, int lineSize, int mWays, WritingPolicy policy,
			int accessTime) {
		this.cacheSize = cacheSize;
		this.lineSize = lineSize;
		this.mWays = mWays;
		this.policy = policy;
		this.accessTime = accessTime;
		this.numberOfLines = cacheSize / lineSize;
		this.sets = new CacheSet[numberOfLines / mWays];
		for (int i = 0; i < sets.length; i++)
			sets[i] = new CacheSet(mWays, lineSize);
	}

	public Word[] fetch(int index, int tag) throws RuntimeException {
		if (index > this.sets.length)
			throw new StructuralException("Out of bounds");
		else
			return sets[index].fetch(tag);
	}

	public CacheBlock write(Word[] data, int index, int tag) {
		return sets[index].write(data, tag , this.policy);
	}
	
	public CacheBlock writeByte(Word data, int index, int tag, int offset) {
		return sets[index].writeByte(data, tag, offset);
	}

}
