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
	private FunctionalUnit[] allStations;
	private ArrayList<Integer> dataToCommit;

	public ReservationStation(int loadNum, int loadTime, int storeNum,
			int storeTime, int jmpNum, int jmpTime, int beqNum, int beqTime,
			int jalrNum, int jalrTime, int retNum, int retTime, int addNum,
			int addTime, int subNum, int subTime, int addINum, int addITime,
			int nandNum, int nandTime, int mulNum, int mulTime) {
		allStations = new FunctionalUnit[11];
		allStations[0] = store = new FunctionalUnit("STORE", storeNum,
				storeTime);
		allStations[1] = load = new FunctionalUnit("LOAD", loadNum, loadTime);
		allStations[2] = jmp = new FunctionalUnit("JMP", jmpNum, jmpTime);
		allStations[3] = beq = new FunctionalUnit("BEQ", beqNum, beqTime);
		allStations[4] = jalr = new FunctionalUnit("JALR", jalrNum, jalrTime);
		allStations[5] = ret = new FunctionalUnit("RET", retNum, retTime);
		allStations[6] = add = new FunctionalUnit("ADD", addNum, addTime);
		allStations[7] = sub = new FunctionalUnit("SUB", subNum, subTime);
		allStations[8] = addI = new FunctionalUnit("ADDI", addINum, addITime);
		allStations[9] = nand = new FunctionalUnit("NAND", nandNum, nandTime);
		allStations[10] = mul = new FunctionalUnit("MUL", mulNum, mulTime);
		dataToCommit = new ArrayList<Integer>();
	}

	public boolean issue(int clockCycle, Instruction instruction, int ROBEntryNumber) {
		boolean done = false;
		switch (instruction.getType()) {
		case "LW":
			done = load.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "SW":
			done = store.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "JMP":
			done = jmp.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "BEQ":
			done = beq.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "JALR":
			done = jalr.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "RET":
			done = ret.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "ADD":
			done = add.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "SUB":
			done = sub.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "ADDI":
			done = addI.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "NAND":
			done = nand.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		case "MUL":
			done = mul.issue(clockCycle, instruction, ROBEntryNumber);
			break;
		}
		return done;
	}

	public void execute(int clockCycle) {
		boolean writeOnce = false;
		for (int i = 0; i < allStations.length; i++)
			writeOnce = allStations[i].execute(clockCycle,writeOnce);
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

	public void freeReservedUnit(int ROBentry) {
		for (int i = 0; i < allStations.length; i++)
			for (int j = 0; j < allStations[i].getNumberOfInstances(); j++) {
				if (allStations[i].getQj(j) == ROBentry)
					allStations[i].clearQj(j);
				if (allStations[i].getQk(j) == ROBentry)
					allStations[i].clearQk(j);
			}
	}

}
