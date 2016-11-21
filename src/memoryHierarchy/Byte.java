package memoryHierarchy;

public class Byte implements Cloneable { // 3ashan 5ater Hazem :D

	private String data;

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Byte() {
		this.data = new String();
	}

	public Byte(String data) {
		this.data = data;
	}

	protected Byte clone() {
		Byte cloned = null;
		try {
			cloned = (Byte) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloned.data += "1";
		cloned.data = cloned.data.substring(0, data.length());
		return cloned;
	}

//	public static void main(String[] args) {
//		String x = new String("Hit");
//		Byte y = new Byte(x);
//		Byte z = null;
//		z = (Byte) y.clone();
//		z.setData(x);
//		System.out.println(z.data);
//		System.out.println(y.data);
//	}
}
