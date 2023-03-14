package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPets.ALICE;
import static seedu.address.testutil.TypicalPets.getTypicalPetPal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.exceptions.DuplicatePetException;
import seedu.address.testutil.PetBuilder;

public class AddressBookTest {

    private final PetPal petPal = new PetPal();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), petPal.getPetList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> petPal.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        PetPal newData = getTypicalPetPal();
        petPal.resetData(newData);
        assertEquals(newData, petPal);
    }

    @Test
    public void resetData_withDuplicatePets_throwsDuplicatePetException() {
        // Two Pets with the same identity fields
        Pet editedAlice = new PetBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Pet> newPets = Arrays.asList(ALICE, editedAlice);
        PetPalStub newData = new PetPalStub(newPets);

        assertThrows(DuplicatePetException.class, () -> petPal.resetData(newData));
    }

    @Test
    public void hasPet_nullPet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> petPal.hasPet(null));
    }

    @Test
    public void hasPet_PetNotInAddressBook_returnsFalse() {
        assertFalse(petPal.hasPet(ALICE));
    }

    @Test
    public void hasPet_PetInAddressBook_returnsTrue() {
        petPal.addPet(ALICE);
        assertTrue(petPal.hasPet(ALICE));
    }

    @Test
    public void hasPet_PetWithSameIdentityFieldsInAddressBook_returnsTrue() {
        petPal.addPet(ALICE);
        Pet editedAlice = new PetBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(petPal.hasPet(editedAlice));
    }

    @Test
    public void getPetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> petPal.getPetList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose Pets list can violate interface constraints.
     */
    private static class PetPalStub implements ReadOnlyPetPal {
        private final ObservableList<Pet> Pets = FXCollections.observableArrayList();

        PetPalStub(Collection<Pet> Pets) {
            this.Pets.setAll(Pets);
        }

        @Override
        public ObservableList<Pet> getPetList() {
            return Pets;
        }
    }

}
