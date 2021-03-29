package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	private int ballPosX = 120;
	private int ballPosY =350;
	private int ballDirX = -1;
	private int ballDirY = -2;

	private MapGenerator map;
	
	public GamePlay(){
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	public void paint(Graphics g) {
		//Background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		//Borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);	
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		//draw bricks
		map.draw((Graphics2D)g);
		//draw score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		if(totalBricks <= 0) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Win, Score: " + score, 260, 300);			
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		if(ballPosY > 570) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
				ballDirY = -ballDirY;
			}
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0;j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickWidth + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						
						if(ballRect.intersects(rect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballPosX + 19 <= rect.x || ballPosX + 1 >= rect.x + rect.width) {
								ballDirX *= -1;
							}else {
								ballDirY *= -1;
							}
							break A;
						}
					}
				}
			}
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			if (ballPosX < 0) {
				ballDirX = -ballDirX;
			}
			if (ballPosX > 670) {
				ballDirX = -ballDirX;
			}
			if (ballPosY < 0) {
				ballDirY = -ballDirY;
			}
		}
		repaint();		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			if(playerX >= 600) {
				playerX = 600;
			}else {
				moveRight();
				}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(playerX < 10) {
				playerX = 10;
			}else {
				moveLeft();				
			}		
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				ballPosX = 120;
				ballPosY =350;
				ballDirX = -1;
				ballDirY = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
		}
	}
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
