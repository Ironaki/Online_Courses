import java.lang.Math;

public class Planet
{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img)
	{
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public double getxP()	{return xxPos;}
	public double getyP()	{return yyPos;}
	public double getxV()	{return xxVel;}
	public double getyV()	{return yyVel;}
	public double getm()	{return mass;}
	public String getimg()	{return imgFileName;}

	public Planet(Planet p)
	{
		xxPos = p.getxP();
		yyPos = p.getyP();
		xxVel = p.getxV();
		yyVel = p.getyV();
		mass = p.getm();
		imgFileName = p.getimg();
	}

	public double calcDistance(Planet p2)
	{
		double xDiff = this.getxP() - p2.getxP();
		double yDiff = this.getyP() - p2.getyP();
		double dis = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		return dis;
	}


	public double calcForceExertedBy(Planet p2)
	{
		double G = 6.67e-11;
		double force = G * this.getm() * p2.getm() / (this.calcDistance(p2) * this.calcDistance(p2));
		return force;
	}

	public double calcForceExertedByX(Planet p2)
	{
		double forceX = (p2.getxP() - this.getxP())/this.calcDistance(p2) * this.calcForceExertedBy(p2);
		return forceX;
	}

	public double calcForceExertedByY(Planet p2)
	{
		double forceY = (p2.getyP() - this.getyP())/this.calcDistance(p2) * this.calcForceExertedBy(p2);
		return forceY;
	}

	public double calcNetForceExertedByX(Planet[] allP)
	{
		double ans = 0.0;
		for(Planet items: allP)
		{
			if(!this.equals(items))	{ans += this.calcForceExertedByX(items);}
		}
		return ans;
	}

	public double calcNetForceExertedByY(Planet[] allP)
	{
		double ans = 0.0;
		for(Planet items: allP)
		{
			if(!this.equals(items))	{ans += this.calcForceExertedByY(items);}
		}
		return ans;
	}

	public void update(double dt, double fX, double fY)
	{
		double aX = fX / this.getm();
		double aY = fY / this.getm();
		this.xxVel = this.getxV() + dt * aX;
		this.yyVel = this.getyV() + dt * aY;
		this.xxPos = this.getxP() + dt * this.getxV();
		this.yyPos = this.getyP() + dt * this.getyV();
	}

	public void draw()
	{
		StdDraw.picture(this.getxP(),this.getyP(),"./images/" + this.getimg());
	}















	// public Planet(Planet p)
	// {
	// 	this.xxPos = p.getxP();
	// 	this.yyPos = p.getyP();
	// 	this.xxVel = p.getxV();
	// 	this.yyVel = p.getyV();
	// 	this.mass = p.getm();
	// 	this.imgFileName = p.getimg();
	// }


	// public Planet(Planet p)
	// {
	// 	this(p.getxP(), p.getyP(), p.getxV(), p.getyV(), p.getm(), p.getimg());
	// }
}
