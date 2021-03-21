package com.uniovi.tests;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uniovi.entities.Sale;
import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;
import com.uniovi.services.RolesService;
import com.uniovi.services.SalesService;
import com.uniovi.services.UsersService;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests_16_OfferMessagesTests {

	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;

	@Autowired
	private SalesService salesService;

	@Autowired
	private UsersRepository usersRepository;

	static WebDriver driver = getDriver(PathTests.PathFirefox65, PathTests.Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp() throws Exception {
		init();
		driver.navigate().to(URL);

	}

	// Después de cadaprueba se borran las cookies del navegador
	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();

	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR31. Sobre una búsqueda determinada de ofertas (a elección de
	// desarrollador), enviar un mensaje
	// a una oferta concreta. Se abriría dicha conversación por primera vez.
	// Comprobar que el mensaje aparece
	// en el listado de mensajes.
	@Test
	public void PR31() {
		// Nos logeamos como usuario y que comprobamos que aparecemos en la página
		// de este

		PO_PrivateView.loginAndCheckKey(driver, "PedroDiaz@gmail.com", "123456", "usuarioAutenticado.message",
				PO_Properties.getSPANISH());

		// Pinchamos en la opción de ir de compras: //a[contains(@href,
		// 'sale/shopping')]
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'sale/shopping')]");

		elementos.get(0).click();

		// Compramos una oferta
		elementos = PO_View.checkElement(driver, "free",
				"//*[contains(text(), 'Coche de playmobil')]/following-sibling::*/a[contains(@href, 'sale/conversation')]");
		elementos.get(0).click();
		
		WebElement mensaje = driver.findElement(By.name("message"));
		mensaje.click();
		mensaje.clear();
		mensaje.sendKeys("Mensaje de prueba");
		
		//Pulsar el boton de Enviar.
		By boton = By.className("btn");
		driver.findElement(boton).click();
		
		PO_View.checkElement(driver, "text", "Mensaje de prueba");
		
		// Cerrar sesion
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	
		// PR31. Sobre el listado de conversaciones enviar un mensaje a una conversación ya abierta.
		//Comprobar que el mensaje aparece en la lista de mensajes.
		@Test
		public void PR32() {
			// Nos logeamos como usuario y que comprobamos que aparecemos en la página
			// de este

			PO_PrivateView.loginAndCheckKey(driver, "PedroDiaz@gmail.com", "123456", "usuarioAutenticado.message",
					PO_Properties.getSPANISH());

			// Pinchamos en la opción de ir de compras: //a[contains(@href,
			// 'sale/shopping')]
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'sale/shopping')]");

			elementos.get(0).click();

			// Compramos una oferta
			elementos = PO_View.checkElement(driver, "free",
					"//*[contains(text(), 'Coche de playmobil')]/following-sibling::*/a[contains(@href, 'sale/conversation')]");
			elementos.get(0).click();
			
			WebElement mensaje = driver.findElement(By.name("message"));
			mensaje.click();
			mensaje.clear();
			mensaje.sendKeys("Mensaje de prueba");
			
			//Pulsar el boton de Enviar.
			By boton = By.className("btn");
			driver.findElement(boton).click();
			
			PO_View.checkElement(driver, "text", "Mensaje de prueba");
			
			mensaje = driver.findElement(By.name("message"));
			mensaje.click();
			mensaje.clear();
			mensaje.sendKeys("Mensaje de con una conversacion ya abierta");
			
			PO_View.checkElement(driver, "text", "Mensaje de con una conversacion ya abierta");
			boton = By.className("btn");
			//Pulsar el boton de Enviar.
			driver.findElement(boton).click();
			
			// Cerrar sesion
			PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
		}
	
	public void init() {
		usersRepository.deleteAll();
		User user1 = new User("PedroDiaz@gmail.com", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setMoney(100);
		user1.setRole(rolesService.getRoles()[0]);
		User user2 = new User("LucasNuñez@gmail.com", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setMoney(100);
		user2.setRole(rolesService.getRoles()[0]);
		User user3 = new User("MariaRodriquez@gmail.com", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setMoney(100);
		user3.setRole(rolesService.getRoles()[0]);
		User user4 = new User("LuciaGonzalez@gmail.com", "Lucía", "González");
		user4.setPassword("123456");
		user4.setMoney(100);
		user4.setRole(rolesService.getRoles()[0]);
		User user5 = new User("PabloVega@gmail.com", "Pablo", "Vega");
		user5.setPassword("123456");
		user5.setMoney(100);
		user5.setRole(rolesService.getRoles()[0]);
		User user6 = new User("MartaSuarez@gmail.com", "Marta", "Suarez");
		user6.setPassword("123456");
		user6.setMoney(100);
		user6.setRole(rolesService.getRoles()[0]);
		User user7 = new User("AntonioMoran@gmail.com", "Antonio", "Morán");
		user7.setPassword("123456");
		user7.setMoney(100);
		user7.setRole(rolesService.getRoles()[0]);
		User user8 = new User("admin@email.com", "Administrador", "Administrador");
		user8.setMoney(0);
		user8.setPassword("admin");
		user8.setRole(rolesService.getRoles()[1]);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);

		Sale sale1 = new Sale("Bici de montaña", "Bici de degunda mano", 200.50, user1);
		Sale sale2 = new Sale("Bici de carretera", "Bici de segunda mano", 300, user2);
		Sale sale3 = new Sale("Ordenador portatil HP", "Portatil", 400, user3);
		Sale sale4 = new Sale("Ordenador de sobremesa", "PC viejo", 150, user4);
		Sale sale5 = new Sale("Nevera con congelador", "Nevera", 80, user5);
		Sale sale6 = new Sale("Ruedas de bici", "Ruedas de bici antipinchazo", 30, user6);
		Sale sale7 = new Sale("Juego de mesa", "Juego de mesa del monoply", 150, user7);
		Sale sale8 = new Sale("Mesa de salón", "Mesa de roble", 50.25, user1);
		Sale sale9 = new Sale("Telescopio", "Telescopio", 200, user2);
		Sale sale10 = new Sale("Gafas de buceo", "Gafas de buceo", 10, user3);
		Sale sale11 = new Sale("Gafas de cerca", "Gafas para leer de cerca", 50, user4);
		Sale sale12 = new Sale("Gafas de sol", "Gafas", 30, user5);
		Sale sale13 = new Sale("Desbrozadora", "Desbrozadora", 95.5, user6);
		Sale sale14 = new Sale("Barco de playmobil", "Barco pirata de playmovil", 60, user7);
		Sale sale15 = new Sale("Barco de pesca", "Barco de pesca como nuevo", 20000, user1);
		Sale sale16 = new Sale("Gato hidraulico", "Gato hidraulico", 44.5, user2);
		Sale sale17 = new Sale("Rascador para gatos", "Rascador", 15, user3);
		Sale sale18 = new Sale("Castillo para gatos", "Castillo grande para gatos", 100, user4);
		Sale sale19 = new Sale("Coche de playmobil", "Coche de policia de playmobil", 27.8, user5);
		Sale sale20 = new Sale("Helicoptero de playmobil", "Desbrozadora", 40.5, user6);
		Sale sale21 = new Sale("Helicoptero", "Helicoptero", 135000, user7);

		sale3.setOutstanding(true);
		sale15.setOutstanding(true);
		sale21.setOutstanding(true);
		sale17.setBuyer(user1);
		sale17.setAvailable(false);
		sale20.setBuyer(user1);
		sale20.setAvailable(false);

		salesService.addSale(sale1);
		salesService.addSale(sale2);
		salesService.addSale(sale3);
		salesService.addSale(sale4);
		salesService.addSale(sale5);
		salesService.addSale(sale6);
		salesService.addSale(sale7);
		salesService.addSale(sale8);
		salesService.addSale(sale9);
		salesService.addSale(sale10);
		salesService.addSale(sale11);
		salesService.addSale(sale12);
		salesService.addSale(sale13);
		salesService.addSale(sale14);
		salesService.addSale(sale15);
		salesService.addSale(sale16);
		salesService.addSale(sale17);
		salesService.addSale(sale18);
		salesService.addSale(sale19);
		salesService.addSale(sale20);
		salesService.addSale(sale21);

		salesService.buy(user1, sale2);
		salesService.buy(user1, sale4);
		salesService.buy(user2, sale1);
		salesService.buy(user2, sale5);
		salesService.buy(user3, sale6);
		salesService.buy(user3, sale7);
		salesService.buy(user4, sale9);
		salesService.buy(user4, sale13);
		salesService.buy(user5, sale14);
		salesService.buy(user5, sale16);
		salesService.buy(user6, sale3);
		salesService.buy(user6, sale8);
		salesService.buy(user7, sale15);
		salesService.buy(user7, sale11);

	}
}