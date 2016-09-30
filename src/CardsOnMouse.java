import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class CardsOnMouse
{
	ArrayList<Card> cardsOnMouse;
	int gap = 20;
	
	CardsOnMouse()
	{
		cardsOnMouse = new ArrayList<Card>();	
	}
	
	public void draw(Graphics g, double x, double y) 
	{
		if(cardsOnMouse.isEmpty())
		{}
		else 
		{
			for(int i=0; i<cardsOnMouse.size(); i++)
			{
				g.setColor(new Color(255,200,0));
				g.fillRoundRect((int) x-38, (int) y+i*gap-58, SolitairePanel.cardWidth+4, SolitairePanel.cardHeight+4, SolitairePanel.cardWidth/7, SolitairePanel.cardHeight/7);
				cardsOnMouse.get(i).draw(g, (int) x-36, (int) y+i*gap-56);
			}
		}
	}
}
