package test.task;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DataApplication {
	private RedissonClient redissonClient;

	public DataApplication(RedissonClient redissonClient){
		this.redissonClient = redissonClient;

		// user-has-a-grade
		Map<String, String> courses = new HashMap<>();
		courses.put("First course", "A");
		courses.put("Second course", "A");

		RBucket<Map<String, String>> bucket = redissonClient.getBucket("user-has-a-grade");
		bucket.set(courses);

		// user-has-f-grade
		courses = new HashMap<>();
		courses.put("First course", "F");
		courses.put("Second course", "F");
		bucket = redissonClient.getBucket("user-has-f-grade");
		bucket.set(courses);
	}

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
		System.out.println("DATA");
	}
}
