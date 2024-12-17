package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.exceptions.*;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("vaccineKey", Prompt.vaccineKey());
        addStringField("veterinarianKey", Prompt.veterinarianKey());
        addStringField("animalKey", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String vaccineKey = stringField("vaccineKey");
        String veterinarianKey = stringField("veterinarianKey");
        String animalKey = stringField("animalKey");

        try {
            _receiver.registerMedicalAct(vaccineKey, veterinarianKey, animalKey);
        } catch (UnknownVaccineException e) {
            throw new UnknownVaccineKeyException(vaccineKey);
        } catch (UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(veterinarianKey);
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(animalKey);
        } catch (VeterinarianAuthorisationException e) {
            throw new VeterinarianNotAuthorizedException
                    (veterinarianKey, e.getSpeciesKey());
        } catch (WrongVaccineException e) {
            _display.popup(Message.wrongVaccine(vaccineKey, animalKey));
        }
    }

}
