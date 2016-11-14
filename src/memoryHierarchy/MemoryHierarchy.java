package memoryHierarchy;


public class MemoryHierarchy {

	private Memory mainMemory;
	private Cache[] caches;
	private static int totalAccessTime;
		
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
		for (int i = 0; i < caches.length; i++) {
			totalAccessTime += caches[i].getAccessTime();
			int offset = (int) (Math.log(caches[i].getLineSize())/Math.log(2));
			int index = (int) (Math.log(caches[i].getNumberOfLines() / caches[i].getmWays())/Math.log(2));
			int tag = (int) (address / (Math.pow(2, offset) * Math.pow(2, index)));
			try {
				return caches[i].fetch(index,tag,offset); //still need to write it in upper cache levels in case of read miss
			} catch (Exception e) {
				continue;
			}
		}
			return mainMemory.fetch(address);
	}
}
