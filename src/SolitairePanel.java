
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;


public class SolitairePanel extends JPanel
{
	public static int cardHeight=140, cardWidth=85;
	GameEvents gameEvents = new GameEvents();
	Timer gameTimer = new Timer(1, gameEvents);
	Deck pokeDex;
	ArrayList<Pile> pileList = new ArrayList<Pile>();
	ArrayList<FinalPile> finalPileList = new ArrayList<FinalPile>();
	CardsOnMouse c = new CardsOnMouse();
	//ArrayList<Card> cardsOnMouse = new ArrayList<Card>();
	BufferedImage IHomestuck = null, Icover = null, Iskaia = null;
	int mouseClicks, finishedCards, count, velx=1;
	boolean gameFinished;
	
	public SolitairePanel()
	{
		try 
		{
			IHomestuck = ImageIO.read(getClass().getResource("Homestuck1.png"));
			Icover = ImageIO.read(getClass().getResource("cover.jpg"));
			Iskaia = ImageIO.read(getClass().getResource("skaia.png"));
		}
		catch (IOException e)
		{System.out.println("Pictures failed to load");}
		
		pokeDex = new Deck(readImages(), 50, 50);
		pokeDex.shuffle();
		for(int i=0; i<7; i++)
		{
			ArrayList<Card> cardList = new ArrayList<Card>(pokeDex.giveMultiple(i+1));
			for(Card c: cardList)
			{
				c.setPilenum(i);
			}
			Pile temp = new Pile(cardList, i*100+50, 200);
			pileList.add(temp);
		}
		for(int i=0; i<4; i++)
		{
			FinalPile temp = new FinalPile(i*100+350,50);
			finalPileList.add(temp);
		}
		this.addKeyListener(gameEvents);
		this.addMouseListener(gameEvents);
		gameTimer.start();
		this.setPreferredSize(new Dimension(800,650));
		this.setBackground(new Color(0,50,200));
		this.setBackground(new Color(50,50,50));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(!gameFinished)
		{
			g.drawImage(Iskaia,0-count/6,0,null);
//			g.drawImage(IHomestuck,0,this.getHeight()-(int)(mouseClicks*4),null);
			g.drawImage(IHomestuck,0,this.getHeight()-(int)(finishedCards*195.0/52.0),null);
			pokeDex.draw(g);
			g.setColor(Color.BLUE);
			g.setFont(new Font("Mysterious Mr.L", Font.PLAIN, 14));
			g.drawString("Mouse Clicks: "+mouseClicks, 45, 20); //+" Finished Cards: "+finishedCards
			g.drawString("Deck Size: "+Integer.toString(pokeDex.size()), 50, 40);
			for(Pile p: pileList)
			{
				p.draw(g);
			}
			for(FinalPile f: finalPileList)
			{
				f.draw(g);
			}
			double x = MouseInfo.getPointerInfo().getLocation().getX()-this.getLocationOnScreen().getX();
			double y = MouseInfo.getPointerInfo().getLocation().getY()-this.getLocationOnScreen().getY();
			c.draw(g,x,y);
		}
		if(gameFinished)
		{
			int x,y,width,height,aw,ah;
			x=190; y=280; width=415; height=110; aw=30; ah=30;
			g.drawImage(Icover,0,0,null);
			g.setColor(Color.black);
			g.fillRoundRect(x,y,width,height,aw,ah);
			g.setColor(Color.green);
			g.drawRoundRect(x,y,width,height,aw,ah);
			g.setColor(new Color( (int) (Math.random()*255.0), (int) (Math.random()*50.0+200),(int) (Math.random()*255.0)));
			g.setFont(new Font("TitleFont", Font.PLAIN, 60));
			g.drawString("You Won!!!!!!!!" ,x+10, y+60); //800 650
			g.setFont(new Font("ScoreFont", Font.PLAIN, 30));
			g.setColor(Color.orange);
			g.drawString("Your Score is: "+(1104-mouseClicks), x+75, y+95);
		}
	}
	
	public BufferedImage readImages()
	{
		BufferedImage Icards = null;
		try 
		{
			Icards = ImageIO.read(getClass().getResource("cards.png"));
		} 
		catch (IOException e)
		{
			System.out.println("Pictures failed to load");
		}
		return Icards;
	}
	
	public class GameEvents implements ActionListener, KeyListener, MouseListener
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			repaint();
			if(count<0)
				velx=+1;
			if(count>Iskaia.getWidth()-800)
				velx=-1;
			count=count+velx;	
			if(finishedCards==52&&!gameFinished)
			{
				//JOptionPane.showConfirmDialog(null, "Game Complete!!!!!!!! You used "+mouseClicks+" clicks. Click yes to see your score.");
				gameFinished = true;
			}
		}
		
		@Override
		public void keyPressed(KeyEvent arg0) 
		{
	
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			mouseClicks++; //finishedCards=finishedCards+13;
			if(pokeDex.contains(e.getX(), e.getY()))
			{
				if(e.getX()<pokeDex.getX()+pokeDex.getWidth()/2)
				{
					pokeDex.nextCard();
				}
				else
				{
					if(c.cardsOnMouse.isEmpty())
					{
						c.cardsOnMouse.add(pokeDex.dealCardUp());
					}
					else
					{
						if(c.cardsOnMouse.get(0).getPilenum()==-1)
						{
							//cardsOnMouse.get(0).setPilenum(-2);
							pokeDex.cardDex.add(pokeDex.cardUp,c.cardsOnMouse.get(0));
							c.cardsOnMouse.clear();
						}
					}
				}
			}
			for(int i=0; i<pileList.size(); i++)
			{
				if(pileList.get(i).contains(e.getX(), e.getY()))
				{
					if(c.cardsOnMouse.isEmpty())
					{
						c.cardsOnMouse.addAll(pileList.get(i).dealCardsUpToPoint(e.getY()));
					}
					else
					{
						if(pileList.get(i).canAdd(c.cardsOnMouse, i))
						{
							pileList.get(i).add(c.cardsOnMouse);
							for(Card x: c.cardsOnMouse)
							{
								x.setPilenum(i);
							}
							c.cardsOnMouse.clear();
							for(int j=0; j<pileList.size(); j++)
								pileList.get(j).turnOverTopCard();
						}
					}
				}
			}
			for(int i=0; i<finalPileList.size(); i++)
			{
				if(finalPileList.get(i).contains(e.getX(), e.getY()))
				{
					if(c.cardsOnMouse.size()==1)
					{
						if(finalPileList.get(i).canAdd(c.cardsOnMouse))
						{
							finishedCards++;
							finalPileList.get(i).add(c.cardsOnMouse);
							c.cardsOnMouse.clear();
						}
						for(int j=0; j<pileList.size(); j++)
							pileList.get(j).turnOverTopCard();
					}
					else if(c.cardsOnMouse.isEmpty())
					{
						if(!finalPileList.get(i).cardDex.isEmpty())
						{
							c.cardsOnMouse.add(finalPileList.get(i).dealTopCard());
							finishedCards--;
						}
					}
				}
			}
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}

	public static void main(String[] args)
	{
		JFrame f = new JFrame("SolitaireStuck");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SolitairePanel p = new SolitairePanel();
		f.add(p);
		f.pack();
		f.validate();
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(800, 650);
		p.requestFocusInWindow();
	}
	
}
