package LA3Q1;

public class AugustsValueEntry {
    int key;
    public AugustsValueEntry(){
        this.key=-1;
    }//base case, set key to null
    AugustsValueEntry(int key){
        this.key=key;
    }//constructor set node key to key
    public void setKey(int key){//set key
        this.key=key;
    }
    public int getKey(){
        return this.key;
    }//get key
}
