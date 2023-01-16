package profitsoft.homework.fifth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProfiTSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfiTSoftApplication.class, args);
    }

}
