package it.unibo.oop.hearthcode.view.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

/**
 * Base class for scenes with a scalable background image
 * and texture-based buttons.
 */
public abstract class AbstractBackgroundScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    private final transient BufferedImage background;

    /**
     * Builds the scene with the given background resource.
     *
     * @param backgroundPath the background resource path
     */
    protected AbstractBackgroundScene(final String backgroundPath) {
        this.background = loadImage(backgroundPath);
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
        button.setMinimumSize(size);
        button.setMaximumSize(size);

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
        final BufferedImage image = loadImage(path);
        final Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Loads an image from resources.
     *
     * @param path the image path
     * @return the loaded image
     */
    protected static BufferedImage loadImage(final String path) {
        try {
            return ImageIO.read(
                Objects.requireNonNull(
                    AbstractBackgroundScene.class.getResource(path),
                    "Resource not found: " + path
                )
            );
        } catch (final IOException e) {
            throw new IllegalStateException("Cannot load resource: " + path, e);
        }
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final int panelWidth = this.getWidth();
        final int panelHeight = this.getHeight();

        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        final double scale = Math.max(
            (double) panelWidth / this.background.getWidth(),
            (double) panelHeight / this.background.getHeight()
        );

        final int width = (int) Math.ceil(this.background.getWidth() * scale);
        final int height = (int) Math.ceil(this.background.getHeight() * scale);

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
