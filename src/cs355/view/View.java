package cs355.view;

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
		
		for(int i = 0; i < shapes.size(); i++) {
			Shape currentShape = shapes.get(i);
			
			g2d.setColor(currentShape.getColor());
			g2d.fill(shapeFactory(currentShape)); //Uses the factory to determine the current shape to set the fill
			g2d.draw(shapeFactory(currentShape)); //Uses the factory to determine the current shape to draw the image
		}
	}
	
	//Use a factory to determine what type is being dealt with
	//TODO Needs to add logic for the rest of the shapes
	public java.awt.Shape shapeFactory(Shape currentShape)
	{
		if (currentShape.getShapeType() == Shape.type.LINE)
		{
			Line line = (Line)currentShape;
			
			Point2D.Double start = new Point2D.Double(line.getStart().x, line.getStart().y);		
			Point2D.Double end = new Point2D.Double(line.getEnd().x, line.getEnd().y);
			
			return new Line2D.Double(start.x, start.y, end.x, end.y);
		}
		else if (currentShape.getShapeType() == Shape.type.CIRCLE)
		{
			Circle circle = (Circle)currentShape;
						
			double x = circle.getCenter().getX();
			double y = circle.getCenter().getY();
			double width = circle.getRadius();
			double height = circle.getRadius();
						
			return new Ellipse2D.Double(x, y, width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.ELLIPSE)
		{
			Ellipse ellipse = (Ellipse)currentShape;
			
			double x = ellipse.getCenter().getX();
			double y = ellipse.getCenter().getY();
			double width = ellipse.getWidth();
			double height = ellipse.getHeight();
						
			return new Ellipse2D.Double(x, y, width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.RECTANGLE)
		{
			Rectangle rectangle = (Rectangle)currentShape;
			
			double x = rectangle.getUpperLeft().getX();
			double y = rectangle.getUpperLeft().getY();
			double width = rectangle.getWidth();
			double height = rectangle.getHeight();
			
			return new Rectangle2D.Double(x, y, width, height);
		}
		else if (currentShape.getShapeType() == Shape.type.SQUARE)
		{
			Square square = (Square)currentShape;
			double x = square.getUpperLeft().getX();
			double y = square.getUpperLeft().getY();
			double width = square.getSize();
			double height = square.getSize();
						
			return new Rectangle2D.Double(x, y, width, height);
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

