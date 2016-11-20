package instructionSetArchitecture;

public class ISA {

	private Register[] registers;
	private Register PC;

	public ISA() {
		registers = new Register[8];
		PC = new Register();
	}

	public Integer readReg(int index) {
		return registers[index].getValue();
	}

	public void writeReg(int index, Integer value) {
		registers[index].setValue(value);
	}

	public Register[] getRegisters() {
		return registers;
	}

	public void setRegisters(Register[] registers) {
		this.registers = registers;
	}

	public Register getPC() {
		return PC;
	}

	public void setPC(Register pC) {
		PC = pC;
	}

}
