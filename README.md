# HVA Application

## Overview

This project is an animal hotel management application that allows users to manage various aspects of a hotel, including employees, animals, habitats, and more.

## Dependencies

- Java Development Kit (JDK) 8 or higher
- [po-uilib](po-uilib-202408310000) library (for input and output)

## Project Structure

```
hva-app/
    Makefile
    src/
        hva/
            app/
                animal/
                `hva-app/src/hva/app/App.java`
                employee/
                exceptions/
                habitat/
                main/
                Renderer.java
                search/
                vaccine/
hva-core/
    Makefile
    src/
        hva/
            employee/
            exceptions/
            habitat/
            Hotel.java
            `hva-core/src/hva/HotelManager.java`
            tree/
            util/
            vaccine/
po-uilib-202408310000/
    LICENSE
    Makefile
    `README.md`
    src/
        pt/
            tecnico/
uml/
```

## Building the Project

To build the project, run the following command from the root directory:

```sh
make all
```

This will compile the source code and create the necessary JAR files.

# Setting the CLASSPATH
Before running the application, set the `CLASSPATH` environment variable:
```sh
export CLASSPATH=/usr/share/java/po-uilib.jar:$(pwd)/hva-core/hva-core.jar:$(pwd)/hva-app/hva-app.jar
```
You can also change the path to the po-uilib.jar

## Running the Application

To run the application, use the following command:

```sh
java -Dimport=test.import -Din=test.in -Dout=test.out hva.app.App
```

Alternatively, you can run the application with different input and output files:

```sh
java -Dimport=test.import hva.app.App
```

## Cleaning the Project

To clean the project, run the following command:

```sh
make clean
```

This will remove all compiled classes and JAR files.
