package main;

import java.util.*;

public class MazeGenerator {
    private int width, height;
    private char[][] maze;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new char[height][width];
        generateMaze(0, 0);
        // Thêm đường vào và đường ra
        maze[0][1] = ' '; // Đường vào ở trên
        maze[height - 1][width - 2] = ' '; // Đường ra ở dưới
        markPath(); // Đánh dấu đường đi phù hợp
    }

    private void generateMaze(int x, int y) {
        maze[y][x] = ' ';
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            int newX = x + direction.dx;
            int newY = y + direction.dy;
            if (isValid(newX, newY) && maze[newY][newX] == '\0') {
                maze[(y + newY) / 2][(x + newX) / 2] = ' ';
                generateMaze(newX, newY);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private void markPath() {
        boolean[][] visited = new boolean[height][width];
        dfs(visited, 1, 0);
    }

    private boolean dfs(boolean[][] visited, int x, int y) {
        if (x == width - 2 && y == height - 1) {
            return true;
        }
        visited[y][x] = true;
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            int newX = x + direction.dx;
            int newY = y + direction.dy;
            if (isValid(newX, newY) && !visited[newY][newX] && maze[newY][newX] == ' ') {
                if (dfs(visited, newX, newY)) {
                    maze[newY][newX] = '+';
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0 || j == width - 1) {
                    sb.append('#'); // Viền trái và phải
                } else {
                    sb.append(maze[i][j] == '\0' ? '#' : maze[i][j]);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private enum Direction {
        NORTH(0, -2), EAST(2, 0), SOUTH(0, 2), WEST(-2, 0);

        private final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public static void main(String[] args) {
        int width = 21; // số hàng số cột lẻ để đảm bảo có đường đi
        int height = 21;
        MazeGenerator mazeGenerator = new MazeGenerator(width, height);
        System.out.println(mazeGenerator);
    }
}
