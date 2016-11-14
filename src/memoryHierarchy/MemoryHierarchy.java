package memoryHierarchy;

public class MemoryHierarchy {

	private Memory mainMemory;
	private Cache[] caches;
	
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
	//Main Simulator Class will create the caches array and pass it to the MemoryHierarchy
	public MemoryHierarchy(Cache[] caches, int memoryAccessTime){
		mainMemory = new Memory(memoryAccessTime);
		this.caches = caches;
		
	}
	
}
