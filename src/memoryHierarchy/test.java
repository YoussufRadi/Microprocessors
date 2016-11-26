package memoryHierarchy;

public class test {

	public static void main(String[] args) throws CloneNotSupportedException {
		
		 Cache c = new Cache(2, 2, 1, WritingPolicy.WRITE_THROUGH, 5);
		 Cache c1 = new Cache(4, 2, 1, WritingPolicy.WRITE_BACK, 5);
		 Cache c2 = new Cache(8, 2, 1, WritingPolicy.WRITE_BACK, 5);
//		 System.out.println(c.getLineSize());
//		 System.out.println(c.getSets().length);
//		 System.out.println(c.getSets()[0].getBlocks().size());
		 
		 MemoryHierarchy main = new MemoryHierarchy( new Cache[] {c, c1 ,c2}, 50);
//		 System.out.println(main.getCaches().length);
		 main.write(new Word("Hi"), 4000);
		 main.write(new Word("Bye"), 4000);
//		 System.out.println(main.getMainMemory().getData()[4000].getData());
		 System.out.println(c.getSets()[0].getBlocks().get(0).getData()[0].getData());
		 System.out.println(c1.getSets()[0].getBlocks().get(0).getData()[0].getData());
		 System.out.println(c2.getSets()[0].getBlocks().get(0).getData()[0].getData());
//		 for(int i=0; i<c.getSets().length;i++){
//			 if(c.getSets()[i].getBlocks().size() != 0 && c.getSets()[i].getBlocks().get(0).getData() != null && c.getSets()[i].getBlocks().get(0).getData()[0] != null ){
//			 System.out.println(c.getSets()[i].getBlocks().get(0).getData()[0].getData());
//			 System.out.println(c.getSets()[i].getBlocks().get(0).getData()[1].getData());
//			 }
//		 }
		 System.out.println(main.getMainMemory().getData()[4000].getData());
		 System.out.println(main.fetch(4000).getData() + "  Normal Fetching from cache first");
	}

}
