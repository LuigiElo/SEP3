package database;





import domain.Item;
import domain.Party;
import domain.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public interface DatabaseCon extends Remote {

    public Connection connect() throws RemoteException, SQLException;
    public void close() throws RemoteException;

    public Party getParty(String partyID) throws RemoteException, SQLException;
    public ArrayList<Person> getParticipants(String partyID) throws RemoteException, SQLException;
    public ArrayList<Item> getItems(Party party) throws RemoteException, SQLException;
    public ArrayList<Person> getPeopleByName(String name) throws RemoteException, SQLException;


    void addParticipant(Person person, Party party) throws RemoteException, SQLException;
    void addItem(Item item, Party party) throws RemoteException, SQLException;
    void createPerson(Person person) throws RemoteException, SQLException;
    void createItem(Item item) throws RemoteException, SQLException;

    void updateParty(Party party) throws RemoteException, SQLException;

    void removeParticipant(Party party, Person person) throws RemoteException, SQLException;
    void removeItem(Party party, Item item) throws RemoteException, SQLException;

    public void createParty(Party party)throws RemoteException, SQLException;


}
