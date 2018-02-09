# View

The class `View` is a monitor which holds a `JFrame` object which contains the GUI itself. The play field is represented by a grid of `Tile` objects. Every `Tile` on the play field can be colored by calling the `View.colorTileAt(int, int, String)` method. The play field is used to display the location and form of all snakes.

At the time of writing, six different colors are supported:

* Red
* Green
* Blue
* Orange
* (Red)
* (Black)

Black is currently used to represent an empty tile. Red is currently used in the top panel to signal to the user that their snake is dead. These are subject to change.

## Top Panel

The panel at the top of the view window consists of two fields. The left side field shows which color the snake the user controls has and the right side field tells the user if their snake is alive or not. The right side field can be updated by calling the `View.displayDeadSnakeStatus()` method.

## Example

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