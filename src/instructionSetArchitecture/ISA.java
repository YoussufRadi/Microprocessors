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

	public int getPC() {
		return PC.getValue();
	}

	public void setPC(int PC_value) {
		PC.setValue(PC_value);
	}

}
