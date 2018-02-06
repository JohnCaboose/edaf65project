package client.view;

public class RunView {
	public static void main(String[] args) {
		View v = new View();
		
		// TODO: remove debugging
		Thread t = new Thread(new Runnable() {
			public void run() {
				v.colorTileAt(0, 0, "red");
				v.colorTileAt(15, 0, "green");
				v.colorTileAt(15, 15, "blue");
				v.colorTileAt(0, 15, "orange");
			}
		});
		t.start();
	}
}
