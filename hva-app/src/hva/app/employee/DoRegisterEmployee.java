package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.DuplicateEmployeeException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        addStringField("key", Prompt.employeeKey());
        addStringField("name", Prompt.employeeName());
        addOptionField("type", Prompt.employeeType(), "VET", "TRT");
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        String name = stringField("name");

        try {
            switch (stringField("type")) {
                case "VET" -> _receiver.registerVeterinarian(key, name);
                case "TRT" -> _receiver.registerKeeper(key, name);
            }
        } catch (DuplicateEmployeeException e) {
            throw new DuplicateEmployeeKeyException(key);
        }
    }

}
