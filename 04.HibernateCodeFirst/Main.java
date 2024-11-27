package bg.softuni;

import bg.softuni.composition.Batch;
import bg.softuni.composition.Ingredient;
import bg.softuni.composition.Label;
import bg.softuni.composition.Shampoo;
import bg.softuni.entities.Bike;
import bg.softuni.entities.Car;
import bg.softuni.entities.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpa-relations");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        Bike bike = new Bike();
        bike.setWheelSize(23.3);

        Car car = new Car();
        car.setPaintCode("red");

        entityManager.persist(car);
        entityManager.persist(bike);

        List<Vehicle> vehicles = List.of(bike, car);

        persistShampoo(entityManager);

        Shampoo shampoo = entityManager.find(Shampoo.class, 1L);
        System.out.println(shampoo.getBatch().getSerialNumber());

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    private static void persistShampoo(EntityManager entityManager) {
        Shampoo shampoo = new Shampoo();
        Label label = new Label();
        label.setTitle("Primary Label");
        Batch batch = new Batch();
        batch.setSerialNumber("2-xd-ff344");

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Lavander");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Salt");

        shampoo.setLabel(label);
        shampoo.setBatch(batch);
        shampoo.setIngredients(List.of(ingredient, ingredient2));

        entityManager.persist(label);
        entityManager.persist(shampoo);
        entityManager.persist(batch);
        entityManager.persist(ingredient);
        entityManager.persist(ingredient2);
    }
}