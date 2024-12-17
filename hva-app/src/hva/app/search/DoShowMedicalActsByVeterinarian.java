package hva.app.search;

import hva.Hotel;
import hva.app.Renderer;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.employee.Veterinarian;
import hva.exceptions.UnknownVeterinarianException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
	    addStringField("key", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        String key = stringField("key");
        try {
            Veterinarian vet = _receiver.findVeterinarian(key);
            vet.getMedicalActs()
                    .stream()
                    .map(v -> v.accept(new Renderer()))
                    .forEach(_display::popup);
        } catch (UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(key);
        }
    }

}
