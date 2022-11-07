public class Jogada {

    private Integer id;
    private String jogador;
    private Integer posicao;

    public Jogada() {
    }

    public Jogada(Integer id, String jogador, Integer posicao) {
        this.id = id;
        this.jogador = jogador;
        this.posicao = posicao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}
