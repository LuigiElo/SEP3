package domain;

import sun.util.calendar.LocalGregorianCalendar;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Party implements Serializable {

    private String partyID;
    private String description;
    private Date time; //🤷🏻‍️not sure if it works
    private String address;



    ArrayList<Item> items;
    ArrayList<Person> people;

    public Party() {

    }


    public Item getItem(int index) {
        return items.get(index);
    }

    public void setItems(Item items, int index) {
        this.items.add(index, items);
    }

    public Person getPerson(int index) {
        return people.get(index);
    }

    public void setPeople(Person person, int index) {
        this.people.add(index, person);
    }

    public Party(String partyID, String description, String time, String address) {
        GregorianCalendar calendar = new GregorianCalendar();
        this.partyID = partyID;
        this.description = description;
        this.time = (Date) calendar.getTime();
        String s;
        this.address = address;
        items = new ArrayList<>(100);
        people = new ArrayList<>(100);

    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
