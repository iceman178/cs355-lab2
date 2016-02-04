package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.scene.Instance;

public class View implements ViewRefresher {

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		ArrayList<Shape> shapes = (ArrayList<Shape>) Model.instance().getShapes();
		
		int curShapeIndex = Model.instance().getCurShapeIndex();
		
		for(int a = 0; a < shapes.size(); a++) 
		{
			Shape currentShape = shapes.get(a);
			g2d.setColor(currentShape.getColor());
			
			//Uses the factory to determine the current shape to set the fill
			g2d.fill(shapeFactory(currentShape, g2d, false)); 
			//Uses the factory to determine the current shape to draw the image
			
				g2d.draw(shapeFactory(currentShape, g2d, curShapeIndex == a)); 
			
		}
	}
	
	//Use a factory to determine what type is being dealt with
	//TODO Needs to add logic for the rest of the shapes
	public java.awt.Shape shapeFactory(Shape currentShape, Graphics2D g2d, boolean shapeSelected)
	{
		if (currentShape.getShapeType() == Shape.type.LINE)
		{
			Line line = (Line)currentShape;
			
			Point2D.Double start = new Point2D.Double(line.getCenter().x, line.getCenter().y);		
			Point2D.Double end = new Point2D.Double(line.getEnd().x, line.getEnd().y);
			
			if(shapeSelected)
			{
				
				
				
				
			}
			return new Line2D.Double(start.x, start.y, end.x, end.y);
		}
		else if (currentShape.getShapeType() == Shape.type.CIRCLE)
		{
			Circle circle = (Circle)currentShape;
						
			double x = circle.getCenter().getX();
			double y = circle.getCenter().getY();
			double width = circle.getRadius() * 2;
			double height = circle.getRadius() * 2;
			
			if(shapeSelected)
			{
				g2d.setColor(new Color(153, 255, 153));
				g2d.drawRect((int)(x - (width/2))-1, (int)(y - (height/2))-1, (int)width+2, (int)height+2);
//				g2d.drawOval(-6, (int)-diameter/2 - 15, 11, 11);
				g2d.setColor(circle.getColor());
			}
			return new Ellipse2D.Double(x - (width/2), y - (height/2), width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.ELLIPSE)
		{
			Ellipse ellipse = (Ellipse)currentShape;
			
			double x = ellipse.getCenter().getX();
			double y = ellipse.getCenter().getY();
			double width = ellipse.getWidth() * 2;
			double height = ellipse.getHeight() * 2;
			
			if(shapeSelected)
			{
				
			}
			return new Ellipse2D.Double(x - (width/2), y - (height/2), width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.RECTANGLE)
		{
			Rectangle rectangle = (Rectangle)currentShape;
			
			double x = rectangle.getCenter().getX();
			double y = rectangle.getCenter().getY();
			double width = rectangle.getWidth();
			double height = rectangle.getHeight();
			if(shapeSelected)
			{
				
			}
			return new Rectangle2D.Double(x - (width/2), y - (height/2), width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.SQUARE)
		{
			Square square = (Square)currentShape;
			double x = square.getCenter().getX();
			double y = square.getCenter().getY();
			double width = square.getSize();
			double height = square.getSize();
			if(shapeSelected)
			{
				
			}
			return new Rectangle2D.Double(x - (width/2), y - (height/2), width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.TRIANGLE)
		{
			Triangle triangle = (Triangle)currentShape;
			
			int[] x = new int[3];
			int[] y = new int[3];
			
			x[0] = (int)triangle.getA().getX();
			x[1] = (int)triangle.getB().getX();
			x[2] = (int)triangle.getC().getX();
			
			y[0] = (int)triangle.getA().getY();
			y[1] = (int)triangle.getB().getY();
			y[2] = (int)triangle.getC().getY();
			
			Polygon tri = new Polygon();
			
			tri.addPoint(x[0], y[0]);
			tri.addPoint(x[1], y[1]);
			tri.addPoint(x[2], y[2]);
			
			return tri;
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
}

