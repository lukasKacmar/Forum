package sk.tsystems.forum.service.section;

import sk.tsystems.forum.entity.Category;
import sk.tsystems.forum.entity.Section;

import java.util.List;

public interface SectionService {

    void addSection(Section section);

    void updateSection(Section section);

    void addCategory(Category category);

    void deleteCategory(long id);

    Section getSection(long id);

    Category getCategory(long id);

    List<Section> getSections();

    List<Section> getSections(Category category);

    List<Category> getCategories();

    void deleteSection(long id);
}
