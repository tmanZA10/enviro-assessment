package com.enviro.assessment.grad001.ThaboThobakgale.services;

import com.enviro.assessment.grad001.ThaboThobakgale.model.Guideline;
import com.enviro.assessment.grad001.ThaboThobakgale.model.Material;
import com.enviro.assessment.grad001.ThaboThobakgale.model.WasteCategory;
import com.enviro.assessment.grad001.ThaboThobakgale.repository.WasteManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.addWasteCategory(wasteCategoryNoId);
    }

    public int updateWasteCategory(WasteCategory wasteCategory) {
        return repository.updateWasteCategory(wasteCategory);
    }

    public int deleteWasteCategory(int id) {
        return repository.deleteWasteCategory(id);

    }

    public WasteCategory getWasteCategory(String name){
        return repository.getWasteCategoryByName(name);
    }

    public List<Material> getMaterials(){
        return repository.getMaterials();
    }

    public int addMaterial(Material material){
        WasteCategory waste = repository.getWasteCategoryByName(material.getWaste_category());
        return repository.addMaterial(waste.getId(),material.getName());

    }

    public int updateMaterial(Material material) {
        WasteCategory category = repository.getWasteCategoryByName(material.getWaste_category());
        return repository.updateMaterial(material, category.getId());
    }

    public int deleteMaterial(int id){
        return repository.deleteMaterial(id);
    }

    public Material lookUpMaterial(String material){
        return repository.getMaterialByName(material);
    }


    public List<Guideline> getMaterialGuidelines(String material) {
        return repository.getMaterialGuideLines(material);
    }

    public List<Guideline> getAllGuidelines() {
        return repository.getAllGuideLines();
    }

    public int addGuideline(Guideline guideline){
        Material material = repository.getMaterialByName(guideline.getMaterial());
        return repository.addGuideline(material.getId(),guideline.getGuideline());

    }

    public int updateGuideline(Guideline guideline) {
        Material material = repository.getMaterialByName(guideline.getMaterial());
        return repository.updateGuideline(material.getId(), guideline);
    }

    public int deleteGuideline(int id){
        return repository.deleteGuideline(id);
    }


}
