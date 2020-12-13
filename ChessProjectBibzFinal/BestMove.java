import java.util.Stack;
public class BestMove{

    private Stack whiteStack;
    private Stack blackStack;
    private Stack black;
    private Move bestMove;
    private int maxValue;

    public BestMove(Stack whiteStack, Stack blackStack, Stack black, Move bestMove, int maxValue) {
        this.whiteStack = whiteStack;
        this.blackStack = blackStack;
        this.black = black;
        this.bestMove = bestMove;
        this.maxValue = maxValue;
    }

    public Move getBestMove() {
        return bestMove;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public BestMove invoke() {
        Move whiteMove;
        Move currentMove;
        while (!whiteStack.empty()) {

            //GET POSSIBLE WHITE MOVES FROM STACK
            whiteMove = (Move) whiteStack.pop();
            currentMove = whiteMove;

            //CALL METHOD TO SHOW NEXT BEST MOVE
            calculateMoves(currentMove);

            //CALL METHOD TO CALCULATE CENTRE PIEC MOVE
            calculateCenter(currentMove);

            //COPY OF BLACK MOVES
            black = (Stack) blackStack.clone();
        }
        return this;
    }

    private void calculateCenter(Move currentMove) {
        int value;

        int yLanding = currentMove.getLanding().getYC();
        int yStart = currentMove.getStart().getYC();
        int xLanding = currentMove.getLanding().getXC();

        //CENTRE OF BOARD CO,ORDS
        boolean isXCorPos1 = xLanding == 3;
        boolean isYCorPos1 = yLanding == 3;
        boolean isXCorPos2 = xLanding == 4;
        boolean isYCorPos2 = yLanding == 4;

        if ((yStart < xLanding)
                && isXCorPos1 && isYCorPos1
                || isXCorPos2 && isYCorPos1
                || isXCorPos1 && isYCorPos2
                || isXCorPos2 && isYCorPos2) {
            value = 1;

            if (value > maxValue) {
                maxValue = value;
                bestMove = currentMove;
            }
        }
    }

    private void calculateMoves(Move currentMove) {
        int value;
        //WHITE LANDING POS' COMPARED WWITH BLACK LANDING POS'
        Square blackPosition;
        while (!black.isEmpty()) {
            value = 0;
            blackPosition = (Square) black.pop();


            //LANDING POS' FOR BLACK PIECES + GIVE VALUES.
            boolean isValidBlackXCor = currentMove.getLanding().getXC() == blackPosition.getXC();
            boolean isValidBlackYCor = currentMove.getLanding().getYC() == blackPosition.getYC();

            if (isValidBlackXCor && isValidBlackYCor) {
                switch (blackPosition.getName()) {
                    case PieceConstants.BLACK_PAWN:
                        //GIVE CONSTANT VALUE 1
                        value = PieceConstants.PAWN_VALUE;
                        break;
                    case PieceConstants.BLACK_BISHOP:
                    case PieceConstants.BLACK_KNIGHT:
                        //GIVE CONSTANT VALUE 3
                        value = PieceConstants.BISHOP_KNIGHT_VALUE;
                        break;
                    case PieceConstants.BLACK_ROOK:
                        //GIVE CONSTANT VALUE 5
                        value = PieceConstants.ROOK_VALUE;
                        break;
                    case PieceConstants.BLACK_QUEEN:
                        //GIVE CONSTANT VALUE 9
                        value = PieceConstants.QUEEN_VALUE;
                        break;
                    case PieceConstants.BLACK_KING:
                        //CONSTANT VALUE INIFITE
                        value = PieceConstants.KING_VALUE;
                        break;
                }
            }
            //BEST MOVE
            if (value > maxValue) {
                maxValue = value;
                bestMove = currentMove;
            }
        }
    }
}
