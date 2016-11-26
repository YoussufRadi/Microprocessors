package tomasulo;

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
}
