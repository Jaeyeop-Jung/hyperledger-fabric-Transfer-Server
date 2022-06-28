package com.capstone.hyperledgerfabrictransferserver;

import com.capstone.hyperledgerfabrictransferserver.util.EnrollAdmin;
import com.capstone.hyperledgerfabrictransferserver.util.RegisterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableAsync
public class HyperledgerFabricTransferServerApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ac = SpringApplication.run(HyperledgerFabricTransferServerApplication.class, args);
		ac.getBean(EnrollAdmin.class).main(null);
		ac.getBean(RegisterUser.class).main(null);
	}

}
