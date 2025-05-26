//"Tic Tac Toe is fun!!!"
import java.awt.*;
import java.awt.event.*;

public class ClassicTicTacToe extends Frame {
    private Button[][] board = new Button[3][3];
    private boolean isXNext = true;
    private Label gameStatus;
    private Button restartButton;

    public ClassicTicTacToe() {
        super("Tic-Tac-Toe (AWT Edition)");
        setupUI();
        centerWindow();
        setVisible(true);
    }

    private void setupUI() {
        setSize(400, 450);
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        gameStatus = new Label("Player X begins", Label.CENTER);
        gameStatus.setFont(new Font("Verdana", Font.BOLD, 18));
        gameStatus.setBackground(new Color(200, 220, 255)); // Light blue
        gameStatus.setForeground(Color.BLACK);
        add(gameStatus, BorderLayout.NORTH);

        Panel gameGrid = new Panel(new GridLayout(3, 3, 5, 5));
        gameGrid.setBackground(new Color(180, 180, 180)); // Slightly darker background
        Font cellFont = new Font("Arial", Font.BOLD, 40);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button cell = new Button("");
                cell.setFont(cellFont);
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
                cell.addActionListener(new CellClickHandler(r, c));
                board[r][c] = cell;
                gameGrid.add(cell);
            }
        }

        add(gameGrid, BorderLayout.CENTER);

        restartButton = new Button("Start New Game");
        restartButton.setBackground(new Color(100, 180, 100)); // Green
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        restartButton.addActionListener(new RestartHandler());
        add(restartButton, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
    }

    private void centerWindow() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - getWidth()) / 2;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private class CellClickHandler implements ActionListener {
        private int row, col;

        CellClickHandler(int r, int c) {
            this.row = r;
            this.col = c;
        }

        public void actionPerformed(ActionEvent e) {
            Button selected = board[row][col];
            if (!selected.getLabel().isEmpty())
                return;

            String currentPlayer = isXNext ? "X" : "O";
            selected.setLabel(currentPlayer);
            selected.setForeground(currentPlayer.equals("X") ? Color.BLUE : Color.RED);

            if (checkVictory(currentPlayer)) {
                gameStatus.setText("Player " + currentPlayer + " wins!");
                highlightWinningLine(currentPlayer);
                lockBoard();
            } else if (isFull()) {
                gameStatus.setText("It's a draw!");
            } else {
                isXNext = !isXNext;
                gameStatus.setText("Player " + (isXNext ? "X" : "O") + "'s turn");
            }
        }
    }

    private boolean checkVictory(String symbol) {
        for (int i = 0; i < 3; i++) {

            if (symbol.equals(board[i][0].getLabel()) &&
                    symbol.equals(board[i][1].getLabel()) &&
                    symbol.equals(board[i][2].getLabel())) {
                return true;
            }

            if (symbol.equals(board[0][i].getLabel()) &&
                    symbol.equals(board[1][i].getLabel()) &&
                    symbol.equals(board[2][i].getLabel())) {
                return true;
            }
        }

        return (symbol.equals(board[0][0].getLabel()) &&
                symbol.equals(board[1][1].getLabel()) &&
                symbol.equals(board[2][2].getLabel())) ||
                (symbol.equals(board[0][2].getLabel()) &&
                        symbol.equals(board[1][1].getLabel()) &&
                        symbol.equals(board[2][0].getLabel()));
    }

    private void highlightWinningLine(String player) {
        Color highlightColor = new Color(255, 215, 0); // Gold

        for (int i = 0; i < 3; i++) {

            if (player.equals(board[i][0].getLabel()) &&
                    player.equals(board[i][1].getLabel()) &&
                    player.equals(board[i][2].getLabel())) {
                for (int j = 0; j < 3; j++)
                    board[i][j].setBackground(highlightColor);
            }

            if (player.equals(board[0][i].getLabel()) &&
                    player.equals(board[1][i].getLabel()) &&
                    player.equals(board[2][i].getLabel())) {
                for (int j = 0; j < 3; j++)
                    board[j][i].setBackground(highlightColor);
            }
        }

        if (player.equals(board[0][0].getLabel()) &&
                player.equals(board[1][1].getLabel()) &&
                player.equals(board[2][2].getLabel())) {
            board[0][0].setBackground(highlightColor);
            board[1][1].setBackground(highlightColor);
            board[2][2].setBackground(highlightColor);
        }
        if (player.equals(board[0][2].getLabel()) &&
                player.equals(board[1][1].getLabel()) &&
                player.equals(board[2][0].getLabel())) {
            board[0][2].setBackground(highlightColor);
            board[1][1].setBackground(highlightColor);
            board[2][0].setBackground(highlightColor);
        }
    }

    private boolean isFull() {
        for (Button[] row : board)
            for (Button cell : row)
                if (cell.getLabel().isEmpty())
                    return false;
        return true;
    }

    private void lockBoard() {
        for (Button[] row : board)
            for (Button cell : row)
                cell.setEnabled(false);
    }

    private class RestartHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Button[] row : board) {
                for (Button cell : row) {
                    cell.setLabel("");
                    cell.setEnabled(true);
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
            }
            isXNext = true;
            gameStatus.setText("Player X begins");
        }
    }

    public static void main(String[] args) {
        new ClassicTicTacToe();
    }
}
