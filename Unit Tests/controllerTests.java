import org.example.controller;
import org.example.mage;
import org.example.repository;
import org.junit.*;
import java.util.*;

import static org.mockito.Mockito.*;

public class controllerTests {
	private repository mageRepository;
	private controller mageController;
	private mage existing, notexisting;

	@Before
	public void setup(){
		this.mageRepository = mock(repository.class);
		this.mageController = new controller(this.mageRepository);
		this.existing = new mage("existing",10);
		this.notexisting = new mage("notexisting",5);

		when(this.mageRepository.find("existing")).thenReturn(Optional.of(existing));
		when(this.mageRepository.find("notexisting")).thenReturn(Optional.empty());
	}

	@Test
	public void checkDeleteCon(){
		System.out.println("\nDeleting test for controller =>\nNot existing: " + this.mageController.delete(this.notexisting.getName()) +"\nExisting: " + this.mageController.delete(this.existing.getName()));
		verify(this.mageRepository, times(2)).delete(anyString());
	}
	@Test
	public void checkFindCon(){
		System.out.println("\nFinding test for controller =>\nNot existing: " + this.mageController.find(this.notexisting.getName()) +"\nExisting: " + this.mageController.find(this.existing.getName()));
		verify(this.mageRepository, times(2)).find(anyString());
	}
	@Test
	public void checkSaveCon(){
		System.out.println("\nSaving test for controller =>\nNot existing: " + this.mageController.save(this.notexisting.getName(), this.notexisting.strLevel()) +"\nExisting: " + this.mageController.save(this.existing.getName(),this.existing.strLevel()));
		verify(this.mageRepository, times(2)).save(any(mage.class));
	}
}


