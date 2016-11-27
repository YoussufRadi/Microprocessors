package tomasulo;

import instructionSetArchitecture.Instruction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import memoryHierarchy.Word;

public class Tomasulo {

	private ArrayList<InstExtend> waitBuffer;
	private Queue<Instruction> instructionBuffer;
	private int numberOfWays;
	private int sizeBuffer;
	private ArrayList<Integer> commitData;

	public Tomasulo(int numberOfWays, int sizeBuffer) {

		this.numberOfWays = numberOfWays;
		this.waitBuffer = new ArrayList<InstExtend>();
		this.instructionBuffer = new LinkedList<Instruction>();
		this.sizeBuffer = sizeBuffer;
		commitData = new ArrayList<Integer>();

		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));
		instructionBuffer.add(new Instruction("ADD R0 R1 R2"));

		Simulator.instructionMemory.write(new Word("ADD r0 r1 r2"), 3);
		Simulator.instructionMemory.write(new Word("ADD r3 r1 r2"), 4);
		Simulator.instructionMemory.write(new Word("SUB r4 r1 r2"), 5);
		Simulator.instructionMemory.write(new Word("ADD r5 r1 r2"), 6);
		Simulator.instructionMemory.write(new Word("ADD r6 r1 r2"), 7);
		Simulator.instructionMemory.write(new Word("SUB r7 r1 r2"), 8);

	}

	public boolean isFull() {
		return instructionBuffer.size() == sizeBuffer;
	}

	public boolean isEmpty() {
		return instructionBuffer.size() == 0;
	}

	public void fetch() {

		int currentCycle = Simulator.clockCycle;
		int largestAccessTime = 0;
		for (int i = 0; i < numberOfWays; i++) {
			int pc = Simulator.ISA_regs.getPC();
			// System.out.println(pc);
			if (this.isFull())
				return;

			if (Simulator.instructionMemory.fetch(pc) == null)
				continue;
			String word = Simulator.instructionMemory.fetch(pc).getData();
			Instruction instruction = new Instruction(word);

			if (Simulator.instructionMemory.getLatestAccessTime() > largestAccessTime)
				largestAccessTime = Simulator.instructionMemory
						.getLatestAccessTime();
			// System.out.println(instruction.getType());
			waitBuffer.add(new InstExtend(instruction));
			instruction.updatePC();
		}

		int index = waitBuffer.size() - 1;

		for (int i = 0; i < numberOfWays; i++) {
			if (index == -1)
				break;
			waitBuffer.get(index).setExpectedCycle(
					currentCycle + largestAccessTime);
			index--;
		}

		for (int i = 0; i < waitBuffer.size(); i++) {

			if (waitBuffer.get(i).getExpectedCycle() == currentCycle) {
				instructionBuffer.add(waitBuffer.remove(i).getInstruction());
			}
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
				doneFlag = Simulator.RS.issue(clockCycle,
						instructionBuffer.peek(), ROBentry);
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
