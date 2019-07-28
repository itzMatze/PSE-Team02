package kit.edu.mensameet.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;

import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.controller.UserRepository;
import kit.edu.mensameet.server.model.User;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
	private static String serviceAccountFilePath;
	private static UserController userController;
	private static ResourceLoader resourceLoader; 
	
	@Value("${path.to.service.account.file}")
    public void setServiceAccountFilePath(String serviceAccountFilePath) {
        this.serviceAccountFilePath = serviceAccountFilePath;
    }
	
	@Autowired
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@Autowired
    public void setUserController(UserController userController){
        this.userController = userController;
    }
	
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        initializeFirebase();
        userController.initializeAdminUser();
    }

    @Override
    protected SpringApplicationBuilder configure(
      SpringApplicationBuilder application) {
      return application.sources(Application.class);
    }

    private static void initializeFirebase() throws IOException {
    	String relativePath = new File("").getAbsolutePath();
 
    	resourceLoader.getResource("classpath:service-account-file.json").getURL();
    	
    	//To load the configurations via an explicit address isn't recommended and insecure and should be changed in the future.
    	FileInputStream  credentialsStream = new FileInputStream(relativePath + serviceAccountFilePath);
    	
        FirebaseOptions options =  FirebaseOptions.builder()
        		.setCredentials(GoogleCredentials.fromStream(credentialsStream))
        		.setDatabaseUrl("https://mensameet-33fe5.firebaseio.com")
        		.build();
		
 
        FirebaseApp.initializeApp(options);
    }
}
