package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.employee.Employee;
import hva.exceptions.UnknownEmployeeException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("key", Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        try {
            Employee employee = _receiver.findEmployee(key);
            _display.popup(employee.calculateSatisfactionRounded());
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(key);
        }
    }

}
