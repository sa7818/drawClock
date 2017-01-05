
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

// Main class
public class DrawClock extends Frame {
	GridCanvas grid;
	Label infor;
	int r=10;
	Timer timer = new Timer();
	// Constructor
	public DrawClock(int span) {
		super("Analog Clock");
		// create & add a grid canvas
		grid = new GridCanvas(span);
		add("Center", grid);
		// add an information bar
		infor = new Label();
		infor.setAlignment(infor.CENTER);
		add("South", infor);
		addWindowListener(new ExitListener());
		timer.scheduleAtFixedRate((new UpdateClock()), 0, 1000);
	}
	// Exit listener
	class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			timer.cancel();
			System.exit(0);
		}
	}
	// TimerTask for updating the colck
	class UpdateClock extends TimerTask {
		public void run() {
			Calendar time = Calendar.getInstance();
			int hour = time.get(Calendar.HOUR_OF_DAY);
			int minute = time.get(Calendar.MINUTE);
			int second = time.get(Calendar.SECOND);
			// display the current time
			infor.setText("Current time: " + hour + (minute>9?":"+minute:":0"+minute) + (second>9?":"+second:":0"+second));
			grid.setTime(hour, minute, second);
		}
	}

	public static void main(String[] args) {
		int span = 10;
		if ( args.length == 1 )
			span = Integer.parseInt(args[0]);
		DrawClock window = new DrawClock(span);
		window.setSize(400, 450);
		
		window.setResizable(true);
		window.setVisible(true);
	}
}

// Canvas with grid shown
class GridCanvas extends Canvas {
	// parameter that controls the span of the grid
	int span, width, height, xoff, yoff;
	int hour, minute, second;
	public static int counter;
	public  int[][] coordinates = new int[400][2];
	int countMin,countSec =0;
	// Initialize the grid size;
	public GridCanvas(int s) {
		span = s;
	}
	public void setTime(int h, int m, int s) {
		hour = h; minute = m; second = s;
		repaint();
	}
	// Draw the grids
	public void drawGrid(Graphics2D g2D) {
		g2D.setColor(Color.lightGray);
		for ( int x=-width ; x<=width ; x++ )
			g2D.draw(new Line2D.Float(new Dot(x, -height).toCoord(), new Dot(x, height).toCoord()));
		for ( int y=-height ; y<=height ; y++ )
			g2D.draw(new Line2D.Float(new Dot(-width, y).toCoord(), new Dot(width, y).toCoord()));
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		// calculate the number of cells to be shown
		width  =  (getWidth() / span - 1) / 2;
		height = (getHeight() /span - 1) / 2;
		xoff = getWidth() / 2;
		yoff = getHeight() / 2;
		counter =0;
		int countpix=0; int temp=0;int temp3=0; int radius =0;
		drawGrid(g2D);
		
		// example of how to display a dot
		g2D.setColor(Color.red);
		g2D.fill(new Dot(0, 0));
	
       if(width<height)
       {
    	 radius = width;
         drawCircle(g2D, radius,coordinates);}
       else{ radius = height;
       drawCircle(g2D, radius,coordinates);}
       
       
	    /*
		double pixel = (double)counter / 60;//every second
		// System.out.println(pixel);
		countpix =(int) (second *pixel);//number of pixels for second
		
	    temp = counter/4;//number of pixels in each quarter
	    temp3 = counter/8; // numebr of pixel in 1/8 of circle
		//int x1 = second%width; int y1 = minute%height;
		int x1=0;int y1=0;
		//find clock hand position
		int count,quarter,countf=0;
		int[] secondPos={0,0};
		quarter = countpix/temp;
		//System.out.println(quarter);
		count = temp - (countpix - (quarter*temp));//System.out.println(count);
		countf = count -temp3; //if countf>0 then in fisrt 1/8 of circle
		int temp2=0;
		for(int j = height/2; j >=0; j--)
			{
			for(int i = 0; i<=width; i++ )
			{
				
				for(int k =0; k <= counter; k++)
				{
				if(coordinates[k][0] == i && coordinates[k][1] == j && temp2 <= count)
				{
				  temp2++;	
				  secondPos[0] = i;secondPos[1]=j;}
				}
			}
		}
		//if(quarter == 0)//nahie 1
		//{
	     x1 = secondPos[0]; y1=secondPos[1]; 
*/
				//1 is second, 2 is minute, 3 is hour
       int pixelDeg = 90/15;
       int hourpixDeg = 90/3;
       int secondDeg = second*pixelDeg; 
       int minDeg =    minute*pixelDeg;
       int hourDeg =   hour*hourpixDeg;
    
       int x1 = (int) (radius*Math.sin(Math.toRadians(secondDeg)));
       int y1 = (int) (radius*Math.cos(Math.toRadians(secondDeg)));
       int xmin = (int) (radius*Math.sin(Math.toRadians(minDeg)));  
       int ymin = (int) (radius*Math.cos(Math.toRadians(minDeg)));
       int xhour = (int) (radius*Math.sin(Math.toRadians(hourDeg)));
       int yhour = (int) (radius*Math.cos(Math.toRadians(hourDeg)));
       int lineposition = (counter*(minute*60+second))/3600; 
       int linepos =  ((counter*(hour*3600+minute*60+second))/(12*3600))%counter;System.out.println(linepos);
       int lineposSec =  ((counter*(hour*3600+minute*60+second))/(12*3600))%counter;
       /*
       //countSecond=0, countMinute;
		if(second ==0 || second==60)// draw minute
		{
			drawHand(x1,y1,g2D,2,radius);
			countSec++;
		}
		if(countMin ==0 || countMin==3600)
		{
			drawHand(x1,y1,g2D,3,radius);//draw hour 
			countMin++;
		}*/
      // drawMinute(x1,y1,g2D);
     
       drawHand(x1,y1,g2D,1,radius);//second
      drawHand((7*xmin)/8,(7*ymin)/8,g2D,2,radius);
       
        drawHand((3*xhour)/4,((3*yhour)/4),g2D,3,radius);//hour

       }
	public void drawHand(int x1, int y1, Graphics2D g2D, int type, int radius){
		
		
	    int initDeltax= x1; int initDeltay=y1;
	    if(x1>0 && y1>=0 && Math.abs(initDeltax)>Math.abs(initDeltay))
	    drawLine(x1,y1,g2D,type,radius);
	    if(x1>=0 && y1>0 && Math.abs(initDeltay)>Math.abs(initDeltax))
		    drawLine2(x1,y1,g2D, type, radius);
	    if(x1<=0 && y1>0 && Math.abs(initDeltay)>Math.abs(initDeltax))
		    drawLine3(x1,y1,g2D, type, radius);
	    if(x1<0 && y1>=0 && Math.abs(initDeltax)>Math.abs(initDeltay))
		    drawLine4(x1,y1,g2D, type, radius);
	    	//drawLine4(x1,y1,g2D);
	    if(x1<0 && y1<0 && Math.abs(initDeltax)>Math.abs(initDeltay))
		    drawLine5(x1,y1,g2D, type, radius);
	    if(x1<0 && y1<0 && Math.abs(initDeltay)>Math.abs(initDeltax))
		    drawLine6(x1,y1,g2D, type, radius);
	    if(x1>=0 && y1<0 && Math.abs(initDeltay)>Math.abs(initDeltax))
		    drawLine7(x1,y1,g2D, type, radius);
	    if(x1>0 && y1<=0 && Math.abs(initDeltax)>Math.abs(initDeltay))
		    drawLine8(x1,y1,g2D, type,radius);
	    	//drawLine8(x1,y1,g2D);
	    	 
		
	    
	}
	public void drawLine(int x1, int y1, Graphics2D g2D,int type, int radius){
		int numDots=0;
		int hourD = 0;
	    int d2=0;int deltay=y1;
		int deltax =x1;
		d2 = 2*deltay-deltax;
		int x=0,y=0;
		while(x<x1)
		{
			deltay = y1-y; deltax = x1-x;
			if(d2<=0)
			{
				d2 += deltay*2;
				x++;
			}else
			{
				d2 += (deltay-deltax)*2;
				x++;y++;
			}
			if(type== 2&&numDots< radius)
			{
				g2D.setColor(Color.green);
				g2D.fill(new Dot(x, y));
				numDots++;
			}else 
				if(type== 3&&hourD < radius)
			{
				g2D.setColor(Color.blue);
				g2D.fill(new Dot(x, y));
				hourD++;
				
			}
			else if(type==1){
				g2D.setColor(Color.red);
				g2D.fill(new Dot(x, y));
			}
		}
		
	}
public void drawLine2(int x1, int y1, Graphics2D g2D, int type, int radius){
		
	    int numDots=0;
	    int hourD = 0;
	    int d2=0;int deltay=y1;
		int deltax =x1;
		d2 = deltay-2*deltax;
		int x=0,y=0;
		
		while(y<y1)
		{
			deltay = y1-y; deltax = x1-x;
			if(d2>=0)
			{
				d2 += -deltax*2;
				y++;
			}else
			{
				d2 += (deltay-deltax)*2;
				x++;y++;
			}
			if(type== 2&&numDots<radius)
			{
				g2D.setColor(Color.green);
				g2D.fill(new Dot(x, y));System.out.println(numDots);
				numDots++;
			}else 
				if(type== 3&&hourD< radius)
			{
				g2D.setColor(Color.blue);
				g2D.fill(new Dot(x, y));
				hourD++;
			}else if(type==1)
			{
				g2D.setColor(Color.red);
				g2D.fill(new Dot(x, y));
			}
		}
	}
public void drawLine3(int x1, int y1, Graphics2D g2D, int type, int radius){
	
    int numDots =0;
    int hourD = 0;
    int d2=0;int deltay=y1;
	int deltax =x1;
	d2 = -deltay-2*deltax;
	int x=0,y=0;
	while(y<y1)
	{
		deltay = y1-y; deltax = x1-x;
		if(d2<=0)
		{
			d2 += -deltax*2;
			y++;
		}else
		{
			d2 += (-deltay-deltax)*2;
			x--;y++;
		}
		if(type== 2&&numDots< radius)
		{
			g2D.setColor(Color.green);
			g2D.fill(new Dot(x, y));
			numDots++;
		}else 
			if(type== 3&&hourD<radius)
		{
			g2D.setColor(Color.blue);
			g2D.fill(new Dot(x, y));
			hourD++;
		}else
		{
			g2D.setColor(Color.red);
			g2D.fill(new Dot(x, y));
		}
	}
}
		public void drawLine4(int x1, int y1, Graphics2D g2D,int type, int radius){
			
			int numDots=0;int hourD=0;
		    int d2=0;int deltay=y1;
			int deltax =x1;
			d2 = -2*deltay-deltax;
			int x=0,y=0;
			while(x>x1)
			{
				deltay = y1-y; deltax = x1-x;
				if(d2<=0)
				{
					d2 += deltay*2;
					x--;
				}else
				{
					d2 += (deltay+deltax)*2;
					x--;y++;
				}
				if(type== 2&&numDots< radius)
				{
					g2D.setColor(Color.green);
					g2D.fill(new Dot(x, y));
					numDots++;
				}else 
					if(type== 3&&hourD< radius)
					{
						g2D.setColor(Color.blue);
						g2D.fill(new Dot(x, y));
						hourD++;
					}else if(type==1)
				{
					g2D.setColor(Color.red);
					g2D.fill(new Dot(x, y));
				}
			}
	}
public void drawLine5(int x1, int y1, Graphics2D g2D, int type, int radius){
			
	int numDots=0;int hourD =0;
		    int d2=0;int deltay=y1;
			int deltax =x1;
			d2 = -2*deltay+deltax;
			int x=0,y=0;
			while(x>x1)
			{
				deltay = y1-y; deltax = x1-x;
				if(d2<=0)
				{
					d2 += -deltay*2;
					x--;
				}else
				{
					d2 += (-deltay+deltax)*2;
					x--;y--;
				}
				if(type== 2&&numDots< radius)
				{
					g2D.setColor(Color.green);System.out.println(radius);
					g2D.fill(new Dot(x, y));//System.out.println(numDots);
					numDots++;
				}else 
					if(type== 3&&hourD< radius)
					{
						g2D.setColor(Color.blue);
						g2D.fill(new Dot(x, y));
						hourD++;
					}else if(type==1)
				{
					g2D.setColor(Color.red);
					g2D.fill(new Dot(x, y));
				}
			}
	} 
public void drawLine6(int x1, int y1, Graphics2D g2D, int type, int radius)
{
	int numDots=0;int hourD=0;
	int d2=0;int deltay=y1;
	int deltax =x1;
	d2 = -2*deltay+deltax;
	int x=0,y=0;
	while(y>y1)
	{
		deltay = y1-y; deltax = x1-x;
		if(d2>=0)
		{
			d2 += deltax*2;
			y--;
		}else
		{
			d2 += (-deltay+deltax)*2;
			x--;y--;
		}
		if(type== 2&&numDots< radius)
		{
			g2D.setColor(Color.green);
			g2D.fill(new Dot(x, y));
			numDots++;
		}else 
			if(type== 3&&hourD< radius)
		{
			g2D.setColor(Color.blue);
			g2D.fill(new Dot(x, y));
			hourD++;
		}else if(type==1)
		{
			g2D.setColor(Color.red);
			g2D.fill(new Dot(x, y));
		}
	}
}
public void drawLine7(int x1, int y1, Graphics2D g2D, int type, int radius)
{
	int numDots=0;int hourD=0;
	int d2=0;int deltay=y1;
	int deltax =x1;
	d2 = deltay+2*deltax;
	int x=0,y=0;
	while(y>y1)
	{
		deltay = y1-y; deltax = x1-x;
		if(d2<=0)
		{
			d2 += deltax*2;
			y--;
		}else
		{
			d2 += (deltay+deltax)*2;
			x++;y--;
		}
		if(type== 2&&numDots< radius)
		{
			g2D.setColor(Color.green);
			g2D.fill(new Dot(x, y));
			numDots++;
		}else 
			if(type== 3&&hourD< radius)
		{
			g2D.setColor(Color.blue);
			g2D.fill(new Dot(x, y));
			hourD++;
		}else if(type==1)
		{
			g2D.setColor(Color.red);
			g2D.fill(new Dot(x, y));
		}
	}	
}
public void drawLine8(int x1, int y1, Graphics2D g2D, int type, int radius){
			
	int numDots=0;int hourD=0;
	int d2=0;int deltay=y1;
			int deltax =x1;
			d2 = 2*deltay+deltax;
			int x=0,y=0;
			while(x<x1)
			{
				deltay = y1-y; deltax = x1-x;
				if(d2>=0)
				{
					d2 += deltay*2;
					x++;
				}else
				{
					d2 += (deltay+deltax)*2;
					x++;y--;
				}
				if(type== 2&&numDots< radius)
				{
					g2D.setColor(Color.green);
					g2D.fill(new Dot(x, y));
					numDots++;
				}else 
					if(type== 3&&hourD< radius)
					{
						g2D.setColor(Color.blue);
						g2D.fill(new Dot(x, y));
						hourD++;
					}else if(type==1)
				{
					g2D.setColor(Color.red);
					g2D.fill(new Dot(x, y));
				}
			}
	} 
    public void drawCircle(Graphics2D g2D,int r, int coordinates[][])
    {
    	int x=0; int y = r;
    	int d = 1 -r;
        int i=-1;
    	while ( y >= x )
    	{
    		/*
    		g2D.fill(new Dot(x, y));  coordinates[++i][0]=x; coordinates[i][1]= y;  
    		g2D.fill(new Dot(y, x));  coordinates[++i][0]=y; coordinates[i][1]= x; 
    		g2D.fill(new Dot(-x, y)); coordinates[++i][0]=-x;coordinates[i][1]= y; 
    		g2D.fill(new Dot(-y, x)); coordinates[++i][0]=-y;coordinates[i][1]= x; 
    		g2D.fill(new Dot(-x, -y));coordinates[++i][0]=-x;coordinates[i][1]=-y; 
    		g2D.fill(new Dot(-y, -x));coordinates[++i][0]=-y;coordinates[i][1]=-x; 
    		g2D.fill(new Dot(x, -y)); coordinates[++i][0]= x; coordinates[i][1]=-y; 
    		g2D.fill(new Dot(y, -x)); coordinates[++i][0]=y ;coordinates[i][1]=-x;
    		*/
    		g2D.setColor(Color.black);
    		g2D.fill(new Dot(x, y)); 
    		coordinates[++i][0]=x; coordinates[i][1]= y;  
    		g2D.fill(new Dot(y, x));  coordinates[++i][0]=y; coordinates[i][1]= x; 
    		g2D.fill(new Dot(-x, y)); coordinates[++i][0]=-x;coordinates[i][1]= y; 
    		g2D.fill(new Dot(-y, x)); coordinates[++i][0]=-y;coordinates[i][1]= x; 
    		g2D.fill(new Dot(-x, -y));coordinates[++i][0]=-x;coordinates[i][1]=-y; 
    		g2D.fill(new Dot(-y, -x));coordinates[++i][0]=-y;coordinates[i][1]=-x; 
    		g2D.fill(new Dot(x, -y)); coordinates[++i][0]= x; coordinates[i][1]=-y; 
    		g2D.fill(new Dot(y, -x)); coordinates[++i][0]=y ;coordinates[i][1]=-x; 
            counter+=8;
    		if(d<0)
    		{
    			d+= x*2+3; 
    			x++;
    			
    		}
    		else
    		{
    			d+= (x-y)*2+5;
    			x++; y--;
    			
    		}
    	}
    	
    }

    // Represent a dot at given coordinate
	class Dot extends Ellipse2D.Float {
		public Dot(int x, int y) {
			super(x*span-span/2+xoff, -y*span-span/2+yoff, span, span);
		}
		public Point toCoord() {
			return new Point((int)x+span/2, (int)y+span/2);
		}
	}
	
}
