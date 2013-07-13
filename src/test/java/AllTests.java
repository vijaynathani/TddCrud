import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { org.vijay.tddCrud.model.CustomerTableTest.class,
	org.vijay.tddCrud.acceptanceTests.CustomerFunctionalTest.class,
	org.vijay.tddCrud.concordionTests.DemoTest.class,
        // Other slow tests can be added here.
        // Run all fast tests also
        AllFastTests.class })
public class AllTests {
	@AfterClass
	public static void closeDatabase() {
		// BaseTest.closeDatabase();
	}
}
//"mvn test" will run all the tests, even without this test suite
