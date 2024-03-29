package userInterface;
import static additional.Quick.*;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.*;

public class UI {
	
	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	private TrueTypeFont font;
	private Font awtFont;
	
	public UI() {
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<Menu>();
		awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}

	public void drawString(int x, int y, String text, Color color) {
		font.drawString(x, y, text, color);
	}
	
	public void addButton(String name, String textureName, int x, int y) {
		buttonList.add(new Button(name, QuickLoad(textureName), x, y));
	}

	public boolean isButtonClicked(String buttonName) { // method to ensure mouse is over button
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
				mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
			return true;
		}
		return false;
	}
	
	private Button getButton(String buttonName) {
		for (Button b: buttonList) {
			if (b.getName().equals(buttonName)) {
				return b;
			}
		}
		return null;
	}
	
	public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
		menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
	}
	
	public Menu getMenu(String name) {
		for (Menu m: menuList)
			if(name.equals(m.getName()))
				return m;
		return null;
	}
	
	public void draw() {
		for(Button b: buttonList)
			DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		for (Menu m: menuList)
			m.draw();
	}
	
	public class Menu {
		
		String name;
		private ArrayList<Button> menuButtons;
		private int x, y, buttonAmount, optionsWidth, padding;
		
		public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.optionsWidth = optionsWidth;
			this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
			this.buttonAmount = 0;
			this.menuButtons = new ArrayList<Button>();
		}
		
		public void addButton(Button b) {
			if (optionsWidth !=0)
				b.setY(y + (buttonAmount / optionsWidth) * TILE_SIZE);
			b.setX(x + (buttonAmount % 2) * (padding + TILE_SIZE));
			buttonAmount++;
			menuButtons.add(b);
		}
		
		public boolean isButtonClicked(String buttonName) { // method to ensure mouse is over button
			Button b = getButton(buttonName);
			float mouseY = HEIGHT - Mouse.getY() - 1;
			if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
					mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
				return true;
			}
			return false;
		}
		
		private Button getButton(String buttonName) {
			for (Button b: menuButtons) {
				if (b.getName().equals(buttonName)) {
					return b;
				}
			}
			return null;
		}
		
		public void draw() {
			for (Button b: menuButtons)
				DrawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
		
		public void drawString(int x, int y, String text) {
			font.drawString(x, y, text);
		}
		
		public String getName() {
			return name;
		}
	}
}

