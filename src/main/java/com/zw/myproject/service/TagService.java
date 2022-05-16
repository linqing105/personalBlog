package com.zw.myproject.service;

import com.zw.myproject.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer  size);
    Tag updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);
}
