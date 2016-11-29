package tomasulo;

import memoryHierarchy.Word;
import instructionSetArchitecture.Instruction;
import instructionSetArchitecture.Register;

public class ROB {
	private ROBEntry[] entry;
	private int[] oldValue;
	private int head;
	private int tail;

	public ROB(int size) {
		entry = new ROBEntry[size];
		oldValue = new int[size];
		head = 0;
		tail = 0;
	}

	public boolean isFull() {
		if (head == tail)
			if (entry[tail] != null && !entry[tail].isReady())
				return true;
		return false;
	}

	public boolean isEmpty() {
		if (head == tail)
			if (entry[tail] == null || entry[tail].isReady())
				return true;
		return false;
	}

	public int getInstructionToCommit() {
		return head;
	}

	public void flushAll() {
		while (!isEmpty()) {
			Instruction ins = entry[head].getInstruction();
			if (ins.getDestination() instanceof Register)
				((Register) ins.getDestination()).setValue(oldValue[head]);
			else if (ins.getType() == "SW")
				if (oldValue[head] != -1)
					Simulator.dataMemory.write(new Word(oldValue[head] + ""),
							(int) ins.getDestination());
				else
					Simulator.dataMemory.write(new Word(),
							(int) ins.getDestination());
			else if (ins.getType().equals("BEQ"))
				Simulator.ISA_regs.setPC(oldValue[head]);
			else
				Simulator.ISA_regs.setPC(entry[head].getInstruction().fetchPC);
			entry[head].setReady(true);
			head++;
			if (head == entry.length)
				head = 0;
		}
		entry = new ROBEntry[entry.length];
		head = 0;
		tail = 0;
	}

	public int issue(Instruction instruction) {
		int entryNum = tail;
		if (isFull())
			return -1;
		entry[tail] = new ROBEntry(instruction);
		if (instruction.getDestination() instanceof Register)
			oldValue[tail] = ((Register) instruction.getDestination())
					.getValue();
		else {
			if (instruction.getType().equals("SW")) {
				Word x = Simulator.dataMemory.fetch((int) instruction
						.getDestination());
				if (x != null)
					oldValue[tail] = Integer.parseInt(x.getData());
				else
					oldValue[tail] = -1;
			} else
				oldValue[tail] = (int) instruction.getDestination();
			// handling PC Left Still

		}
		tail++;
		if (tail == entry.length)
			tail = 0;
		return entryNum;
	}

	public boolean commit() {
		if (isEmpty())
			return false;
		System.out.print("C \t");
		if (entry[head].getInstruction().isMissPridiction()){
			Simulator.MissPredictionsBranches++;
			Simulator.save = true;
		}
		else
			Simulator.save = false;
		if (entry[head].getInstruction().getType().equals("BEQ")
				&& entry[head].getInstruction().isMissPridiction()) {
//			System.out.println("Hey There I am miss pridiction");
			flushAll();
			return true;
		}
		entry[head].setReady(true);
		Simulator.RS.freeReservedUnit(head);
		head++;
		if (head == entry.length)
			head = 0;
		return true;
	}
}
