package DAO;
import Database.Database;
import Models.Conseiller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConseillerDAO {

    public int create(Conseiller conseiller) {
        String sql = "INSERT INTO conseiller (nom, prenom, email) VALUES (?, ?, ?)";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, conseiller.getNom());
            stmt.setString(2, conseiller.getPrenom());
            stmt.setString(3, conseiller.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        conseiller.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du conseiller: " + e.getMessage());
        }
        return -1;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM conseiller WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du conseiller: " + e.getMessage());
        }
        return false;
    }

    public Optional<Conseiller> findById(int id) {
        String sql = "SELECT * FROM conseiller WHERE id = ?";
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Conseiller conseiller = new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                return Optional.of(conseiller);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du conseiller: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Conseiller> findAll() {
        List<Conseiller> conseillers = new ArrayList<>();
        String sql = "SELECT * FROM conseiller";
        try (Connection conn = Database.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conseiller conseiller = new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
                conseillers.add(conseiller);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des conseillers: " + e.getMessage());
        }
        return conseillers;
    }
}
