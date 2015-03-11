package hello;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableWebMvcSecurity
@RequestMapping(value="/api/v1")
@RestController
public class Controller extends WebSecurityConfigurerAdapter {
	
	
	Moderator mod = new Moderator();
	Poll poll = new Poll();
	
	ArrayList <Moderator> Modstringlist = new ArrayList<Moderator>();
	ArrayList <Poll> Pollstringlist = new ArrayList<Poll>();

	private static final AtomicLong counter = new AtomicLong(123455);
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	
     int [] tmpresult = new int[2];
     int [] result = new int[2];
     int [] fnlresult= new int[2];
	 String [] choice = new String[2];
    
	 protected void configure(HttpSecurity http) throws Exception {
                http
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
               // .antMatchers(HttpMethod.GET,"/api/v1/").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/moderators").permitAll()
                .antMatchers("/api/v1/polls/**").permitAll()
                .antMatchers("/api/v1/moderators/**").fullyAuthenticated().anyRequest().hasRole("USER");            
                                
            
            }
         
         @Autowired
            public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth
                    .inMemoryAuthentication()
                        .withUser("foo").password("bar").roles("USER");
            }
    
    
    
    
	@RequestMapping(value = "/moderators", method = RequestMethod.POST)
	
	public ResponseEntity <Moderator> moderator(@Valid @RequestBody Moderator mod) {
		
		String date = new Date().toString();	
		mod.setCreated_at(formater.format(new Date()));
		mod.setId((int)counter.incrementAndGet());
		Modstringlist.add(mod);
		
		return new ResponseEntity<Moderator>(mod,HttpStatus.CREATED);
		
	   }

	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.GET)
		public ResponseEntity <Moderator> Viewmoderator(@PathVariable int id) {
	
		int myidentifier = 0;
		
		System.out.println("size is"+Modstringlist.size());
		
		for(int i=0;i<Modstringlist.size();i++)
		{
			if(id == Modstringlist.get(i).getId())
			{
				myidentifier=i;
			}
		}
		
		return new ResponseEntity<Moderator>(Modstringlist.get(myidentifier),HttpStatus.OK);
	   
	}
	
	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.PUT)
	 public ResponseEntity <Moderator> updatemoderator(@Valid @RequestBody Moderator mod,@PathVariable int id) {
		
         int identifier = 0;
		
         String email = mod.getEmail();
		 String password= mod.getPassword();
		
		System.out.println("size is"+Modstringlist.size());
		for(int i=0;i<Modstringlist.size();i++)
		{
			if(id == Modstringlist.get(i).getId())
			{
			
				identifier=i;
				Modstringlist.get(i).setEmail(email);
				Modstringlist.get(i).setPassword(password);
				
			}
			
		}
		return new ResponseEntity<Moderator>(Modstringlist.get(identifier),HttpStatus.CREATED);
	   }
	
    @RequestMapping(value = "/moderators/{id}/polls", method = RequestMethod.POST)

	public ResponseEntity <Poll> createPoll(@Valid @RequestBody Poll poll,@PathVariable int id) {
		
    	poll.setId(Integer.toString((int) counter.incrementAndGet(),36));
    	Pollstringlist.add(poll);
    
    	
		for(int i=0;i<Modstringlist.size();i++)
		{
			if(id == Modstringlist.get(i).getId())
			{
				Modstringlist.get(i).getPollslist().add(poll);
		
			}
			
		}
	
		return new ResponseEntity<Poll>(poll,HttpStatus.CREATED);
		
	   }

	@RequestMapping(value = "/polls/{id1}", method = RequestMethod.GET)
		
	    public ResponseEntity <Poll> viewPollsWithoughResult(@PathVariable String id1) {
	
		int myidentifier = 0;
		
		System.out.println("size is"+Pollstringlist.size());
		
		for(int i=0; i<Pollstringlist.size(); i++)
		{
			if(id1.equals(Pollstringlist.get(i).getId()))
			{
				myidentifier=i;
			}
		}
		
		return new ResponseEntity<Poll>(Pollstringlist.get(myidentifier),HttpStatus.OK);
	}
	

	@RequestMapping(value = "/moderators/{id}/polls/{id1}", method = RequestMethod.GET)
	public ResponseEntity viewPollWithResult(@PathVariable int id,@PathVariable String id1) {

    int identifier = 0;
	System.out.println("size is"+Modstringlist.size());
	
	for(int i=0;i<Modstringlist.size();i++)
	{
		if(id == Modstringlist.get(i).getId())
		{
		
			for(int j=0;j<Pollstringlist.size();j++)
			{
				if(id1.equals(Pollstringlist.get(j).getId()))	
				{
					return new ResponseEntity(Modstringlist.get(i).getPollslist().get(j),HttpStatus.OK);
				}
			}
		}	
	
	}
	
	
     return new ResponseEntity("View Polls is not sucessfull",HttpStatus.OK);
}

	@RequestMapping(value = "/moderators/{id}/polls", method = RequestMethod.GET)
	public ResponseEntity listAllPolls(@PathVariable int id) {

    int identifier = 0;
	System.out.println("size is"+Modstringlist.size());
	
	for(int i=0;i<Modstringlist.size();i++)
	{
		if(id == Modstringlist.get(i).getId())
		{
		
					return new ResponseEntity(Modstringlist.get(i).getPollslist(),HttpStatus.OK);
		}	
	}
	
     return new ResponseEntity("View Polls is not sucessfull",HttpStatus.OK);
}

	
	
	@RequestMapping(value = "/moderators/{id}/polls/{id1}", method = RequestMethod.DELETE)
	public ResponseEntity deletePoll(@PathVariable int id,@PathVariable String id1) {

    int identifier = 0;
	System.out.println("size is"+Modstringlist.size());
	
	
	for(int i=0;i<Modstringlist.size();i++)
	{
		if(id == Modstringlist.get(i).getId())
		{
		
			for(int j=0;j<Pollstringlist.size();j++)
			{
				if(id1.equals(Pollstringlist.get(j).getId()))	
				{
					Pollstringlist.remove(j);
					return new ResponseEntity(Modstringlist.get(i).getPollslist(),HttpStatus.NO_CONTENT);
				}
			}
		}	
	
	}
	
	
     return new ResponseEntity("Delete Polls is not sucessfull",HttpStatus.OK);
}

	
	
	 @RequestMapping(value = "/polls/{id1}", method = RequestMethod.PUT)
	 public ResponseEntity voteAPoll(@PathVariable String id1,@RequestParam(value="choice")int choice_index) 
	 {
		 for(int i=0;i<Pollstringlist.size();i++)
		    {
			  if(id1.equals(Pollstringlist.get(i).getId()))
			  {
			   
				  	if(choice_index == 0)
				  		{
				  		
				  		tmpresult=Pollstringlist.get(i).getResult();
				  		tmpresult[choice_index]=tmpresult[choice_index]+1;	
				  		Pollstringlist.get(i).setResult(tmpresult);
				  	 	return new ResponseEntity(HttpStatus.NO_CONTENT);
				  		
				  		}
				  	else if(choice_index==1)
		            {
				  		 tmpresult=Pollstringlist.get(i).getResult();
				  		 tmpresult[choice_index]=tmpresult[choice_index]+1;
				  		Pollstringlist.get(i).setResult(tmpresult);
						 return new ResponseEntity(HttpStatus.NO_CONTENT);
		            }
				  	
    		    }
            }
		    	 
           	return new ResponseEntity("Not able to vote",HttpStatus.NO_CONTENT);
		
	   }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseBody
     public ResponseEntity handleBadInput(MethodArgumentNotValidException e)
     {
         String errors="";
         for(FieldError obj: e.getBindingResult().getFieldErrors())
             {
                 errors+=obj.getDefaultMessage();
             }    
         return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
     }
}