/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import services.User;

/**
 *
 * @author adity
 */
@Named(value = "ClientManagedBean")
@RequestScoped
public class ClientManagedBean {
    
    private String userName;
    private String email;
    private String password;
    private List<User> userdetails;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(List<User> userdetails) {
        this.userdetails = userdetails;
    }
    
    public String getUsersList(){
        //get list of users
        BLogicsResource_JerseyClient BpClient = new BLogicsResource_JerseyClient();
        GenericType<List<User>> gType = new GenericType<List<User>>() {};
        List<User> persons = (List<User>) BpClient.getUsers(gType);
        List<User> listofusers = new ArrayList<User>();
        for (User p : persons) {
            listofusers.add(p);
        }
        userdetails = listofusers;
        return "List of Users";
    }
    
     public String addUser() {
        //Registration for New Users 
        BLogicsResource_JerseyClient BpClient1 = new BLogicsResource_JerseyClient();
        User b = new User();
        b.setEmail(email);
        b.setUsername(userName);
        b.setPassword(password);
        BpClient1.CreateUsers(b);
        return "New User Added";
    }

    static class BLogicsResource_JerseyClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/petagenct4BLL/webresources";

        public BLogicsResource_JerseyClient() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("bs");
        }

        public <T> T getUsers(GenericType<T> responseType) throws ClientErrorException {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        }

        public Response CreateUsers(Object requestEntity) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public void close() {
            client.close();
        }
    }
    
    
    
}
