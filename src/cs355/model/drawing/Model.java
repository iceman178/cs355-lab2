package cs355.model.drawing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Model extends CS355Drawing {

	//Use a singleton so that the model can be accessed by the view when repainting
	private static Model _instance;
	
	private Shape.type currentMode;
	private Color selectedColor;
	private ArrayList<Shape> shapes;
	
	//If the model had not been initialized, it will be.
	public static Model instance() 
	{
		if (_instance == null) 
		{
			_instance = new Model();
		}
		return _instance;
	}
	
	public Model() 
	{
		selectedColor = Color.WHITE;
		shapes = new ArrayList<Shape>();
		currentMode = Shape.type.NONE;
	}
	
	public Shape getLastShape() {
		return shapes.get(shapes.size() - 1);
	}
	
	public void updateShape(int index) {
		// TODO need to be able to update any shape
	}
	
	public void updateLastShape(Shape newShape) {
		shapes.remove(shapes.size() - 1);
		shapes.add(newShape);
	}
	
	//Notifies the observers
	public void notifyObservers() {
		super.notifyObservers();
		System.out.println("Update Issued");
	}

	@Override
	public Shape getShape(int index) {
		return shapes.get(index);
	}

	@Override
	public int addShape(Shape s) {
		shapes.add(s);
		return shapes.size();
	}

	@Override
	public void deleteShape(int index) {
		shapes.remove(index);
	}

	@Override
	public void moveToFront(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movetoBack(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveForward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBackward(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public List<Shape> getShapesReversed() {
		return shapes;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		this.shapes = (ArrayList<Shape>) shapes;
	}
	
	public static Model get_instance() {
		return _instance;
	}

	public static void set_instance(Model _instance) {
		Model._instance = _instance;
	}

	public Shape.type getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(Shape.type currentMode) {
		this.currentMode = currentMode;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	
	
	
	
}