

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { org.vijay.tddCrud.domain.OperationsTest.class,
	org.vijay.tddCrud.domain.CustomerCrudServiceTest.class })
public class AllFastTests {
}

//Use command mvn -Dtest=AllFastTests test to run this testsuite