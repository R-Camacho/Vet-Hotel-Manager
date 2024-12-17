package hva.app.habitat;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;

class DoShowAllHabitats extends Command<Hotel> {

    DoShowAllHabitats(Hotel receiver) {
        super(Label.SHOW_ALL_HABITATS, receiver);
    }

    @Override
    protected void execute() {
        _receiver.getAllHabitats()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }

}
