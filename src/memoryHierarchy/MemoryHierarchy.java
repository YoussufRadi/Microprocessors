package memoryHierarchy;

public class MemoryHierarchy {

	private Memory mainMemory;
	private Cache[] caches;
	private int totalAccessTime;
	private int latestAccessTime;

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

	public int getTotalAccessTime() {
		return this.totalAccessTime;
	}

	public int getLatestAccessTime() {
		return this.latestAccessTime;
	}

	public void setLatestAccessTime(int latestAccessTime) {
		this.latestAccessTime = latestAccessTime;
	}

	public void setTotalAccessTime(int totalAccessTime) {
		this.totalAccessTime = totalAccessTime;
	}

	// Main Simulator Class will create the caches array and pass it to the
	// MemoryHierarchy
	public MemoryHierarchy(Cache[] caches, int memoryAccessTime) {
		mainMemory = new Memory(memoryAccessTime);
		this.caches = caches;
	}

	public Word fetch(int address) throws RuntimeException {
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math
				.log(2));
		int offset = (int) (address % Math.pow(2, offsetBits));
		int instructionAccessTime = 0;
		Word[] x = null;
		for (int i = 0; i < caches.length; i++) {
			instructionAccessTime += caches[i].getAccessTime();
			
			int index = (int) (address / Math.pow(2, offsetBits))
					% caches[i].getSets().length;
			int indexBits = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
					indexBits));
			try {
				totalAccessTime += caches[i].getAccessTime();
				setLatestAccessTime(instructionAccessTime);
				x = caches[i].fetch(index, tag);
				int c = i;
				writeInUpperLevel(x, --c, address);
				return x[offset];
			} catch (Exception e) {
				continue;
			}

		}
		x = mainMemory.fetch(address,offset,caches[0].getLineSize());
		writeInUpperLevel(x, caches.length - 1, address);
		return x[offset];
	}

	private void writeInUpperLevel(Word[] bytes, int j, int address) {
		if (j < 0)
			return;
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		int newIndex = (int) (address / Math.pow(2, offsetBits))
				% caches[j].getSets().length;
		int indexBits = (int) (Math.log(caches[j].getNumberOfLines()
				/ caches[j].getmWays()) / Math.log(2));
		int newTag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
				indexBits));

		CacheBlock x1 = caches[j].write(bytes, newIndex, newTag);
		int c = j;
		if (x1 != null)
			writeInLowerLevel(x1, ++c, address);
		c = j;
		writeInUpperLevel(bytes, --c, address);

	}

	public void write(Word data, int address) {
		int instructionAccessTime = 0;
		CacheBlock x = null;
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		int offset = (int) (address % Math.pow(2, offsetBits));
		for (int i = 0; i < caches.length; i++) {

			instructionAccessTime += caches[i].getAccessTime();
			totalAccessTime += caches[i].getAccessTime();
			setLatestAccessTime(instructionAccessTime);
			int index = (int) (address / Math.pow(2, offsetBits))
					% caches[i].getSets().length;
			int indexBits = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
					indexBits));
			Word dataCloned = (Word) data.clone();
			x = caches[i].writeByte(dataCloned, index, tag, offset);

			if (x != null) {
				int c = i;
				writeInLowerLevel(x, ++c, address);

				Word[] y = new Word[x.getData().length];
				for (int z = 0; z < y.length; z++)
					if (x.getData()[z] != null)
						y[z] = (Word) x.getData()[z].clone();
				c = i;
				writeInUpperLevel(x.getData(), --c, address);
				return;
			}
		}
		if (x == null) {
			Word[] y = mainMemory.writeByte(data, address, offset,
					caches[0].getLineSize());
			writeInUpperLevel(y, caches.length - 1, address);
		}
	}

	private void writeInLowerLevel(CacheBlock x, int i, int address) {
		CacheBlock x1 = null;
		Word[] y = new Word[x.getData().length];
		for (int z = 0; z < y.length; z++)
			if (x.getData()[z] != null)
				y[z] = (Word) x.getData()[z].clone();
		if (i >= caches.length - 1) {
			mainMemory.write(y, address);
			return;
		} else if (caches[i - 1].getPolicy()
				.equals(WritingPolicy.WRITE_THROUGH)) {
			int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math
					.log(2));
			int newIndex = (int) (address / Math.pow(2, offsetBits))
					% caches[i].getSets().length;
			int indexBits = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int newTag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
					indexBits));
			int c = i;
			x1 = caches[i].write(y, newIndex, newTag);
			if (x1 != null)
				writeInLowerLevel(x1, ++c, address);
			c = i;
			if (caches[i].getPolicy().equals(WritingPolicy.WRITE_THROUGH))
				writeInLowerLevel(x, ++c, address);
		}
	}
}
