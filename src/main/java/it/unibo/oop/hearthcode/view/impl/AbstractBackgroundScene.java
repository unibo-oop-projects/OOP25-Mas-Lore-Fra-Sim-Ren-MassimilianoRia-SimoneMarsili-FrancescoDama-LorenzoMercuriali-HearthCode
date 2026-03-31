package it.unibo.oop.hearthcode.view.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;

/**
 * Base class for scenes with a scalable background image and texture-based buttons.
 */
public abstract class AbstractBackgroundScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    private final transient Image background;

    /**
     * Builds the scene with the given background resource.
     *
     * @param backgroundPath the background resource path
     */
    protected AbstractBackgroundScene(final String backgroundPath) {
        this.background = ImageLoader.load(backgroundPath).getImage();
    }

    /**
     * Creates a button with normal, hover and pressed textures.
     *
     * @param normalPath the normal icon path
     * @param hoverPath the hover icon path
     * @param pressedPath the pressed icon path
     * @param buttonWidth the target button width
     * @param buttonHeight the target button height
     * @return the configured button
     */
    protected final JButton createImageButton(
        final String normalPath,
        final String hoverPath,
        final String pressedPath,
        final int buttonWidth,
        final int buttonHeight
    ) {
        final JButton button = new JButton(loadIcon(normalPath, buttonWidth, buttonHeight));
        button.setRolloverIcon(loadIcon(hoverPath, buttonWidth, buttonHeight));
        button.setPressedIcon(loadIcon(pressedPath, buttonWidth, buttonHeight));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        final Dimension size = new Dimension(buttonWidth, buttonHeight);
        button.setPreferredSize(size);
        return button;
    }

    /**
     * Loads and scales an icon from resources.
     *
     * @param path the image path
     * @param width the target width
     * @param height the target height
     * @return the scaled icon
     */
    protected static ImageIcon loadIcon(final String path, final int width, final int height) {
        return ImageLoader.load(path, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final int panelWidth = this.getWidth();
        final int panelHeight = this.getHeight();
        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }
        final double scale = Math.max(
            (double) panelWidth / this.background.getWidth(null),
            (double) panelHeight / this.background.getHeight(null)
        );
        final int width = (int) Math.ceil(this.background.getWidth(null) * scale);
        final int height = (int) Math.ceil(this.background.getHeight(null) * scale);
        final int x = (panelWidth - width) / 2;
        final int y = (panelHeight - height) / 2;
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BICUBIC
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );
        g2d.drawImage(this.background, x, y, width, height, null);
        g2d.dispose();
    }

}
