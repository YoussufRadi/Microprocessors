package tomasulo;

import instructionSetArchitecture.Instruction;

public class ROBEntry {
	private String type;
	private Object dest;
	private int value;
	private boolean ready;
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getType() {
		return type;
	}

	public Object getDest() {
		return dest;
	}

	public int getValue() {
		return value;
	}

	public ROBEntry(Instruction instruction) {
		this.type = instruction.getType();
		this.dest = instruction.getDestination();
		value = 0;
		ready = false;
	}
}
