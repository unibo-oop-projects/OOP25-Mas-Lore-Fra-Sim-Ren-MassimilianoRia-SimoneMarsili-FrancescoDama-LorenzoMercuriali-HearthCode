package it.unibo.oop.hearthcode.view.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

/**
 * It represents the menu scene of the application.
 */
public final class MenuScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;

    private static final int BUTTON_PADDING_Y = 12;

    private final transient BufferedImage background;

    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton quitButton;

    /**
     * Builds the panel.
     */
    public MenuScene() {
        super(new GridBagLayout());

        this.background = loadImage(BACKGROUND_PATH);

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
        gbc.insets = new Insets(BUTTON_PADDING_Y, 0, BUTTON_PADDING_Y, 0);

        gbc.gridy = 0;
        this.add(this.playButton, gbc);

        gbc.gridy = 1;
        this.add(this.settingsButton, gbc);

        gbc.gridy = 2;
        this.add(this.quitButton, gbc);
    }

    /**
     * Binds the play action.
     *
     * @param action the action to execute
     */
    public void onPlay(final Runnable action) {
        this.playButton.addActionListener(event -> action.run());
    }

    /**
     * Binds the settings action.
     *
     * @param action the action to execute
     */
    public void onSettings(final Runnable action) {
        this.settingsButton.addActionListener(event -> action.run());
    }

    /**
     * Binds the quit action.
     *
     * @param action the action to execute
     */
    public void onQuit(final Runnable action) {
        this.quitButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private JButton createImageButton(
        final String normalPath,
        final String hoverPath,
        final String pressedPath
    ) {
        final JButton button = new JButton(loadIcon(normalPath));
        button.setRolloverIcon(loadIcon(hoverPath));
        button.setPressedIcon(loadIcon(pressedPath));

        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        final Dimension size = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        return button;
    }

    private static ImageIcon loadIcon(final String path) {
        final BufferedImage image = loadImage(path);
        return new ImageIcon(image.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH));
    }

    private static BufferedImage loadImage(final String path) {
        try {
            return ImageIO.read(
                Objects.requireNonNull(
                    MenuScene.class.getResource(path),
                    "Resource not found: " + path
                )
            );
        } catch (final IOException e) {
            throw new IllegalStateException("Cannot load resource: " + path, e);
        }
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
