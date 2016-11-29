package instructionSetArchitecture;

public class Register {

	private String name;
	private int value;
	private int ROBEnteryUsing;

	public Register(String name) {
		this.name = name;
		this.value = 0;
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
		if (!this.name.equals("R0"))
			this.value = value;
	}

	public int getROBEnteryUsing() {
		return ROBEnteryUsing;
	}

	public void setROBEnteryUsing(int ROBEnteryUsing) {
		this.ROBEnteryUsing = ROBEnteryUsing;
	}

}
