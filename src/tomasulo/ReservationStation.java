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
	private ArrayList<Instruction> dataToCommit;

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
	}

	public boolean issue(Instruction instruction) {
		boolean done = false;
		switch (instruction.getType()) {
		case "LW":
			done = load.issue(instruction);
			break;
		case "SW":
			done = store.issue(instruction);
			break;
		case "JMP":
			done = jmp.issue(instruction);
			break;
		case "BEQ":
			done = beq.issue(instruction);
			break;
		case "JALR":
			done = jalr.issue(instruction);
			break;
		case "RET":
			done = ret.issue(instruction);
			break;
		case "ADD":
			done = add.issue(instruction);
			break;
		case "SUB":
			done = sub.issue(instruction);
			break;
		case "ADDI":
			done = addI.issue(instruction);
			break;
		case "NAND":
			done = nand.issue(instruction);
			break;
		case "MUL":
			done = mul.issue(instruction);
			break;
		}
		return done;
	}

	public void execute(int clockCycle) {
		for (int i = 0; i < allStations.length; i++)
			allStations[i].execute(clockCycle);
	}

	public void write() {
		for (int i = 0; i < allStations.length; i++)
			if (allStations[i].hasOutput())
				for (int j = 0; j < allStations[i].resultSize(); j++)
					dataToCommit.add(allStations[i].extractWriteResult(j) );
	}
}
