package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.WasteManagementRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WasteManagementService {

    private final WasteManagementRepository repository;

    public WasteManagementService(WasteManagementRepository repository) {
        this.repository = repository;
    }
    public List<WasteCategory> getWasteCategories(){
        return repository.getWasteCategories();
    }

    public int addWasteCategory(WasteCategory wasteCategoryNoId){
        try{
            return repository.addWasteCategory(wasteCategoryNoId);
        }catch (DataAccessException x){
            return 0;
        }
    }

    public int updateWasteCategory(WasteCategory wasteCategory) {
        return repository.updateWasteCategory(wasteCategory);
    }

    public int deleteWasteCategory(int id) {
        return repository.deleteWasteCategory(id);

    }

    public Optional<WasteCategory> getWasteCategory(String name){
        return repository.getWasteCategoryByName(name);
    }

    public List<Material> getMaterials(){
        return repository.getMaterials();
    }

    public int addMaterial(Material material){
        Optional<WasteCategory> waste = repository.getWasteCategoryByName(material.getWaste_category());
        if (waste.isEmpty()) return -1;
        try{
            return repository.addMaterial(waste.get().getId(), material.getName());
        }catch (DataAccessException x){
            return 0;
        }

    }

    public int updateMaterial(Material material) {
        Optional<WasteCategory> category = repository.getWasteCategoryByName(material.getWaste_category());
        if (category.isEmpty()) return 0;
        return repository.updateMaterial(material, category.get().getId());
    }

    public int deleteMaterial(int id){
        return repository.deleteMaterial(id);
    }

    public Optional<Material> lookUpMaterial(String material){
        return repository.getMaterialByName(material);
    }


    public List<Guideline> getMaterialGuidelines(String material) {
        return repository.getMaterialGuideLines(material);
    }

    public List<Guideline> getAllGuidelines() {
        return repository.getAllGuideLines();
    }

    public int addGuideline(Guideline guideline){
        Optional<Material> material = repository.getMaterialByName(guideline.getMaterial());
        if (material.isEmpty()) return -1;
        return repository.addGuideline(material.get().getId(),guideline.getGuideline());

    }

    public int updateGuideline(Guideline guideline) {
        Optional<Material> material = repository.getMaterialByName(guideline.getMaterial());
        if (material.isEmpty()) return 0;
        return repository.updateGuideline(material.get().getId(), guideline);
    }

    public int deleteGuideline(int id){
        return repository.deleteGuideline(id);
    }

    public Guideline getGuideline(String guideline){
        return repository.getGuideLine(guideline).get();
    }


}
