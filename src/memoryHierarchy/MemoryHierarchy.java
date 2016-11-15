package memoryHierarchy;

public class MemoryHierarchy {

	private Memory mainMemory;
	private Cache[] caches;
	private static int totalAccessTime;
	private static int latestAccessTime;

	public Memory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(Memory mainMemory) {
		this.mainMemory = mainMemory;
	}

	public Cache[] getCaches() {
		return caches;
	}

	public void setCaches(Cache[] caches) {
		this.caches = caches;
	}

	public static int getTotalAccessTime() {
		return totalAccessTime;
	}

	public static int getLatestAccessTime() {
		return latestAccessTime;
	}

	public static void setLatestAccessTime(int latestAccessTime) {
		MemoryHierarchy.latestAccessTime = latestAccessTime;
	}

	public static void setTotalAccessTime(int totalAccessTime) {
		MemoryHierarchy.totalAccessTime = totalAccessTime;
	}

	// Main Simulator Class will create the caches array and pass it to the
	// MemoryHierarchy
	public MemoryHierarchy(Cache[] caches, int memoryAccessTime) {
		mainMemory = new Memory(memoryAccessTime);
		this.caches = caches;

	}

	public Byte fetch(int address) throws RuntimeException {
		int instructionAccessTime = 0;
		Byte x = null;
		for (int i = 0; i < caches.length; i++) {
			instructionAccessTime += caches[i].getAccessTime();
			int offset = (int) (Math.log(caches[i].getLineSize()) / Math.log(2));
			int index = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / (Math.pow(2, offset) * Math
					.pow(2, index)));
			try {
				totalAccessTime += caches[i].getAccessTime();
				setLatestAccessTime(instructionAccessTime);
				x = caches[i].fetch(index, tag, offset);
				writeInUpperLevel(new Byte[] { x }, --i, address);
				return x;
			} catch (Exception e) {
				continue;
			}
		}
		writeInUpperLevel(new Byte[] { x }, caches.length - 1, address);
		return mainMemory.fetch(address);
	}

	private void writeInUpperLevel(Byte[] bytes, int j, int address) {
		int newOffset = (int) (Math.log(caches[j].getLineSize()) / Math.log(2));
		int newIndex = (int) (Math.log(caches[j].getNumberOfLines()
				/ caches[j].getmWays()) / Math.log(2));
		int newTag = (int) (address / (Math.pow(2, newOffset) * Math.pow(2,
				newIndex)));
		CacheBlock x1 = caches[j].write(bytes, newIndex, newTag);
		if (x1 != null)
			if (caches[j].getPolicy().equals(WritingPolicy.WRITE_THROUGH)
					|| x1.isDirty())
				writeInLowerLevel(x1, ++j, address);
		if(j >= 0)
			writeInUpperLevel(bytes, ++j, address);

	}

	public void write(Byte[] data, int address) {
		int instructionAccessTime = 0;
		boolean policy = true;
		for (int i = 0; i < caches.length; i++) {
			instructionAccessTime += caches[i].getAccessTime();
			totalAccessTime += caches[i].getAccessTime();
			setLatestAccessTime(instructionAccessTime);
			int offset = (int) (Math.log(caches[i].getLineSize()) / Math.log(2));
			int index = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / (Math.pow(2, offset) * Math
					.pow(2, index)));
			CacheBlock x = caches[i].write(data, index, tag);
			if (x != null)
				if (caches[i].getPolicy().equals(WritingPolicy.WRITE_THROUGH)
						|| x.isDirty())
					writeInLowerLevel(x, ++i, address);
			policy = (caches[i].getPolicy() == WritingPolicy.WRITE_THROUGH);
		}
		if (policy)
			mainMemory.write(data, address);
	}

	private void writeInLowerLevel(CacheBlock x, int i, int address) {
		CacheBlock x1 = null;
		if (i >= caches.length) {
			mainMemory.write(x.getData(), address);
		} else {
			int newOffset = (int) (Math.log(caches[i].getLineSize()) / Math
					.log(2));
			int newIndex = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int newTag = (int) (address / (Math.pow(2, newOffset) * Math.pow(2,
					newIndex)));
			x1 = caches[i].write(x.getData(), newIndex, newTag);
			if (x1 != null)
				if (caches[i].getPolicy().equals(WritingPolicy.WRITE_THROUGH)
						|| x.isDirty())
					writeInLowerLevel(x1, ++i, address);
		}
	}
}
