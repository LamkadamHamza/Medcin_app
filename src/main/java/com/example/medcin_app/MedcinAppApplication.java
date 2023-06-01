package com.example.medcin_app;

import com.example.medcin_app.entities.Patient;
import com.example.medcin_app.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MedcinAppApplication  implements CommandLineRunner {





    @Autowired
    PatientRepository patientRepository ;



    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
   CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder=passwordEncoder();
        return args -> {
          jdbcUserDetailsManager.createUser(
                  User.withUsername("user1").password(passwordEncoder.encode("123")).roles("USER").build()
          );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user2").password(passwordEncoder.encode("123")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin").password(passwordEncoder.encode("123")).roles("USER","ADMIN").build()
            );
        };
   }


    public static void main(String[] args) {
        SpringApplication.run(MedcinAppApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

        /**** Ajoute des patient ****/

        /***
        patientRepository.save(new Patient(null,"AAAA",new Date(),true,110));
        patientRepository.save(new Patient(null,"BBBB",new Date(),false,111));
        patientRepository.save(new Patient(null,"CCCC",new Date(),true,200));

         */

        /**** Trouve les patient

        List<Patient> patients =patientRepository.findAll();
        patients.forEach(p->{
            System.out.println(p.toString());
        });****/

        /**** Trouve un patient
        Patient patient= patientRepository.findById(Long.valueOf(1)).get();
        System.out.println("***********************");
        System.out.println(patient.getId());
        System.out.println(patient.getNom());
        System.out.println(patient.getDateNaissance());
        System.out.println(patient.getMalade());
        System.out.println(patient.getScore());
        System.out.println("***********");****/

        /**** Mettre à jour un patient
        Patient patient2= patientRepository.findById(Long.valueOf(1)).get();
        patient2.setNom(patient2.getNom());
        patientRepository.save(patient2);****/


        /**** supprimer un patient
        Patient patient3= patientRepository.findById(Long.valueOf(1)).get();
        patientRepository.delete(patient3);****/

    }


}
