package com.ecampix.services;

import com.ecampix.entities.Livreur;
import com.ecampix.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreurService {

    private static LivreurService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public LivreurService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static LivreurService getInstance() {
        if (instance == null) {
            instance = new LivreurService();
        }
        return instance;
    }

    public List<Livreur> getAll() {
        List<Livreur> listLivreur = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `livreur`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listLivreur.add(new Livreur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getFloat("tarif"),
                        resultSet.getString("email")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) livreur : " + exception.getMessage());
        }
        return listLivreur;
    }

    public boolean checkExist(Livreur livreur) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `livreur` WHERE `nom` = ? AND `prenom` = ? AND `adresse` = ? AND `tarif` = ? AND `email` = ?");

            preparedStatement.setString(1, livreur.getNom());
            preparedStatement.setString(2, livreur.getPrenom());
            preparedStatement.setString(3, livreur.getAdresse());
            preparedStatement.setFloat(4, livreur.getTarif());
            preparedStatement.setString(5, livreur.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (getAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(Livreur livreur) {

        if (checkExist(livreur)) {
            return false;
        }

        String request = "INSERT INTO `livreur`(`nom`, `prenom`, `adresse`, `tarif`, `email`) VALUES(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, livreur.getNom());
            preparedStatement.setString(2, livreur.getPrenom());
            preparedStatement.setString(3, livreur.getAdresse());
            preparedStatement.setFloat(4, livreur.getTarif());
            preparedStatement.setString(5, livreur.getEmail());

            preparedStatement.executeUpdate();
            System.out.println("Livreur added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) livreur : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Livreur livreur) {

        if (checkExist(livreur)) {
            return false;
        }

        String request = "UPDATE `livreur` SET `nom` = ?, `prenom` = ?, `adresse` = ?, `tarif` = ?, `email` = ? WHERE `id`=" + livreur.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, livreur.getNom());
            preparedStatement.setString(2, livreur.getPrenom());
            preparedStatement.setString(3, livreur.getAdresse());
            preparedStatement.setFloat(4, livreur.getTarif());
            preparedStatement.setString(5, livreur.getEmail());

            preparedStatement.executeUpdate();
            System.out.println("Livreur edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) livreur : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `livreur` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Livreur deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) livreur : " + exception.getMessage());
        }
        return false;
    }
}
