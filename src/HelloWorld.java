import src.Party;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.GregorianCalendar;

    // The Java class will be hosted at the URI path "/helloworld"
    @Path("/helloworld")
    public class HelloWorld {


        /*@GET
        @Produces(MediaType.TEXT_PLAIN)
        public String sayHello(){
            return "Hello,I am text!";
        }


        @POST
        @Produces(MediaType.TEXT_XML)
        public String sayXMLHello() {
            return "<?xml version=\"1.0\"?>" + "<hello> Hello,I am xml!" + "</hello>";
        }

        @GET
        @Produces(MediaType.TEXT_HTML)
        public String sayHtmlHello() {
            return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                    + "<body><h1>" + "Hello,I am html!" + "</body></h1>" + "</html> ";
        } */

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Party sayJSONHello() {
            GregorianCalendar calendar = new GregorianCalendar();
            Party party = new Party("dd","dd",(Date) calendar.getTime(),"here");

            return party;
        }

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        public Party getJSONHello(Party party) {


            return party;
        }


    }

