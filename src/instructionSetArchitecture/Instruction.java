package instructionSetArchitecture;

public class Instruction {

	private String type;
	private String regA;
	private String regB;
	private String regC;
	private String imm;

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
				|| fields[0].equals("NAND") || fields[0].equals("MUL")){
			this.regB = fields[2];
			this.regC = fields[3];
		}
		
		if (fields[0].equals("JMP")){
			this.imm = fields[2];
		}
		
		if (fields[0].equals("JALR")){
			this.regB = fields[2]; 
		}
		
	}
	
	public static void main(String[] args) {
		String instruction = "BEQ r0 r1 80";
		Instruction i0 = new Instruction(instruction);
		System.out.println("Type: "+i0.type);
		System.out.println("regA: "+i0.regA);
		System.out.println("regB: "+i0.regB);
		System.out.println("regC: "+i0.regC);
		System.out.println("imm: "+i0.imm);
	}

}
