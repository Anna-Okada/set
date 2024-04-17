package org.example.model;

public class Card {

    // Attributes
    private int value;
    private int shape;
    private int color;
    private int shading;
    private int tablePosition;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static final String CLEAR_TRIANGLE = "\u25B3";
    public static final String SHADED_TRIANGLE = "\u27C1";
    public static final String SOLID_TRIANGLE = "\u25B2";
    public static final String CLEAR_CIRCLE = "\u25CB";
    public static final String SHADED_CIRCLE = "\u29BE";
    public static final String SOLID_CIRCLE = "\u25CF";
    public static final String CLEAR_SQUARE = "\u25A1";
    public static final String SHADED_SQUARE = "\u29C8";
    public static final String SOLID_SQUARE = "\u25A0";

    // Constructors
    public Card() {
        value = 1;
        shape = 1;
        color = 1;
        shading = 1;
        tablePosition = -1;
    }

    public Card(int value, int shape, int color, int shading, int tablePosition) {
        this.value = value;
        this.shape = shape;
        this.color = color;
        this.shading = shading;
        this.tablePosition = tablePosition;
    }

    // Methods
    public int getValue() {
        return this.value;
    }

    public int getShape() {
        return this.shape;
    }

    public int getColor() {
        return this.color;
    }

    public int getShading() {
        return this.shading;
    }

    public int getTablePosition() {
        return this.tablePosition;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setShading(int shading) {
        this.shading = shading;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getValueAsString() {
        String valueString = "";
        if (value == 1) {
            valueString = "One";
        } else if (value == 2) {
            valueString = "Two";
        } else if (value == 3) {
            valueString = "Three";
        }
        return valueString;
    }

    public String getShapeAsString() {
        String shapeString = "";
        if (shape == 1 && shading == 1) {
            shapeString = CLEAR_TRIANGLE;
        } else if (shape == 1 && shading == 2) {
            shapeString = SHADED_TRIANGLE;
        } else if (shape == 1 && shading == 3) {
            shapeString = SOLID_TRIANGLE;
        } else if(shape ==2 && shading ==1) {
            shapeString = CLEAR_CIRCLE;
        } else if(shape==2 && shading ==2) {
            shapeString = SHADED_CIRCLE;
        } else if (shape==2 && shading ==3) {
            shapeString = SOLID_CIRCLE;
        } else if(shape ==3 && shading ==1) {
            shapeString = CLEAR_SQUARE;
        } else if (shape ==3 && shading ==2) {
            shapeString = SHADED_SQUARE;
        } else if(shape ==3 && shading ==3) {
            shapeString = SOLID_SQUARE;
        }
        if (color == 1) {
            shapeString = ANSI_BLUE + shapeString + ANSI_RESET;
        } else if (color == 2) {
            shapeString = ANSI_GREEN + shapeString + ANSI_RESET;
        } else if (color == 3) {
            shapeString = ANSI_PURPLE + shapeString + ANSI_RESET;
        }
        if (value == 2) {
            shapeString = shapeString + " " + shapeString;
        } else if (value == 3) {
            shapeString = shapeString + " " + shapeString + " " + shapeString;
        }
        return shapeString;
    }

    public String getColorAsString() {
        String colorString = "";
        if (color == 1) {
            colorString = ANSI_BLUE + "Blue" + ANSI_RESET;
        } else if (color == 2) {
            colorString = ANSI_GREEN + "Green" + ANSI_RESET;
        } else if (color == 3) {
            colorString = ANSI_PURPLE + "Purple" + ANSI_RESET;
        }
        return colorString;
    }

    public String getShadingAsString() {
        String shadingString = "";
        if (shading == 1) {
            shadingString = "Clear";
        } else if (shading == 2) {
            shadingString = "Shaded";
        } else if (shading == 3) {
            shadingString = "Solid";
        }
        return shadingString;
    }

    public String getName() {
        String name = "";

        if (this.value == 1) {
            name = "One";
        } else if (value == 2) {
            name = "Two";
        } else if (value == 3) {
            name = "Three";
        }

        if (shape == 1)
            name += " Triangle";
        else if (shape == 2)
            name += " Circle";
        else if (shape == 3)
            name += " Square";

        if (color == 1)
            name += " Blue";
        else if (color == 2)
            name += " Green";
        else if (color == 3)
            name += " Purple";

        if (shading == 1) {
            name += " Clear";
        } else if (shading == 2) {
            name += " Shaded";
        } else if (shading == 3) {
            name += " Solid";
        }

        name = name + " " + tablePosition;

        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
