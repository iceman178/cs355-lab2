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
	private boolean rotating = false;
	private int curShapeIndex = -1;
	private ArrayList<Point2D.Double> trianglePoints = new ArrayList<>();
	private Mode curControllerMode = Mode.NONE;
	
	public enum Mode {
		SHAPE, SELECT, ZOOM_IN, ZOOM_OUT, NONE
	}
	
	private double calculateCenterTriangle(double coord1, double coord2, double coord3) {
		return ((coord1 + coord2 + coord3) / 3);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if (curControllerMode == Mode.SELECT)
		{
			Point2D.Double curClick = new Point2D.Double(arg0.getX(), arg0.getY());
			curShapeIndex = Model.instance().checkIfSelectedShape(curClick);
			GUIFunctions.refresh();
		}
		else
		{
			if (Model.instance().getCurrentMode() == Shape.type.TRIANGLE)
			{
				Point2D.Double newPoint = new Point2D.Double(arg0.getX(), arg0.getY());
				trianglePoints.add(newPoint);
				
				if (trianglePoints.size() == 3)
				{
					Point2D.Double point1 = new Point2D.Double(this.trianglePoints.get(0).getX(), this.trianglePoints.get(0).getY());
					Point2D.Double point2 = new Point2D.Double(this.trianglePoints.get(1).getX(), this.trianglePoints.get(1).getY());
					Point2D.Double point3 = new Point2D.Double(this.trianglePoints.get(2).getX(), this.trianglePoints.get(2).getY());
									
					Point2D.Double center = new Point2D.Double(this.calculateCenterTriangle(point1.getX(), point2.getX(), point3.getX()),
							this.calculateCenterTriangle(point1.getY(), point2.getY(), point3.getY()));
					
					Triangle triangle = new Triangle(Model.instance().getSelectedColor(), center, point1, point2, point3);
					//Model.instance().addShape(triangle);
					//this.trianglePoints.clear();
					//Model.instance().changeMade();
//					Point2D.Double p1 = new Point2D.Double(trianglePoints.get(0).getX(), trianglePoints.get(0).getY());
//					Point2D.Double p2 = new Point2D.Double(trianglePoints.get(1).getX(), trianglePoints.get(1).getY());
//					Point2D.Double p3 = new Point2D.Double(trianglePoints.get(2).getX(), trianglePoints.get(2).getY());
//					
//					double centerX = (p1.getX() + p2.getX() + p3.getX()) / 3;
//					double centerY = (p1.getY() + p2.getY() + p3.getY()) / 3;
//					
//					Point2D.Double triCenter = new Point2D.Double(centerX, centerY);
//					
//					Triangle triangle = new Triangle(Model.instance().getSelectedColor(),
//													 triCenter,
//													 p1,
//													 p2,
//													 p3);
					Model.instance().addShape(triangle);
					resetCurMode();;
					GUIFunctions.refresh();
				}
			}
		}
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(curControllerMode == Mode.SHAPE)
		{
			switch(Model.instance().getCurrentMode())
			{
			case LINE:
				Point2D.Double start_line = new Point2D.Double(arg0.getX(), arg0.getY());		
				Point2D.Double end_line = new Point2D.Double(arg0.getX(), arg0.getY());
				Line line = new Line(Model.instance().getSelectedColor(), start_line, end_line);
				Model.instance().addShape(line);
				shapeSelected = true;
				break;
			case CIRCLE:
				Point2D.Double origin_circle = new Point2D.Double(arg0.getX(), arg0.getY());
				Point2D.Double center_circle = new Point2D.Double(arg0.getX(), arg0.getY());
				Circle circle = new Circle(Model.instance().getSelectedColor(), center_circle, origin_circle, 0);
				Model.instance().addShape(circle);
				shapeSelected = true;			
				break;
			case ELLIPSE:
				Point2D.Double origin_ellipse = new Point2D.Double(arg0.getX(), arg0.getY());
				Point2D.Double center_ellipse = new Point2D.Double(arg0.getX(), arg0.getY());
				Ellipse ellipse = new Ellipse(Model.instance().getSelectedColor(), center_ellipse, origin_ellipse, 0, 0);
				Model.instance().addShape(ellipse);
				shapeSelected = true;
				break;
			case RECTANGLE:
				Point2D.Double origin_rectangle = new Point2D.Double(arg0.getX(), arg0.getY());
				Point2D.Double center_rectangle = new Point2D.Double(arg0.getX(), arg0.getY());
				Rectangle rectangle = new Rectangle(Model.instance().getSelectedColor(), center_rectangle, origin_rectangle, 0, 0);
				Model.instance().addShape(rectangle);
				shapeSelected = true;
				break;
			case SQUARE:
				Point2D.Double origin_square = new Point2D.Double(arg0.getX(), arg0.getY());
				Point2D.Double center_square = new Point2D.Double(arg0.getX(), arg0.getY());
				Square square = new Square(Model.instance().getSelectedColor(), center_square, origin_square, 0);
				Model.instance().addShape(square);
				shapeSelected = true;
				break;
			case TRIANGLE:	
				break;
			default:
				break;
			}
		}
		else if(curControllerMode == Mode.SELECT)
		{
			// TODO
			// Not sure yet 
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
		if (curControllerMode == Mode.SHAPE)
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
		else if(curControllerMode == Mode.SELECT)
		{
			
		}
		
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
		Point2D.Double curMousePos = new Point2D.Double(arg0.getX(), arg0.getY());

		double side_length = Math.min(Math.abs(circle.getOrigin().getX() - curMousePos.getX()), 
									  Math.abs(circle.getOrigin().getY() - curMousePos.getY()));
		circle.setRadius(side_length / 2);
		
		// Left side of origin point
		if (curMousePos.getX() < circle.getOrigin().getX())
		{
			// Above origin point
			if (curMousePos.getY() < circle.getOrigin().getY())
			{
				double x = circle.getOrigin().getX() - side_length/2;
				double y = circle.getOrigin().getY() - side_length/2;
				circle.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = circle.getOrigin().getX() - side_length/2;
				double y = circle.getOrigin().getY() + side_length/2;
				circle.setCenter(new Point2D.Double(x, y));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (curMousePos.getY() < circle.getOrigin().getY())
			{
				double x = circle.getOrigin().getX() + side_length/2;
				double y = circle.getOrigin().getY() - side_length/2;
				circle.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = circle.getOrigin().getX() + side_length/2;
				double y = circle.getOrigin().getY() + side_length/2;
				circle.setCenter(new Point2D.Double(x, y));
			}
		}
		
		Model.instance().updateLastShape(circle);
	}
	
	private void updateCurrentEllipse(Shape currentShape, MouseEvent arg0) 
	{
		Ellipse ellipse = (Ellipse) currentShape;
		Point2D.Double curMousePos = new Point2D.Double(arg0.getX(), arg0.getY());
		
		double width = Math.abs(ellipse.getOrigin().getX() - curMousePos.getX());
		double height = Math.abs(ellipse.getOrigin().getY() - curMousePos.getY());
		
		ellipse.setWidth(width/2);
		ellipse.setHeight(height/2);
		
		// Left side of origin point
		if (curMousePos.getX() < ellipse.getOrigin().getX())
		{
			// Above origin point
			if (curMousePos.getY() < ellipse.getOrigin().getY())
			{
				double x = ellipse.getOrigin().getX() - width/2;
				double y = ellipse.getOrigin().getY() - height/2;
				ellipse.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = ellipse.getOrigin().getX() - width/2;
				double y = ellipse.getOrigin().getY() + height/2;
				ellipse.setCenter(new Point2D.Double(x, y));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (curMousePos.getY() < ellipse.getOrigin().getY())
			{
				double x = ellipse.getOrigin().getX() + width/2;
				double y = ellipse.getOrigin().getY() - height/2;
				ellipse.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = ellipse.getOrigin().getX() + width/2;
				double y = ellipse.getOrigin().getY() + height/2;
				ellipse.setCenter(new Point2D.Double(x, y));
			}
		}
		
		Model.instance().updateLastShape(ellipse);
	}
		
	private void updateCurrentRectangle(Shape currentShape, MouseEvent arg0) 
	{
		Rectangle rectangle = (Rectangle) currentShape;
		Point2D.Double curMousePos = new Point2D.Double(arg0.getX(), arg0.getY());
		
		double width = Math.abs(rectangle.getOrigin().getX() - curMousePos.getX());
		double height = Math.abs(rectangle.getOrigin().getY() - curMousePos.getY());
		
		rectangle.setWidth(width);
		rectangle.setHeight(height);

		// Left side of origin point
		if (curMousePos.getX() < rectangle.getOrigin().getX())
		{
			// Above origin point
			if (curMousePos.getY() < rectangle.getOrigin().getY())
			{
				double x = rectangle.getOrigin().getX() - width/2;
				double y = rectangle.getOrigin().getY() - height/2;
				rectangle.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = rectangle.getOrigin().getX() - width/2;
				double y = rectangle.getOrigin().getY() + height/2;
				rectangle.setCenter(new Point2D.Double(x, y));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (curMousePos.getY() < rectangle.getOrigin().getY())
			{
				double x = rectangle.getOrigin().getX() + width/2;
				double y = rectangle.getOrigin().getY() - height/2;
				rectangle.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = rectangle.getOrigin().getX() + width/2;
				double y = rectangle.getOrigin().getY() + height/2;
				rectangle.setCenter(new Point2D.Double(x, y));
			}
		}

		Model.instance().updateLastShape(rectangle);		
	}

	private void updateCurrentSquare(Shape currentShape, MouseEvent arg0) 
	{
		Square square = (Square) currentShape;
		Point2D.Double curMousePos = new Point2D.Double(arg0.getX(), arg0.getY());

		double side_length = Math.min(Math.abs(square.getOrigin().getX() - curMousePos.getX()), 
									  Math.abs(square.getOrigin().getY() - curMousePos.getY()));
		square.setSize(side_length);

		// Left side of origin point
		if (curMousePos.getX() < square.getOrigin().getX())
		{
			// Above origin point
			if (curMousePos.getY() < square.getOrigin().getY())
			{
				double x = square.getOrigin().getX() - side_length/2;
				double y = square.getOrigin().getY() - side_length/2;
				square.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = square.getOrigin().getX() - side_length/2;
				double y = square.getOrigin().getY() + side_length/2;
				square.setCenter(new Point2D.Double(x, y));
			}
		}
		else // Right side of origin point
		{
			// Above origin point
			if (curMousePos.getY() < square.getOrigin().getY())
			{
				double x = square.getOrigin().getX() + side_length/2;
				double y = square.getOrigin().getY() - side_length/2;
				square.setCenter(new Point2D.Double(x, y));
			}
			else // Below origin point
			{
				double x = square.getOrigin().getX() + side_length/2;
				double y = square.getOrigin().getY() + side_length/2;
				square.setCenter(new Point2D.Double(x, y));
			}
		}
		
		Model.instance().updateLastShape(square);
	}
	
	public void resetCurMode()
	{
		trianglePoints.clear();
		curControllerMode = Mode.NONE;
		Model.instance().setCurShapeIndex(-1);
		curShapeIndex = -1;
		GUIFunctions.refresh();
	}
	
	@Override
	public void colorButtonHit(Color c) 
	{
		if (c == null)
		{
			return;
		}
		if (curControllerMode == Mode.SELECT)
		{
			Model.instance().updateColor(c);
		}
		else
		{
			Model.instance().setSelectedColor(c);
			GUIFunctions.changeSelectedColor(c);
		}
		GUIFunctions.refresh();
	}

	@Override
	public void lineButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.LINE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void squareButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.SQUARE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void rectangleButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.RECTANGLE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void circleButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.CIRCLE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void ellipseButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.ELLIPSE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void triangleButtonHit() 
	{
		resetCurMode();;
		Model.instance().setCurrentMode(Shape.type.TRIANGLE);
		curControllerMode = Mode.SHAPE;
	}

	@Override
	public void selectButtonHit() 
	{
		resetCurMode();
		curControllerMode = Mode.SELECT;
		System.out.println("Controller:selectButtonHit");
	}

	@Override
	public void zoomInButtonHit() 
	{
		resetCurMode();
		curControllerMode = Mode.ZOOM_IN;
		System.out.println("Controller:zoomInButtonHit");
	}

	@Override
	public void zoomOutButtonHit() 
	{
		resetCurMode();
		curControllerMode = Mode.ZOOM_OUT;
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
	public void doDeleteShape() {
		if(curControllerMode == Mode.SELECT && curShapeIndex != -1)
		{
			Model.instance().deleteShape(curShapeIndex);
			curShapeIndex = Model.get_instance().getCurShapeIndex();
		}
		GUIFunctions.refresh();
	}

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
	public void doMoveForward() {
		if(curControllerMode == Mode.SELECT && curShapeIndex != -1)
		{
			Model.instance().moveForward(curShapeIndex);
			curShapeIndex = Model.get_instance().getCurShapeIndex();
		}
		GUIFunctions.refresh();
	}
	
	@Override
	public void doMoveBackward() {
		if(curControllerMode == Mode.SELECT && curShapeIndex != -1)
		{
			Model.instance().moveBackward(curShapeIndex);
			curShapeIndex = Model.get_instance().getCurShapeIndex();
		}
		GUIFunctions.refresh();
	}
	
	@Override
	public void doSendToFront() {
		if(curControllerMode == Mode.SELECT && curShapeIndex != -1)
		{
			Model.instance().moveToFront(curShapeIndex);
			curShapeIndex = Model.get_instance().getCurShapeIndex();
		}
		GUIFunctions.refresh();
	}
	
	@Override
	public void doSendtoBack() {
		if(curControllerMode == Mode.SELECT && curShapeIndex != -1)
		{
			Model.instance().movetoBack(curShapeIndex);
			curShapeIndex = Model.get_instance().getCurShapeIndex();
		}
		GUIFunctions.refresh();
	}

	public boolean isRotating() {
		return rotating;
	}

	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	}

	public int getCurShapeIndex() {
		return curShapeIndex;
	}

	public void setCurShapeIndex(int curShapeIndex) {
		this.curShapeIndex = curShapeIndex;
	}

	public Mode getCurControllerMode() {
		return curControllerMode;
	}

	public void setCurControllerMode(Mode curControllerMode) {
		this.curControllerMode = curControllerMode;
	}
	
	
	
	
}