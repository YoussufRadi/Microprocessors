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
		Byte[] x = null;
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
				x = caches[i].fetch(index, tag);
				writeInUpperLevel(x, --i, address);
				return x[offset];
			} catch (Exception e) {
				continue;
			}
		}
		writeInUpperLevel(x, caches.length - 1, address);
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
			writeInLowerLevel(x1, ++j, address);
		if (j >= 0)
			writeInUpperLevel(bytes, --j, address);

	}

	public void write(Byte data, int address) {
		int instructionAccessTime = 0;	
		CacheBlock x = null;
		int offset = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		
		for (int i = 0; i < caches.length; i++) {
			
			instructionAccessTime += caches[i].getAccessTime();
			totalAccessTime += caches[i].getAccessTime();
			setLatestAccessTime(instructionAccessTime);
			
			int index = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / (Math.pow(2, offset) * Math
					.pow(2, index)));
			
			x = caches[i].writeByte(data, index, tag, offset);
			
			if (x != null) {
				
				writeInLowerLevel(x, ++i, address);
				
				Byte[] y = new Byte[x.getData().length];
				for (int z = 0; z < y.length; z++)
					y[z] = new Byte(x.getData()[z]);
				writeInUpperLevel(y, --i, address);
			}
		}
		if (x == null) {
			Byte[] y = mainMemory.writeByte(data, address, offset,
					caches[0].getLineSize());
			writeInUpperLevel(y, caches.length - 1, address);
		}
	}

	private void writeInLowerLevel(CacheBlock x, int i, int address) {
		CacheBlock x1 = null;
		int newOffset = (int) (Math.log(caches[i].getLineSize()) / Math
				.log(2));
		int newIndex = (int) (Math.log(caches[i].getNumberOfLines()
				/ caches[i].getmWays()) / Math.log(2));
		int newTag = (int) (address / (Math.pow(2, newOffset) * Math.pow(2,
				newIndex)));
		Byte[] y = new Byte[x.getData().length];
		for (int z = 0; z < y.length; z++)
			y[z] = new Byte(x.getData()[z]);
		
		if (i >= caches.length) {
			mainMemory.write(y, address);
		} else if (caches[i-1].getPolicy().equals(WritingPolicy.WRITE_THROUGH)
				|| x.isDirty()) {
			x1 = caches[i].write(y, newIndex, newTag);
			if (x1 != null)
				writeInLowerLevel(x1, ++i, address);
			if (caches[i].getPolicy().equals(WritingPolicy.WRITE_THROUGH))
				writeInLowerLevel(x, ++i, address);
		}
	}
}
