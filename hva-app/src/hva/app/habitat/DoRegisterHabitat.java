package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.DuplicateHabitatException;
import hva.exceptions.InvalidAreaException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("key", Prompt.habitatKey());
        addStringField("name", Prompt.habitatName());
        addIntegerField("area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        String name = stringField("name");
        int area = integerField("area");
        try {
            _receiver.registerHabitat(key, name, area);
        } catch (DuplicateHabitatException e) {
            throw new DuplicateHabitatKeyException(key);
        } catch (InvalidAreaException e) {
            _display.popup("Área inválida!");
        }
    }

}
