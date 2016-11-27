package tomasulo;

import instructionSetArchitecture.Instruction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tomasulo {

	private Queue<Instruction> instructionBuffer;
	private int numberOfWays;
	private int sizeBuffer;
	private ArrayList<Integer> commitData;

	public Tomasulo(int numberOfWays, int sizeBuffer) {
		this.numberOfWays = numberOfWays;
		this.instructionBuffer = new LinkedList<Instruction>();
		this.sizeBuffer = sizeBuffer;
		commitData = new ArrayList<Integer>();
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
	}

	public boolean isFull() {
		return instructionBuffer.size() == sizeBuffer;
	}

	public boolean isEmpty() {
		return instructionBuffer.size() == 0;
	}

	public void fetch() {
		int pc = Simulator.ISA_regs.getPC();
		for (int i = 0; i < numberOfWays; i++) {
			if (this.isFull())
				return;
			String word = Simulator.instructionMemory.fetch(pc++).getData(); // note:
																				// branch
																				// target
																				// address
																				// handling
			Instruction instruction = new Instruction(word);
			instructionBuffer.add(instruction);
		}
	}

	int ROBentry = -1;

	public void issue(int clockCycle) {

		boolean doneFlag = false;
		for (int i = 0; i < numberOfWays; i++) {
			if (isEmpty())
				return;
			if (ROBentry == -1)
				ROBentry = Simulator.ROB.issue(instructionBuffer.peek());
			if (ROBentry != -1)
				doneFlag = Simulator.RS.issue(clockCycle, instructionBuffer.peek(),
						ROBentry);
			if (doneFlag) {
				instructionBuffer.poll();
				ROBentry = -1;
			}
		}
	}

	public void execute(int clockCycle) {
		Simulator.RS.execute(clockCycle);
	}

	public void write(int clockCycle) {

	}

	public void commit(int clockCycle) {
		for (int i = 0; i < commitData.size(); i++)
			if (commitData.get(i) == Simulator.ROB.getInstructionToCommit()) {
				Simulator.ROB.commit();
				commitData.remove(i);
				break;
			}
		Simulator.RS.getRsultsFromWrite();
		if (Simulator.RS.hasDataToCommit())
			commitData.add(Simulator.RS.extractDataToCommit());
	}
}
