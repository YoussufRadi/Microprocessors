package components;

public class Register {
	
	private int value;
	private String name;
	
	public Register(){
		this.value = 0;
	}
	
	public Register(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		if(!this.name.equals("R0")) this.value = value;
	}
	
	public int add(Register r){
		return r.value + this.value;
	}
	
	public int shiftLeft(int shamt){
		int result = this.value;
		for (int i = 0; i < shamt; i++)
			result *=2;
		return result;
	}
	
	public int shiftRight(int shamt){
		int result = this.value;
		for(int i = 0; i < shamt; i++)
			result /= 2;
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
	