package tomasulo;

import memoryHierarchy.Cache;
import memoryHierarchy.MemoryHierarchy;
import memoryHierarchy.WritingPolicy;
import instructionSetArchitecture.ISA;

public class Simulator {
	public static ISA ISA_regs;
	public static MemoryHierarchy dataMemory;
	public static MemoryHierarchy instructionMemory;
	public static ROB ROB;
	public static ReservationStation RS;
	public static Tomasulo algorithm;
	public static int clockCycle = 0;
	public static boolean run = true;

	public Simulator(int loadNum, int loadTime, int storeNum, int storeTime,
			int jmpNum, int jmpTime, int beqNum, int beqTime, int jalrNum,
			int jalrTime, int retNum, int retTime, int addNum, int addTime,
			int subNum, int subTime, int addINum, int addITime, int nandNum,
			int nandTime, int mulNum, int mulTime, Cache[] instructionCaches,
			Cache[] dataCaches, int memoryAccessTime, int ROBSize,
			int numberOfWays, int sizeBuffer) {

		ISA_regs = new ISA();
		dataMemory = new MemoryHierarchy(dataCaches, memoryAccessTime);
		instructionMemory = new MemoryHierarchy(instructionCaches,
				memoryAccessTime);
		ROB = new ROB(ROBSize);
		RS = new ReservationStation(loadNum, loadTime, storeNum, storeTime,
				jmpNum, jmpTime, beqNum, beqTime, jalrNum, jalrTime, retNum,
				retTime, addNum, addTime, subNum, subTime, addINum, addITime,
				nandNum, nandTime, mulNum, mulTime);
		algorithm = new Tomasulo(numberOfWays, sizeBuffer);
		run();
	}

	private void run() {
		while (run || !ROB.isEmpty()) {
			algorithm.fetch();
			algorithm.issue(clockCycle);
			algorithm.execute(clockCycle);
			algorithm.write(clockCycle);
			algorithm.commit(clockCycle);
			clockCycle++;
		}
	}

	public static void main(String[] args) {
		Cache c = new Cache(2, 2, 1, WritingPolicy.WRITE_THROUGH, 5);
		Cache c1 = new Cache(4, 2, 1, WritingPolicy.WRITE_BACK, 5);
		Cache c2 = new Cache(8, 2, 1, WritingPolicy.WRITE_BACK, 5);
		Cache[] dCach = { c, c1 };
		Cache[] iCach = { c2 };
		new Simulator(1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10,
				1, 10, 1, 10, 1, 10, iCach, dCach, 50, 10, 3, 10);

	}
}
