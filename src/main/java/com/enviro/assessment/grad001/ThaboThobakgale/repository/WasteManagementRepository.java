package com.enviro.assessment.grad001.ThaboThobakgale.repository;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers.GuidelineMapper;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers.MaterialMapper;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.mappers.WasteCategoryMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WasteManagementRepository {

    private final JdbcTemplate template;

    public WasteManagementRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<WasteCategory> getWasteCategories(){
        String sql = "SELECT * FROM WASTE_CATEGORIES";
        return template.query(sql, new WasteCategoryMapper());
    }

    public int addWasteCategory(WasteCategory category){
        String sql = "INSERT INTO WASTE_CATEGORIES(NAME) VALUES (?)";
        return template.update(sql, category.getName());
    }

    public int updateWasteCategory(WasteCategory wasteCategory) {
        String sql = "UPDATE WASTE_CATEGORIES SET NAME = ? WHERE ID = ?";
        return template.update(sql, wasteCategory.getName(), wasteCategory.getId());
    }

    public int deleteWasteCategory(int id) {
        String sql = "DELETE FROM WASTE_CATEGORIES WHERE ID = ?";
        return template.update(sql, id);
    }

    public List<Material> getMaterials(){
        String sql = "SELECT" +
                " MATERIALS.ID, WASTE_CATEGORIES.NAME AS CATEGORY, MATERIALS.NAME" +
                " FROM MATERIALS JOIN WASTE_CATEGORIES" +
                " ON MATERIALS.WASTE_ID = WASTE_CATEGORIES.ID";
        return template.query(sql, new MaterialMapper());
    }

    public Optional<WasteCategory> getWasteCategoryByName(String name){
        String sql = "SELECT * FROM WASTE_CATEGORIES WHERE NAME = ?";
        try{
            return Optional.of(template.queryForObject(sql, new WasteCategoryMapper(),name));
        }catch (EmptyResultDataAccessException x){
            return Optional.empty();
        }
    }

    public int addMaterial(int wasteId, String name){
        String sql = "INSERT INTO MATERIALS(WASTE_ID,NAME) VALUES (?,?)";
        return template.update(sql,wasteId, name);
    }

    public int updateMaterial(Material material, int waste_id) {
        String sql = "UPDATE MATERIALS SET NAME = ?, WASTE_ID = ? WHERE ID = ?";
        return template.update(sql, material.getName(),waste_id,material.getId());
    }

    public int deleteMaterial(int id){
        String sql = "DELETE FROM MATERIALS WHERE ID = ?";
        return template.update(sql,id);
    }

    public Optional<Material> getMaterialByName(String material){
        String sql = "SELECT" +
                " MATERIALS.ID, WASTE_CATEGORIES.NAME AS CATEGORY, MATERIALS.NAME" +
                " FROM MATERIALS JOIN WASTE_CATEGORIES" +
                " ON MATERIALS.WASTE_ID = WASTE_CATEGORIES.ID " +
                "WHERE MATERIALS.NAME = ?";
        try{
            return Optional.of(template.queryForObject(sql,new MaterialMapper(),material));
        }catch (EmptyResultDataAccessException x){
            return Optional.empty();
        }
    }

    public List<Guideline> getMaterialGuideLines(String material) {
        String sql = "SELECT GUIDELINES.ID, MATERIALS.NAME AS MATERIAL_NAME, " +
                "GUIDELINES.GUIDELINE " +
                "FROM GUIDELINES JOIN MATERIALS ON " +
                "GUIDELINES.MATERIAL_ID = MATERIALS.ID " +
                "WHERE MATERIALS.NAME = ?";
        return template.query(sql,new GuidelineMapper(), material);
    }

    public List<Guideline> getAllGuideLines() {
        String sql = "SELECT GUIDELINES.ID, MATERIALS.NAME AS MATERIAL_NAME, " +
                "GUIDELINES.GUIDELINE " +
                "FROM GUIDELINES JOIN MATERIALS ON " +
                "GUIDELINES.MATERIAL_ID = MATERIALS.ID ";
        return template.query(sql,new GuidelineMapper());
    }

    public int addGuideline(int materialId, String guideline){
        String sql = "INSERT INTO GUIDELINES(MATERIAL_ID,GUIDELINE) VALUES (?,?)";
        return template.update(sql,materialId, guideline);
    }

    public int updateGuideline(int materialId, Guideline guideline){
        String sql = "UPDATE GUIDELINES SET GUIDELINE = ?, MATERIAL_ID = ? WHERE ID = ?";
        return template.update(sql,guideline.getGuideline(), materialId, guideline.getId());
    }

    public int deleteGuideline(int materialId){
        String sql = "DELETE FROM GUIDELINES WHERE ID = ?";
        return template.update(sql,materialId);
    }

}
