import java.awt.event.*;
import java.awt.*;
import java.awt.font.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Pong extends JPanel implements ActionListener, KeyListener {
	static boolean starting = true, processing = false, gameover = false;
	static int ballX = 10, ballY = 10;
	static int xinc = 2, yinc = 1; // speed of moving the ball
	static int paddleX = 0, paddleY = 250;
	static int paddleTwoX = 580, paddleTwoY = 250;
	static int player1score = 0, player2score = 0;
	static int width = 600, height = 550;
	String player1, player2;

	// game speed
	int[] levels = { 1000 / 60, 1000 / 80, 1000 / 100, 1000 / 120, 1000 / 200 };
	public int level;

	// game victory condition
	private int score;

	static Frame f; // frame is global so actionPerformed can call repaint

	public Pong() {
		// set.Background(Color.BLACK);
		addKeyListener(this);
		setFocusable(true);
		player1 = JOptionPane.showInputDialog("Please type in Player 1's name");
		player2 = JOptionPane.showInputDialog("Please type in Player 2's name");
		try {
			level = Integer
					.parseInt(JOptionPane
							.showInputDialog("Please type in game level\n1.very slow\n2.slow\n3.fast\n4.very fast\n5.fastest\nattention: you must input 1-5"));
			score = Integer
					.parseInt(JOptionPane
							.showInputDialog("Please type in game victory condition\ne.g if you input 5, a player will win when he(she) get 5 score"));
		}
		catch (Exception e) {
			// if user doesn't input integer, set level 1
			// and set victory condition for 5
			this.level = 0;
			score = 5;
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		// title screen
		if (starting) {
			// draw the title of PONG
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(Color.gray);
			g.drawString("Pong Game", 190, 150);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			g.drawString("Press 'SPACE' to start", 200, 300);
			// draw control
			g.setColor(Color.gray);
			g.setFont(new Font(Font.DIALOG, Font.ROMAN_BASELINE, 15));
			g.drawString("Player1 control: w(up) s(down) a(left) d(right)",
					160, 350);
			g.drawString("Player1 control: arrows:  up  down  left  right",
					160, 370);
		}
		else if (processing) {
			// draw the ball
			g.setColor(Color.red);
			g.fillOval(ballX, ballY, 22, 22);
			// draw the paddles
			g.setColor(Color.gray);
			g.fillRect(paddleX, paddleY, 15, 90);
			g.fillRect(paddleTwoX, paddleTwoY, 15, 90);
			g.setColor(Color.gray);
			// draw the names that players enter
			g.drawString("player1: " + player1, 70, 20);
			g.drawString("player2: " + player2, 480, 20);
			// draw the scores
			g.setColor(Color.MAGENTA);
			g.drawString("Score " + player1score, 70, 40);
			g.drawString("Score " + player2score, 480, 40);
			// draw parting line
			g.setColor(Color.gray);
			g.drawLine(width / 2 - 5, 20, width / 2 - 5, height - 20);
		}
		else if (gameover) {
			g.setColor(Color.red);
			g.fillOval(ballX, ballY, 22, 22);
			// draw the paddles
			g.setColor(Color.gray);
			g.fillRect(paddleX, paddleY, 15, 90);
			g.fillRect(paddleTwoX, paddleTwoY, 15, 90);
			g.setColor(Color.gray);
			// draw the names that players enter
			g.drawString("player1: " + player1, 70, 20);
			g.drawString("player2: " + player2, 480, 20);
			// draw the scores
			g.setColor(Color.MAGENTA);
			g.drawString("Score " + player1score, 70, 40);
			g.drawString("Score " + player2score, 480, 40);
			// draw winner
			g.setColor(Color.gray);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			g.drawString("Winner: "
					+ (player1score == score ? player1 : player2) + "!", 200,
					height / 2 - 10);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// reverse ball bounce
		if ((ballX >= width - 22) || (ballX <= 0))
			xinc = -xinc;
		if ((ballY >= height - 22) || (ballY <= 0))
			yinc = -yinc;
		// move ball along
		ballX += xinc;
		ballY += yinc;
		// keep the left paddle inside the screen
		if (paddleX < 1)
			paddleX = 1;
		else if (paddleX > 585)
			paddleX = 585;
		if (paddleY < 0)
			paddleY = 0;
		else if (paddleY > 460)
			paddleY = 460;
		// keep the right paddle inside the screen
		if (paddleTwoX < 1)
			paddleTwoX = 1;
		else if (paddleTwoX > 585)
			paddleTwoX = 585;
		if (paddleTwoY < 0)
			paddleTwoY = 0;
		else if (paddleTwoY > 460)
			paddleTwoY = 460;
		if ((ballX == paddleX + 15) && (ballY >= paddleY - 15)
				&& (ballY <= paddleY + 90)) {
			xinc = -xinc;
		}
		if ((ballX == paddleX - 22) && (ballY >= paddleY - 15)
				&& (ballY <= paddleY + 90)) {
			xinc = -xinc;
		}
		if ((ballY == paddleY + 90) && (ballX >= paddleX - 15)
				&& (ballX <= paddleX)) {
			yinc = -yinc;
		}
		if ((ballY == paddleY - 22) && (ballX >= paddleX - 15)
				&& (ballX <= paddleX)) {
			yinc = -yinc;
		}
		if ((ballX == paddleTwoX + 15) && (ballY >= paddleTwoY - 15)
				&& (ballY <= paddleTwoY + 90)) {
			xinc = -xinc;
		}
		if ((ballX == paddleTwoX - 22) && (ballY >= paddleTwoY - 15)
				&& (ballY <= paddleTwoY + 90)) {
			xinc = -xinc;
		}
		if ((ballY == paddleTwoY + 90) && (ballX >= paddleTwoX - 15)
				&& (ballX <= paddleTwoX)) {
			yinc = -yinc;
		}
		if ((ballY == paddleTwoY - 22) && (ballX >= paddleTwoX - 15)
				&& (ballX <= paddleTwoX)) {
			yinc = -yinc;
		}
		if ((ballX == paddleX - 22) && (ballY == paddleY - 22)
				|| (ballX == paddleX + 15) && (ballY == paddleY - 22)
				|| (ballX == paddleX - 22) && (ballY == paddleY + 90)
				|| (ballX == paddleX + 15) && (ballY == paddleY + 90)) {
			xinc = -xinc;
			yinc = -yinc;
		}
		if ((ballX == paddleTwoX - 22) && (ballY == paddleTwoY - 22)
				|| (ballX == paddleTwoX + 15) && (ballY == paddleTwoY - 22)
				|| (ballX == paddleTwoX - 22) && (ballY == paddleTwoY + 90)
				|| (ballX == paddleTwoX + 15) && (ballY == paddleTwoY + 90)) {
			xinc = -xinc;
			yinc = -yinc;
		}
		// player 1 scores 1 point when the ball bounce to the right side
		if (ballX == width - 22) {
			player1score++;
		}
		// player 2 scores 1 point when the ball bounce to the left side
		if (ballX == 0) {
			player2score++;
		}
		// the ball stop moving when there is a winner
		if ((player1score == score) || (player2score == score)) {
			xinc = 0;
			yinc = 0;
			ballX = 50;
			ballY = 50;
			starting = false;
			processing = false;
			gameover = true;
		}
		f.repaint(); // stuffed has moved, it would repaint itself
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// press 'SPACE' to start playing in the title screen
		if (starting) {
			if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
				starting = false;
				processing = true;
			}
		}
		// allow the left paddle moving around the screen
		if (ke.getKeyCode() == KeyEvent.VK_W)
			paddleY -= 30;
		else if (ke.getKeyCode() == KeyEvent.VK_S)
			paddleY += 30;
		if (ke.getKeyCode() == KeyEvent.VK_A)
			paddleX -= 30;
		else if (ke.getKeyCode() == KeyEvent.VK_D)
			paddleX += 30;
		// allow the right paddle moving around the screen
		if (ke.getKeyCode() == KeyEvent.VK_UP)
			paddleTwoY -= 30;
		else if (ke.getKeyCode() == KeyEvent.VK_DOWN)
			paddleTwoY += 30;
		if (ke.getKeyCode() == KeyEvent.VK_LEFT)
			paddleTwoX -= 30;
		else if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
			paddleTwoX += 30;
		f.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	// Main
	public static void main(String args[]) {
		Pong p = new Pong(); // create an instance of pong
		f = new JFrame(); // make a frame
		f.add(p); // put pong into the frame
		f.setResizable(false);
		f.setSize(width, height + 25); // declare size of the frame
		f.setVisible(true);
		f.setBackground(Color.black);
		Timer t = new Timer(p.levels[p.level - 1], p); // make timer call
														// actionperformed
		t.start(); // starts the timer
	}
}
