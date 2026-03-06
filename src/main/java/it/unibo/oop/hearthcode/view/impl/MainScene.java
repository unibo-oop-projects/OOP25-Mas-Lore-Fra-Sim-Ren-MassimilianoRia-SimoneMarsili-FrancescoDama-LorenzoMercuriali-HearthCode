package it.unibo.oop.hearthcode.view.impl;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

/**
 * It represents the menu scene of the application.
 */
public final class MainScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;

    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton quitButton;

    private final BufferedImage background;

    /**
     * Builds the panel.
     */
    public MainScene() {
        super(new GridBagLayout());

        this.background = loadBufferedImage(BACKGROUND_PATH);

        this.playButton = this.createImageButton(
            "/play-normal.png",
            "/play-hover.png",
            "/play-pressed.png"
        );

        this.settingsButton = this.createImageButton(
            "/settings-normal.png",
            "/settings-hover.png",
            "/settings-pressed.png"
        );

        this.quitButton = this.createImageButton(
            "/quit-normal.png",
            "/quit-hover.png",
            "/quit-pressed.png"
        );

        this.initializeLayout();
    }

    private void initializeLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(12, 0, 12, 0);

        gbc.gridy = 0;
        this.add(this.playButton, gbc);

        gbc.gridy = 1;
        this.add(this.settingsButton, gbc);

        gbc.gridy = 2;
        this.add(this.quitButton, gbc);
    }

    /**
     * Adds a listener to the play button.
     *
     * @param listener the listener to add
     */
    public void addPlayListener(final ActionListener listener) {
        this.playButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the settings button.
     *
     * @param listener the listener to add
     */
    public void addSettingsListener(final ActionListener listener) {
        this.settingsButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the quit button.
     *
     * @param listener the listener to add
     */
    public void addQuitListener(final ActionListener listener) {
        this.quitButton.addActionListener(listener);
    }

    private JButton createImageButton(
        final String normalPath,
        final String hoverPath,
        final String pressedPath
    ) {
        final ImageIcon normalIcon = loadScaledIcon(normalPath, BUTTON_WIDTH, BUTTON_HEIGHT);
        final ImageIcon hoverIcon = loadScaledIcon(hoverPath, BUTTON_WIDTH, BUTTON_HEIGHT);
        final ImageIcon pressedIcon = loadScaledIcon(pressedPath, BUTTON_WIDTH, BUTTON_HEIGHT);

        final JButton button = new JButton(normalIcon);
        button.setRolloverIcon(hoverIcon);
        button.setPressedIcon(pressedIcon);

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setText(null);

        final Dimension size = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        return button;
    }

    private static ImageIcon loadScaledIcon(
        final String path,
        final int width,
        final int height
    ) {
        final BufferedImage image = loadBufferedImage(path);
        return new ImageIcon(scaleButtonImageHighQuality(image, width, height));
    }

    private static BufferedImage loadBufferedImage(final String path) {
        try {
            return ImageIO.read(
                Objects.requireNonNull(
                    MainScene.class.getResource(path),
                    "Resource not found: " + path
                )
            );
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load resource: " + path, e);
        }
    }

    private static BufferedImage scaleButtonImageHighQuality(
        final BufferedImage source,
        final int targetWidth,
        final int targetHeight
    ) {
        int currentWidth = source.getWidth();
        int currentHeight = source.getHeight();
        BufferedImage currentImage = source;

        while (currentWidth / 2 >= targetWidth && currentHeight / 2 >= targetHeight) {
            currentWidth /= 2;
            currentHeight /= 2;

            final BufferedImage tmp = new BufferedImage(
                currentWidth,
                currentHeight,
                BufferedImage.TYPE_INT_ARGB
            );

            final Graphics2D g2d = tmp.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2d.drawImage(currentImage, 0, 0, currentWidth, currentHeight, null);
            g2d.dispose();

            currentImage = tmp;
        }

        if (currentWidth != targetWidth || currentHeight != targetHeight) {
            final BufferedImage finalImage = new BufferedImage(
                targetWidth,
                targetHeight,
                BufferedImage.TYPE_INT_ARGB
            );

            final Graphics2D g2d = finalImage.createGraphics();
            g2d.setComposite(AlphaComposite.Src);
            g2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2d.drawImage(currentImage, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();

            currentImage = finalImage;
        }

        return currentImage;
    }

    @Override
    protected void paintComponent(final Graphics g) {
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

        final int drawWidth = (int) Math.ceil(this.background.getWidth() * scale);
        final int drawHeight = (int) Math.ceil(this.background.getHeight() * scale);

        final int x = (panelWidth - drawWidth) / 2;
        final int y = (panelHeight - drawHeight) / 2;

        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BICUBIC
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2d.drawImage(this.background, x, y, drawWidth, drawHeight, null);
        g2d.dispose();
    }
}