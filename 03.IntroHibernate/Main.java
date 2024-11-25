package bg.me;

//import bg.me.entities.Student;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;

import bg.me.entities.Student2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Configuration config = new Configuration();
//        config.configure();
//
//        SessionFactory sessionFactory = config.buildSessionFactory();
//        Session currentSession = sessionFactory.openSession();
//        currentSession.beginTransaction();
//
//        Student student = new Student();
//        student.setName("Hiber");
//        currentSession.persist(student);
//
//        Student user = currentSession.get(Student.class, 1);
//        System.out.println(user);
//
//        List<Student> from_student = currentSession.createQuery("FROM Student", Student.class).list();
//        for(Student studentt: from_student){
//            System.out.println(studentt);
//        }
//
//        currentSession.getTransaction().commit();
//        currentSession.close();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student2 student2 = new Student2("Teo");
        em.persist(student2);
        em.getTransaction().commit();

        Student2 foundStudent = em.find(Student2.class, 1L);
        List<Student2> from_student2 = em.createQuery("FROM Student2", Student2.class).getResultList();

        for(Student2 sttudent: from_student2){
            System.out.println(sttudent);
        }

        Student2 lastToBeRemoved = from_student2.get(from_student2.size() - 1);
        em.remove(lastToBeRemoved);

        System.out.println(foundStudent);
        em.getTransaction().commit();


    }
}
