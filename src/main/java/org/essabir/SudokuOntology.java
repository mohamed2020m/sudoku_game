package org.essabir;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class SudokuOntology {
    private Model model;
    private Resource sudokuPuzzle;
    private String ns = "http://example.org/sudoku#";

    public SudokuOntology() {
        model = ModelFactory.createDefaultModel();
        model.setNsPrefix("sudoku", ns);

        Resource Cell = model.createResource(ns + "Cell");
        Resource SudokuPuzzle = model.createResource(ns + "SudokuPuzzle");

        Property hasValue = model.createProperty(ns + "hasValue");
        Property inRow = model.createProperty(ns + "inRow");
        Property inColumn = model.createProperty(ns + "inColumn");
        Property inSubgrid = model.createProperty(ns + "inSubgrid");

        sudokuPuzzle = model.createResource(SudokuPuzzle);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Resource cell = model.createResource(ns + "Cell_" + row + "_" + col, Cell);
                cell.addProperty(inRow, model.createLiteral(Integer.toString(row)));
                cell.addProperty(inColumn, model.createLiteral(Integer.toString(col)));
                cell.addProperty(inSubgrid, model.createLiteral(Integer.toString((row / 3) * 3 + (col / 3))));
                sudokuPuzzle.addProperty(RDF.li((row * 9) + col + 1), cell);
            }
        }
    }

    public Model getModel() {
        return model;
    }

    public Resource getSudokuPuzzle() {
        return sudokuPuzzle;
    }
}
