package com.enviro.assessment.grad001.ThaboThobakgale.api.recyclingtips;

import com.enviro.assessment.grad001.ThaboThobakgale.EnviroAssessmentApplication;
import com.enviro.assessment.grad001.ThaboThobakgale.model.RecyclingTip;
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
public class RecyclingTipsTest {

    private ConfigurableApplicationContext context;
    @Autowired
    private JdbcTemplate template;
    private final String URL = "http://localhost:8080";

    private final RowMapper<RecyclingTip> mapper = (rs, rowNum) ->
            new RecyclingTip(rs.getInt(1), rs.getString(2));

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
    public void testGetAllTips(){
        HttpResponse<JsonNode> response = Unirest.get(URL+"/recyclingtips").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(0, response.getBody().getArray().length());

        RecyclingTip newTip = new RecyclingTip("something");
        addToDb(newTip);
        response = Unirest.get(URL+"/recyclingtips").asJson();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1, response.getBody().getArray().length());
        assertEquals(1, response.getBody().getArray().getJSONObject(0).getInt("id"));
        assertEquals(newTip.getTip(), response.getBody().getArray().getJSONObject(0).getString("tip"));


    }

    @Test
    public void testGetATip(){
        HttpResponse<JsonNode> response = Unirest.get(URL+"/recyclingtip/-1").asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        response = Unirest.get(URL+"/recyclingtip/0").asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        response = Unirest.get(URL+"/recyclingtip/1").asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        RecyclingTip newTip = new RecyclingTip("something");
        addToDb(newTip);
        response = Unirest.get(URL+"/recyclingtip/1").asJson();
        System.out.println(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(newTip.getTip(), response.getBody().getObject().getString("tip"));


    }

    @Test
    public void testAddTip(){
        RecyclingTip newTip = new RecyclingTip("something");
        HttpResponse<JsonNode> response = Unirest.post(URL+"/new/recyclingtip")
                .header("Content-Type", "application/json")
                .body(newTip)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1,response.getBody().getObject().getInt("id"));
        assertEquals(newTip.getTip(), response.getBody().getObject().getString("tip"));
        newTip.setId(1);
        assertTrue(isInTable(newTip));

        response = Unirest.post(URL+"/new/recyclingtip")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }

    @Test
    public void testUpdateATip(){
        RecyclingTip newTip = new RecyclingTip("something");
        RecyclingTip updatedTip = new RecyclingTip(1,"something new");
        addToDb(newTip);
        HttpResponse<JsonNode> response = Unirest.put(URL+"/update/recyclingtip")
                .header("Content-Type", "application/json")
                .body(updatedTip)
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(1,response.getBody().getObject().getInt("id"));
        assertEquals(updatedTip.getTip(), response.getBody().getObject().getString("tip"));
        assertTrue(isInTable(updatedTip));

        response = Unirest.put(URL+"/update/recyclingtip")
                .header("Content-Type", "application/json")
                .body("{}")
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        updatedTip.setId(2);
        response = Unirest.put(URL+"/update/recyclingtip")
                .header("Content-Type", "application/json")
                .body(updatedTip)
                .asJson();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

    }

    @Test
    public void testDeleteTip(){
        RecyclingTip newTip = new RecyclingTip(1,"something");
        HttpResponse<JsonNode> response = Unirest.delete(URL+"/delete/recyclingtip/1")
                .asJson();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

        addToDb(newTip);

        response = Unirest.delete(URL+"/delete/recyclingtip/1")
                .asJson();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertFalse(isInTable(newTip));

    }

    private void clearTable(){
        String sql = "TRUNCATE TABLE RECYCLING_TIPS RESTART IDENTITY";
        template.update(sql);
    }

    private void addToDb(RecyclingTip tip){
        String sql = "INSERT INTO RECYCLING_TIPS(TIP) VALUES (?)";
        template.update(sql, tip.getTip());
    }

    private RecyclingTip getTip(int id){
        String sql = "SELECT * FROM RECYCLING_TIPS WHERE ID = ?";
        return template.queryForObject(sql,mapper,id);
    }

    private boolean isInTable(RecyclingTip tip){
        try{
            return getTip(tip.getId()).equals(tip);
        } catch (EmptyResultDataAccessException x){
            return false;
        }
    }

}
