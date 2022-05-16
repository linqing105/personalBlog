package com.zw.myproject.controller.admin;

import com.zw.myproject.pojo.Blog;
import com.zw.myproject.pojo.User;
import com.zw.myproject.service.BlogService;
import com.zw.myproject.service.TagService;
import com.zw.myproject.service.TypeService;
import com.zw.myproject.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static String INPUT="admin/blog-input";
    private static String LIST="admin/blog-admin";
    private static String REDIRECT_LIST="redirect:/admin/blog-admin";
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blog-admin")
    public String blogs(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog ));

        return LIST;

    }

    @PostMapping("/blog-admin/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
//        返回blog-admin页面下的blogList，实现局部渲染
        return "admin/blog-admin :: blogList";

    }

    // 新增博客功能
    @GetMapping("/blog-admin/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTag(Model model) {
        // model.addAttribute("flags", flagService.listFlag());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    // 修改博客功能
    @Transactional
    @GetMapping("/blog-admin/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init(); // convert tag list to string for html display, not tagIds is Transient type
        model.addAttribute("blog", blog);
        return INPUT;
    }



    @PostMapping("/blog-admin")
    public String post(Blog blog, RedirectAttributes attributes,HttpSession session){
//        更新和新增共用一个方法
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTagList(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else {
            b=blogService.updateBlog(blog.getId(),blog);
        }

        if(b==null){
//            如果没有保存成功
            attributes.addFlashAttribute("message","操作失败×");
        }else {
            attributes.addFlashAttribute("message","操作成功√");
        }
//th:value="${blog?.}"
        return REDIRECT_LIST;
    }

    @GetMapping("/blog-admin/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBolg(id);
        attributes.addFlashAttribute("message","删除成功√");
        return REDIRECT_LIST;
    }


}
