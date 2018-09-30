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

    public int hashCode() {
        /*
         * Arraylist.contains is evaluated by using (o==null ? e==null : o.equals(e)) as mentioned in the javadoc
         * and Object.equals() would evaluate using hashcode() first to check if the object o is equal to object e
         * before calling .equals() method to evaluate.
         *
         * As mentioned in java doc at http://docs.oracle.com/javase/7/docs/api/java/util/Collection.html#equals(java.lang.Object)
         * c1.equals(c2) implies that c1.hashCode()==c2.hashCode() should be satisfied
         * which is not in this question
         */
        return 0;
    }
}
