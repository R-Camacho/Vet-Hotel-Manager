package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpeciesException;
import hva.habitat.Habitat;
import hva.habitat.InfluencePolarity;
import hva.habitat.Species;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("speciesKey", hva.app.animal.Prompt.speciesKey());
        addOptionField("influence",
                Prompt.habitatInfluence(), "POS", "NEG", "NEU");
    }

    @Override
    protected void execute() throws CommandException {
        InfluencePolarity influence;
        switch (optionField("influence")) {
            case "POS" -> influence = InfluencePolarity.POS;
            case "NEG" -> influence = InfluencePolarity.NEG;
            default -> influence = InfluencePolarity.NEU;
        }

        String habitatKey = stringField("habitatKey");
        String speciesKey = stringField("speciesKey");
        try {
            Habitat habitat = _receiver.findHabitat(habitatKey);
            Species species = _receiver.findSpecies(speciesKey);
            habitat.changeSpeciesInfluence(species, influence);
            _receiver.changed();
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(habitatKey);
        } catch (UnknownSpeciesException e) {
            throw new UnknownSpeciesKeyException(speciesKey);
        }
    }

}
