package uws.mad.assessment1;

public class item {
    String id;
    String Name;
    String Location;
    int price;
    int quantity;
    String image;

    public item(String id, String name, int price, int quantity,String location, String image) {
        this.id = id;
        Name = name;
        Location = location;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }
}

