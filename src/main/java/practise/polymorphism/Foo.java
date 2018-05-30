package practise.polymorphism;

import java.io.IOException;

public class Foo {

	Foo() {
		System.out.println("Foo");
	}
	public void display() throws Exception{
		System.out.println("Foo class");
		throw new Exception();
	}

	public static void main(String[] args) {
		Foo b = new Bar();
		try {
			b.display();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Bar extends Foo {
	Bar() {
		System.out.println("Bar");
	}
	public  void display() throws IOException{
		try {
			super.display();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Bar class");
	//	throw new IOException();
	}


}
