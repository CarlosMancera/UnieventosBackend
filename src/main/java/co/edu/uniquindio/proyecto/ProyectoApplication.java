package co.edu.uniquindio.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProyectoApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(ProyectoApplication.class, args);

        Thread.currentThread().join();

    }


}

