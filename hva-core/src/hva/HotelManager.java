package hva;

import hva.exceptions.ImportFileException;
import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.UnavailableFileException;

import java.io.*;


/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** This is the hotel manager. */
    private String _filename = "";

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();


    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (_filename == null || _filename.isEmpty())
            throw new MissingFileAssociationException();
        _hotel.setChanged(false);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(_filename))) {
            oos.writeObject(getHotel());
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _filename = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        if (filename == null || filename.isEmpty())
            throw new UnavailableFileException(filename);

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            _hotel = (Hotel) ois.readObject();
            _filename = filename;
            _hotel.setChanged(false);
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException when there are problems importing the file
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    /**
     * @return changed?
     */
    public boolean changed() {
        return _hotel.hasChanged();
    }

    /**
     * @return hotel
     */
    public Hotel getHotel() {
        return _hotel;
    }

    /**
     * Resets the hotel.
     */
    public void reset() {
        _filename = null;
        _hotel = new Hotel();
    }
}
