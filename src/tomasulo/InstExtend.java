package tomasulo;

import instructionSetArchitecture.Instruction;

public class InstExtend {
	
	private Instruction instruction;
	private int expectedCycle;
	
	public InstExtend(Instruction instruction) {
		
		this.instruction = instruction;
		this.expectedCycle = 0;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public int getExpectedCycle() {
		return expectedCycle;
	}

	public void setExpectedCycle(int expectedCycle) {
		this.expectedCycle = expectedCycle;
	}
	
}
