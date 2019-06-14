public class SelfTest
{
	public static void main (String[] args)
	{
		Planet s = new Planet(1,0,0,0,10,"");
		Planet r = new Planet(5,-3,0,0,50,"");


		System.out.println(s.calcForceExertedBy(r));
		System.out.println(s.calcForceExertedByX(r));
	}
}