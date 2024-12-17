package hva.app.employee;

import hva.Hotel;
import hva.app.Renderer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllEmployees extends Command<Hotel> {

    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    /** @see  pt.tecnico.uilib.menus.Command#execute() */
    @Override
    protected void execute() throws CommandException {
        _receiver.getAllEmployees()
                .stream()
                .map(v -> v.accept(new Renderer()))
                .forEach(_display::popup);
    }

}
