package hva.app.vaccine;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;

class DoShowAllVaccines extends Command<Hotel> {

    DoShowAllVaccines(Hotel receiver) {
        super(Label.SHOW_ALL_VACCINES, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.getAllVaccines()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }
}