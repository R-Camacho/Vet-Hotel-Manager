package hva;

import java.io.*;
import java.util.*;

import hva.employee.Employee;
import hva.employee.Keeper;
import hva.employee.Veterinarian;
import hva.exceptions.*;
import hva.habitat.Animal;
import hva.habitat.Habitat;
import hva.habitat.Species;
import hva.tree.deciduous.DeciduousTree;
import hva.tree.evergreen.EvergreenTree;
import hva.tree.Tree;
import hva.util.Responsibility;
import hva.util.Season;
import hva.vaccine.MedicalAct;
import hva.vaccine.Vaccine;

public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;

    /**
     * Hotel object has been changed.
     */
    private boolean _changed = false;

    /**
     * Species.
     */
    private final Map<String, Species> _species =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Habitats.
     */
    private final Map<String, Habitat> _habitats =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Buffer to keep trees before their habitats have been loaded.
     */
    private final Map<String, Tree> _treeBuffer =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * The employees working in the hotel.
     */
    private final Map<String, Employee> _employees =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** The Vaccines available in the hotel. */
    private final Map<String, Vaccine> _vaccines =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** All vaccination events in the hotel. */
    private final List<MedicalAct> _medicalActs = new ArrayList<>();

    /** The current season (always starts on spring). */
    private Season _season = Season.SPRING;

    /**
     * Constructor: empty by default
     */
    public Hotel() {
        // empty: default
    }

    /**
     * Advances to next season
     * @return the number of the new season
     * being Spring 0 , Summer 1, Fall 2, Winter 3
     */
    public int nextSeason() {
        _season = _season.next();
        getAllTrees().forEach(Tree::ageSeason);
        changed();
        return _season.ordinal();
    }

    /**
     * @return the current season
     */
    private Season getSeason() { return _season; }

    /**
     * @return has hotel changed?
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * @param changed hotel has changed
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Set changed.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * Adds new tree to the buffer
     * @param tree new tree to add
     * @throws DuplicateTreeException when tree with the same key exists
     */
    private void addTree(Tree tree) throws DuplicateTreeException {
        assertNewTree(tree.getKey());
        _treeBuffer.put(tree.getKey(), tree);
        changed();
    }

    /**
     * Checks buffer for existence of this new tree
     * @param key tree key
     * @throws DuplicateTreeException when tree with the same key exists
     */
    private void assertNewTree(String key) throws DuplicateTreeException {
        if (_treeBuffer.containsKey(key))
            throw new DuplicateTreeException(key);
    }

    /**
     * Removes the tree from the buffer after being associated with its habitat
     * @param tree tree to remove
     */
    public void removeTree(Tree tree) {
        _treeBuffer.remove(tree.getKey());
    }

    /**
     * Register a new species to the hotel
     * @param key species key
     * @param name species name
     * @throws DuplicateSpeciesException If a species with that key
     * or name already exists
     */
    public void registerSpecies(String key, String name)
            throws DuplicateSpeciesException {
        if (_species.containsKey(key))
            throw new DuplicateSpeciesException(key);
        for (Species species : _species.values()) { // check for same name
            if (species.getName().equals(name))
                throw new DuplicateSpeciesException(key);
        }

        Species species = new Species(key, name);
        _species.put(key, species);
        changed();
    }

    /**
     * Register a new habitat to the hotel
     * @param key habitat key
     * @param name habitat name
     * @param area habitat area
     * @throws DuplicateHabitatException If a habitat with that key
     * already exists
     */
    public void registerHabitat(String key, String name, int area)
            throws DuplicateHabitatException, InvalidAreaException {
        if (_habitats.containsKey(key))
            throw new DuplicateHabitatException(key);
        if (area <= 0)
            throw new InvalidAreaException(area);

        Habitat habitat = new Habitat(key, name, area);
        _habitats.put(key, habitat);
        changed();
    }

    /**
     * Register a new animal to the hotel
     * @param key animal key
     * @param name animal name
     * @param speciesKey species key
     * @param habitatKey habitat key
     * @throws DuplicateAnimalException if animal with the same key exists
     * @throws UnknownHabitatException if habitat does not exist
     */
    public void registerAnimal
    (String key, String name, String speciesKey, String habitatKey)
            throws DuplicateAnimalException, UnknownHabitatException {
        for (Animal checkAnimal : getAllAnimals()) {
            if (checkAnimal.getKey().equals(key))
                throw new DuplicateAnimalException(key);
        }
        if (!_habitats.containsKey(habitatKey))
            throw new UnknownHabitatException(habitatKey);

        Habitat habitat = _habitats.get(habitatKey);
        Species species = _species.get(speciesKey);
        Animal animal = new Animal(key, name, species, habitat);

        habitat.addAnimal(animal);
        species.addAnimal(animal);
        changed();
    }

    /**
     * Registers a new keeper to the hotel
     * @param key keeper key
     * @param name keeper name
     * @throws DuplicateEmployeeException if employee with the same key exists
     */
    public void registerKeeper(String key, String name)
            throws DuplicateEmployeeException {
        if (_employees.containsKey(key))
            throw new DuplicateEmployeeException(key);

        Keeper keeper = new Keeper(key, name);

        _employees.put(key, keeper);
        changed();
    }

    /**
     * Registers a new veterinarian to the hotel
     * @param key veterinarian key
     * @param name veterinarian name
     * @throws DuplicateEmployeeException if employee with the same key exists
     */
    public void registerVeterinarian(String key, String name)
            throws DuplicateEmployeeException {
        if (_employees.containsKey(key))
            throw new DuplicateEmployeeException(key);

        Veterinarian veterinarian = new Veterinarian(key, name);

        _employees.put(key, veterinarian);
        changed();
    }

    /**
     * Register a new vaccine to the hotel
     * @param key vaccine key
     * @param name vaccine name
     * @param speciesKeys keys from all the species the vaccine can be
     *                    applied to
     * @throws DuplicateVaccineException if vaccine with the same key exists
     * @throws UnknownSpeciesException if a species does not exist
     */
    public void registerVaccine(String key, String name, String... speciesKeys)
            throws DuplicateVaccineException, UnknownSpeciesException {
        if (_vaccines.containsKey(key))
            throw new DuplicateVaccineException(key);

        Vaccine vaccine = new Vaccine(key, name);

        if (speciesKeys != null) {
            for (String speciesKey : speciesKeys) {
                vaccine.addSpecies(findSpecies(speciesKey));
            }
        }
        _vaccines.put(key, vaccine);
        changed();
    }

    /**
     * Registers a new evergreen tree and adds it to its habitat
     * @param key tree key
     * @param name tree name
     * @param age tree age
     * @param difficulty tree difficulty
     * @param habitatKey tree habitat
     * @return the tree
     * @throws DuplicateTreeException if tree with the same key exists
     * @throws UnknownHabitatException if habitat does not exist
     */
    public EvergreenTree registerEvergreenTree
    (String key, String name, int age, int difficulty, String habitatKey)
            throws DuplicateTreeException, UnknownHabitatException {
        try {
            findTree(key);
        } catch (UnknownTreeException e) {
            Habitat habitat = findHabitat(habitatKey);
            EvergreenTree tree =
                    new EvergreenTree(key, name, age, difficulty, getSeason());
            habitat.plantTree(tree);
            changed();
            return tree;
        }
        throw new DuplicateTreeException(key);

    }

    /**
     * Registers a new deciduous tree and adds it to its habitat
     * @param key tree key
     * @param name tree name
     * @param age tree age
     * @param difficulty tree difficulty
     * @param habitatKey tree habitat
     * @return the tree
     * @throws DuplicateTreeException if tree with the same key exists
     * @throws UnknownHabitatException if habitat does not exist
     */
    public DeciduousTree registerDeciduousTree
    (String key, String name, int age, int difficulty, String habitatKey)
            throws DuplicateTreeException, UnknownHabitatException {
        try {
            findTree(key);
        } catch (UnknownTreeException e) {
            Habitat habitat = findHabitat(habitatKey);
            DeciduousTree tree =
                    new DeciduousTree(key, name, age, difficulty, getSeason());
            habitat.plantTree(tree);
            changed();
            return tree;
        }
        throw new DuplicateTreeException(key);

    }

    /**
     * Registers a new vaccination event
     * @param vaccineKey vaccine key
     * @param veterinarianKey vet key
     * @param animalKey animal key
     * @throws UnknownVaccineException if vaccine does not exist
     * @throws UnknownVeterinarianException if vet does not exist
     * @throws UnknownAnimalException if animal does not exist
     * @throws VeterinarianAuthorisationException if vet is not
     * authorised to vaccinate this species
     * @throws WrongVaccineException if the vaccine is not suited
     * for this species
     */
    public void registerMedicalAct
            (String vaccineKey, String veterinarianKey, String animalKey)
        throws UnknownVaccineException, UnknownVeterinarianException,
            UnknownAnimalException, VeterinarianAuthorisationException,
            WrongVaccineException {

        Vaccine vaccine = findVaccine(vaccineKey);
        Veterinarian veterinarian = findVeterinarian(veterinarianKey);
        Animal animal = findAnimal(animalKey);
        Species species = animal.getSpecies();

        if (!veterinarian.getSpecies().contains(species))
            throw new VeterinarianAuthorisationException
                    (veterinarianKey, species.getKey());

        MedicalAct medicalAct = new MedicalAct(vaccine, animal, veterinarian);

        animal.vaccinate(medicalAct);
        veterinarian.addMedicalAct(medicalAct);
        vaccine.apply();
        _medicalActs.add(medicalAct);
        changed();

        if (medicalAct.isWrongVaccination())
            throw new WrongVaccineException();

    }

    /**
     * Transfer animal to another habitat
     * @param animalKey animal key
     * @param habitatKey habitat key
     * @throws UnknownAnimalException if animal does not exist
     * @throws UnknownHabitatException if habitat does not exist
     */
    public void transferAnimal(String animalKey, String habitatKey)
        throws UnknownAnimalException, UnknownHabitatException {
        Animal animal = findAnimal(animalKey);
        Habitat habitat = findHabitat(habitatKey);
        animal.transferTo(habitat);
        changed();
    }

    /**
     * Change habitat area
     * @param habitatKey habitat key
     * @param area new area
     * @throws UnknownHabitatException if habitat does not exist
     * @throws InvalidAreaException if area is not positive
     */
    public void changeHabitatArea(String habitatKey, int area)
            throws UnknownHabitatException, InvalidAreaException {
        Habitat habitat = findHabitat(habitatKey);
        habitat.setArea(area);
        changed();
    }

    /**
     * Finds a species in the hotel
     * @param key species key
     * @return the species
     * @throws UnknownSpeciesException if species does not exist
     */
    public Species findSpecies(String key) throws UnknownSpeciesException{
        if (!_species.containsKey(key))
            throw new UnknownSpeciesException(key);
        return _species.get(key);
    }

    /**
     * Returns the tree in the tree buffer
     * @param key the tree key
     * @return The tree
     * @throws UnknownTreeException if the tree does not exist
     */
    private Tree findTreeInBuffer(String key) throws UnknownTreeException {
        if (!_treeBuffer.containsKey(key))
            throw new UnknownTreeException(key);
        return _treeBuffer.get(key);
    }

    /**
     * Finds an employee in the hotel
     * @param key employee key
     * @return the employee
     * @throws UnknownEmployeeException if employee does not exist
     */
    public Employee findEmployee(String key)
            throws UnknownEmployeeException {
        if (!_employees.containsKey(key)) {
            throw new UnknownEmployeeException(key);
        }
        return _employees.get(key);
    }

    /**
     * Finds a veterinarian in the hotel
     * @param key veterinarian key
     * @return the veterinarian
     * @throws UnknownVeterinarianException if veterinarian does not exist
     */
    public Veterinarian findVeterinarian(String key)
        throws UnknownVeterinarianException {
        try {
            Employee vet = findEmployee(key);
            ((Veterinarian) vet).getMedicalActs(); // Try casting
            return (Veterinarian) vet;
        } catch (UnknownEmployeeException | ClassCastException e) {
            throw new UnknownVeterinarianException(key);
        }
    }

    /**
     * Finds a habitat in the hotel
     * @param key habitat key
     * @return the habitat
     * @throws UnknownHabitatException if habitat does not exist
     */
    public Habitat findHabitat(String key) throws UnknownHabitatException {
        if (!_habitats.containsKey(key))
            throw new UnknownHabitatException(key);
        return _habitats.get(key);
    }

    /**
     * Finds a vaccine in the hotel
     * @param key vaccine key
     * @return the vaccine
     * @throws UnknownVaccineException if vaccine does not exist
     */
    private Vaccine findVaccine(String key) throws UnknownVaccineException {
        if (!_vaccines.containsKey(key))
            throw new UnknownVaccineException(key);
        return _vaccines.get(key);
    }

    /**
     * Finds a responsibility (species or habitat) in the hotel
     * @param key responsibility key
     * @return the responsibility (species or habitat)
     * @throws ResponsibilityException if responsibility does not exist
     */
    public Responsibility findResponsibility(String key)
            throws ResponsibilityException {
        try {
            return findSpecies(key);
        } catch (UnknownSpeciesException e) {
            try {
                return findHabitat(key);
            } catch (UnknownHabitatException e1) {
                throw new ResponsibilityException(key);
            }
        }
    }

    /**
     * Finds an animal in the hotel
     * @param key animal key
     * @return the animal
     * @throws UnknownAnimalException if animal does not exist
     */
    public Animal findAnimal(String key) throws UnknownAnimalException {
        for (Habitat habitat: _habitats.values()) {
            try{
                return habitat.findAnimal(key);
            } catch (UnknownAnimalException e) {
                // Continue on the loop
            }
        }
        throw new UnknownAnimalException(key);
    }

    /**
     * Finds a tree in the hotel
     *
     * @param key tree key
     * @throws UnknownTreeException if tree does not exist
     */
    private void findTree(String key) throws UnknownTreeException {
        for (Habitat habitat: _habitats.values()) {
            try{
                habitat.findTree(key);
                return;
            } catch (UnknownTreeException e) {
                // Continue on the loop
            }
        }
        throw new UnknownTreeException(key);
    }

    /**
     * Gets all habitats in the hotel, sorted by their key
     * in natural lexicographic order.
     * @return A sorted Collection of all habitats
     */
    public Collection<Habitat> getAllHabitats() {
        return Collections.unmodifiableCollection(_habitats.values());
    }

    /**
     * Gets all employees in the hotel, sorted by their key
     * in natural lexicographic order.
     * @return A sorted Collection of all employees
     */
    public Collection<Employee> getAllEmployees() {
        return Collections.unmodifiableCollection(_employees.values());
    }

    /**
     * Gets all vaccines in the hotel, sorted by their key
     * in natural lexicographic order.
     * @return A sorted Collection of all vaccines
     */
    public Collection<Vaccine> getAllVaccines() {
        return Collections.unmodifiableCollection(_vaccines.values());
    }

    /**
     * Gets all habitats in the hotel, sorted by their key
     * in natural lexicographic order.
     * @return A sorted Collection of all habitats
     */
    public Collection<Animal> getAllAnimals() {
        TreeMap<String, Animal> allAnimals =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Species species : _species.values()) {
            allAnimals.putAll(species.getAnimals());
        }

        return Collections.unmodifiableCollection(allAnimals.values());
    }

    /**
     * Gets all trees in the hotel,sorted by their key
     * in natural lexicographic order
     * @return A sorted Collection of all Trees
     */
    public Collection<Tree> getAllTrees() {
        List<Tree> allTrees = new ArrayList<>();

        for (Habitat habitat : _habitats.values()) {
            allTrees.addAll(habitat.getAllTrees());
        }

        return Collections.unmodifiableCollection(allTrees);

    }

    /**
     * Gets all vaccination events in the hotel, sorted by application
     * order
     * @return A List of all vaccinations
     */
    public List<MedicalAct> getAllVaccinations() {
        return Collections.unmodifiableList(_medicalActs);
    }

    /**
     * Gets the wrong vaccinations in the hotel, sorted by application
     * order
     * @return A list of the wrong vaccinations
     */
    public List<MedicalAct> getWrongVaccinations() {
        return getAllVaccinations().stream()
                .filter(MedicalAct::isWrongVaccination)
                .toList();
    }

    /**
     * Gets the sum of the satisfaction values of all entities
     * (animals and employees) in the hotel,
     * rounded to the nearest integer
     * @return global satisfaction
     */
    public int getTotalSatisfaction() {

        return (int)  (Math.round(getAllEmployees().stream()
                        .mapToDouble(Employee::calculateSatisfaction)
                        .sum())
                        + Math.round(getAllAnimals().stream()
                        .mapToDouble(Animal::calculateSatisfaction)
                        .sum()));
    }

    /**
     * Reads text input file and create domain entities.
     * @param filename name of the text input file
     * @throws ImportFileException when there are problems importing the file
     */
    void importFile(String filename) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                try {
                    registerEntry(fields);
                } catch (UnrecognizedEntryException | InvalidEntryException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new ImportFileException(filename, e);
        }
    }

    /**
     * Handles a line from the import file and creates a class
     * @param fields attributes of the new class to create
     * @throws UnrecognizedEntryException when an entry is badly formatted
     * @throws InvalidEntryException when an illegal entry is found
     */
    private void registerEntry(String... fields)
            throws UnrecognizedEntryException, InvalidEntryException {
        switch (fields[0]) {
            case "ESPÉCIE" -> importSpecies(fields);
            case "ÁRVORE" -> importTree(fields);
            case "HABITAT" -> importHabitat(fields);
            case "ANIMAL" -> importAnimal(fields);
            case "TRATADOR", "VETERINÁRIO" -> importEmployee(fields);
            case "VACINA" -> importVaccine(fields);
            default -> {
                    System.out.println(fields[0]); throw new UnrecognizedEntryException(fields[0]); }
        }
    }

    /**
     * Imports a new species from a file
     * @param fields attributes
     * @throws UnrecognizedEntryException when an entry is badly formatted
     */
    private void importSpecies(String... fields)
            throws UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];

        try {
            registerSpecies(key, name);
        } catch (DuplicateSpeciesException e) {
            throw new UnrecognizedEntryException(e.getKey());
        }
        changed();
    }

    /**
     * Imports a new tree from a file
     * @param fields attributes
     * @throws UnrecognizedEntryException when an entry is badly formatted
     */
    private void importTree(String... fields)
            throws UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];
        int age = Integer.parseInt(fields[3]);
        int difficulty = Integer.parseInt(fields[4]);
        Tree tree = switch (fields[5]) {
            case "PERENE" -> new EvergreenTree(key, name, age, difficulty, getSeason());
            case "CADUCA" -> new DeciduousTree(key, name, age, difficulty, getSeason());
            default -> throw new UnrecognizedEntryException(fields[5]);
        };
        try {
            addTree(tree);
        } catch (DuplicateTreeException e) {
            throw new UnrecognizedEntryException(tree.getKey());
        }

        changed();
    }

    /**
     * Imports a new habitat from a file
     * @param fields attributes
     * @throws InvalidEntryException when an entry is invalid
     * @throws UnrecognizedEntryException when an entry is unknown
     */
    private void importHabitat(String... fields)
            throws InvalidEntryException,
            UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];
        int area = Integer.parseInt(fields[3]);

        try {
            registerHabitat(key, name, area);
        } catch (DuplicateHabitatException e) {
            throw new UnrecognizedEntryException(e.getKey());
        } catch (InvalidAreaException e) {
            throw new InvalidEntryException(Integer.toString(e.getKey()));
        }
        Habitat habitat = _habitats.get(key);

        if (fields.length > 4) { // has trees
            String[] treeKeys = (fields[4]).split(",");
            for (String treeKey : treeKeys) {
                try{
                    Tree tree = findTreeInBuffer(treeKey);
                    habitat.plantTree(tree);
                    removeTree(tree);
                } catch (UnknownTreeException e) {
                    throw new UnrecognizedEntryException(treeKey);
                }
            }
        }
        changed();
    }

    /**
     * Imports a new animal from a file
     * @param fields attributes
     * @throws UnrecognizedEntryException when an entry is unknown
     */
    private void importAnimal(String... fields)
            throws UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];
        String speciesKey = fields[3];
        String habitatKey = fields[4];

        try {
            registerAnimal(key, name, speciesKey, habitatKey);
        } catch (DuplicateAnimalException e) {
            throw new UnrecognizedEntryException(e.getKey());
        } catch (UnknownHabitatException e) {
            throw new UnrecognizedEntryException(e.getKey());
        }
        changed();
    }

    /**
     * Imports a new employee from a file
     * @param fields attributes
     * @throws UnrecognizedEntryException when an entry is invalid
     */
    private void importEmployee(String... fields)
            throws UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];

        switch (fields[0]) {
            case "TRATADOR" -> {
                try {
                    registerKeeper(key, name);
                } catch (DuplicateEmployeeException e) {
                    throw new UnrecognizedEntryException(key);
                }
                Keeper keeper = (Keeper) _employees.get(key);
                if (fields.length > 3) { // has responsibilities (habitats)
                    try {
                        handleKeeperResponsibilities(keeper, fields[3]);
                    } catch (UnknownHabitatException | ResponsibilityException e) {
                        throw new UnrecognizedEntryException(key);
                    }
                }
            }
            case "VETERINÁRIO" -> {
                try {
                    registerVeterinarian(key, name);
                } catch (DuplicateEmployeeException e) {
                    throw new UnrecognizedEntryException(key);
                }
                Veterinarian veterinarian = (Veterinarian) _employees.get(key);
                if (fields.length > 3) { // has responsibilities (species)
                    try {
                        handleVeterinarianResponsibilities(veterinarian, fields[3]);
                    } catch (UnknownSpeciesException | ResponsibilityException e) {
                        throw new UnrecognizedEntryException(key);
                    }
                }
            }
            default -> throw new UnrecognizedEntryException(fields[0]);
        }

        changed();
    }

    /**
     * Handles keeper responsibilities when importing a new one
     * @param keeper keeper to add responsibilities
     * @param keys responsibility keys (separated by ',')
     * @throws UnknownHabitatException when a habitat does not exist
     */
    private void handleKeeperResponsibilities(Keeper keeper, String keys)
            throws UnknownHabitatException, ResponsibilityException {
        String[] habitatKeys = (keys).split(",");
        for (String habitatKey : habitatKeys) {
            Habitat habitat = findHabitat(habitatKey);
            keeper.addResponsibility(habitat);
        }
    }

    /**
     * Handles veterinarian responsibilities when importing a new one
     * @param veterinarian veterinarian to add responsibilities
     * @param keys responsibility keys (separated by ',')
     * @throws UnknownSpeciesException when a species does not exist
     */
    private void handleVeterinarianResponsibilities(Veterinarian veterinarian, String keys)
            throws UnknownSpeciesException, ResponsibilityException {
        String[] speciesKeys = (keys).split(",");
        for (String speciesKey : speciesKeys) {
            Species species = findSpecies(speciesKey);
            veterinarian.addResponsibility(species);
        }
    }

    /**
     * Imports a new vaccine from a file
     * @param fields attributes
     * @throws UnrecognizedEntryException when an entry is badly formatted
     */
    private void importVaccine(String... fields)
            throws UnrecognizedEntryException {
        String key = fields[1];
        String name = fields[2];
        String[] speciesKeys = null;

        if (fields.length > 3) { // can be applied to certain species
            speciesKeys = fields[3].split(",");
        }

        try {
            registerVaccine(key, name, speciesKeys);
        } catch (DuplicateVaccineException e) {
            throw new UnrecognizedEntryException(e.getKey());
        } catch (UnknownSpeciesException e) {
            throw new UnrecognizedEntryException(e.getKey());
        }
        changed();
    }

}