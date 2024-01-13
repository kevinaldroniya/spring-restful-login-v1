package spring.restful.login.v1.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.restful.login.v1.payload.CategoryDto;
import spring.restful.login.v1.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto addCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(addCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> allCategories = categoryService.getAllCategories();
//        return new ResponseEntity<>(allCategories, HttpStatus.OK);
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId")Long categoryId){
        CategoryDto category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);

    }


}
