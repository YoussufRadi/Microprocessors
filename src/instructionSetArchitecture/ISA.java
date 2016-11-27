package instructionSetArchitecture;

public class ISA {

	private Register[] registers;
	private Register PC;

	public ISA() {
		registers = new Register[8];
		for(int i = 0; i < 8; i++)
			registers[i] = new Register("R"+i);
		PC = new Register("PC");
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
