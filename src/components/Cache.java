package components;

import exceptions.CacheMissException;
import exceptions.StructuralException;

public class Cache {
	
	int size;
	int setSize;
	int lineSize;
	CacheSet [] sets;
	
	public Cache(int size, int lineSize, int setSize){
		this.size = size;
		this.lineSize = lineSize;
		this.setSize = setSize;
		this.sets = new CacheSet[size/setSize];	// need to handle division to result in ceiling
		for (int i = 0; i < sets.length; i++)
			sets[i] = new CacheSet(setSize, lineSize);
	}
	
	public CacheBlock fetch(int index, int tag) throws Exception{
		if(index > this.sets.length){
			throw new StructuralException("Out of bounds");
		}
		else{
			CacheSet selectedSet = this.sets[index];
			for(int i = 0; i < this.setSize; i++){
				if(selectedSet.blocks[i].tag == tag) return selectedSet.blocks[i];
			}
			throw new CacheMissException("Miss");
		}
	}
	
}
