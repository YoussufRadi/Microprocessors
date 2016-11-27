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
	private int[] issueTime;
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
		this.issueTime = new int[numberOfInstances];
		this.busy = new boolean[numberOfInstances];
		this.Op = new Instruction[numberOfInstances];
		this.Vj = new Register[numberOfInstances];
		this.Vk = new Register[numberOfInstances];
		this.Qj = new int[numberOfInstances];
		this.Qk = new int[numberOfInstances];
		this.dest = new int[numberOfInstances];
		this.A = new int[numberOfInstances];
		this.writeResult = new ArrayList<Integer>();

		for (int i = 0; i < numberOfInstances; i++) {
			this.execTime[i] = execTime;
			start[i] = -1;
			issueTime[i] = -1;
			Qj[i] = -1;
			Qk[i] = -1;
			dest[i] = -1;
		}
	}

	public String getName() {
		return name;
	}

	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	public int getQj(int unit) {
		return Qj[unit];
	}

	public void clearQj(int unit) {
		Qj[unit] = -1;
	}

	public int getQk(int unit) {
		return Qk[unit];
	}

	public void clearQk(int unit) {
		Qk[unit] = -1;
	}

	public boolean hasOutput() {
		if (writeResult.size() == 0)
			return false;
		return true;
	}

	public int resultSize() {
		return writeResult.size();
	}

	public int extractWriteResult() {
		return writeResult.remove(0);
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

	public boolean issue(int clockCycle, Instruction instruction, int ROBEntryNumber) {
		if (isFull())
			return false;
		busy[unitCount] = true;
		issueTime[unitCount] = clockCycle;
		Op[unitCount] = instruction;
		Qj[unitCount] = Op[unitCount].getVj().getROBEnteryUsing();
		if (Op[unitCount].getVk() != null)
			Qk[unitCount] = Op[unitCount].getVk().getROBEnteryUsing();
		if (instruction.getDestination() instanceof Register)
			((Register) instruction.getDestination())
					.setROBEnteryUsing(ROBEntryNumber);
		dest[unitCount] = ROBEntryNumber;
		A[unitCount] = instruction.getImm();
		unitCount++;
		return true;
	}

	public void executeNewInstruction(int clockCycle, int unit) {
		if (Qj[unit] != -1 || Qk[unit] != -1)
			return;
		Op[unit].execute();
		System.out.println("write in " + ((Register) Op[unit].getDestination()).getName() + " value : " + ((Register) Op[unit].getDestination()).getValue());
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
		issueTime[i] = -1;
		busy[i] = false;
		if (Op[i].getDestination() instanceof Register)
			((Register) Op[i].getDestination()).setROBEnteryUsing(-1);
		Op[i] = null;
		Vj[i] = null;
		Vk[i] = null;
		dest[i] = -1;
		A[i] = 0;
		unitCount--;
		shift(i);
	}
	
	public void shift(int p){
		for(int i = p; i < unitCount; i++){
			execTime[i] = execTime[i+1];
			start[i] = start[i+1];
			issueTime[i] = start[i+1];
			busy[i] = busy[i+1];
			Op[i] = Op[i+1];
			Vj[i] = Vj[i+1];
			Vk[i] = Vk[i+1];
			Qj[i] = Qj[i+1];
			Qk[i] = Qk[i+1];
			dest[i] = dest[i+1];
			A[i] = A[i+1];
		}
		start[unitCount] = -1;
		issueTime[unitCount] = -1;
		busy[unitCount] = false;
		Op[unitCount] = null;
		Vj[unitCount] = null;
		Vk[unitCount] = null;
		Qj[unitCount] = -1;
		Qk[unitCount] = -1;
		dest[unitCount] = -1;
		A[unitCount] = 0;
	}
	

	public boolean execute(int clockCycle, boolean writeOnce) {
		if (isEmpty())
			return false;
		boolean ret = writeOnce;
		for (int i = 0; i < unitCount; i++) {
			int cyclesLeft = start[i] + execTime[i] - clockCycle;
			if (start[i] == -1 && issueTime[i] != clockCycle)
				executeNewInstruction(clockCycle, i);
			else if (cyclesLeft <= 0 && !ret){
				write(i);
				ret = true;
			}
		}
		return ret;
	}

}
