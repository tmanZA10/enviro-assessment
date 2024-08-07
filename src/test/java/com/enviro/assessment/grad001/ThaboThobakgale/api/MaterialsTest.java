package com.enviro.assessment.grad001.ThaboThobakgale.api;

import com.enviro.assessment.grad001.ThaboThobakgale.EnviroAssessmentApplication;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MaterialsTest {

    private ConfigurableApplicationContext context;
    @Autowired
    private JdbcTemplate template;
    private final String URL = "http://localhost:8080";
    private final RowMapper<Material> mapper = (rs, rowNum) ->
            new Material(
                    rs.getInt(1),
                    rs.getString(3),
                    rs.getString(2)
            );
    private final WasteCategory category = new WasteCategory(1,"toxic waste");

    @BeforeEach
    public void setUp(){
        context = SpringApplication.run(EnviroAssessmentApplication.class);
    }

    @AfterEach
    public void close(){
        context.close();
        clearTable();
        Unirest.shutDown();
    }

    @Test
    public void testGetAllMaterials(){
        addWasteCategoryToDb(category);
        HttpResponse<JsonNode> response = Unirest.get(URL+"/waste/materials").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(0, response.getBody().getArray().length());

        Material material = new Material(1,"something",category.getName());
        addMaterialToDb(material,category.getId());
        response = Unirest.get(URL+"/waste/materials").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getBody().getArray().length());
        assertEquals(1, response.getBody().getArray().getJSONObject(0).getInt("id"));
        assertEquals(material.getName(), response.getBody().getArray().getJSONObject(0).getString("name"));
        assertEquals(material.getWaste_category(), response.getBody().getArray().getJSONObject(0).getString("waste_category"));
    }

    @Test
    public void testLookUpMaterial(){
        HttpResponse<JsonNode> response = Unirest.get(URL+"/waste/materials/lookup/something").asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        addWasteCategoryToDb(category);
        Material material = new Material(1,"something",category.getName());
        addMaterialToDb(material,1);
        response = Unirest.get(URL+"/waste/materials/lookup/something").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        JSONObject body = response.getBody().getObject();
        assertEquals(material.getId(), body.getInt("id"));
        assertEquals(material.getName(), body.getString("name"));
        assertEquals(material.getWaste_category(), body.getString("waste_category"));

    }

    @Test
    public void testAddMaterial(){
        Material material = new Material(1,"something", category.getName());

        HttpResponse<JsonNode> response = Unirest.post(URL+"/waste/new/material")
                .header("Content-Type", "application/json")
                .body(material)
                .asJson();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        response = Unirest.post(URL+"/waste/new/material")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatus());

        addWasteCategoryToDb(category);
        response = Unirest.post(URL+"/waste/new/material")
                .header("Content-Type", "application/json")
                .body(material)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        JSONObject body = response.getBody().getObject();
        assertEquals(1, body.getInt("id"));
        assertEquals(material.getName(), body.getString("name"));
        assertEquals(material.getWaste_category(), body.getString("waste_category"));
        assertTrue(isInTable(material));

        response = Unirest.post(URL+"/waste/new/material")
                .header("Content-Type", "application/json")
                .body(material)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }

    @Test
    public void testUpdateAMaterial(){
        Material material = new Material(1, "something",category.getName());
        Material updatedMaterial = new Material(1, "something new",category.getName());
        addWasteCategoryToDb(category);
        addMaterialToDb(material,1);
        HttpResponse<JsonNode> response = Unirest.put(URL+"/waste/update/material")
                .header("Content-Type", "application/json")
                .body(updatedMaterial)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1,response.getBody().getObject().getInt("id"));
        JSONObject body = response.getBody().getObject();
        assertEquals(updatedMaterial.getName(), body.getString("name"));
        assertEquals(updatedMaterial.getWaste_category(), body.getString("waste_category"));
        assertTrue(isInTable(updatedMaterial));

        response = Unirest.put(URL+"/waste/update/material")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        updatedMaterial.setId(2);
        response = Unirest.put(URL+"/waste/update/material")
                .header("Content-Type", "application/json")
                .body(updatedMaterial)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updatedMaterial));

        updatedMaterial.setId(-1);
        response = Unirest.put(URL+"/waste/update/material")
                .header("Content-Type", "application/json")
                .body(updatedMaterial)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updatedMaterial));

        updatedMaterial.setId(1);
        updatedMaterial.setWaste_category("toxc wae");
        response = Unirest.put(URL+"/waste/update/material")
                .header("Content-Type", "application/json")
                .body(updatedMaterial)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updatedMaterial));

    }

    @Test
    public void testDeleteMaterial(){
        addWasteCategoryToDb(category);
        Material material = new Material(1,"something", category.getName());
        addMaterialToDb(material,1);

        HttpResponse<JsonNode> response = Unirest.delete(URL+"/waste/material/delete/1").asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(isInTable(material));

        response = Unirest.delete(URL+"/waste/material/delete/1").asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        response = Unirest.delete(URL+"/waste/material/delete/-1").asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }


    private void clearTable(){
        disableConstraints();
        truncateTables();
        enableConstraints();
    }

    private void disableConstraints(){
        String sql = "ALTER TABLE MATERIALS DROP CONSTRAINT FK_WASTE_ID;" +
                "ALTER TABLE GUIDELINES DROP CONSTRAINT FK_MATERIAL_ID;";
        template.update(sql);
    }

    private void truncateTables(){
        String sql = "TRUNCATE TABLE WASTE_CATEGORIES RESTART IDENTITY;" +
                "TRUNCATE TABLE MATERIALS RESTART IDENTITY;";
        template.update(sql);
    }

    private void enableConstraints(){
        String sql = "ALTER TABLE MATERIALS ADD CONSTRAINT FK_WASTE_ID " +
                "FOREIGN KEY (WASTE_ID) REFERENCES WASTE_CATEGORIES(ID) " +
                "ON DELETE CASCADE;" +
                "ALTER TABLE GUIDELINES ADD CONSTRAINT FK_MATERIAL_ID " +
                "FOREIGN KEY (MATERIAL_ID) REFERENCES MATERIALS(ID) " +
                "ON DELETE CASCADE";
        template.update(sql);
    }

    private void addWasteCategoryToDb(WasteCategory category){
        String sql = "INSERT INTO WASTE_CATEGORIES(NAME) VALUES (?)";
        template.update(sql, category.getName());
    }

    private void addMaterialToDb(Material material, int id){
        String sql = "INSERT INTO MATERIALS(WASTE_ID,NAME) VALUES (?, ?)";
        template.update(sql, id, material.getName());
    }

    private Material getMaterial(int id){
        String sql = "SELECT" +
                " MATERIALS.ID, WASTE_CATEGORIES.NAME AS CATEGORY, MATERIALS.NAME" +
                " FROM MATERIALS JOIN WASTE_CATEGORIES" +
                " ON MATERIALS.WASTE_ID = WASTE_CATEGORIES.ID " +
                "WHERE MATERIALS.ID = ?";
        return template.queryForObject(sql,mapper,id);
    }

    private boolean isInTable(Material material){
        try{
            return getMaterial(material.getId()).equals(material);
        } catch (EmptyResultDataAccessException x){
            return false;
        }
    }
}
