package com.ecampix.services;

import com.ecampix.entities.Livraison;
import com.ecampix.utils.DatabaseConnection;
import com.ecampix.utils.RelationObject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService {

    private static LivraisonService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public LivraisonService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static LivraisonService getInstance() {
        if (instance == null) {
            instance = new LivraisonService();
        }
        return instance;
    }

    public List<Livraison> getAll() {
        List<Livraison> listLivraison = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `livraison` AS x RIGHT JOIN `livreur` AS y ON x.livreur_id = y.id WHERE x.livreur_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listLivraison.add(new Livraison(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("livreur_id"), resultSet.getString("y.nom")),
                        resultSet.getString("num_serie"),
                        resultSet.getString("adresse"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) livraison : " + exception.getMessage());
        }
        return listLivraison;
    }

    public List<RelationObject> getAllLivreurs() {
        List<RelationObject> listLivreurs = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `livreur`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listLivreurs.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("nom")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) livreurs : " + exception.getMessage());
        }
        return listLivreurs;
    }

    public boolean checkExist(Livraison livraison) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `livraison` WHERE `livreur_id` = ? AND `num_serie` = ? AND `adresse` = ? AND `date` = ?");

            preparedStatement.setInt(1, livraison.getLivreurId().getId());
            preparedStatement.setString(2, livraison.getNumSerie());
            preparedStatement.setString(3, livraison.getAdresse());
            preparedStatement.setDate(4, Date.valueOf(livraison.getDate()));

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (getAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(Livraison livraison) {

        if (checkExist(livraison)) {
            return false;
        }

        String request = "INSERT INTO `livraison`(`livreur_id`, `num_serie`, `adresse`, `date`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, livraison.getLivreurId().getId());
            preparedStatement.setString(2, livraison.getNumSerie());
            preparedStatement.setString(3, livraison.getAdresse());
            preparedStatement.setDate(4, Date.valueOf(livraison.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Livraison added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) livraison : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Livraison livraison) {

        if (checkExist(livraison)) {
            return false;
        }

        String request = "UPDATE `livraison` SET `livreur_id` = ?, `num_serie` = ?, `adresse` = ?, `date` = ? WHERE `id`=" + livraison.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, livraison.getLivreurId().getId());
            preparedStatement.setString(2, livraison.getNumSerie());
            preparedStatement.setString(3, livraison.getAdresse());
            preparedStatement.setDate(4, Date.valueOf(livraison.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Livraison edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) livraison : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `livraison` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Livraison deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) livraison : " + exception.getMessage());
        }
        return false;
    }
}
