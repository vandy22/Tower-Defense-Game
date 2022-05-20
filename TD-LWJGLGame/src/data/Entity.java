package data;

public interface Entity {
	public float getX();
	public float getY();
	public int getW();
	public int getH();
	public void setX(float x);
	public void setY(float y);
	public void setW(int w);
	public void setH(int h);
	public void update();
	public void draw();
}
