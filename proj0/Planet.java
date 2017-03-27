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
