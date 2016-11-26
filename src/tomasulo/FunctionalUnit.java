package tomasulo;

import java.util.ArrayList;

import instructionSetArchitecture.Instruction;
import instructionSetArchitecture.Register;

public class FunctionalUnit {

	private String name;
	private int numberOfInstances;
	private int[] execTime;
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
	private ArrayList<Instruction> writeResult; // need to be modified with ROB
												// number

	public FunctionalUnit(String name, int numberOfInstances, int execTime) {
		this.name = name;
		this.numberOfInstances = numberOfInstances;
		this.execTime = new int[numberOfInstances];
		for (int i = 0; i < numberOfInstances; i++)
			this.execTime[i] = execTime;
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
	
	public String getName() {
		return name;
	}

	public boolean hasOutput() {
		if (writeResult.size() == 0)
			return false;
		return true;
	}
	
	public int resultSize(){
		return writeResult.size();
	}
	public Instruction extractWriteResult(int i) {
		Instruction y = writeResult.get(i);
		writeResult.remove(i);
		return y;
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


	public boolean issue(Instruction instruction) {
		if (isFull())
			return false;
		busy[unitCount] = true;
		Op[unitCount] = instruction;
		Qj[unitCount] = Vj[unitCount].getUnitUsing();
		Qk[unitCount] = Vk[unitCount].getUnitUsing();
		if (instruction.getDestination() != null)
			instruction.getDestination().setUnitUsing(this);
		dest[unitCount] = this;
		A[unitCount] = instruction.getImm();
		unitCount++;
		return true;
	}

	public void executeNewInstruction(int clockCycle, int unit) {
		if (Op[unit].getVj().getUnitUsing() == null
				|| (Op[unit].getVk() != null && Op[unit].getVk().getUnitUsing() == null))
			return;
		Op[unit].execute();
		if (name.equals("LOAD") || name.equals("STORE"))
			execTime[unit] = Op[unit].getAccessTime();
		start[unit] = clockCycle;
		Vj[unit] = Op[unit].getVj();
		if (Op[unit].getVk() != null)
			Vk[unit] = Op[unit].getVk();
		Qj[unit] = null;
		Qk[unit] = null;
	}

	public void write(int i) {
		writeResult.add(Op[i]);
		start[i] = -1;
		busy[unitCount] = false;
		Op[i].getDestination().setUnitUsing(null);
		Op[i] = null;
		Vj[i] = null;
		Vk[i] = null;
		dest[i] = null;
		A[i] = 0;
		unitCount--;
	}

	public void execute(int clockCycle) {
		if (isEmpty())
			return;
		for (int i = 0; i < unitCount; i++) {
			int cyclesLeft = start[i] + execTime[i] - clockCycle;
			if (start[i] == -1)
				executeNewInstruction(clockCycle, i);
			else if (cyclesLeft == 0)
				write(i);
		}
	}

}
