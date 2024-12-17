package hva.app.habitat;

import hva.Hotel;
import hva.app.Renderer;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.DuplicateTreeException;
import hva.exceptions.UnknownHabitatException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("treeKey", Prompt.treeKey());
        addStringField("treeName", Prompt.treeName());
        addIntegerField("treeAge", Prompt.treeAge());
        addIntegerField("treeDifficulty", Prompt.treeDifficulty());
        addOptionField("treeType",
                Prompt.treeType(), "PERENE", "CADUCA");
    }

    @Override
    protected void execute() throws CommandException {
        String habitatKey = stringField("habitatKey");
        String treeKey = stringField("treeKey");
        String treeName = stringField("treeName");
        int treeAge = integerField("treeAge");
        int treeDifficulty = integerField("treeDifficulty");
        try {
            switch (stringField("treeType")) {
                case "PERENE" ->
                    _display.popup(_receiver.registerEvergreenTree(treeKey,
                                    treeName, treeAge, treeDifficulty, habitatKey)
                            .accept(new Renderer()));

                case "CADUCA" ->
                    _display.popup(_receiver.registerDeciduousTree(treeKey,
                                    treeName, treeAge, treeDifficulty, habitatKey)
                            .accept(new Renderer()));

            }

        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(habitatKey);
        } catch (DuplicateTreeException e) {
            throw new DuplicateTreeKeyException(treeKey);
        }
    }

}
