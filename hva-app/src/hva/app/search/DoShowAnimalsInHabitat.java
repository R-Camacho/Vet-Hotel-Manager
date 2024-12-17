package hva.app.search;

import hva.Hotel;
import hva.app.Renderer;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownHabitatException;
import hva.habitat.Habitat;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("key", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        try {
            Habitat habitat = _receiver.findHabitat(key);
            habitat.getAllAnimals()
                    .stream()
                    .map(v-> v.accept(new Renderer()))
                    .forEach(_display::popup);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(key);
        }
    }

}
