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
	private int[] Qj;
	private int[] Qk;
	private int[] dest;
	private int[] A;
	private ArrayList<Integer> writeResult; // need to be modified with ROB
												// number

	public FunctionalUnit(String name, int numberOfInstances, int execTime) {
		this.name = name;
		this.numberOfInstances = numberOfInstances;
		this.execTime = new int[numberOfInstances];
	
		this.unitCount = 0;
		this.start = new int[numberOfInstances];
		this.busy = new boolean[numberOfInstances];
		this.Op = new Instruction[numberOfInstances];
		this.Vj = new Register[numberOfInstances];
		this.Vk = new Register[numberOfInstances];
		this.Qj = new int[numberOfInstances];
		this.Qk = new int[numberOfInstances];
		this.dest = new int[numberOfInstances];
		this.A = new int[numberOfInstances];
		this.writeResult = new ArrayList<Integer>();
		
		for (int i = 0; i < numberOfInstances; i++){
			this.execTime[i] = execTime;
			start[i] = -1;
			Qj[i] = -1;
			Qk[i] = -1;
			dest[i] = -1;
		}
	}

	public String getName() {
		return name;
	}

	public boolean hasOutput() {
		if (writeResult.size() == 0)
			return false;
		return true;
	}

	public int resultSize() {
		return writeResult.size();
	}

	public int extractWriteResult(int i) {
		Integer y = writeResult.get(i);
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

	public boolean issue(Instruction instruction, int ROBEntryNumber) {
		if (isFull())
			return false;
		busy[unitCount] = true;
		Op[unitCount] = instruction;
		Qj[unitCount] = Op[unitCount].getVj().getROBEnteryUsing();
		if (Op[unitCount].getVk() != null)
			Qk[unitCount] = Op[unitCount].getVk().getROBEnteryUsing();
		if (instruction.getDestination() instanceof Register ) {
			instruction.getDestination().setROBEnteryUsing(ROBEntryNumber);
		}
		dest[unitCount] = ROBEntryNumber;
		A[unitCount] = instruction.getImm();
		unitCount++;
		return true;
	}

	public void executeNewInstruction(int clockCycle, int unit) {
		if (Qj[unit] != -1 || Qk[unit] == -1)
			return;
		Op[unit].execute();
		if (name.equals("LOAD") || name.equals("STORE"))
			execTime[unit] = Op[unit].getAccessTime();
		start[unit] = clockCycle;
		Vj[unit] = Op[unit].getVj();
		if (Op[unit].getVk() != null)
			Vk[unit] = Op[unit].getVk();
		Qj[unit] = -1;
		Qk[unit] = -1;
	}

	public void write(int i) {
		writeResult.add(dest[i]);
		start[i] = -1;
		busy[unitCount] = false;
		Op[i].getDestination().setROBEnteryUsing(-1);
		Op[i] = null;
		Vj[i] = null;
		Vk[i] = null;
		dest[i] = -1;
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
