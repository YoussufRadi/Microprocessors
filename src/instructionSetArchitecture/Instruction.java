package instructionSetArchitecture;

import memoryHierarchy.Word;
import tomasulo.Simulator;

public class Instruction {

	private String type;
	private String regA;
	private String regB;
	private String regC;
	private String imm;
	private int accessTime;
	private int destAddress;

	public String getType() {
		return type;
	}

	public Instruction(String instruction) {

		String[] fields = instruction.split(" ");
		this.type = fields[0];
		this.regA = fields[1];
		if (fields[0].equals("LW") || fields[0].equals("SW")
				|| fields[0].equals("BEQ") || fields[0].equals("ADDI")) {
			this.regB = fields[2];
			this.imm = fields[3];
		}

		if (fields[0].equals("ADD") || fields[0].equals("SUB")
				|| fields[0].equals("NAND") || fields[0].equals("MUL")) {
			this.regB = fields[2];
			this.regC = fields[3];
		}

		if (fields[0].equals("JMP")) {
			this.imm = fields[2];
		}

		if (fields[0].equals("JALR")) {
			this.regB = fields[2];
		}

	}
	public void updatePC() {
		switch (this.type) {
		case "JMP":
			jmpUpdate(this.regA, this.imm);
			break;
		case "BEQ":
			beqUpdate(this.regA, this.regB, this.imm);
			break;
		case "JALR":
			jalrUpdate(this.regA, this.regB);
			break;
		case "RET":
			retUpdate(this.regA);
			break;
		default: Simulator.ISA_regs.setPC(Simulator.ISA_regs.getPC()+1);
		}
	}

	public void execute() {
		switch (this.type) {
		case "LW":
			lw(this.regA, this.regB, this.imm);
			break;
		case "SW":
			sw(this.regA, this.regB, this.imm);
			break;
		case "JMP":
			jmp(this.regA, this.imm);
			break;
		case "BEQ":
			beq(this.regA, this.regB, this.imm);
			break;
		case "JALR":
			jalr(this.regA, this.regB);
			break;
		case "RET":
			ret(this.regA);
			break;
		case "ADD":
			add(this.regA, this.regB, this.regC);
			break;
		case "SUB":
			sub(this.regA, this.regB, this.regC);
			break;
		case "ADDI":
			addImm(this.regA, this.regB, this.imm);
			break;
		case "NAND":
			nand(this.regA, this.regB, this.regC);
			break;
		case "MUL":
			mul(this.regA, this.regB, this.regC);
			break;
		}
	}

	public void lw(String rA_str, String rB_str, String imm_str) {
		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int immediate = Integer.parseInt(imm_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int address = immediate + rB_value;

		String word = Simulator.dataMemory.fetch(address).getData();
		int value = Integer.parseInt(word);
		Simulator.ISA_regs.writeReg(rA_index, value);

		this.accessTime = Simulator.dataMemory.getLatestAccessTime();
	}

	public void sw(String rA_str, String rB_str, String imm_str) {
		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int immediate = Integer.parseInt(imm_str);

		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int address = immediate + rB_value;

		String word = "" + rA_value;
		Simulator.dataMemory.write(new Word(word), address);

		this.accessTime = Simulator.dataMemory.getLatestAccessTime();
		this.destAddress = address;
	}

	public void add(String rA_str, String rB_str, String rC_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int rC_index = getRegIndex(rC_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int rC_value = Simulator.ISA_regs.readReg(rC_index);
		int value = rB_value + rC_value;
		Simulator.ISA_regs.writeReg(rA_index, value);
	}

	public void sub(String rA_str, String rB_str, String rC_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int rC_index = getRegIndex(rC_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int rC_value = Simulator.ISA_regs.readReg(rC_index);
		int value = rB_value - rC_value;

		Simulator.ISA_regs.writeReg(rA_index, value);
	}

	public void nand(String rA_str, String rB_str, String rC_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int rC_index = getRegIndex(rC_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int rC_value = Simulator.ISA_regs.readReg(rC_index);
		int value = ~(rB_value & rC_value);

		Simulator.ISA_regs.writeReg(rA_index, value);
	}

	public void mul(String rA_str, String rB_str, String rC_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int rC_index = getRegIndex(rC_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int rC_value = Simulator.ISA_regs.readReg(rC_index);
		int value = rB_value * rC_value;

		Simulator.ISA_regs.writeReg(rA_index, value);
	}

	public void addImm(String rA_str, String rB_str, String imm_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int immediate = Integer.parseInt(imm_str);

		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int value = rB_value + immediate;

		Simulator.ISA_regs.writeReg(rA_index, value);
	}

	public void jmp(String rA_str, String imm_str) {

		int rA_index = getRegIndex(rA_str);
		int immediate = Integer.parseInt(imm_str);

		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		int pc = Simulator.ISA_regs.getPC();
		int address = pc + 1 + rA_value + immediate;

//		Simulator.ISA_regs.setPC(address);
		this.destAddress = address;
	}
	
	public void jmpUpdate(String rA_str, String imm_str) {

		int rA_index = getRegIndex(rA_str);
		int immediate = Integer.parseInt(imm_str);

		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		int pc = Simulator.ISA_regs.getPC();
		int address = pc + 1 + rA_value + immediate;

		Simulator.ISA_regs.setPC(address);
	}
	public void jalr(String rA_str, String rB_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);

		int pc = Simulator.ISA_regs.getPC();
		int rB_value = Simulator.ISA_regs.readReg(rB_index);

		Simulator.ISA_regs.writeReg(rA_index, pc + 1);
//		Simulator.ISA_regs.setPC(rB_value);
	}
	
	public void jalrUpdate(String rA_str, String rB_str) {

		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);

		int pc = Simulator.ISA_regs.getPC();
		int rB_value = Simulator.ISA_regs.readReg(rB_index);

		Simulator.ISA_regs.setPC(rB_value);
	}

	public void ret(String rA_str) {

		int rA_index = getRegIndex(rA_str);
		int rA_value = Simulator.ISA_regs.readReg(rA_index);
//		Simulator.ISA_regs.setPC(rA_value);

		this.destAddress = rA_value;
	}
	
	public void retUpdate(String rA_str) {

		int rA_index = getRegIndex(rA_str);
		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		
		Simulator.ISA_regs.setPC(rA_value);
		
	}

	public void beq(String rA_str, String rB_str, String imm_str) {
		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int immediate = Integer.parseInt(imm_str);

		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int pc = Simulator.ISA_regs.getPC();
		boolean isEqual = rA_value == rB_value;

//		if ((isEqual && immediate > 0) || (!isEqual && immediate < 0)) {
//			Simulator.ISA_regs.setPC(pc + 1 + immediate);
//		}
		this.destAddress = pc + 1 + immediate;
	}
	
	public void beqUpdate(String rA_str, String rB_str, String imm_str) {
		int rA_index = getRegIndex(rA_str);
		int rB_index = getRegIndex(rB_str);
		int immediate = Integer.parseInt(imm_str);

		int rA_value = Simulator.ISA_regs.readReg(rA_index);
		int rB_value = Simulator.ISA_regs.readReg(rB_index);
		int pc = Simulator.ISA_regs.getPC();
		boolean isEqual = rA_value == rB_value;

		if ((isEqual && immediate > 0) || (!isEqual && immediate < 0)) {
			Simulator.ISA_regs.setPC(pc + 1 + immediate);
		}
		
	}

	public static int getRegIndex(String register) {
		String regNum = register.substring(1);
		int regNum_int = Integer.parseInt(regNum);
		return regNum_int;
	}

	public Object getDestination() {
		if (this.type.equals("LW") || this.type.equals("ADD")
				|| this.type.equals("SUB") || this.type.equals("NAND")
				|| this.type.equals("MUL") || this.type.equals("ADDI")
				|| this.type.equals("JALR")) {
			int rA_index = getRegIndex(this.regA);
			return Simulator.ISA_regs.getRegisters()[rA_index];
		} else
			return this.destAddress;
	}

	public Register getVj() {

		if (this.type.equals("LW") || this.type.equals("JALR")
				|| this.type.equals("ADD") || this.type.equals("SUB")
				|| this.type.equals("NAND") || this.type.equals("MUL")
				|| this.type.equals("ADDI")) {
			int rB_index = getRegIndex(this.regB);
			return Simulator.ISA_regs.getRegisters()[rB_index];
		}
		int rA_index = getRegIndex(this.regA);
		return Simulator.ISA_regs.getRegisters()[rA_index];

	}

	public Register getVk() {

		if (this.type.equals("SW") || this.type.equals("BEQ")) {
			int rB_index = getRegIndex(this.regB);
			return Simulator.ISA_regs.getRegisters()[rB_index];
		}
		if (this.type.equals("ADD") || this.type.equals("SUB")
				|| this.type.equals("NAND") || this.type.equals("MUL")) {
			int rC_index = getRegIndex(this.regC);
			return Simulator.ISA_regs.getRegisters()[rC_index];
		}
		return null;
	}

	public int getImm() {
		if(imm == null)
			return -1;
		return Integer.parseInt(imm);
	}

	public int getAccessTime() {
		return this.accessTime;
	}

	// public static void main(String[] args) {
	//String instruction = "BEQ r4 r1 80";
	//Instruction i0 = new Instruction(instruction);
	// System.out.println("Type: " + i0.type);
	// System.out.println("regA: " + i0.regA);
	// System.out.println("regB: " + i0.regB);
	// System.out.println("regC: " + i0.regC);
	// System.out.println("imm: " + i0.imm);
	// System.out.println(getRegIndex(i0.regB));
	// }

}
