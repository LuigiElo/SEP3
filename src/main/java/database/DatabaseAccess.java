package database;



import domain.Item;
import domain.Party;
import domain.Person;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class DatabaseAccess implements DatabaseCon {

    Connection connection;

    private static final String DRIVER = "org.postgres.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "postgres";


    public DatabaseAccess() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect() throws RemoteException, SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    /**
     * Method for closing the connection.
     * Recommended to use after getting the tables or updating them.
     */
    @Override
    public void close() {
        try {
            connection.close();

        } catch (SQLException e) {
            System.out.println("No closing this one");
            e.printStackTrace();
        }
    }

    @Override
    public Party getParty(String partyID) throws RemoteException, SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM partyplanner.party_table WHERE partyID = " + partyID + ";");
        rs = statement.executeQuery();

        String description = rs.getString(2);
        String address = rs.getString(3);
        Date date = rs.getDate(4);

        Party party = new Party(partyID, description, date.toString(), address);

        return party;
    }

    @Override
    public ArrayList<Person> getParticipants(String partyID) throws RemoteException, SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM partyplanner.participates_in_party WHERE partyID = " + partyID + " AND isHost = false" + ";");
        rs = statement.executeQuery();

        ArrayList<String> participants = new ArrayList<>(100);

        while (rs.next()) {

            String participantID = rs.getString(2);

            participants.add(participantID);
        }

        ArrayList<Person> people = new ArrayList<>(100);

        for (int i = 0; i < participants.size(); i++) {

            rs = null;
            connect();
            statement = connection.prepareStatement
                    ("SELECT * FROM partyplanner.personTable WHERE personID = " + participants.get(i) + ";");
            rs = statement.executeQuery();

            while (rs.next()) {

                String personID = rs.getString(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);

                Person person = new Person(personID, name, email, password, false);
                people.add(person);
            }
        }
        return people;
    }

    @Override
    public ArrayList<Item> getItems(Party party) throws RemoteException, SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM partyplanner.party_has_items WHERE partyID = " + party.getPartyID() + ";");
        rs = statement.executeQuery();

        ArrayList<String> itemsInParty = new ArrayList<>(100);

        while (rs.next()) {

            String itemID = rs.getString(2);

            itemsInParty.add(itemID);
        }

        ArrayList<Item> items = new ArrayList<>(100);

        for (int i = 0; i < itemsInParty.size(); i++) {

            rs = null;
            connect();
            statement = connection.prepareStatement
                    ("SELECT * FROM partyplanner.item_table WHERE itemID = " + itemsInParty.get(i) + ";");
            rs = statement.executeQuery();

            while (rs.next()) {

                String itemID = rs.getString(1);
                Double price = rs.getDouble(2);
                String name = rs.getString(3);

                Item item = new Item(itemID, price, name);
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public ArrayList<Person> getPeopleByName(String name) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM partyplanner.person_table");
        return null;
    }

    @Override
    public void addParticipant(Person person, Party party) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO partyplanner.participates_in_party VALUES (?,?,?);");
        statement.setString(1, party.getPartyID());
        statement.setString(2, person.getPersonID());
        statement.setBoolean(3, false);
        statement.executeQuery();
    }

    @Override
    public void addItem(Item item, Party party) throws RemoteException, SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO partyplanner.party_has_items VALUES (?,?);");
        statement.setString(1, party.getPartyID());
        statement.setString(2, item.getItemID());
        statement.executeQuery();
    }

    @Override
    public void createPerson(Person person) throws RemoteException, SQLException {
        /*
        The block below might need an if() check to make sure
        there isn't one just like it already in the database.
        However all the values are set to 'unique'
        so it shouldn't be able to happen anyway.
        */
        PreparedStatement statement2 = connection.prepareStatement
                ("INSERT INTO partyplanner.person_table VALUES(?,?,?,?);");
        statement2.setString(1, person.getPersonID());
        statement2.setString(2, person.getName());
        statement2.setString(3, person.getEmail());
        statement2.setString(4, person.getPassword());
        statement2.executeQuery();
    }

    @Override
    public void createItem(Item item) throws RemoteException, SQLException {
        /*
        The block below might need an if() check to make sure
        there isn't one just like it already in the database.
        However all the values are set to 'unique'
        so it shouldn't be able to happen anyway.
        */
        PreparedStatement statement2 = connection.prepareStatement
                ("INSERT INTO partyplanner.item_table VALUES (?,?,?);");
        statement2.setString(1, item.getItemID());
        statement2.setDouble(1, item.getPrice());
        statement2.setString(1, item.getName());
        statement2.executeQuery();
    }

    @Override
    public void updateParty(Party party) throws RemoteException, SQLException {

    }

    @Override
    public void removeParticipant(Party party, Person person) throws RemoteException, SQLException {

        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM partyplanner.participates_in_party WHERE personID = ? AND partyID = ?;");
        statement.setString(1, person.getPersonID());
        statement.setString(2, party.getPartyID());
        statement.executeQuery();
    }

    @Override
    public void removeItem(Party party, Item item) throws RemoteException, SQLException {

        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM partyplanner.party_has_items WHERE partyID = ? AND itemID = ?;");
        statement.setString(1, party.getPartyID());
        statement.setString(2, item.getItemID());
        statement.executeQuery();
    }

    @Override
    public void createParty(Party party) throws RemoteException, SQLException {

        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO public.party_table VALUES (?,?,?,?)");
        statement.setString(1, party.getPartyID());
        statement.setString(2, party.getDescription());
        statement.setString(3, party.getAddress());
        statement.setString(4,  party.getTime().toString());
        statement.executeUpdate();
    }

}