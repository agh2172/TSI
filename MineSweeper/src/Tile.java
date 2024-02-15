public class Tile {
    //is mine or not
    private boolean mine;

    private int row;

    private int col;


    private boolean flag;

    //count of mines bordering
    public int count;

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    //show?
    private boolean show;

    public Tile(boolean mine, int r, int c){
        count=0;
        show=false;
        this.mine=mine;
        flag = false;
        row = r;
        col = c;
    }

    public String toString(){
        if(show){
            if(mine){
                return "!";
            } else if (count>0) {
                return Integer.toString(count);
            }
            return " ";
        }
        return "-";
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
