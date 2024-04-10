import org.example.controller;
import org.example.mage;
import org.example.repository;
import org.junit.*;
import java.util.*;
import static org.mockito.Mockito.*;


public class  repositoryTests {
	private repository mageRepository;
	private controller mageController;
	private mage existing, notexisting;

	@Before
	public void setup() {
		this.mageRepository = new repository();
		this.existing = new mage("existing", 10);
		this.notexisting = new mage("notexisting", 5);
		this.mageRepository.getMages().add(this.existing);
		this.mageController = new controller(this.mageRepository);
	}

	@Test
	public void checkDeleteRep() {
		System.out.println("\nDeleting test for repository => (Not existing)");
		this.mageRepository.delete(this.notexisting.getName());
	}

	@Test
	public void checkFindRep() {
		System.out.println("\nFinding test for repository =>\nNot existing: " + this.mageRepository.find(this.notexisting.getName()) + "\nExisting: " + this.mageRepository.find(this.existing.getName()));
	}

	@Test
	public void checkSaveRep() {
		System.out.println("\nSaving test for repository => (Existing)");
		this.mageRepository.save(this.existing);
	}
}
