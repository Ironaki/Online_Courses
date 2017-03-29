public class NBody
{
	public static double readRadius(String dir)
	{
		In in = new In(dir);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String dir)
	{
		In in = new In(dir);
		in.readInt();
		in.readDouble();
		
		Planet[] planets = new Planet[5];

		for(int i = 0; i < 5; i ++)
		{
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();

			Planet p = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
			planets[i] = p;
		}

		return planets;
	}

}
