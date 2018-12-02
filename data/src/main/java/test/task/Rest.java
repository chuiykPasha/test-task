package test.task;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class Rest {
    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "/data/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> logout(@PathVariable("userId") String userId){
        System.out.println("DATA REST " + userId);

        if(redissonClient.getKeys().isExists(userId) == 1){
            System.out.println("HAVE BUCKET");
            RBucket<Map<String, String>> bucket = redissonClient.getBucket(userId);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("user", userId);
            result.put("total", bucket.get().size());
            result.put("courses", bucket.get());
            return result;
        }

        return null;
    }
}
