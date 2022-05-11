package com.capstone.hyperledgerfabrictransferserver;

import com.capstone.hyperledgerfabrictransferserver.util.EnrollAdmin;
import com.capstone.hyperledgerfabrictransferserver.util.RegisterUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableAsync
public class HyperledgerFabricTransferServerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HyperledgerFabricTransferServerApplication.class, args);
		EnrollAdmin.main(null);
		RegisterUser.main(null);
	}

}
