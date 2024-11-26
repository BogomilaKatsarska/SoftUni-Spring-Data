package bg.me;

import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class MainExercise {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        //Business Logic
        townsToUpperCase(entityManager);
        containsEmployee(entityManager);
        employeesWithSalaryOver50000(entityManager);
        employeesFromResearchDepartment(entityManager);
        newAddress(entityManager);
        addressessWithEmployeeCount(entityManager);
        getEmployeesWithProject(entityManager);
        findLatest10Projects(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
    }



    private static void findLatest10Projects(EntityManager entityManager) {
        entityManager.createQuery("FROM Projects ORDER BY startDate DESC, name", Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(p -> System.out.printf("Project name: %s\n" +
                        " \tProject Description: %s\n" +
                        " \tProject Start Date: %s\n" +
                        " \tProject End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));
    }

    private static void getEmployeesWithProject(EntityManager entityManager) throws IOException {
        Employee employee = entityManager.find(Employee.class, Integer.parseInt(READER.readLine()));
        if (employee.getProjects().isEmpty()){
            return;
        }

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(), employee.getLastName(), employee.getJobTitile());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("\t%s%n",
                        p.getName()));
    }

    private static void addressessWithEmployeeCount(EntityManager entityManager) {
        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.printf("%s %s - %d employees%n",
                        a.getText(), a.getTown.getName(), a.getEmployees.size()));
    }

    private static void newAddress(EntityManager entityManager) throws IOException {
        String lastName = READER.readLine();
        Address address = new Address();
        address.setText("Vitotoshka 15");
        entityManager.persist(address);
        Employee employee = entityManager.createQuery("FROM Empoyee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        employee.setAddress(address);
        entityManager.persist(employee);
    }

    private static void employeesFromResearchDepartment(EntityManager entityManager) {
        entityManager
                .createQuery("FROM Employee WHERE department.name = 'Research and Development'" + " " +
                        "ORDER BY salary, id", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private static void employeesWithSalaryOver50000(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    private static void containsEmployee(EntityManager entityManager) throws IOException {
        String[] strings = READER.readLine().split("\\s+");
        Query query =
                entityManager.createQuery("FROM Employee WHERE firstName = :firstName AND lastName = :lastName", Employee.class);

        query.setParameter("firstName", strings[0]);
        query.setParameter("lastName", strings[1]);
        List<Employee> resultList = query.getResultList();

        if(resultList.isEmpty()){
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }


    private static void townsToUpperCase(EntityManager entityManager) {
        List<Town> towns =
                entityManager.createQuery("FROM Tow WHERE LENGTH(name) > 5", Town.class).getResultList();

        towns.forEach( t -> {
            t.setName(t.getName().toUpperCase());
            entityManager.persist(t);
        });

        //entityManager.createQuery("UPDATE Town SET name = UPPER(name) WHERE LENGTH(name) > 5").executeUpdate();
    }
}
