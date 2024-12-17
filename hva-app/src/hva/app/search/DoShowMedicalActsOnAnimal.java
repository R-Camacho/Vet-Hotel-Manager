package hva.app.search;

import hva.Hotel;
import hva.app.Renderer;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownAnimalException;
import hva.habitat.Animal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("key", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        String key  = stringField("key");
        try {
            Animal animal  = _receiver.findAnimal(key);
            animal.getMedicalHistory()
                    .stream()
                    .map(v -> v.accept(new Renderer()))
                    .forEach(_display::popup);

        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(key);
        }
    }

}
