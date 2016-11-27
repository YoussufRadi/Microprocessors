package instructionSetArchitecture;

public class Register {

	private int value;
	private int ROBEnteryUsing;
	
	public Register() {
		this.value = 0;
		ROBEnteryUsing = -1;
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

	public int getROBEnteryUsing() {
		return ROBEnteryUsing;
	}

	public void setROBEnteryUsing(int ROBEnteryUsing) {
		this.ROBEnteryUsing = ROBEnteryUsing;
	}

}
