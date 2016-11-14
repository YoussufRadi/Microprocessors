package components;

public class test {
	
	public static void main(String[] args) {
		Cache c = new Cache(8, 3, 2);
		System.out.println(c.size);
		System.out.println(c.sets.length);
		System.out.println(c.sets[0].size);
		System.out.println(c.sets[0].blocks.length);
		System.out.println(c.sets[0].blocks[0].size);
	}

}
