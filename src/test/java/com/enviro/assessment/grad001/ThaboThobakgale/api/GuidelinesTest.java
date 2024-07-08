package com.enviro.assessment.grad001.ThaboThobakgale.api;

import com.enviro.assessment.grad001.ThaboThobakgale.EnviroAssessmentApplication;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
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
public class GuidelinesTest {

    private ConfigurableApplicationContext context;
    @Autowired
    private JdbcTemplate template;
    private final String URL = "http://localhost:8080";
    private final RowMapper<Guideline> mapper = (rs, rowNum) ->
            new Guideline(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
    private final WasteCategory category = new WasteCategory(1,"toxic waste");
    private final Material material = new Material(1,"something", category.getName());

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
    public void testGetAllGuidelines(){
        addWasteCategoryToDb(category);
        addMaterialToDb(material,category.getId());

        HttpResponse<JsonNode> response = Unirest.get(URL+"/waste/guidelines").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(0, response.getBody().getArray().length());

        Guideline guideline = new Guideline(1, material.getName(), "blah blah");
        addGuidelineToDb(guideline, 1);

        response = Unirest.get(URL+"/waste/guidelines").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        JSONArray body = response.getBody().getArray();
        assertEquals(1, body.length());
        assertEquals(1, body.getJSONObject(0).getInt("id"));
        assertEquals(material.getName(), body.getJSONObject(0).getString("material"));
        assertEquals(guideline.getGuideline(), body.getJSONObject(0).getString("guideline"));
    }

    @Test
    public void testLookUpGuidelinesByMaterial(){

        HttpResponse<JsonNode> response = Unirest.get(URL+"/waste/guidelines/lookup/" + material.getName()).asJson();
        JSONArray body = response.getBody().getArray();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(0, body.length());

        addWasteCategoryToDb(category);
        addMaterialToDb(material,category.getId());
        Guideline guideline = new Guideline(1, material.getName(), "blah blah");
        Guideline anotherGuideline = new Guideline(1, material.getName(), "blah blah again");
        addGuidelineToDb(guideline, material.getId());
        addGuidelineToDb(anotherGuideline, material.getId());
        response = Unirest.get(URL+"/waste/guidelines/lookup/" + material.getName()).asJson();
//
        assertEquals(HttpStatus.OK, response.getStatus());
        body = response.getBody().getArray();
        assertEquals(2,body.length());

    }

    @Test
    public void testAddGuideline(){
        addWasteCategoryToDb(category);

        Guideline guideline = new Guideline(1, material.getName(), "blah blah");
        Guideline anotherGuideline = new Guideline(2, material.getName(), "blah blah again");

        HttpResponse<JsonNode> response = Unirest.post(URL+"/waste/new/guideline")
                .header("Content-Type", "application/json")
                .body(guideline)
                .asJson();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        response = Unirest.post(URL+"/waste/new/guideline")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatus());

        addMaterialToDb(material,1);
        response = Unirest.post(URL+"/waste/new/guideline")
                .header("Content-Type", "application/json")
                .body(guideline)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        JSONObject body = response.getBody().getObject();
        assertEquals(1, body.getInt("id"));
        assertEquals(guideline.getMaterial(), body.getString("material"));
        assertEquals(guideline.getGuideline(), body.getString("guideline"));
        assertTrue(isInTable(guideline));

        response = Unirest.post(URL+"/waste/new/guideline")
                .header("Content-Type", "application/json")
                .body(anotherGuideline)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        body = response.getBody().getObject();
        assertEquals(2, body.getInt("id"));
        assertEquals(anotherGuideline.getMaterial(), body.getString("material"));
        assertEquals(anotherGuideline.getGuideline(), body.getString("guideline"));
        assertTrue(isInTable(guideline));

    }

    @Test
    public void testUpdateGuideLine(){

        Guideline guideline = new Guideline(1,material.getName(),"blah blah");
        Guideline updateGuideline = new Guideline(1,material.getName(),"more blah blah");
        addWasteCategoryToDb(category);
        addMaterialToDb(material, category.getId());
        addGuidelineToDb(guideline,material.getId());

        HttpResponse<JsonNode> response = Unirest.put(URL+"/waste/update/guideline")
                .header("Content-Type", "application/json")
                .body(updateGuideline)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        JSONObject body = response.getBody().getObject();
        assertEquals(updateGuideline.getId(), body.getInt("id"));
        assertEquals(material.getName(), body.getString("material"));
        assertEquals(updateGuideline.getGuideline(), body.getString("guideline"));
        assertTrue(isInTable(updateGuideline));

        response = Unirest.put(URL+"/waste/update/guideline")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        updateGuideline.setId(2);
        response = Unirest.put(URL+"/waste/update/guideline")
                .header("Content-Type", "application/json")
                .body(updateGuideline)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updateGuideline));

        updateGuideline.setId(-1);
        response = Unirest.put(URL+"/waste/update/guideline")
                .header("Content-Type", "application/json")
                .body(updateGuideline)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updateGuideline));

        updateGuideline.setId(1);
        updateGuideline.setMaterial("nothing");
        response = Unirest.put(URL+"/waste/update/guideline")
                .header("Content-Type", "application/json")
                .body(updateGuideline)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertFalse(isInTable(updateGuideline));

    }

    @Test
    public void testDeleteGuideline(){
        addWasteCategoryToDb(category);
        addMaterialToDb(material,category.getId());

        Guideline guideline = new Guideline(1,material.getName(), "blah blah");
        addGuidelineToDb(guideline, material.getId());

        HttpResponse<JsonNode> response = Unirest.delete(URL+"/waste/guideline/delete/1").asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(isInTable(guideline));

        response = Unirest.delete(URL+"/waste/guideline/delete/1").asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        response = Unirest.delete(URL+"/waste/guideline/delete/-1").asJson();
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
                "TRUNCATE TABLE MATERIALS RESTART IDENTITY;"+
                "TRUNCATE TABLE GUIDELINES RESTART IDENTITY;";
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

    private void addGuidelineToDb(Guideline guideline, int id){
        String sql = "INSERT INTO GUIDELINES(MATERIAL_ID,GUIDELINE) VALUES (?, ?)";
        template.update(sql, id, guideline.getGuideline());
    }

    private Guideline getGuideline(int id){
        String sql = "SELECT" +
                " GUIDELINES.ID, MATERIALS.NAME AS MATERIAL_NAME, GUIDELINES.GUIDELINE" +
                " FROM GUIDELINES JOIN MATERIALS" +
                " ON GUIDELINES.MATERIAL_ID = MATERIALS.ID " +
                "WHERE GUIDELINES.ID = ?";
        return template.queryForObject(sql,mapper,id);
    }

    private boolean isInTable(Guideline guideline){
        try{
            return getGuideline(guideline.getId()).equals(guideline);
        } catch (EmptyResultDataAccessException x){
            return false;
        }
    }
}
