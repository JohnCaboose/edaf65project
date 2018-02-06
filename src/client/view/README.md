# How to use View

The class `View` is a monitor which consists of a grid of `Tile`s representing the play field. Every `Tile` on the play field can be colored by calling `View.colorTileAt(int, int, String)`. At the time of writing, five different colors are supported:

* Red
* Green
* Blue
* Orange
* Black

Black is currently used to represent an empty tile. It is subject to change.

# Example usage

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