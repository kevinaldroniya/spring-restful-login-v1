package spring.restful.login.v1.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.restful.login.v1.entity.Category;
import spring.restful.login.v1.exception.ResourceNotFoundException;
import spring.restful.login.v1.payload.CategoryDto;
import spring.restful.login.v1.repository.CategoryRepository;
import spring.restful.login.v1.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto mapped = modelMapper.map(savedCategory, CategoryDto.class);
        return mapped;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", categoryId)
        );

        CategoryDto mapped = modelMapper.map(category, CategoryDto.class);

        return mapped;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }
}
