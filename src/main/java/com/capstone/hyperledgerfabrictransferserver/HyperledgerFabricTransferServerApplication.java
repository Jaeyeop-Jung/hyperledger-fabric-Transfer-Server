package com.capstone.hyperledgerfabrictransferserver;

import com.capstone.hyperledgerfabrictransferserver.util.EnrollAdmin;
import com.capstone.hyperledgerfabrictransferserver.util.RegisterUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HyperledgerFabricTransferServerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HyperledgerFabricTransferServerApplication.class, args);
		EnrollAdmin.main(null);
		RegisterUser.main(null);
	}

}
