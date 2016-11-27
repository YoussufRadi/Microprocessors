package tomasulo;

import instructionSetArchitecture.Instruction;

public class ROBEntry {
	private Instruction instruction;
	private Object dest;
	private int value;
	private boolean ready;
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public Object getDest() {
		return dest;
	}

	public int getValue() {
		return value;
	}

	public ROBEntry(Instruction instruction) {
		this.instruction = instruction;
		this.dest = instruction.getDestination();
		value = 0;
		ready = false;
	}
}
