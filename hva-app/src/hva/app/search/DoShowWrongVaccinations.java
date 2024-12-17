package hva.app.search;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowWrongVaccinations extends Command<Hotel> {

    DoShowWrongVaccinations(Hotel receiver) {
        super(Label.WRONG_VACCINATIONS, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        _receiver.getWrongVaccinations()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }

}
