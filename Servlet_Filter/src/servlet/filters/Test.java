package servlet.filters;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* Simple Test Class
*  
* @author Luis Escobar
* @date 20160217
* @version 1.0
*/
@RestController
@RequestMapping("/test")
public class Test {
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces ="text/plain;charset=utf-8")
	public String test(@PathVariable String name) {
		String hello = "test";
		return (name);
	}
}