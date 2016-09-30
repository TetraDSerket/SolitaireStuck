import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.*;



public class Deck extends Rectangle 
{

	ArrayList<Card> cardDex = new ArrayList<Card>();
	BufferedImage Icards; 
	int cardUp;
	
	public Deck(int x,int y) //used for Piles
	{
		super(x,y,SolitairePanel.cardWidth,SolitairePanel.cardHeight);
		cardUp=0;
	}
	
	public Deck(BufferedImage Icards, int x, int y)
	{
		super(x,y,100+SolitairePanel.cardWidth ,SolitairePanel.cardHeight);
		this.Icards = Icards;
		initDeck(Icards);
		cardUp = 0;
		//shuffle();
	}

	public void shuffle() 
	{
		ArrayList<Card> newDex = new ArrayList<Card>();
		for(int i=cardDex.size(); i>0; i--)
		{
			double size = (double) cardDex.size();
			int r = (int) (Math.random()*size);
			newDex.add(cardDex.get(r));
			cardDex.remove(r);
		}
		cardDex = newDex;
	}	
/* Initializes the data structure in which the cards are stored
*  Initializes the Cards in this deck, constructing them all and
*  placing them into the data structure holding them.
*/			
	private void initDeck(BufferedImage Icards) 
	{
		for(int v=1; v<=13; v++)
		{
			for(int s=1; s<=4; s++)
			{
				cardDex.add(new Card(v,s,Icards));
			}
		}
	}
// returns the number of Cards left in the Deck
	public int size()
	{
		return cardDex.size();
	}

/*
* Returns the num Card from this deck, removing it from its  
*  data structure
*/
	public Card deal(int num)
	{
		if(!cardDex.isEmpty()&&num<cardDex.size())
		{
			Card top = cardDex.get(num);
			cardDex.remove(num);
			return top;
		}
		else
			return null;
	}
	
	public ArrayList<Card> giveMultiple(int num)
	{
		ArrayList<Card> removeDex = new ArrayList<Card>();
		for(int i=0; i<num; i++)
		{
			removeDex.add(deal(cardDex.size()-1));
		}
		//cardDex.removeAll(removeDex);
		return removeDex;
	}
	
/*
 * Adds the Cards from pile to the data structure holding Cards in this
 * Deck.  The Cards from pile should be added to the "bottom" of this Deck.
 * You do not have to worry about duplicate Cards
 */
	public void add(List<Card> pile)
	{
		cardDex.addAll(pile);
	}
	public void add(Card c)
	{
		cardDex.add(c);
	}
/* This returns a String representation of this Deck.  It should return 
*  a String built out of the Cards that the deck contains
*/
	@Override
	public String toString()
	{
		String s = "size: "+cardDex.size()+" ";
		for(Card c: cardDex)
		{
			s += c.toString()+"; ";
		}
		return s;
	}
	
	public void nextCard()
	{
		if(!cardDex.isEmpty())
		{
			if(cardUp<cardDex.size()-1)
				cardUp++;
			else if(cardUp>=cardDex.size()-1)
				cardUp=0;
		}
	}
	
	public void draw(Graphics g)
	{
		int cy, cx;
		
		cy = 4*615;   // coords for a face-down card.
		cx = 2*395;
		
		if(cardDex.size()>1)
		{
			g.drawImage(Icards,x,y,x+SolitairePanel.cardWidth,y+SolitairePanel.cardHeight,cx,cy,cx+395,cy+615,null);
			cardDex.get(cardUp).draw(g,x+width-SolitairePanel.cardWidth,y);
		}
		else if(cardDex.size()==1)
		{
			drawEmptyPlace(g,x,y);
			cardDex.get(cardUp).draw(g,x+width-SolitairePanel.cardWidth,y);
		}
		else
		{
			drawEmptyPlace(g,x,y);
			drawEmptyPlace(g,x+width-SolitairePanel.cardWidth,y);
		}
	}
	
	public void drawEmptyPlace(Graphics g, int x, int y)
	{
		g.setColor(new Color(0,88,0));
		g.fillRoundRect(x, y, SolitairePanel.cardWidth, height, SolitairePanel.cardWidth/4, height/4);
		g.setColor(Color.green);
		g.drawRoundRect(x, y, SolitairePanel.cardWidth, height, SolitairePanel.cardWidth/4, height/4);
	}
	
	public boolean contains(int pointx, int pointy)
	{
		if(pointx>x&&pointx<x+width&&pointy>y&&pointy<y+height)
		{
			return true;
		}
		return false;
	}
	
	public ArrayList<Card> pickUpCard()
	{
		ArrayList<Card> List = new ArrayList<Card>();
		List.add(deal(cardUp));
		return List;
	}
	
	
	public Card dealCardUp()
	{
		Card c = deal(cardUp);
		if(cardUp>0)
			cardUp--;
		else
			cardUp=cardDex.size()-1;
		return c;
	}
}
