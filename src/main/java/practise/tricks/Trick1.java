package practise.tricks;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Trick1 {


		 static int a = 1111;
		 static
		 {
	//		 System.out.println("static");
		        a = a-- - --a;
		 }

		{
			System.out.println("normal");
		        a = a++ + ++a;
		}

		 public static void main(String[] args)  {
	//		 Trick1 obj= new Trick1();
	//	       System.out.println(a);
		    URL url;
			try {
				url = new URL("https://wordpress.org/plugins/about/readme.txt");
				InputStream is = url.openStream();
				System.out.println(is.read());
//				String text = new Scanner( url.openStream() ).useDelimiter("\\A").next();
//				System.out.println("Abc is "+text);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    }

		}

