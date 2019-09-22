package net.streets.authentication.test;

import org.testng.annotations.Test;

import static net.streets.authentication.StreetsAuthenticator.generateSalt;
import static net.streets.authentication.StreetsAuthenticator.hashPassword;
import static org.testng.Assert.assertNotNull;

@Test
public class StreetsAuthenticatorTest {

//	@BeforeClass
//	public void setUp() {
//		new ClassPathXmlApplicationContext("test-str_authentication-context.xml");
//	}

	@Test
	public void hashPasswordTest() throws Exception {
		String salt = generateSalt();
		System.out.println("Hashing password 1234 with salt " + salt);
		String hashedPassword = hashPassword("1234", salt);
		System.out.println("Salted hashed password = " + hashedPassword);
		assertNotNull(hashedPassword);
	}}
