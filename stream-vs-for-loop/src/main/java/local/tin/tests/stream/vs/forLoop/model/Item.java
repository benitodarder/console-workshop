package local.tin.tests.stream.vs.forLoop.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author benitodarder
 */
public class Item implements Cloneable {
    
    private Date date;    
    private Integer id;
    private String name;

    public Item() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "date=" + date.getTime() + ", id=" + id + ", name=" + name + '}';
    }

    @Override
    public Item clone() throws CloneNotSupportedException {
        Item item = (Item) super.clone(); 
        item.setId(this.id);
        item.setName(this.name);
        item.setDate(new Date());
        return item;
    }

    
}
