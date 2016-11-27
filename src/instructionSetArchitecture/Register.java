package instructionSetArchitecture;

public class Register {

	private String name;
	private int value;
	private int ROBEnteryUsing;
	
	public Register(String name) {
		this.value = 3;
		ROBEnteryUsing = -1;
	}

	public String getName() {
		return name;
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
