package game.TileMap;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileMap {

    private double x, y;
    private int xmin, xmax;
    private int ymin, ymax;

    private double drift;

    private int[][] map;
    private int tileSize;
    private int numRows, numCols;
    private int width, height;

    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles; // << this is the cause for the left-edge-collision bug.
    // The collision checking method accesses this which identifies the tile by an integer index, so tiles to
    // the left are rounded down and result in false collision return value. I will have to redesign this section to
    // fix the design flaw but don't have enough time before the due date.

    private int rowOffset, colOffset;
    private int numRowsToDraw, numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.FRAME_WIDTH / tileSize + 2;
        numColsToDraw = GamePanel.WORLD_WIDTH / tileSize + 2;

        drift = 0.7;
    }

    public void loadTiles(String s) {

        try {
            tileset = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[4][numTilesAcross];

            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++) {
                // FIRST ROW
                subimage = tileset.getSubimage(
                        col * tileSize,
                        0,
                        tileSize,
                        tileSize
                );
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                // SECOND ROW
                subimage = tileset.getSubimage(
                        col * tileSize,
                        tileSize,
                        tileSize,
                        tileSize
                );
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
                // THIRD ROW
                subimage = tileset.getSubimage(
                        col * tileSize,
                        tileSize*2,
                        tileSize,
                        tileSize
                );
                tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
                subimage = tileset.getSubimage(
                        col * tileSize,
                        tileSize*3,
                        tileSize,
                        tileSize
                );
                tiles[3][col] = new Tile(subimage, Tile.NORMAL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String s) {
        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in)
            );

            numCols = Integer.parseInt(reader.readLine());
            numRows = Integer.parseInt(reader.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GamePanel.FRAME_WIDTH - width;
            xmax = 0;
            ymin = GamePanel.FRAME_HEIGHT - height;
            ymax = 0;

            String delimeter = "\\s+";
            for(int row = 0; row < numRows; row++) {
                String line = reader.readLine();
                String[] tokens = line.split(delimeter);
                for(int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getTileSize() {
        return tileSize;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDrift(double d) { drift = d; }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getType(int row, int col) {
        int pos = map[row][col];

        int r = pos / numTilesAcross;
        int c = pos % numTilesAcross;

        return tiles[r][c].getType();
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * drift;
        this.y += (y - this.y) * drift;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;

    }

    private void fixBounds() {
        if(x < xmin) x = xmin;
        if(x > xmax) x = xmax;
        if(y < ymin) y = ymin;
        if(y > ymax) y = ymax;
    }

    public void draw(Graphics2D g2d) {
        for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

            if(row >= numRows) break;

            for(int col = colOffset; col < colOffset + numColsToDraw; col++) {

                if(col >= numCols) break;

                if(map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g2d.drawImage( tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);

            }
        }
    }

}
