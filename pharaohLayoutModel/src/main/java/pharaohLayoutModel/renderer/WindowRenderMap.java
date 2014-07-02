package pharaohLayoutModel.renderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import pharaohLayoutModel.Map;

public class WindowRenderMap extends JFrame {

	private final Map map;

	final MapRenderer renderer;

	public WindowRenderMap(Map map) {
		this.map = map;

		this.setBounds(100, 100, 1000, 1000);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.renderer = new MapRenderer(this.map);
		this.add(this.renderer);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_F5:
					WindowRenderMap.this.repaint();
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					;
				}
			}
		});
	}

	public MapRenderer getRenderer() {
		return this.renderer;
	}

	public void join() throws InterruptedException {
		this.renderer.repaint();
		this.setVisible(true);
		Thread.currentThread().join();
	}
}
