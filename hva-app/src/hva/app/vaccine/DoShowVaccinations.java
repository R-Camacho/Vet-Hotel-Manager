package hva.app.vaccine;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.getAllVaccinations()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }
}
