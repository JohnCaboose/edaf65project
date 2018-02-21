# View

The class `View` is a monitor which holds a `JFrame` object which contains the GUI itself. The play field is represented by a grid of `Tile` objects. Every `Tile` on the play field can be colored by calling the `View.colorTileAt(int, int, String)` method. The play field is used to display the location and form of all snakes. All color constants available in `java.awt.Color` are supported.

It is recommended to refrain from using some colors to paint the play field, as a handful of them are used for some specific use cases:

* Black -- used to represent an empty tile on the play field

* Red -- signaling to the user that their snake is **dead**.

* Green -- signaling to the user that their snake is **alive**.

The red and green colors are meant to be used exclusively by the top panel.

## Top Panel

The panel at the top of the view window consists of two fields. The left side field shows which color the snake the user controls has and the right side field tells the user if their snake is alive or not. The right side field can be updated by calling the `View.displayDeadSnakeStatus()` method.

## Bottom Panel

Below the playing field is a text area which contains lines of text. It serves the purpose of a simple console log window, where messages about the state of the game can be conveyed to the user. You can send `String`s of text to this panel by calling the `View.println(String)` method.

## Examples

### Using the play field

Given an object `View v` and a play field size of (16, 16), the following thread colors the four corners of the play field with different colors:

```java
Thread t = new Thread(new Runnable() {
	public void run() {
		v.colorTileAt(0, 0, "red");
		v.colorTileAt(15, 0, "green");
		v.colorTileAt(15, 15, "blue");
		v.colorTileAt(0, 15, "orange");
	}
});
t.start();
```

Note that the third argument `String color` of `View.colorTileAt(int, int, String)` has no requirement for the `String` to be in lower case, i.e. `"red"`, `"RED"` and `"rEd"` are all valid parameters to the method.

### Printing to the console

The following method paints the head of a `Snake` object in the play field and prints its new position to the console in the bottom panel.

```java
private void paintHeadOfSnake() {
	LinkedList<Coordinate> occupiedSpaces = snake.getBody();
	Coordinate head = occupiedSpaces.getFirst();
	v.colorTileAt(head.x, head.y, color);
	
	v.println(String.format("Snake moved to (%d, %d).", head.x, head.y));
}
```