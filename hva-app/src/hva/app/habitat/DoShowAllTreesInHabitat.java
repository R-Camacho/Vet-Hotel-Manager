package hva.app.habitat;

import hva.Hotel;
import hva.app.Renderer;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.UnknownHabitatException;
import hva.habitat.Habitat;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("key", Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        try {
            Habitat habitat = _receiver.findHabitat(key);
            habitat.getAllTrees()
                   .stream()
                   .map(v-> v.accept(new Renderer()))
                   .forEach(_display::popup);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(key);
        }
    }

}
