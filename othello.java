 package othelloGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Game.Othello;


class othello extends JFrame implements ActionListener {

	private final static int xDir[] = { -1, -1, -1, 0, 1, 1, 1, 0 };
	private final static int yDir[] = { -1, 0, 1, 1, 1, 0, -1, -1 };
	public final static int Player1Win = 1;
	public final static int Player2Win = 2;
	public final static int DRAW = 3;
	public final static int INCOMPLETE = 4;
	boolean player1Turn = true;

	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame("Othello");
	JPanel[] row = new JPanel[9];
	JButton[] button = new JButton[64];
	JTextArea display = new JTextArea(2, 25);
	Dimension displayDimension = new Dimension(300, 40);
	Dimension regularDimension = new Dimension(75, 75);
	Font font = new Font("Times new Roman", Font.BOLD, 30);

	public othello() {
		setDesign();
		setSize(700, 720);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(9, 8);
		setLayout(grid);
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 1, 1);
		for (int i = 0; i < 9; i++)
			row[i] = new JPanel();

		row[0].setLayout(f1);

		for (int i = 1; i < 9; i++)
			row[i].setLayout(f2);

		for (int i = 0; i < 64; i++) {
			button[i] = new JButton();
			button[i].setText(" ");
			button[i].addActionListener(this);
			button[i].setBackground(Color.yellow);
		}
		display.setFont(font);
		display.setEditable(false);
		display.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		display.setPreferredSize(displayDimension);

		for (int i = 0; i < 64; i++)
			button[i].setPreferredSize(regularDimension);

		row[0].add(display);
		add(row[0]);

		for (int i = 1; i < 9; i++) {
			for (int j = 8 * (i - 1); j < 8 * i; j++) {
				row[i].add(button[j]);
				button[j].setEnabled(false);
			}
			add(row[i]);
		}
		button[27].setEnabled(true);
		button[27].setBackground(Color.WHITE);
		button[28].setEnabled(true);
		button[28].setBackground(Color.BLACK);
		button[35].setEnabled(true);
		button[35].setBackground(Color.BLACK);
		button[36].setEnabled(true);
		button[36].setBackground(Color.WHITE);
		setVisible(true);
		turn();
	}

	private void setDesign() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void turn() {
		if (player1Turn) {
			display.setText("Player 1 Turn : White Box");
			unlock(Color.WHITE, Color.BLACK);
		} else {
			
			display.setText("Player 2 Turn : Black Box");
			unlock(Color.BLACK, Color.WHITE);
		}
	}

	
	private void unlock(Color symbol1, Color symbol2) {
		for (int i = 0; i < 8; i++) {
			for (int j = i * 8; j < (i + 1) * 8; j++) {
				
				
				if (button[j].isEnabled() && button[j].getBackground() == symbol2) {
					if (i * 8 >= 0 && (i + 1) * 8 < 64 && j - 1 >= i * 8 && j + 1 < (i + 1) * 8) {
						if (button[j - 1].getBackground() == symbol1) {
							button[j + 1].setEnabled(true);
						}
						if (button[j + 1].getBackground() == symbol1) {
							button[j - 1].setEnabled(true);
						}
					}
				
					
					if ((i * 8) - 8 >= 0 && ((i + 1) * 8) + 8 < 64 && j - 8 >= (i * 8) - 7
							&& j + 8 < ((i + 1) * 8) + 7) {
						if (button[j + 8].getBackground() == symbol1) {
							button[j - 8].setEnabled(true);
						}
						if (button[j - 8].getBackground() == symbol1) {
							button[j + 8].setEnabled(true);
						}
					}
					if ((i * 8) - 7 >= 0 && ((i + 1) * 8) + 7 < 64 && j - 7 >= (i * 8) - 6
							&& j + 7 < ((i + 1) * 8) + 6) {
						if (button[j + 7].getBackground() == symbol1) {
							button[j - 7].setEnabled(true);
						}
						if (button[j - 7].getBackground() == symbol1) {
							button[j + 7].setEnabled(true);
						}
					}
					if ((i * 8) - 9 >= 0 && ((i + 1) * 8) + 9 < 64 && j - 9 >= (i * 8) - 8
							&& j + 9 < ((i + 1) * 8) + 8) {
						if (button[j + 9].getBackground() == symbol1) {
							button[j - 9].setEnabled(true);
						}
						if (button[j - 9].getBackground() == symbol1) {
							button[j + 9].setEnabled(true);
						}
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 8; i++) {
			for (int j = 8 * i; j < 8 * (i + 1); j++) {
				if (e.getSource() == button[j]) {
					start(i, j - (i * 8));
				}
			}
		}
	}

	private void start(int x, int y) {
		if (gamestatus() == INCOMPLETE) {
			boolean done = false;
			if (player1Turn) {
				while (!done) {
					move(Color.WHITE, Color.BLACK, x, y);
					done = true;
				}
				player1Turn = false;
			} else {
				while (!done) {
					move(Color.BLACK, Color.WHITE, x, y);
					done = true;
				}
				player1Turn = true;
			}
		} else if (gamestatus() == Player1Win) {
			display.setText("Player 1 Win!!!");
			return;
		} else if (gamestatus() == Player2Win) {
			display.setText("Player 2 Win!!!");
			return;
		} else {
			display.setText("It's A Draw!!!");
			return;
		}
		turn();
	}


	public boolean move(Color symbol1, Color symbol2, int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return false;
		}
		if (button[x * 8 + y].getBackground() == symbol1 || button[x * 8 + y].getBackground() == symbol2) {
			for (int i = 0; i < 8; i++) {
				for (int j = 8 * i; j < 8 * (i + 1); j++) {
					if (button[j].isEnabled() && button[j].getBackground() != symbol1
							&& button[j].getBackground() != symbol2) {
						button[j].setEnabled(false);
					}
				}
			}
			return false;
		}
		
		for (int i = 0; i < xDir.length; i++) {
			int stepX = xDir[i];
			int stepY = yDir[i];
			int count = 0;
			int currentX = x + stepX;
			int currentY = y + stepY;
			while (currentX >= 0 && currentX < 8 && currentY >= 0 && currentY < 8 && count >= 0) {
				if (!button[currentX * 8 + currentY].isEnabled()) {
					break;
				} else if (button[currentX * 8 + currentY].isEnabled()
						&& button[currentX * 8 + currentY].getBackground() != symbol1
						&& button[currentX * 8 + currentY].getBackground() != symbol2) {
					break;
				} else if (button[currentX * 8 + currentY].getBackground() == symbol2) {
					count++;
					currentX += stepX;
					currentY += stepY;
				} else {
					currentX -= stepX;
					currentY -= stepY;
					if (count >= 0) {
						while (count >= 0) {
							button[currentX * 8 + currentY].setBackground(symbol1);
							currentX -= stepX;
							currentY -= stepY;
							count--;
						}
					}
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 8 * i; j < 8 * (i + 1); j++) {
				if (button[j].isEnabled() && button[j].getBackground() != symbol1
						&& button[j].getBackground() != symbol2) {
					button[j].setEnabled(false);
				}
			}
		}
		return true;
	}

	public int gamestatus() {
		int count1 = 0, count2 = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 8 * i; j < 8 * (i + 1); j++) {
				if (button[j].isEnabled() && button[j].getBackground() == Color.WHITE
						|| button[j].getBackground() == Color.BLACK) {
					if (j - 1 >= 0) {
						if (button[j - 1].isEnabled()) {
							if (button[j - 1].getBackground() != Color.WHITE
									&& button[j - 1].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j + 1 < 64) {
						if (button[j + 1].isEnabled()) {
							if (button[j + 1].getBackground() != Color.WHITE
									&& button[j + 1].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j - 8 >= 0) {
						if (button[j - 8].isEnabled()) {
							if (button[j - 8].getBackground() != Color.WHITE
									&& button[j - 8].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j + 8 < 64) {
						if (button[j + 8].isEnabled()) {
							if (button[j + 8].getBackground() != Color.WHITE
									&& button[j + 8].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j - 7 >= 0) {
						if (button[j - 7].isEnabled()) {
							if (button[j - 7].getBackground() != Color.WHITE
									&& button[j - 7].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j + 7 < 64) {
						if (button[j + 7].isEnabled()) {
							if (button[j + 7].getBackground() != Color.WHITE
									&& button[j + 7].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j - 9 >= 0) {
						if (button[j - 9].isEnabled()) {
							if (button[j - 9].getBackground() != Color.WHITE
									&& button[j - 9].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
					if (j + 9 < 64) {
						if (button[j + 9].isEnabled()) {
							if (button[j + 9].getBackground() != Color.WHITE
									&& button[j + 9].getBackground() != Color.BLACK) {
								return INCOMPLETE;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 8 * i; j < 8 * (i + 1); j++) {
				if (button[j].isEnabled()) {
					if (button[j].getBackground() == Color.WHITE) {
						count1++;
					} else if (button[j].getBackground() == Color.BLACK) {
						count2++;
					}
				}
			}
		}
		if (count1 > count2) {
			return Player1Win;
		} else if (count1 < count2) {
			return Player2Win;
		} else {
			return DRAW;
		}
	}
	
	public static void main(String[] args) {
		new othello();
	}
	
	
	
}