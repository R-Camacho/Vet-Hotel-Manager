package hva.app.main;

import hva.HotelManager;
import hva.app.exceptions.FileOpenFailedException;
import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;

class DoOpenFile extends Command<HotelManager> {
    DoOpenFile(HotelManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    /** @see  pt.tecnico.uilib.menus.Command#execute() */
    @Override
    protected final void execute() throws CommandException {
        if (_receiver.changed() &&
                Form.confirm(Prompt.saveBeforeExit())) {
            try {
                _receiver.save();
            } catch (IOException e) {
                throw new FileOpenFailedException(e);
            } catch (MissingFileAssociationException e) {
                String filename = Form.requestString(Prompt.newSaveAs());
                try {
                    _receiver.saveAs(filename);
                } catch (IOException | MissingFileAssociationException e2) {
                    throw new FileOpenFailedException(e2);
                }
            }
        }
        try {
            _receiver.load(Form.requestString(Prompt.openFile()));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
