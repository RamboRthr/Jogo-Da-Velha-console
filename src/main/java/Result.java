public class Result {
    private String winner;
    private Integer nr_plays;
    private boolean draw;

    public Result(String winner, Integer nr_plays, boolean draw) {
        this.winner = winner;
        this.nr_plays = nr_plays;
        this.draw = draw;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Integer getNr_plays() {
        return nr_plays;
    }

    public void setNr_plays(Integer nr_plays) {
        this.nr_plays = nr_plays;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
}
