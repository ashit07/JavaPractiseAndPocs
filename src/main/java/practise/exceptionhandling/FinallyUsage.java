package practise.exceptionhandling;

public class FinallyUsage {

	public int sum(int a, int b) throws Exception{
		if(a < 0 || b < 0) {
			throw new Exception("Not allowed");
		}
		return a+b;
	}

	public void calculate (int a, int b) throws Exception{

		try {
			sum (a,b);
			throw new Exception();
	//		int v = a/0;
		} finally {
			System.out.println("Finally block");
		}
	}

	public static void main (String[] args){
		FinallyUsage fu = new FinallyUsage();
		try {
			fu.calculate(3, 7);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
