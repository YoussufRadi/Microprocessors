package memoryHierarchy;

public class test {

	public static void main(String[] args) {
		
		 Cache c = new Cache(8, 2, 1, WritingPolicy.WRITE_THROUGH, 5);
//		 System.out.println(c.getLineSize());
//		 System.out.println(c.getSets().length);
//		 System.out.println(c.getSets()[0].getBlocks().size());
		 
		 MemoryHierarchy main = new MemoryHierarchy( new Cache[] {c}, 50);
//		 System.out.println(main.getCaches().length);
		 main.write(new Byte("Hi"), 4000);
//		 System.out.println(main.getMainMemory().getData()[4000].getData());
		 System.out.println(c.getSets()[0].getBlocks().get(0).getData()[0].getData());
//		 for(int i=0; i<c.getSets().length;i++){
//			 if(c.getSets()[i].getBlocks().size() != 0 && c.getSets()[i].getBlocks().get(0).getData() != null && c.getSets()[i].getBlocks().get(0).getData()[0] != null ){
//			 System.out.println(c.getSets()[i].getBlocks().get(0).getData()[0].getData());
//			 System.out.println(c.getSets()[i].getBlocks().get(0).getData()[1].getData());
//			 }
//		 }
//		 System.out.println(main.fetch(4000).getData());
	}

}
