package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.DuplicateAnimalException;
import hva.exceptions.DuplicateSpeciesException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpeciesException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("key", Prompt.animalKey());
        addStringField("name", Prompt.animalName());
        addStringField("speciesKey", Prompt.speciesKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String speciesKey = stringField("speciesKey");
        try {
            _receiver.findSpecies(speciesKey);
        } catch (UnknownSpeciesException e) {
            String speciesName = Form.requestString(Prompt.speciesName());
            try {
                _receiver.registerSpecies(speciesKey, speciesName);
            } catch (DuplicateSpeciesException e1) {
                _display.popup("Uma espécie com o nome "
                        + speciesName + " já existe.");
            }
        }
        String animalKey = stringField("key");
        String animalName = stringField("name");
        String habitatKey =
                Form.requestString(hva.app.habitat.Prompt.habitatKey());
        try {
            _receiver.registerAnimal(animalKey, animalName, speciesKey, habitatKey);
        } catch (DuplicateAnimalException e) {
            throw new DuplicateAnimalKeyException(animalKey);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(habitatKey);
        }
    }

}
