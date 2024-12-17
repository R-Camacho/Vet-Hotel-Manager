package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.UnknownAnimalException;
import hva.habitat.Animal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("key", Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String key = stringField("key");
        try {
            Animal animal = _receiver.findAnimal(key);
            int satisfaction = animal.calculateSatisfactionRounded();
            _display.popup(satisfaction);
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(key);
        }
    }

}
