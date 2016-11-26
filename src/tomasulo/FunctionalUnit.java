package tomasulo;

public class FunctionalUnit {

	private int numberOfInstances;
	private int[] start;
	private int execTime;
	private int unitCount;
	
   public FunctionalUnit(int numberOfInstances, int execTime) {
	   this.numberOfInstances = numberOfInstances;
	   this.execTime = execTime;
}
}
