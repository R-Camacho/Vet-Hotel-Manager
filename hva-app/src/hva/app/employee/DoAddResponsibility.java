package hva.app.employee;

import hva.Hotel;
import hva.exceptions.ResponsibilityException;
import hva.util.Responsibility;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.NoResponsibilityException;
import hva.employee.Employee;
import hva.exceptions.UnknownEmployeeException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("employeeKey", Prompt.employeeKey());
        addStringField("responsibilityKey", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        String employeeKey = stringField("employeeKey");
        String responsibilityKey = stringField("responsibilityKey");

        try {
            Employee employee = _receiver.findEmployee(employeeKey);
            Responsibility responsibility =
                    _receiver.findResponsibility(responsibilityKey);
            employee.addResponsibility(responsibility);
            _receiver.changed();
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(employeeKey);
        } catch (ResponsibilityException e) {
            throw new NoResponsibilityException(employeeKey, responsibilityKey);
        }
    }

}
