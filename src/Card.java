
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Card extends Rectangle implements Comparable<Card>
{
	private int suit, value;
	private String Ssuit, Svalue;
	private boolean faceUp;
	private BufferedImage Icards;
	private int pilenum=-1;
	
	
	public Card(int value, int suit, BufferedImage cardFront)
	{
		height = SolitairePanel.cardHeight; width = SolitairePanel.cardWidth;
		this.Icards = cardFront;
		this.suit=suit;
		this.value=value;
		faceUp = true;
		findString();
	}
	@Override
	public int compareTo(Card c)
	{
		return c.getValue()-this.getValue();
	}
	
	public void setPilenum(int p)
	{pilenum = p;}
	
	public int getPilenum()
	{return pilenum;}
	
	public void findString()
	{
		switch(suit)
		{
		case 1:
			Ssuit="Clubs";
			break;
		case 2:
			Ssuit="Diamonds";
			break;
		case 3:
			Ssuit="Hearts";
			break;
		case 4:
			Ssuit="Spades";
			break;
		default:
			Ssuit="This is not a suit something is wrong";
		}
		
		switch(value)
		{
		case 1:
			Svalue="Ace";
			break;
		case 11:
			Svalue="Jack";
			break;
		case 12:
			Svalue="Queen";
			break;
		case 13:
			Svalue="King";
			break;
		default:
			Svalue=Integer.toString(value);
		}	
	}
	
	public void turnOver()
	{faceUp = !faceUp;}
	public void faceDown()
	{faceUp = false;}
	public void faceUp()
	{faceUp = true;}
	public boolean isFaceUp()
	{return faceUp;}
	
	// represents this Card in the following manner
	// if the card is the Ace of Spades, then 
	// it returns "Ace of Spades". 2 - 10 can be represented
	// as "2 of Hearts" or "Two of Hearts".  Your choice.
	@Override
	public String toString()
	{	
		return Svalue+" of "+Ssuit;
	}
	
	public int getSuit()
	{
		return suit;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void draw(Graphics g,int x,int y)
	{
		super.x=x; super.y=y;
		int cx;    // x-coord of upper left corner of the card inside cardsImage
		int cy;    // y-coord of upper left corner of the card inside cardsImage
		if (faceUp) 
		{
		    cx = (value-1)*395;
		    cy = (suit-1)*615;
		}
		else 
		{
			cy = 4*615;   // cords for a face-down card.
			cx = 2*395;
		}
		g.drawImage(Icards,x,y,x+width,y+height,cx,cy,cx+395,cy+615,null);
		//70-325
	}
}
