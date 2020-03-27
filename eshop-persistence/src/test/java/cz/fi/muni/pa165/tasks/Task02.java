package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	static Category electro = new Category();
	static Category kitchen = new Category();
	static Product flashlight = new Product();
	static Product kitchenRobot = new Product();
	static Product plate = new Product();


	@BeforeClass
	public void before(){
		flashlight.setName("Flashlight");
		kitchenRobot.setName("Kitchen Robot");
		plate.setName("Plate");

		electro.setName("Electro");
		electro.addProduct(flashlight);
		electro.addProduct(kitchenRobot);
		kitchen.setName("Kitchen");
		kitchen.addProduct(kitchenRobot);
		kitchen.addProduct(plate);

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(flashlight);
		entityManager.persist(kitchenRobot);
		entityManager.persist(plate);

		entityManager.persist(electro);
		entityManager.persist(kitchen);

		entityManager.getTransaction().commit();
		entityManager.close();
	}


	@Test
	public void testElectro() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Category category = entityManager.find(Category.class, electro.getId());
		entityManager.getTransaction().commit();

		assertContainsProductWithName(category.getProducts(), flashlight.getName());
		assertContainsProductWithName(category.getProducts(), kitchenRobot.getName());

		entityManager.close();
	}

	@Test
	public void testKitchen() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Category category = entityManager.find(Category.class, kitchen.getId());
		entityManager.getTransaction().commit();

		assertContainsProductWithName(category.getProducts(), plate.getName());
		assertContainsProductWithName(category.getProducts(), kitchenRobot.getName());

		entityManager.close();
	}

	@Test
	public void testFlashlight() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Product product = entityManager.find(Product.class, flashlight.getId());
		entityManager.getTransaction().commit();

		assertContainsCategoryWithName(product.getCategories(), electro.getName());

		entityManager.close();
	}

	@Test
	public void testPlate() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Product product = entityManager.find(Product.class, plate.getId());
		entityManager.getTransaction().commit();

		assertContainsCategoryWithName(product.getCategories(), kitchen.getName());

		entityManager.close();
	}

	@Test
	public void testKitchenRobot() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Product product = entityManager.find(Product.class, kitchenRobot.getId());
		entityManager.getTransaction().commit();

		assertContainsCategoryWithName(product.getCategories(), electro.getName());
		assertContainsCategoryWithName(product.getCategories(), kitchen.getName());

		entityManager.close();
	}

	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testTask05(){
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(new Product());

		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}
