package hva.app.main;

import hva.HotelManager;
import hva.exceptions.MissingFileAssociationException;
import hva.app.exceptions.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;

class DoSaveFile extends Command<HotelManager> {
    DoSaveFile(HotelManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
    }

    /** @see  pt.tecnico.uilib.menus.Command#execute() */
    @Override
    public final void execute() throws CommandException {
        try {
            _receiver.save();
        } catch (MissingFileAssociationException e) {
            String filename = Form.requestString(Prompt.newSaveAs());
            try {
                _receiver.saveAs(filename);
            } catch (IOException | MissingFileAssociationException e1) {
                throw new FileOpenFailedException(e1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileOpenFailedException(e);
        }
    }

}
