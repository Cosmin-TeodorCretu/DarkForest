package utilz;
import main.Game;

import java.sql.*;

public class Save {

    Game gp;

    public Save(Game gp) {
        this.gp = gp;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GameData.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS GAME " + "(Level INT ," +
                    "Health INT)";
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public void saveAll() {
        Connection c = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GameData.db");
            c.setAutoCommit(false);

            // Clear previous entries (optional)
            String clearSql = "DELETE FROM GAME";
            pstmt = c.prepareStatement(clearSql);
            pstmt.executeUpdate();
            pstmt.close();

            // Insert new values
            String insertSql = "INSERT INTO GAME (Level,Health) VALUES (?,?)";
            pstmt = c.prepareStatement(insertSql);
            pstmt.setInt(1, gp.getPlaying().getLevelManager().getLvlIndex());
            pstmt.setInt(2, gp.getPlaying().getPlayer().getPlayerHealth());
            pstmt.executeUpdate();

            pstmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record inserted successfully");
    }

    public void loadAll() {
        Connection c = null;
        Statement stmt = null;
        int level = 0;
        int health=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:GameData.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAME;");
            while (rs.next()) {
                level = rs.getInt("Level");
                health = rs.getInt("Health");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        gp.getPlaying().getPlayer().changeHealth(health);
        gp.getPlaying().getLevelManager().setLevelIndex(level);
    }
}