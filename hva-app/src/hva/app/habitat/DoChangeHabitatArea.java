package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.InvalidAreaException;
import hva.exceptions.UnknownHabitatException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
        addStringField("key", Prompt.habitatKey());
        addIntegerField("area", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        Integer area = integerField("area");

        try {
            _receiver.changeHabitatArea(key, area);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(key);
        } catch (InvalidAreaException e) {
            _display.popup("Área inválida!");
        }
    }

}
