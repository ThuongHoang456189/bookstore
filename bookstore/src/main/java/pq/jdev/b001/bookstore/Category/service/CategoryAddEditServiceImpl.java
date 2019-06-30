package pq.jdev.b001.bookstore.Category.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.repository.CategoryAddEditRepository;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;
@Service
public class CategoryAddEditServiceImpl implements CategoryAddEditService{
	@Autowired
	private CategoryAddEditRepository categoryrepository;
	@Override
	public Category save(CategoryWeb categoryweb) {
		Category category = new Category();
		category.setName(categoryweb.getName());
		category.setCreatedate(new Date(Calendar.getInstance().getTime().getTime()));
		category.setUpdatedate(new Date(Calendar.getInstance().getTime().getTime()));
		return categoryrepository.save(category);
	}




}
