package com.qa.ims.persistence.domain;

public class Item {

    private Long id;
    private String name;
    private Float value;


    public Item(String name, Float value) {
        this.name = name;
        this.value = value;
    }


    public Item(Long id, String name, Float value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Float getValue() {
        return value;
    }


    public void setValue(Float value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", value: " + value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
		return true;
    }
    
    

    


    
    
}
