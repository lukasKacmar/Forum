package sk.tsystems.forum.service.section;

import sk.tsystems.forum.entity.Section;

import java.util.List;

public interface SectionService {

    void addSection(Section section);

    Section getSection(long id);

    List<Section> getSections();
}
