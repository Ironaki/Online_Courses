public class NBody
{
	public static double readRadius(String dir)
	{
		In in = new In(dir);
		int numP =in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String dir)
	{
		In in = new In(dir);
		int numP = in.readInt();
		double radius = in.readDouble();
		
		Planet[] planets = new Planet[numP];

		for(int i = 0; i < numP; i ++)
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

	public static void main(String[] args)
	{
		/* Collect All Needed Input*/
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		NBody.readRadius(filename);
		NBody.readPlanets(filename);

		/* Draw the Backgroung */
		StdDraw.setScale(-3e11,3e11);
		StdDraw.picture(0,0,"./images/starfield.jpg");

		/* Draw All the Planets*/
		Planet[] allPlanets = readPlanets(filename);
		for(Planet planet: allPlanets)
		{
			planet.draw();
		}

		/* Create an Animation! */
		for(double t= 0.0; t<T; t += dt)
		{
			double[] xForces = new double[allPlanets.length];
			double[] yForces = new double[allPlanets.length];

			for(int p = 0; p < allPlanets.length; p++)
			{
				xForces[p] = allPlanets[p].calcNetForceExertedByX(allPlanets);
			}
		
			for(int p = 0; p < allPlanets.length; p++)
			{
				yForces[p] = allPlanets[p].calcNetForceExertedByY(allPlanets);
			}

			for(int p = 0; p < allPlanets.length; p++)
			{
				allPlanets[p].update(dt, xForces[p], yForces[p]);
			}

			StdDraw.picture(0,0,"./images/starfield.jpg");

			for(Planet planet: allPlanets)
			{
				planet.draw();
			}

			StdDraw.show(10);
		}
		
		StdOut.printf("%d\n", allPlanets.length);
		StdOut.printf("%.2e\n", NBody.readRadius("./data/planets.txt"));
		for (int i = 0; i < allPlanets.length; i++) 
		{
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   			allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel, allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);	
		}

	}

}
