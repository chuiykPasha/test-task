package test.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import test.task.entities.User;
import test.task.entities.UserCourses;
import test.task.excpetion.NotFoundException;
import test.task.repositories.UserRepository;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class Rest {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/data/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> logout(@PathVariable("userId") String userId){
        System.out.println("DATA REST " + userId);

        User user = userRepository.findByName(userId);

        if(user != null){
            Map<String, Object> result = new LinkedHashMap<>();
            Map<String, Object> courses = new LinkedHashMap<>();
            result.put("user", userId);
            result.put("total", user.getUserCourses().size());

            for(UserCourses userCourses : user.getUserCourses()){
                courses.put(userCourses.getCourse().getName(), userCourses.getGrade());
            }

            result.put("courses", courses);
            return result;
        }

        throw new NotFoundException();
    }
}
