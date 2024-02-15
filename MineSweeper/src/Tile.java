public class Tile {
    //is mine or not
    private boolean mine;

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

    public Tile(boolean mine){
        count=0;
        show=false;
        this.mine=mine;
    }

    public String toString(){
        if(show){
            if(mine){
                return "!";
            }
            return Integer.toString(count);
        }
        return "-";
    }



}
