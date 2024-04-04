package compiler_project;

public class SymbolTable {
    private int address;
    private String name;
    private String type;

    public SymbolTable(int _address, String _name, String _type){
        this.address = _address;
        this.name = _name;
        this.type = _type;
    }

    public int GetAddress(){
        return address;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public void setAddress(int _address){
        this.address = _address;
    }

    public void setName(String _name){
        this.name = _name;
    }

    public void setType(String _type){
        this.type = _type;
    }
}
