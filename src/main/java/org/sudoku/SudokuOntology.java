package org.sudoku;

import org.apache.jena.util.FileManager;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SudokuOntology {
    private OWLOntology ontology;
    private OWLReasoner reasoner;

    public SudokuOntology() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            InputStream in = FileManager.get().open("ontology/suduko.owl");
            if (in == null) {
                throw new IllegalArgumentException("File: " + "sudoku.owl" + " not found");
            }
            ontology = manager.loadOntologyFromOntologyDocument(in);

            // Create a reasoner using HermiT
            reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

            System.out.println("Loaded ontology: " + ontology.getOntologyID().getOntologyIRI().toString());
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    public boolean validateBoard(int[][] board) {
        // Convert the Sudoku board to OWL individuals
        Set<OWLNamedIndividual> individuals = convertBoardToIndividuals(board);

        // Check consistency using HermiT reasoner
        return isConsistent(individuals);
    }

    public int[][] solveBoard(int[][] board) {
        // Convert the Sudoku board to OWL individuals
        Set<OWLNamedIndividual> individuals = convertBoardToIndividuals(board);

        // Solve the Sudoku using the ontology
        return solveSudoku(individuals);
    }

    private Set<OWLNamedIndividual> convertBoardToIndividuals(int[][] board) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        Set<OWLNamedIndividual> individuals = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    OWLNamedIndividual individual = factory.getOWLNamedIndividual(IRI.create("Cell" + i + j + "Value" + board[i][j]));
                    individuals.add(individual);
                }
            }
        }
        return individuals;
    }

    private boolean isConsistent(Set<OWLNamedIndividual> individuals) {
        // Check consistency of individuals using HermiT reasoner
        OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        for (OWLNamedIndividual individual : individuals) {
            OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(factory.getOWLThing(), individual);
            if (!reasoner.isEntailed(classAssertion)) {
                return false; // Inconsistent if any individual is not entailed
            }
        }
        return true;
    }

    private int[][] solveSudoku(Set<OWLNamedIndividual> individuals) {
        int[][] solvedBoard = new int[9][9];
        int i = 0;
        for (OWLNamedIndividual individual : individuals) {
            String individualName = individual.getIRI().getShortForm();
            int row = Character.getNumericValue(individualName.charAt(4));
            int col = Character.getNumericValue(individualName.charAt(5));
            int value = Character.getNumericValue(individualName.charAt(11));
            solvedBoard[row][col] = value;
        }
        return solvedBoard;
    }
}
