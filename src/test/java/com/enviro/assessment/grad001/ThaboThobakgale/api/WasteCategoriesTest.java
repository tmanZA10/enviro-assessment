package com.enviro.assessment.grad001.ThaboThobakgale.api;

import com.enviro.assessment.grad001.ThaboThobakgale.EnviroAssessmentApplication;
import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
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
public class WasteCategoriesTest {
    private ConfigurableApplicationContext context;
    @Autowired
    private JdbcTemplate template;
    private final String URL = "http://localhost:8080";
    private final RowMapper<WasteCategory> mapper =
            (rs, rowNum) -> new WasteCategory(
                    rs.getInt(1),
                    rs.getString(2)
            );
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
    public void testGetAllCategories(){
        HttpResponse<JsonNode> response = Unirest.get(URL+"/waste/categories").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(0, response.getBody().getArray().length());

        WasteCategory category = new WasteCategory(1, "toxic waste");
        addToDb(category);
        response = Unirest.get(URL+"/waste/categories").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getBody().getArray().length());
        assertEquals(1, response.getBody().getArray().getJSONObject(0).getInt("id"));
        assertEquals(category.getName(), response.getBody().getArray().getJSONObject(0).getString("name"));
    }

    @Test
    public void testAddCategory(){
        WasteCategory category = new WasteCategory(1, "toxic waste");
        HttpResponse<JsonNode> response = Unirest.post(URL+"/waste/new/category")
                .header("Content-Type", "application/json")
                .body(category)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1,response.getBody().getObject().getInt("id"));
        assertEquals(category.getName(), response.getBody().getObject().getString("name"));
        assertTrue(isInTable(category));

        response = Unirest.post(URL+"/waste/new/category")
                .header("Content-Type", "application/json")
                .body(category)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        response = Unirest.post(URL+"/waste/new/category")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }

    @Test
    public void testUpdateACategory(){
        WasteCategory category = new WasteCategory(1, "txic waste");
        WasteCategory updatedCategory = new WasteCategory(1, "toxic waste");
        addToDb(category);
        HttpResponse<JsonNode> response = Unirest.put(URL+"/waste/update/category")
                .header("Content-Type", "application/json")
                .body(updatedCategory)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1,response.getBody().getObject().getInt("id"));
        assertEquals(updatedCategory.getName(), response.getBody().getObject().getString("name"));
        assertTrue(isInTable(updatedCategory));

        response = Unirest.put(URL+"/waste/update/category")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        updatedCategory.setId(2);
        response = Unirest.put(URL+"/waste/update/category")
                .header("Content-Type", "application/json")
                .body(updatedCategory)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        updatedCategory.setId(-1);
        response = Unirest.put(URL+"/waste/update/category")
                .header("Content-Type", "application/json")
                .body(updatedCategory)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }

    @Test
    public void testDeleteTip(){
        WasteCategory category = new WasteCategory(1, "toxic waste");
        HttpResponse<JsonNode> response = Unirest.delete(URL+"/waste/category/delete/1")
                .asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        response = Unirest.delete(URL+"/waste/category/delete/-1")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        addToDb(category);

        response = Unirest.delete(URL+"/waste/category/delete/1")
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(isInTable(category));

    }


    private void clearTable(){
        String sql = "ALTER TABLE MATERIALS DROP CONSTRAINT FK_WASTE_ID;" +
                "TRUNCATE TABLE WASTE_CATEGORIES RESTART IDENTITY;" +
                "ALTER TABLE MATERIALS ADD CONSTRAINT FK_WASTE_ID " +
                "FOREIGN KEY (WASTE_ID) REFERENCES WASTE_CATEGORIES(ID) " +
                "ON DELETE CASCADE";
        template.update(sql);
    }

    private void addToDb(WasteCategory category){
        String sql = "INSERT INTO WASTE_CATEGORIES(NAME) VALUES (?)";
        template.update(sql, category.getName());
    }

    private WasteCategory getCategory(int id){
        String sql = "SELECT * FROM WASTE_CATEGORIES WHERE ID = ?";
        return template.queryForObject(sql,mapper,id);
    }

    private boolean isInTable(WasteCategory wasteCategory){
        try{
            return getCategory(wasteCategory.getId()).equals(wasteCategory);
        } catch (EmptyResultDataAccessException x){
            return false;
        }
    }
}
