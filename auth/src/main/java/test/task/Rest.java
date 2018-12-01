package test.task;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@RestController
public class Rest {
    private Properties prop;

    public Rest(){
        InputStream is = null;
        try {
            this.prop = new Properties();
            is = this.getClass().getResourceAsStream("/users.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void auth(@RequestParam("login") String login, @RequestParam("password") String password){
        System.out.println("AUTH rest");
        System.out.println("LOGIN " + login + " Password " + password);
        if(!credentialsExists(login, password)){
            throw new InternalServerError();
        }
    }

    private Set<Object> getAllKeys(){
        Set<Object> keys = prop.keySet();
        return keys;
    }

    private String getPropertyValue(String key){
        return this.prop.getProperty(key);
    }

    private boolean credentialsExists(String login, String password){
        Set<Object> keys = getAllKeys();

        for(Object k:keys){
            String key = (String)k;
            System.out.println(key + " " + getPropertyValue(key));
            if(key.equals(login) && getPropertyValue(key).equals(password)){
                return true;
            }
        }

        return false;
    }


}
