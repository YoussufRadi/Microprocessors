package memoryHierarchy;

import java.security.acl.LastOwnerException;

import exceptions.CacheMissException;

public class MemoryHierarchy {

	private Memory mainMemory;
	private Cache[] caches;
	private int[] cacheMisses;
	private int[] cacheAccesses;
	private int totalAccessTime;
	private int latestAccessTime;
	private int access;
	
	public int getAccess() {
		return access;
	}

	public int[] getCacheAccesses() {
		return cacheAccesses;
	}

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
		this.cacheMisses = new int[caches.length];
		this.cacheAccesses = new int[caches.length];
		access = 0;
	}

	public Word fetch(int address) throws RuntimeException {
		access++;
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		int offset = (int) (address % Math.pow(2, offsetBits));
		latestAccessTime = 0;
		Word[] x = null;
		for (int i = 0; i < caches.length; i++) {
			latestAccessTime += caches[i].getAccessTime();

			int index = (int) (address / Math.pow(2, offsetBits))
					% caches[i].getSets().length;
			int indexBits = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
					indexBits));
			this.cacheAccesses[i]++;
			try {
				x = caches[i].fetch(index, tag);
				int c = i;
				writeInUpperLevel(x, --c, address);
				totalAccessTime += latestAccessTime;
				return x[offset];
			} catch (CacheMissException exp) {
				this.cacheMisses[i]++;
				continue;
			} catch (Exception e) {
				continue;
			}

		}
		latestAccessTime += mainMemory.getAccessTime();
		x = mainMemory.fetch(address, offset, caches[0].getLineSize());
		writeInUpperLevel(x, caches.length - 1, address);
		totalAccessTime += latestAccessTime;
		return x[offset];
	}

	public int[] getCacheMisses() {
		return cacheMisses;
	}

	private void writeInUpperLevel(Word[] bytes, int j, int address) {
		if (j < 0)
			return;
		latestAccessTime += caches[j].getAccessTime();
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
		access++;
		latestAccessTime = 0;
		CacheBlock x = null;
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		int offset = (int) (address % Math.pow(2, offsetBits));
		for (int i = 0; i < caches.length; i++) {

			latestAccessTime += caches[i].getAccessTime();
			int index = (int) (address / Math.pow(2, offsetBits))
					% caches[i].getSets().length;
			int indexBits = (int) (Math.log(caches[i].getNumberOfLines()
					/ caches[i].getmWays()) / Math.log(2));
			int tag = (int) (address / Math.pow(2, offsetBits) / Math.pow(2,
					indexBits));
			Word dataCloned = (Word) data.clone();
			this.cacheAccesses[i]++;
			try {
				x = caches[i].writeByte(dataCloned, index, tag, offset);

				if (x != null) {
					int c = i;
					if (caches[i].getPolicy() != WritingPolicy.WRITE_BACK)
						writeInLowerLevel(x, ++c, address);

					Word[] y = new Word[x.getData().length];
					for (int z = 0; z < y.length; z++)
						if (x.getData()[z] != null)
							y[z] = (Word) x.getData()[z].clone();
					c = i;
					writeInUpperLevel(x.getData(), --c, address);
					totalAccessTime += latestAccessTime;
					return;
				}
			} catch (CacheMissException exp) {
				this.cacheMisses[i]++;
				continue;
			}
		}
		if (x == null) {
			latestAccessTime += mainMemory.getAccessTime();
			Word[] y = mainMemory.writeByte(data, address, offset,
					caches[0].getLineSize());
			writeInUpperLevel(y, caches.length - 1, address);
			totalAccessTime += latestAccessTime;
		}
	}

	private void writeInLowerLevel(CacheBlock x, int i, int address) {
		CacheBlock x1 = null;
		Word[] y = new Word[x.getData().length];
		int offsetBits = (int) (Math.log(caches[0].getLineSize()) / Math.log(2));
		int offset = (int) (address % Math.pow(2, offsetBits));
		for (int z = 0; z < y.length; z++)
			if (x.getData()[z] != null)
				y[z] = (Word) x.getData()[z].clone();
		if (i >= caches.length - 1) {

			latestAccessTime += mainMemory.getAccessTime();

			mainMemory.write(y, address, offset);
			return;
		} else if (caches[i - 1].getPolicy()
				.equals(WritingPolicy.WRITE_THROUGH)) {

			latestAccessTime += caches[i].getAccessTime();

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
