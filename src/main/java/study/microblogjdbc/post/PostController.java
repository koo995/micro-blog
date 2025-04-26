package study.microblogjdbc.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.microblogjdbc.post.dto.PostDetailDto;
import study.microblogjdbc.post.dto.PostWithCommentCount;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String list(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                      Model model) {
        Page<PostWithCommentCount> postPage = postService.getPostsWithCommentCount(pageable);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);
        return "post_list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable(name = "id") Long id, Model model) {
        PostDetailDto postDetailDto = postService.getPostDetail(id);
        model.addAttribute("postDetail", postDetailDto);
        return "post_detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new Post());
        return "post_form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        postService.createPost(post.getTitle(), post.getContent());
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable(name = "id") Long id, Model model) {
        Post post = postService.getPostDetail(id).toPost();
        model.addAttribute("post", post);
        return "post_form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable(name = "id") Long id,
                        @Valid @ModelAttribute Post post,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        postService.updatePost(id, post.getTitle(), post.getContent());
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}