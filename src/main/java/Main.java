import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static DataAcces dataAcces = new DataAcces();
    public static void main(String[] args) {
        dataAcces.init();

        Scanner scanner = new Scanner(System.in);
        String[][] board = {{" "," "," "},{" "," "," "},{" "," "," "},{" "," "," "}};
        boolean isOn = true;
        int jogada = 0;
        boolean isDraw = false;
        String marca = new String();
        String vencedor = null;

        while(isOn){

            if(jogada == 9){
                isDraw = true;
                System.out.println("Empate! Fim de jogo");
                try {
                    var result = new Result(null, jogada, true);
                    dataAcces.insertResult(result);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            System.out.println("digite a posição que deseja jogar");
            int pos = Integer.parseInt(scanner.next());
            int linha = 0;
            int coluna = 0;


            //Feito pelo vitor
            var jogada2 = new Jogada(jogada, marca, pos);

            linha = setPos(pos)[0];
            coluna = setPos(pos)[1];

            while(!validarPoicao(board, linha, coluna)){
                System.out.println("posição inválida, tente novamente.");
                pos = Integer.parseInt(scanner.next());
                linha = setPos(pos)[0];
                coluna = setPos(pos)[1];

            }

            if(jogada % 2 == 0){
                marca = "X";
            }

            else {
                marca = "O";
            }

            board[linha][coluna] = marca;
            jogada++;
            var jog = new Jogada(jogada, marca, pos);

            if(isOver(board)){
                vencedor  = marca;
                if (marca == "X") {
                    System.out.println("O jogador X venceu!");
                } else if (marca == "O") {
                    System.out.println("O jogador O venceu!");
                }
                printBoard(board);
                try {
                    var result = new Result(vencedor, jogada, isDraw);
                    dataAcces.insertResult(result);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dataAcces.insertLogs(jog);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    System.out.println("Histórico de jogadas");
                    ResultSet rs = dataAcces.selectLogs();
                    String player;
                    int position;
                    while(rs.next()){
                        player = rs.getString("jogador");
                        position = rs.getInt("posicao");
                        System.out.printf("Jogador %s escolheu a posição %s \n", player, position);
                    }

                    System.out.println("Jogadas do vencedor:");

                    if(vencedor.equalsIgnoreCase("X")){
                        rs = dataAcces.selectLogs();
                        while(rs.next()){
                            player = rs.getString("jogador");
                            position = rs.getInt("posicao");
                            if(player.equalsIgnoreCase("X")){
                                System.out.printf("Jogador escolheu a posição %s\n", position);
                            }
                        }
                    } else if (vencedor.equalsIgnoreCase("O")) {
                        rs = dataAcces.selectLogs();
                        while(rs.next()){
                            player = rs.getString("jogador");
                            position = rs.getInt("posicao");
                            if(player.equalsIgnoreCase("O")){
                                System.out.printf("Jogador escolheu a posição %s\n", position);
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dataAcces.deleteLogs();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }

            printBoard(board);

            try {
                dataAcces.insertLogs(jog);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void printBoard(String[][] board){
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
    }
    private static boolean validarPoicao(String[][] board, int linha, int coluna){
        if(linha == -1 || coluna == -1 || board[linha][coluna] != " "){
            return false;
        }
        else{
            return true;
        }
    }
    private static int[] setPos (int pos){
        int linha;
        int coluna;
        switch (pos){
            case 1:
                linha = 0;
                coluna = 0;
                break;

            case 2:
                linha = 0;
                coluna = 1;
                break;

            case 3:
                linha = 0;
                coluna = 2;
                break;

            case 4:
                linha = 1;
                coluna = 0;
                break;

            case 5:
                linha = 1;
                coluna = 1;
                break;

            case 6:
                linha = 1;
                coluna = 2;
                break;

            case 7:
                linha = 2;
                coluna = 0;
                break;

            case 8:
                linha = 2;
                coluna = 1;
                break;

            case 9:
                linha = 2;
                coluna = 2;
                break;

            default:
                linha = -1;
                coluna = -1;
        }
        return new int[]{linha, coluna};
    }
    private static boolean isOver(String[][] board){
        if(board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != " "){

            return true;
        } else if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != " ") {
            return true;
        } else if (board[2][0] == board[2][1] && board[1][1] == board[2][2] && board[2][0] != " ") {
            return true;
        } else if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != " ") {
            return true;
        } else if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != " ") {
            return true;
        } else if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != " ") {
            return true;
        } else if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != " ") {
            return true;
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != " ") {
            return true;
        }
        else{
            return false;
        }
    }
}
