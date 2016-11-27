package tomasulo;

import java.util.ArrayList;

import instructionSetArchitecture.Instruction;

public class ReservationStation {

	private FunctionalUnit load;
	private FunctionalUnit store;
	private FunctionalUnit jmp;
	private FunctionalUnit beq;
	private FunctionalUnit jalr;
	private FunctionalUnit ret;
	private FunctionalUnit add;
	private FunctionalUnit sub;
	private FunctionalUnit addI;
	private FunctionalUnit nand;
	private FunctionalUnit mul;
	private FunctionalUnit[] allStations = { load, store, jmp, beq, jalr, ret,
			add, sub, addI, nand, mul };
	private ArrayList<Integer> dataToCommit;

	public ReservationStation(int loadNum, int loadTime, int storeNum,
			int storeTime, int jmpNum, int jmpTime, int beqNum, int beqTime,
			int jalrNum, int jalrTime, int retNum, int retTime, int addNum,
			int addTime, int subNum, int subTime, int addINum, int addITime,
			int nandNum, int nandTime, int mulNum, int mulTime) {
		load = new FunctionalUnit("LOAD", loadNum, loadTime);
		store = new FunctionalUnit("STORE", storeNum, storeTime);
		jmp = new FunctionalUnit("JMP", jmpNum, jmpTime);
		beq = new FunctionalUnit("BEQ", beqNum, beqTime);
		jalr = new FunctionalUnit("JALR", jalrNum, jalrTime);
		ret = new FunctionalUnit("RET", retNum, retTime);
		add = new FunctionalUnit("ADD", addNum, addTime);
		sub = new FunctionalUnit("SUB", subNum, subTime);
		addI = new FunctionalUnit("ADDI", addINum, addITime);
		nand = new FunctionalUnit("NAND", nandNum, nandTime);
		mul = new FunctionalUnit("MUL", mulNum, mulTime);
		dataToCommit = new ArrayList<Integer>();
	}

	public boolean issue(Instruction instruction, int ROBEntryNumber) {
		boolean done = false;
		switch (instruction.getType()) {
		case "LW":
			done = load.issue(instruction, ROBEntryNumber);
			break;
		case "SW":
			done = store.issue(instruction, ROBEntryNumber);
			break;
		case "JMP":
			done = jmp.issue(instruction, ROBEntryNumber);
			break;
		case "BEQ":
			done = beq.issue(instruction, ROBEntryNumber);
			break;
		case "JALR":
			done = jalr.issue(instruction, ROBEntryNumber);
			break;
		case "RET":
			done = ret.issue(instruction, ROBEntryNumber);
			break;
		case "ADD":
			done = add.issue(instruction, ROBEntryNumber);
			break;
		case "SUB":
			done = sub.issue(instruction, ROBEntryNumber);
			break;
		case "ADDI":
			done = addI.issue(instruction, ROBEntryNumber);
			break;
		case "NAND":
			done = nand.issue(instruction, ROBEntryNumber);
			break;
		case "MUL":
			done = mul.issue(instruction, ROBEntryNumber);
			break;
		}
		return done;
	}

	public void execute(int clockCycle) {
		for (int i = 0; i < allStations.length; i++)
			allStations[i].execute(clockCycle);
	}

	public void getRsultsFromWrite() {
		for (int i = 0; i < allStations.length; i++)
			if (allStations[i].hasOutput())
				for (int j = 0; j < allStations[i].resultSize(); j++)
					dataToCommit.add(allStations[i].extractWriteResult());
	}

	public boolean hasDataToCommit() {
		return dataToCommit.size() > 0;
	}

	public int extractDataToCommit() {
		return dataToCommit.remove(0);
	}

}
