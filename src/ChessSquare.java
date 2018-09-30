class ChessSquare
{
    private int pos_m;
    private int pos_n;

    ChessSquare(){}

    ChessSquare(int pos_m, int pos_n)
    {
        this.pos_m = pos_m;
        this.pos_n = pos_n;
    }

    public int getPosM()
    {
        return pos_m;
    }

    public int getPosN()
    {
        return pos_n;
    }

    public void setPosM(int pos_m)
    {
        this.pos_m = pos_m;
    }

    public void setPosN(int pos_n)
    {
        this.pos_n = pos_n;
    }

    @Override
    public String toString()
    {
        return "(" + Integer.toString(pos_m) + ", "+ Integer.toString(pos_n) + ")";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        ChessSquare other = (ChessSquare) obj;

        return other.pos_m == this.pos_m && other.pos_n == this.pos_n;
    }

    public int hashCode()
    {
        return 0;
    }
}
