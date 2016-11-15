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
				return caches[i].fetch(index, tag, offset); // still need to
															// write it in upper
															// cache levels in
															// case of read miss
			} catch (Exception e) {
				continue;
			}
		}
		return mainMemory.fetch(address);
	}

	public void write(Byte[] data, int address) {
		int instructionAccessTime = 0;
		for (int i = 0; i < caches.length; i++) {
			instructionAccessTime += caches[i].getAccessTime();
			int offset = (int) (Math.log(caches[i].getLineSize()) / Math.log(2));
			int index = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / (Math.pow(2, offset) * Math
					.pow(2, index)));
			totalAccessTime += caches[i].getAccessTime();
			setLatestAccessTime(instructionAccessTime);
			caches[i].write(data, index, tag);
			// return caches[i].fetch(index,tag,offset);
		}
		// return mainMemory.fetch(address);
	}
}
