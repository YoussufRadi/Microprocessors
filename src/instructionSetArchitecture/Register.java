package instructionSetArchitecture;

import tomasulo.FunctionalUnit;

public class Register {

	private int value;
	private FunctionalUnit unitUsing;
	
	public Register() {
		this.value = 0;
	}

	public Register(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public FunctionalUnit getUnitUsing() {
		return unitUsing;
	}

	public void setUnitUsing(FunctionalUnit unitUsing) {
		this.unitUsing = unitUsing;
	}

}
