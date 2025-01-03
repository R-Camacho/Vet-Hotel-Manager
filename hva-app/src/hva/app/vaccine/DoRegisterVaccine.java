package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.DuplicateVaccineException;
import hva.exceptions.UnknownSpeciesException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
	    addStringField("key", Prompt.vaccineKey());
	    addStringField("name", Prompt.vaccineName());
	    addStringField("speciesKeys", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        String key = stringField("key");
        String name = stringField("name");
        String[] speciesKeys = stringField("speciesKeys")
                .split(",");
        try {
            _receiver.registerVaccine(key, name, speciesKeys);
        } catch (DuplicateVaccineException e) {
            throw new DuplicateVaccineKeyException(key);
        }  catch (UnknownSpeciesException e) {
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
