import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

/*
This class can be used as a starting point for creating your Chess game project. The only piece that 
has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;
	boolean whiteKingKilled = false;

	String pieceName;
	JPanel panels;
	JLabel pieces;
	Boolean agentwins = false;
	Boolean white2Move = false;
	String rand = "rand";
	int pieceScore = 0;

	public ChessProject() {
		Dimension boardSize = new Dimension(600, 600);

		// Use a Layered Pane for this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
			else
				square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
		}

		// Setting up the Initial Chess board.
		for (int i = 8; i < 16; i++) {
			pieces = new JLabel(new ImageIcon("WhitePawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(0);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(1);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(6);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishop.png"));
		panels = (JPanel) chessBoard.getComponent(2);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishop.png"));
		panels = (JPanel) chessBoard.getComponent(5);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKing.png"));
		panels = (JPanel) chessBoard.getComponent(3);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
		panels = (JPanel) chessBoard.getComponent(4);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(7);
		panels.add(pieces);
		for (int i = 48; i < 56; i++) {
			pieces = new JLabel(new ImageIcon("BlackPawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(56);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(57);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(62);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishop.png"));
		panels = (JPanel) chessBoard.getComponent(58);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishop.png"));
		panels = (JPanel) chessBoard.getComponent(61);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKing.png"));
		panels = (JPanel) chessBoard.getComponent(59);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackQueen.png"));
		panels = (JPanel) chessBoard.getComponent(60);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(63);
		panels.add(pieces);
	}

	/*
	 * This method checks if there is a piece present on a particular square.
	 */
	public Boolean piecePresent(int x, int y) {
		Component c = chessBoard.findComponentAt(x, y);
		if (c instanceof JPanel) {
			return false;
		} else {
			return true;
		}
	}


	/*
	 * Method used to get the component/piece and assign a value to it based on it's
	 * piece type.
	 */
	public int getPieceNameFromMove(int x, int y) {
		x = x * 75;
		y = y * 75;
		int pieceWeighting = 0; // inital declaration for piece weighting.
		if (chessBoard.findComponentAt(x, y) instanceof JLabel) {
			Component c1 = chessBoard.findComponentAt(x, y);
			JLabel awaitingPiece = (JLabel) c1;
			String name = awaitingPiece.getIcon().toString();
			if (name.contains("BlackKing")) { // Black king found.
				pieceWeighting = 100;
			} else if (name.contains("BlackQueen")) { // Black queen found.
				pieceWeighting = 9;
			} else if (name.contains("BlackRook")) { // Black rook found.
				pieceWeighting = 5;
			} else if (name.contains("BlackBishop")) { // Black bishop found.
				pieceWeighting = 3;
			} else if (name.contains("BlackKnight")) { // Black knight found.
				pieceWeighting = 3;
			} else if (name.contains("BlackPawn")) { // Black pawn found.
				pieceWeighting = 2;
			} else if (name.isEmpty()) { // No piece found.
				pieceWeighting = 1;
			}
		}
		return pieceWeighting;
	}

	/*
	 * This is a method to check if a piece is a Black piece.
	 */
	public Boolean checkWhiteOponent(int landingX, int landingY) {
		Boolean oponent;
		// pieceScore = 0;
		Component c1 = chessBoard.findComponentAt(landingX, landingY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("Black")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;
	}

	/*
	 * This is a method to check if a piece is a White piece.
	 */
	public Boolean checkBlackOponent(int landingX, int landingY) {
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(landingX, landingY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("White")))) {
			oponent = true;
			if(tmp1.contains("King")){
				whiteKingKilled = true;
			}
		} else {
			oponent = false;
		}
		return oponent;
	}

	/*
	 * This method is called when we press the Mouse. So we need to find out what
	 * piece we have selected. We may also not have selected a piece!
	 */
	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel)
			return;

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX() / 75); // get the starting X tile, current X tile.
		startY = (e.getY() / 75); // get the starting Y tile, current Y tile.
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null)
			return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}

	/*
	 * This method is used when the Mouse is released...we need to make sure the
	 * move was valid before putting the piece back on the board.
	 */
	public void mouseReleased(MouseEvent e) {
		if (chessPiece == null)
			return;

		chessPiece.setVisible(false);
		Boolean success = false;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		pieceName = tmp.substring(0, (tmp.length() - 4));
		Boolean validMove = false;

		// Debug code used for seeing piece information and movement information.
		int landingX = (e.getX() / 75);
		int landingY = (e.getY() / 75);
		int xMovement = Math.abs((e.getX() / 75) - startX);
		int yMovement = Math.abs((e.getY() / 75) - startY);
		System.out.println("------------------------");
		System.out.println("The piece that is being moved is: " + pieceName);
		System.out.println("The starting coordinates are: " + " ( " + startX + "," + startY + ")");
		System.out.println("The xMovement is: " + xMovement);
		System.out.println("The yMovement is: " + yMovement);
		System.out.println("The landing coordinates are: " + "( " + landingX + "," + landingY + ")");

		/*
		 * Begining of player move rules/limitations. e is a mouse event.
		 */
		if (pieceName.equals("BlackPawn")) {
			if (white2Move == false) {
				if (startY == 6) { // Black pawn makes its first move from here.
					if ((yMovement == 1 || yMovement == 2) && (startY > landingY) && (xMovement == 0)) {
						if (yMovement == 2) {
							if (!piecePresent(e.getX(), (e.getY())) && (!piecePresent(e.getX(), e.getY() + 75))) {
								validMove = true;
								white2Move = true;
							}
							// else if (piecePresent(e.getX(), e.getY())) {
							// if (checkBlackOponent(e.getX(), e.getY())) {
							// validMove = true;
							// white2Move = true;
							// if (startY == 1) {
							// success = true;
							// }
							// }
							// }
							else {
								validMove = false;
							}
						} else {
							if (!piecePresent(e.getX(), e.getY())) {
								validMove = true;
								white2Move = true;
								if (startY == 1) {
									success = true;
								}
							} else if (piecePresent(e.getX(), e.getY())) {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
									white2Move = true;
									if (startY == 1) {
										success = true;
									}
								}
							} else {
								validMove = false;
							}
						}
					} else if ((yMovement == 1) && (startY > landingY) && (xMovement == 1)) {
						if (piecePresent(e.getX(), e.getY())) {
							if (checkBlackOponent(e.getX(), e.getY())) {
								validMove = true;
								white2Move = true;
								if (startY == 1) {
									success = true;
								}
							}
						}
					}
				} else { // This is where the pawn makes all moves after its inital start one.
					if ((yMovement == 1) && (startY > landingY) && (xMovement == 0)) {
						if (!piecePresent(e.getX(), e.getY())) {
							validMove = true;
							white2Move = true;
							if (startY == 1) {
								success = true;
							}
						} else {
							validMove = false;
						}
					} else if ((yMovement == 1) && (startY > landingY) && (xMovement == 1)) {
						if (piecePresent(e.getX(), e.getY())) {
							if (checkBlackOponent(e.getX(), e.getY())) {
								validMove = true;
								white2Move = true;
								if (startY == 1) {
									success = true;
								}
							}
						}
					}
				}
			}
		}
		// Potentially redo white pawn to use variables like black pawn instead of the
		// raw maths
		else if (pieceName.equals("WhitePawn")) {
			if (white2Move) {
				if (startY == 1) // Game start pawn rules
				{
					if ((startX == (e.getX() / 75))
							&& ((((e.getY() / 75) - startY) == 1) || ((e.getY() / 75) - startY) == 2)) {
						if ((((e.getY() / 75) - startY) == 2)) { // if statement fires if pawn moves by 2 spaces
							if ((!piecePresent(e.getX(), (e.getY()))) && (!piecePresent(e.getX(), (e.getY() - 75)))) {
								validMove = true;
								white2Move = false;
							} else {
								validMove = false;
							}
						} else { // else statement fires if pawn moves by any other spaces (1)
							if ((!piecePresent(e.getX(), (e.getY())))) {
								validMove = true;
								white2Move = false;
							} else {
								validMove = false;
							}
						}
					} else {
						validMove = false;
					}
				} else { // Every turn after the start pawn rules.
					if ((startX - 1 >= 0) || (startX + 1 <= 7)) {
						if ((piecePresent(e.getX(), (e.getY()))) && ((((landingX == (startX + 1) && (startX + 1 <= 7)
								&& (landingY == (startY + 1) && (startY + 1 <= 7)))))
								|| (((landingX == (startX - 1) && (startX - 1 >= 0)
										&& (landingY == (startY + 1) && (startY + 1 >= 0))))))) // Added and made use of
																								// the the landingY
																								// variable
																								// //||(landingY==(startY+1)&&(startY+1>=0))))
						{
							if (checkWhiteOponent(e.getX(), e.getY())) {
								validMove = true;
								white2Move = false;
								if (startY == 6) {
									success = true;
								}
							} else {
								validMove = false;
							}
						} else {
							if (!piecePresent(e.getX(), (e.getY()))) {
								if ((startX == (e.getX() / 75)) && ((e.getY() / 75) - startY) == 1) {
									if (startY == 6) {
										success = true;
									}
									validMove = true;
									white2Move = false;
								} else {
									validMove = false;
								}
							} else {
								validMove = false;
							}
						}
					} else {
						validMove = false;
					}
				}
			}
		}

		else if (pieceName.contains("Knight")) {
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				if ((((landingX == startX + 1) && (landingY == startY + 2))
						|| ((landingX == startX - 1) && (landingY == startY + 2))
						|| ((landingX == startX + 2) && (landingY == startY + 1))
						|| ((landingX == startX - 2) && (landingY == startY + 1))
						|| ((landingX == startX - 1) && (landingY == startY - 2))
						|| ((landingX == startX + 1) && (landingY == startY - 2))
						|| ((landingX == startX + 2) && (landingY == startY - 1))
						|| ((landingX == startX - 2) && (landingY == startY - 1))) && (landingY >= 0 && landingY <= 7)
						&& (landingX >= 0 && landingX <= 7)) {

					if (piecePresent(e.getX(), e.getY())) {
						// if(white2Move == true){
						if (pieceName.contains("White") && white2Move == true) {
							if (checkWhiteOponent(e.getX(), (e.getY()))) {
								validMove = true;
								white2Move = false;
								if (startY == 6) {
									success = true;
								}
							} else {
								validMove = false;
							}
						}
						// }
						else {
							if (pieceName.contains("Black") && white2Move == false) {
								if (checkBlackOponent(e.getX(), (e.getY()))) {
									validMove = true;
									white2Move = true;
									if (startY == 6) {
										success = true;
									}
								} else {
									validMove = false;
								}
							}
						}
					}

					if (!piecePresent(e.getX(), e.getY())) {
						// if(white2Move == true){
						if (pieceName.contains("White") && white2Move == true) {
							white2Move = false;
							validMove = true;
							if (startY == 6) {
								success = true;
							}
						}
						// }
						if (pieceName.contains("Black") && white2Move == false) {
							white2Move = true;
							validMove = true;
							if (startY == 6) {
								success = true;
							}
						}
					}
				} else {
					validMove = false;
				}
			}
		}

		else if (pieceName.contains("Bishop")) {
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				validMove = bishopMovement(e, landingX, landingY, xMovement, yMovement, distance, inTheWay, validMove,
						pieceName);
			}
		}

		else if (pieceName.contains("Rook")) {
			Boolean inTheWay = false;
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				validMove = rookMovement(e, landingX, landingY, xMovement, yMovement, inTheWay, validMove, pieceName);
			}
		}

		else if (pieceName.contains("Queen")) {
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)) || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) { 
					validMove = rookMovement(e, landingX, landingY, xMovement, yMovement, inTheWay, validMove, pieceName);
				} else {
					validMove = bishopMovement(e, landingX, landingY, xMovement, yMovement, distance, inTheWay, validMove, pieceName);
				}
			}
		}

		else if (pieceName.contains("King")) {
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} 
			else {
				if (((yMovement <= 1) || (yMovement <= -1)) && ((xMovement <= -1) || (xMovement <= 1))) {
					if ((landingX == startX) && (landingY == startY)) {
						validMove = false;
					} 
					if (piecePresent(e.getX(), e.getY()) && pieceName.contains("White") && white2Move == true) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
							white2Move = false;
						}
					} 
					if (piecePresent(e.getX(), e.getY()) && pieceName.contains("Black") && white2Move == false) {
						if (checkBlackOponent(e.getX(), e.getY())) {
							validMove = true;
							white2Move = true;
						}
					}
					if (!piecePresent(e.getX(), e.getY()) && white2Move == true && pieceName.contains("White")) {
						white2Move = false;
						validMove = true;
					}
					if (!piecePresent(e.getX(), e.getY()) && white2Move == false && pieceName.contains("Black")) {
						white2Move = true;
						validMove = true;
					} 
					// else {
					// 	validMove = false;
					// }
				}
			}
		}

		if (!validMove) {
			int location = 0;
			if (startY == 0) {
				location = startX;
			} else {
				location = (startY * 8) + startX;
			}
			String pieceLocation = pieceName + ".png";
			pieces = new JLabel(new ImageIcon(pieceLocation));
			panels = (JPanel) chessBoard.getComponent(location);
			panels.add(pieces);
		} else {
			if (success) {
				int whiteLocation = 56 + (e.getX() / 75);
				int blackLocation = (e.getX() / 75);

				if (c instanceof JLabel && pieceName.contains("Black")) {
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(blackLocation);
					parent.add(pieces);
				} else {
					Container parent = (Container) c;
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(blackLocation);
					parent.add(pieces);
				}
				if (c instanceof JLabel && pieceName.contains("White")) {
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
					parent = (JPanel) chessBoard.getComponent(whiteLocation);
					parent.add(pieces);
				} else {
					Container parent = (Container) c;
					pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
					parent = (JPanel) chessBoard.getComponent(whiteLocation);
					parent.add(pieces);
				}
			} else {
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					parent.add(chessPiece);
				} else {
					Container parent = (Container) c;
					parent.add(chessPiece);
				}
				chessPiece.setVisible(true);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public boolean rookMovement(MouseEvent e, int landingX, int landingY, int xMovement, int yMovement,	boolean inTheWay, boolean validMove, String pieceName) {
		if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0)) || ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
			if (Math.abs(startX - landingX) != 0) {
				xMovement = Math.abs(startX - landingX);
				if (startX - landingX > 0) {
					for (int i = 0; i < xMovement; i++) {
						if (piecePresent(initialX - (i * 75), e.getY())) {
							inTheWay = true;
							break;
						}
						if(!piecePresent(e.getX(), e.getY())){
							if(white2Move == true && pieceName.contains("White")){
								white2Move = false;
								return validMove = true;
							}
							if(white2Move == false && pieceName.contains("Black")) {
								white2Move = true;
								return validMove = true;
							}
							inTheWay = false;
						}
					}
				} else {
					for (int i = 0; i < xMovement; i++) {
						if (piecePresent(initialX + (i * 75), e.getY())) {
							inTheWay = true;
							break;
						} 
						if(!piecePresent(e.getX(), e.getY())){
							if(white2Move == true && pieceName.contains("White")){
								white2Move = false;
								return validMove = true;
							}
							else if(white2Move == false && pieceName.contains("Black")) {
								white2Move = true;
								return validMove = true;
							}
							inTheWay = false;
						}
					}
				}
			} else {
				yMovement = Math.abs(startY - landingY);
				if (startY - landingY > 0) {
					for (int i = 0; i < yMovement; i++) {
						if (piecePresent(e.getX(), initialY - (i * 75))) {
							inTheWay = true;
							break;
						} 
						if(!piecePresent(e.getX(), e.getY())){
							if(white2Move == true && pieceName.contains("White")){
								white2Move = false;
								return validMove = true;
							}
							else if(white2Move == false && pieceName.contains("Black")) {
								white2Move = true;
								return validMove = true;
							}
							inTheWay = false;
						}
					}
				} else {
					for (int i = 0; i < yMovement; i++) {
						if (piecePresent(e.getX(), initialY + (i * 75))) {
							inTheWay = true;
							break;
						} 
						if(!piecePresent(e.getX(), e.getY())){
							if(white2Move == true && pieceName.contains("White")){
								white2Move = false;
								return validMove = true;
							}
							else if(white2Move == false && pieceName.contains("Black")) {
								white2Move = true;
								return validMove = true;
							}
							inTheWay = false;
						}
					}
				}
			}

			if (inTheWay) {
				return validMove = false;
			}
			else {
				if (piecePresent(e.getX(), (e.getY()))) {
					if (pieceName.contains("White") && white2Move == true) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							white2Move = false;
							return validMove = true;
						} else {
							return validMove = false;
							//white2Move = true;
						}
					} 
					if (pieceName.contains("Black") && white2Move == false) {
						if (checkBlackOponent(e.getX(), e.getY())) {
							white2Move = true;
							return validMove = true;
						} else {
							return validMove = false;
						}
					}
					// else {
					// 	validMove = false;
					// }
				}
				else {
					if (!piecePresent(e.getX(), (e.getY()))) {
						if (pieceName.contains("White") && white2Move == true) {
							white2Move = false;
							return validMove = true;
						} 
						if (pieceName.contains("Black") && white2Move == false) {
							white2Move = true;
							return validMove = true;
						}
					}
				}
			}
		} 
		return validMove = false;
	}

	public boolean bishopMovement(MouseEvent e, int landingX, int landingY, int xMovement, int yMovement, int distance,	boolean inTheWay, boolean validMove, String pieceName) {
		// validMove = true;
		if (Math.abs(startX - landingX) == Math.abs(startY - landingY)) {
			if ((startX - landingX < 0) && (startY - landingY < 0)) {
				for (int i = 0; i < distance; i++) {
					if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
						inTheWay = true;
					}
				}
			} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
				for (int i = 0; i < distance; i++) {
					if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
						inTheWay = true;
					}
				}
			} else if ((startX - landingX > 0) && (startY - landingY > 0)) {
				for (int i = 0; i < distance; i++) {
					if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
						inTheWay = true;
					}
				}
			} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
				for (int i = 0; i < distance; i++) {
					if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
						inTheWay = true;
					}
				}
			}
			if (inTheWay) {
				return validMove = false;
			} else {
				if (piecePresent(e.getX(), (e.getY()))) {
					if (pieceName.contains("White") && white2Move == true) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							white2Move = false;
							return validMove = true;
						} else {
							white2Move = true;
							return validMove = false;
						}
					} 
					if(pieceName.contains("Black") && white2Move == false) {
						if (checkBlackOponent(e.getX(), e.getY())) {
							white2Move = true;
							return validMove = true;
						} else {
							white2Move = false;
							return validMove = false;
						}
					}
				}
				else {
					// if ((landingX == startX) && (landingY == startY)) {
					// 	return validMove = false;
					// } else {
					// 	return validMove = true;
					// }
					if (!piecePresent(e.getX(), e.getY()) && white2Move == true && pieceName.contains("White")) {
							white2Move = false;
							return validMove = true;
					} 
					if (!piecePresent(e.getX(), e.getY()) && white2Move == false && pieceName.contains("Black")) {
						white2Move = true;
						return validMove = true;
					} 
				}
			}
		}
		return validMove = false;
	}

	public static void initalCreation() {
		ChessProject frame = new ChessProject();
		// frame.setAlwaysOnTop (true); //Always have this window on top. Good for
		// debugging.
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(false); // Changed to false to stop accidential resize.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/*
	 * Main method that gets the ball moving.
	 */
	public static void main(String[] args) {
		initalCreation(); // Create the chess board.
	}
}