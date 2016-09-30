import java.awt.Graphics;
import java.util.ArrayList;


public class Pile extends Deck 
{
	int gap = 22;
	Pile(ArrayList<Card> startingCards, int x, int y)
	{
		super(x,y);
		this.add(startingCards);
		for(Card c: cardDex)
		{
			c.faceDown();
		}
		cardDex.get(cardDex.size()-1).turnOver();
	}
	
	public void draw(Graphics g)
	{
		resizePile();
		if(cardDex.isEmpty())
		{
			drawEmptyPlace(g,x,y);
		}
		for(int i=0; i<cardDex.size(); i++)
		{
			cardDex.get(i).draw(g, x, y+i*gap);
		}
	}
	
	public void resizePile()
	{
		height = SolitairePanel.cardHeight+cardDex.size()*gap;
	}
	
	public void turnOverTopCard()
	{
		if(!cardDex.isEmpty()&&!cardDex.get(cardDex.size()-1).isFaceUp())
			cardDex.get(cardDex.size()-1).faceUp();
	}
	
	public ArrayList<Card> dealFaceUpCards()
	{
		ArrayList<Card> removeList = new ArrayList<Card>();
		for(int i=0; i<cardDex.size(); i++)
		{
			if(cardDex.get(i).isFaceUp())
			{
				removeList.add(cardDex.get(i));
			}
		}
		cardDex.removeAll(removeList);
		return removeList;
	}
	
	public ArrayList<Card> dealCardsUpToPoint(int yval)
	{
		ArrayList<Card> removeList = new ArrayList<Card>();
		int cnum=yval-200;
		cnum=cnum/gap;
		if(cnum>=cardDex.size())
			cnum=cardDex.size()-1;
		if(cardDex.get(cnum).isFaceUp())
		{
			for(int i=cnum; i<cardDex.size(); i++)
			{
				removeList.add(cardDex.get(i));
			}
		}
		cardDex.removeAll(removeList);
		return removeList;
	}
	
	public boolean canAdd(ArrayList<Card> cardsOnMouse, int pileNum)
	{
		int cVal = cardsOnMouse.get(0).getValue();
		if(cardDex.size()==0&&cVal==13)
		{
			return true;
		}
		if(cardsOnMouse.get(0).getPilenum()==pileNum)
		{
			return true;
		}
		int pSuit = cardDex.get(cardDex.size()-1).getSuit();
		int cSuit = cardsOnMouse.get(0).getSuit();
		int pVal = cardDex.get(cardDex.size()-1).getValue();
		if(cVal+1==pVal)
		{
			if((cSuit==1||cSuit==4)&&(pSuit==2||pSuit==3))
			{
				return true;
			}
			else if((cSuit==2||cSuit==3)&&(pSuit==4||pSuit==1))
			{
				return true;
			}
		}
		return false;
	}
}
