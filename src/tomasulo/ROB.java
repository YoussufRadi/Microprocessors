package tomasulo;

import instructionSetArchitecture.Instruction;

public class ROB {
	private ROBEntry[] entry;
	private int head;
	private int tail;
	
	public ROB(int size) {
		entry = new ROBEntry[size];
		head = 0;
		tail = 0;
	}
	
	public boolean isFull(){
		if(head == tail)
			if(entry[tail] != null && !entry[tail].isReady())
				return true;
		return false;
	}
	
	public boolean isEmpty(){
		if(head == tail)
			if(entry[tail] == null || entry[tail].isReady())
				return false;
		return true;
	}
	
	public int getInstructionToCommit(){
		return head;
	}
	
	public void flushAll(){
		entry = new ROBEntry[entry.length];
		head = 0;
		tail = 0;
	}
	
	public int issue(Instruction instruction){
		int entryNum = tail;
		if(isFull())
			return -1;
		entry[tail] = new ROBEntry(instruction);
		tail++;
		if(tail == entry.length)
			tail =0;
		return entryNum;
	}
	
	public boolean commit(){
		if(isEmpty())
			return false;
		entry[head].setReady(true);
		head++;
		if(head == entry.length)
			head = 0;
		return true;
	}
}
