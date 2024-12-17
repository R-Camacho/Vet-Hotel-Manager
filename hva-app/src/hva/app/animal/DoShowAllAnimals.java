package hva.app.animal;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;

class DoShowAllAnimals extends Command<Hotel> {

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.getAllAnimals()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }

}
