package tomasulo;

import java.util.ArrayList;

import instructionSetArchitecture.Instruction;
import instructionSetArchitecture.Register;

public class FunctionalUnit {

	private String name;
	private int numberOfInstances;
	private int execTime;
	private int unitCount;
	private int[] start;
	private boolean[] busy;
	private Instruction[] Op;
	private Register[] Vj;
	private Register[] Vk;
	private FunctionalUnit[] Qj;
	private FunctionalUnit[] Qk;
	private FunctionalUnit[] dest;
	private int[] A;
	private ArrayList<Instruction> writeResult; //need to be modified with ROB number

	public FunctionalUnit(String name, int numberOfInstances, int execTime) {
		this.name = name;
		this.numberOfInstances = numberOfInstances;
		this.execTime = execTime;
		this.unitCount = 0;
		this.start = new int[numberOfInstances];
		for (int i = 0; i < numberOfInstances; i++)
			start[i] = -1;
		this.busy = new boolean[numberOfInstances];
		this.Op = new Instruction[numberOfInstances];
		this.Vj = new Register[numberOfInstances];
		this.Vk = new Register[numberOfInstances];
		this.Qj = new FunctionalUnit[numberOfInstances];
		this.Qk = new FunctionalUnit[numberOfInstances];
		this.A = new int[numberOfInstances];
		this.writeResult = new ArrayList<Instruction>();
	}

	public boolean isFull() {
		if (unitCount == numberOfInstances)
			return true;
		return false;
	}

	public boolean isEmpty() {
		if (unitCount == 0)
			return true;
		return false;
	}
	
	public boolean hasOutput() {
		if(writeResult.size() == 0)
			return false;
		return true;
	}

	public boolean issue(Instruction instruction) {
		if (isFull())
			return false;
		busy[unitCount] = true;
		Op[unitCount] = instruction;
		Qj[unitCount] = Vj[unitCount].getUnitUsing();
		Qk[unitCount] = Vk[unitCount].getUnitUsing();
		// instruction.getDest().setUnitUsing(this);
		dest[unitCount] = this;
		A[unitCount] = 0;// instruction.getImm();
		unitCount++;
		return true;
	}

	public void executeNewInstruction(int clockCycle, int unit) {
		// if(instruction.getVj().getUnitUsing() != null ||
		// instruction.getVk().getUnitUsing() != null )
		// return;
		start[unit] = clockCycle;
		Vj[unit] = null;// instruction.getVj();
		Vk[unit] = null;// instruction.getVk();
		Qj[unit] = null;
		Qk[unit] = null;
	}

	public void write(int i) {
		start[i] = -1;
		busy[unitCount] = false;
		// Op[unitCount].excute();
		// Op[i].getDest().setUnitUsing(null);
		Op[i] = null;
		Vj[i] = null;
		Vk[i] = null;
		dest[i] = null;
		A[i] = 0;
		unitCount--;
	}

	public void executeExistingInstruction(int clockCycle) {
		if (isEmpty())
			return;
		for (int i = 0; i < unitCount; i++) {
			int cyclesLeft = start[i] + execTime - clockCycle;
			if(start[i] == -1)
				executeNewInstruction(clockCycle, i);
			else if(cyclesLeft == 0)
				write(i);
		}
	}

}
