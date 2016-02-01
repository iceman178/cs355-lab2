package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import cs355.GUIFunctions;
import cs355.model.drawing.*;

public class Controller implements CS355Controller {

	private boolean shapeSelected = false;
	private ArrayList<Point2D.Double> trianglePoints = new ArrayList<>();
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if (Model.instance().getCurrentMode() == Shape.type.TRIANGLE)
		{
			Point2D.Double newPoint = new Point2D.Double(arg0.getX(), arg0.getY());
			trianglePoints.add(newPoint);
			
			if (trianglePoints.size() == 3)
			{
				int x1 = (int)trianglePoints.get(0).getX();
				int x2 = (int)trianglePoints.get(1).getX();
				int x3 = (int)trianglePoints.get(2).getX();
				int y1 = (int)trianglePoints.get(0).getY();
				int y2 = (int)trianglePoints.get(1).getY();
				int y3 = (int)trianglePoints.get(2).getY();
				
				double centerX = (x1 + x2 + x3) / 3;
				double centerY = (y1 + y2 + y3) / 3;
				
				Point2D.Double triCenter = new Point2D.Double(centerX, centerY);
				
				
				Triangle triangle = new Triangle(Model.instance().getSelectedColor(),
												 triCenter,
												 trianglePoints.get(0),
												 trianglePoints.get(1),
												 trianglePoints.get(2));
				triangle.setShapeType(Shape.type.TRIANGLE);
				Model.instance().addShape(triangle);
				resetTriangleInfo();
				GUIFunctions.refresh();
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		//System.out.println("Controller:mousePressed  X=" + arg0.getX() + " Y=" + arg0.getY());

		switch(Model.instance().getCurrentMode())
		{
		case LINE:
			Point2D.Double start_line = new Point2D.Double(arg0.getX(), arg0.getY());		
			Point2D.Double end_line = new Point2D.Double(arg0.getX(), arg0.getY());
			Line line = new Line(Model.instance().getSelectedColor(), start_line, end_line);
			line.setShapeType(Shape.type.LINE);
			Model.instance().addShape(line);
			shapeSelected = true;
			break;
		case CIRCLE:
			Point2D.Double center_circle = new Point2D.Double(arg0.getX(), arg0.getY());
			Circle circle = new Circle(Model.instance().getSelectedColor(), center_circle, 0);
			circle.setShapeType(Shape.type.CIRCLE);
			Model.instance().addShape(circle);
			shapeSelected = true;			
			break;
		case ELLIPSE:
			Point2D.Double center_ellipse = new Point2D.Double(arg0.getX(), arg0.getY());
			Ellipse ellipse = new Ellipse(Model.instance().getSelectedColor(), center_ellipse, 0, 0);
			ellipse.setShapeType(Shape.type.ELLIPSE);
			Model.instance().addShape(ellipse);
			shapeSelected = true;
			break;
		case RECTANGLE:
			Point2D.Double start_rectangle = new Point2D.Double(arg0.getX(), arg0.getY());
			Rectangle rectangle = new Rectangle(Model.instance().getSelectedColor(), start_rectangle, 0, 0);
			rectangle.setShapeType(Shape.type.RECTANGLE);
			Model.instance().addShape(rectangle);
			shapeSelected = true;
			break;
		case SQUARE:
			Point2D.Double origin_square = new Point2D.Double(arg0.getX(), arg0.getY());
			Point2D.Double center_square = new Point2D.Double(arg0.getX(), arg0.getY());
			Square square = new Square(Model.instance().getSelectedColor(), center_square, origin_square, 0);
			square.setShapeType(Shape.type.SQUARE);
			Model.instance().addShape(square);
			shapeSelected = true;
			break;
		case TRIANGLE:	
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		shapeSelected = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		if(shapeSelected) 
		{
			Shape currentShape = Model.instance().getLastShape();
			if (currentShape == null)
			{
				System.out.println("currentShape is null");
				return;
			}
			switch(currentShape.getShapeType())
			{
			case LINE:
				updateCurrentLine(currentShape, arg0);
				break;
			case CIRCLE:
				updateCurrentCircle(currentShape, arg0);		
				break;
			case ELLIPSE:
				updateCurrentEllipse(currentShape, arg0);
				break;
			case RECTANGLE:
				updateCurrentRectangle(currentShape, arg0);
				break;
			case SQUARE:
				updateCurrentSquare(currentShape, arg0);
				break;
			case TRIANGLE:	
				break;
			default:
				break;
			}
		}
		GUIFunctions.refresh();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	private void updateCurrentLine(Shape currentShape, MouseEvent arg0) 
	{
		Line line = (Line) currentShape;
		Point2D.Double end_line = new Point2D.Double(arg0.getX(), arg0.getY());
		line.setEnd(end_line);
		
		Model.instance().updateLastShape(line);
	}

	private void updateCurrentCircle(Shape currentShape, MouseEvent arg0) 
	{
		Circle circle = (Circle) currentShape;
		Point2D.Double current_mouse_pos = new Point2D.Double(arg0.getX(), arg0.getY());

		double side_length = Math.min(Math.abs(circle.getCenter().getX() - current_mouse_pos.getX()), 
									  Math.abs(circle.getCenter().getY() - current_mouse_pos.getY()));
		circle.setRadius(side_length);
		
		// Left side of origin point
		if (current_mouse_pos.getX() < circle.getCenter().getX())
		{
			// Above origin point
			if (current_mouse_pos.getY() < circle.getCenter().getY())
			{
				circle.setCenter(new Point2D.Double(circle.getCenter().getX() - side_length, 
													   circle.getCenter().getY() - side_length));
			}
			else // Below origin point
			{
				circle.setCenter(new Point2D.Double(circle.getCenter().getX() - side_length, circle.getCenter().getY()));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (current_mouse_pos.getY() < circle.getCenter().getY())
			{
				circle.setCenter(new Point2D.Double(circle.getCenter().getX(), circle.getCenter().getY() - side_length));
			}
			else // Below origin point
			{
				circle.setCenter(new Point2D.Double(circle.getCenter().getX(), circle.getCenter().getY()));
			}
		}
		Model.instance().updateLastShape(circle);
	}
	
	private void updateCurrentEllipse(Shape currentShape, MouseEvent arg0) 
	{
		Ellipse ellipse = (Ellipse) currentShape;
		Point2D.Double current_mouse_pos = new Point2D.Double(arg0.getX(), arg0.getY());
		
		// Left side of origin point
		if (current_mouse_pos.getX() < ellipse.getCenter().getX())
		{
			// Above origin point
			if (current_mouse_pos.getY() < ellipse.getCenter().getY())
			{
				ellipse.setCenter(new Point2D.Double(current_mouse_pos.getX(), current_mouse_pos.getY()));
			}
			else // Below origin point
			{
				ellipse.setCenter(new Point2D.Double(current_mouse_pos.getX(), ellipse.getCenter().getY()));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (current_mouse_pos.getY() < ellipse.getCenter().getY())
			{
				ellipse.setCenter(new Point2D.Double(ellipse.getCenter().getX(), current_mouse_pos.getY()));
			}
			else // Below origin point
			{
				ellipse.setCenter(new Point2D.Double(ellipse.getCenter().getX(), ellipse.getCenter().getY()));
			}
		}
		
		double width = Math.abs(ellipse.getCenter().getX() - current_mouse_pos.getX());
		double height = Math.abs(ellipse.getCenter().getY() - current_mouse_pos.getY());
		
		ellipse.setWidth(width);
		ellipse.setHeight(height);
		
		Model.instance().updateLastShape(ellipse);
	}
		
	private void updateCurrentRectangle(Shape currentShape, MouseEvent arg0) 
	{
		Rectangle rectangle = (Rectangle) currentShape;
		Point2D.Double current_mouse_pos = new Point2D.Double(arg0.getX(), arg0.getY());

		// Left side of origin point
		if (current_mouse_pos.getX() < rectangle.getCenter().getX())
		{
			// Above origin point
			if (current_mouse_pos.getY() < rectangle.getCenter().getY())
			{
				rectangle.setCenter(new Point2D.Double(current_mouse_pos.getX(), current_mouse_pos.getY()));
			}
			else // Below origin point
			{
				rectangle.setCenter(new Point2D.Double(current_mouse_pos.getX(), rectangle.getCenter().getY()));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (current_mouse_pos.getY() < rectangle.getCenter().getY())
			{
				rectangle.setCenter(new Point2D.Double(rectangle.getCenter().getX(), current_mouse_pos.getY()));
			}
			else // Below origin point
			{
				rectangle.setCenter(new Point2D.Double(rectangle.getCenter().getX(), rectangle.getCenter().getY()));
			}
		}
		
		double width = Math.abs(rectangle.getCenter().getX() - current_mouse_pos.getX());
		double height = Math.abs(rectangle.getCenter().getY() - current_mouse_pos.getY());
		
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		
		Model.instance().updateLastShape(rectangle);		
	}

	private void updateCurrentSquare(Shape currentShape, MouseEvent arg0) 
	{
		Square square = (Square) currentShape;
		Point2D.Double current_mouse_pos = new Point2D.Double(arg0.getX(), arg0.getY());

		double side_length = Math.min(Math.abs(square.getCenter().getX() - current_mouse_pos.getX()), 
									  Math.abs(square.getCenter().getY() - current_mouse_pos.getY()));
		square.setSize(side_length / 2);
		
		// Left side of origin point
		if (current_mouse_pos.getX() < square.getCenter().getX())
		{
			// Above origin point
			if (current_mouse_pos.getY() < square.getCenter().getY())
			{
				square.setCenter(new Point2D.Double(square.getCenter().getX() - side_length, 
													   square.getCenter().getY() - side_length));
			}
			else // Below origin point
			{
				square.setCenter(new Point2D.Double(square.getCenter().getX() - side_length, square.getCenter().getY()));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (current_mouse_pos.getY() < square.getCenter().getY())
			{
				square.setCenter(new Point2D.Double(square.getCenter().getX(), square.getCenter().getY() - side_length));
			}
			else // Below origin point
			{
				square.setCenter(new Point2D.Double(square.getCenter().getX(), square.getCenter().getY()));
			}
		}
		Model.instance().updateLastShape(square);
	}
	
	public void resetTriangleInfo()
	{
		trianglePoints.clear();
	}
	
	@Override
	public void colorButtonHit(Color c) 
	{
		resetTriangleInfo();
		if (c == null)
		{
			return;
		}
		Model.instance().setSelectedColor(c);
		GUIFunctions.changeSelectedColor(c);
	}

	@Override
	public void lineButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.LINE);
	}

	@Override
	public void squareButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.SQUARE);
	}

	@Override
	public void rectangleButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.RECTANGLE);
	}

	@Override
	public void circleButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.CIRCLE);
	}

	@Override
	public void ellipseButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.ELLIPSE);
	}

	@Override
	public void triangleButtonHit() 
	{
		resetTriangleInfo();
		Model.instance().setCurrentMode(Shape.type.TRIANGLE);
	}

	@Override
	public void selectButtonHit() 
	{
		resetTriangleInfo();
		System.out.println("Controller:selectButtonHit");
	}

	@Override
	public void zoomInButtonHit() 
	{
		resetTriangleInfo();
		System.out.println("Controller:zoomInButtonHit");
	}

	@Override
	public void zoomOutButtonHit() 
	{
		resetTriangleInfo();
		System.out.println("Controller:zoomOutButtonHit");
	}

	@Override
	public void hScrollbarChanged(int value) 
	{
		System.out.println("Controller:hScrollbarChanged  Value=" + value);
	}

	@Override
	public void vScrollbarChanged(int value) 
	{
		System.out.println("Controller:vScrollbarChanged  Value=" + value);
	}

	@Override
	public void openScene(File file) {}

	@Override
	public void toggle3DModelDisplay() {}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {}

	@Override
	public void openImage(File file) {}

	@Override
	public void saveImage(File file) {}

	@Override
	public void toggleBackgroundDisplay() {}

	@Override
	public void saveDrawing(File file) 
	{
		Model.instance().save(file);
		GUIFunctions.refresh();
	}

	@Override
	public void openDrawing(File file) 
	{
		Model.instance().open(file);
		GUIFunctions.refresh();
	}

	@Override
	public void doDeleteShape() {}

	@Override
	public void doEdgeDetection() {}

	@Override
	public void doSharpen() {}

	@Override
	public void doMedianBlur() {}

	@Override
	public void doUniformBlur() {}

	@Override
	public void doGrayscale() {}

	@Override
	public void doChangeContrast(int contrastAmountNum) {}
	
	@Override
	public void doChangeBrightness(int brightnessAmountNum) {}
	
	@Override
	public void doMoveForward() {}
	
	@Override
	public void doMoveBackward() {}
	
	@Override
	public void doSendToFront() {}
	
	@Override
	public void doSendtoBack() {}
	
	
	
	
}