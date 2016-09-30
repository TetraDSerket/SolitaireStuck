import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class FinalPile extends Deck
{
	Color c;
	
	FinalPile(int x, int y)
	{
		super(x,y);
	}

	@Override
	public void draw(Graphics g)
	{
		if(!cardDex.isEmpty())
		{
			cardDex.get(cardDex.size()-1).draw(g, x, y);
			g.setColor(Color.white);
			g.drawRect(x, y-20, width, 10);
			g.setColor(c);
			g.fillRect(x, y-20, cardDex.size()*width/13 , 10);
		}
		else
		{
			drawEmptyPlace(g,x,y);
		}
	}
	
	public Card dealTopCard()
	{
		if(!cardDex.isEmpty())
		{
			return this.deal(cardDex.size()-1);
		}
		return null;
	}
	
	public boolean canAdd(ArrayList<Card> cardsOnMouse)
	{
		if(cardsOnMouse.size()==1)
		{
			int cVal = cardsOnMouse.get(0).getValue();
			int cSuit = cardsOnMouse.get(0).getSuit();
			if(cardDex.size()==0&&cVal==1)
			{
				switch(cSuit)
				{
				case 1:
					c= Color.red;
					break;
				case 2:
					c= Color.green;
					break;
				case 3:
					c= Color.blue;
					break;
				case 4:
					c= new Color(150,0,200);
					break;
				}
				return true;
			}
			int pVal = cardDex.get(cardDex.size()-1).getValue();
			int pSuit = cardDex.get(cardDex.size()-1).getSuit();
			if(cSuit==pSuit&&cVal-1==pVal)
			{
				return true;
			}
		}
		return false;
	}
}
