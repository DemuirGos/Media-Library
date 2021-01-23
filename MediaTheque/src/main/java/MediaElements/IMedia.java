package MediaElements;

abstract public class IMedia {
    private String name;
    private MediaType type;
    private String data; 
    
    IMedia () {}

    IMedia (MediaType type) {
        this.type = type;
    }

    IMedia (String name, MediaType type, String data) {
        this.name = name; 
        this.type = type;
        this.data = data; 
    }

    public String getName() {
        return name;
    }

    public MediaType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) { 
        if (this == obj)
            return true;
        if (!(obj instanceof IMedia))
            return false;
        
        IMedia m = (IMedia) obj;

        return this.name.equals(m.name)
            && this.data.equals(m.data);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + data.hashCode();
    }

    @Override
    public String toString() {  
        return "(" + name
            + ", " + type.toString()
            + ", " + data
            + ")";
    }
}
